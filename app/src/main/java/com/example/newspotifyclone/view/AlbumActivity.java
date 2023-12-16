package com.example.newspotifyclone.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.databinding.ActivityAlbumBinding;
import com.example.newspotifyclone.helper.TrackInAlbumAdapter;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

public class AlbumActivity extends AppCompatActivity implements TrackInAlbumAdapter.TrackInterface {
    ActivityAlbumBinding activityAlbumBinding;
    RecyclerView trackItemRecycler;
    TrackInAlbumAdapter trackInAlbumAdapter;
    String albumName,albumImgUrl;
    IndividualAlbumModel.Tracks tracksRecieverObject;

    IndividualAlbumModel.Tracks.TrackIndividual selectedTrack;
    ImageView albumImageView;

    String trackPreviewUrklKey="preview_url";

    private MediaPlayer mediaPlayer;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        activityAlbumBinding = DataBindingUtil.setContentView(this,R.layout.activity_album);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        albumImgUrl = getIntent().getStringExtra("ImageUrl");

        albumImageView=activityAlbumBinding.albumImageInTracksView;
        Picasso.get().load(albumImgUrl).into(albumImageView);
        trackItemRecycler = activityAlbumBinding.tracksListAlbum;
        tracksRecieverObject = (IndividualAlbumModel.Tracks) getIntent().getSerializableExtra("These are the tracks");
        albumName = getIntent().getStringExtra("This is the album name");
        trackInAlbumAdapter = new TrackInAlbumAdapter(tracksRecieverObject.getItems(),this,this::onCategoryClick);

        trackItemRecycler.setAdapter(trackInAlbumAdapter);

        toolbar=activityAlbumBinding.toolbar;
        toolbar.setTitle(albumName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
////        collapsingToolbarLayout.setTitle("Hello");

        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

    }




    @Override
    public void onCategoryClick(int position, long itsColor) {
        String previewURLToMusicPlayer = tracksRecieverObject.getItems().get(position).getPreview_url();
        String trackNameToMusicPlayer = tracksRecieverObject.getItems().get(position).getName();
        String artistNameToMusicPlayer = tracksRecieverObject.getItems().get(position).getArtists()
                .get(0).getName();
        Intent i = new Intent(AlbumActivity.this, MusicPlayerActivity.class);
        if(previewURLToMusicPlayer==null || albumName==null ||artistNameToMusicPlayer==null){
            Toast.makeText(this,"Selected track doesn't have a preview URL",Toast.LENGTH_SHORT).show();
        }else {
            i.putExtra(trackPreviewUrklKey,previewURLToMusicPlayer);
            i.putExtra("track name",trackNameToMusicPlayer);
            i.putExtra("albumImage",albumImgUrl);
            i.putExtra("artist name",artistNameToMusicPlayer);
            i.putExtra("track Color Filter",itsColor);
            i.putExtra("album name", albumName);
            i.putExtra("These are the tracks",tracksRecieverObject);
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
    }


}