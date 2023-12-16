package com.example.newspotifyclone.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newspotifyclone.ApiManager;
import com.example.newspotifyclone.R;
import com.example.newspotifyclone.databinding.ActivityArtistBinding;
import com.example.newspotifyclone.helper.SearchResultsRecyclerViewAdapter;
import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistActivity extends AppCompatActivity implements SearchResultsRecyclerViewAdapter.SearchResultItemInterface{

    ActivityArtistBinding activityArtistBinding;
    ApiManager apiManager;
    IndividualAlbumModel.Tracks tracksObject;
    ArrayList<AlbumModel.Album> albums;
    RecyclerView albumRecyclerView;
    ImageView artistImageView;
    Toolbar toolbar;
    SearchResultsRecyclerViewAdapter searchResultsRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        activityArtistBinding = DataBindingUtil.setContentView(this,R.layout.activity_artist);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        artistImageView=activityArtistBinding.artistImageInArtistActivityView;
        albumRecyclerView = activityArtistBinding.albumListRecycler;
        String imageURL= getIntent().getStringExtra("artistImageURL");
        String artistName = getIntent().getStringExtra("artist name");
        Picasso.get().load(imageURL)
                .resize(400, 310).centerCrop().into(artistImageView);
        albums = new ArrayList<>();
        searchResultsRecyclerViewAdapter = new SearchResultsRecyclerViewAdapter(new ArrayList<>(),albums,new ArrayList<>(),this,this::onCategoryClick,"album");

        toolbar=activityArtistBinding.toolbarArtist;
        toolbar.setTitle(artistName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_artist);

        apiManager= new ApiManager(this);
        String artistId = getIntent().getStringExtra("artist id");
        getArtistAlbumResult(artistId,albums);
    }
    public void  getArtistAlbumResult(String artistId, ArrayList<AlbumModel.Album> albums){
        apiManager.getArtistAlbumResult(artistId,new Callback<AlbumModel>() {
            @Override
            public void onResponse(Call<AlbumModel> call, Response<AlbumModel> response) {
                AlbumModel albumModel = response.body();
                if(albumModel!=null){
                    albums.addAll(albumModel.getItems());
                    albumRecyclerView.setAdapter(searchResultsRecyclerViewAdapter);
                    searchResultsRecyclerViewAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ArtistActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlbumModel> call, Throwable t) {
                t.printStackTrace();;
            }
        });
    }

    public void  getAlbumResultFunction(String albumId){
        apiManager.getAlbumResult(albumId, new Callback<IndividualAlbumModel>() {
            @Override
            public void onResponse(Call<IndividualAlbumModel> call, Response<IndividualAlbumModel> response) {
                IndividualAlbumModel individualAlbumModel = response.body();
                if(individualAlbumModel!=null){
                    tracksObject=individualAlbumModel.getTracks();

                    if(tracksObject==null || tracksObject.getItems().size()==0){
                        Toast.makeText(ArtistActivity.this,"Selected track doesn't have a preview URL",Toast.LENGTH_SHORT).show();
                    }else {
                        Intent i = new Intent(ArtistActivity.this, AlbumActivity.class);
                        i.putExtra("This is the album name",individualAlbumModel.getName());
                        i.putExtra("These are the tracks",tracksObject);
                        i.putExtra("ImageUrl",individualAlbumModel.getImages().get(0).getUrl());
                        startActivity(i);
                    }
                }
                else{
                    Toast.makeText(ArtistActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IndividualAlbumModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onCategoryClick(int position, int itemViewType) {
        String category = albums.get(position).getId();
        getAlbumResultFunction(category);
    }
}