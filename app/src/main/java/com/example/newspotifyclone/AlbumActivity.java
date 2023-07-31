package com.example.newspotifyclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

public class AlbumActivity extends AppCompatActivity implements TrackInAlbumAdapter.TrackInterface{
    RecyclerView trackItemRecycler;
    TrackInAlbumAdapter trackInAlbumAdapter;
    IndividualAlbumModel.Tracks tracksRecieverObject;

    IndividualAlbumModel.Tracks.TrackIndividual selectedTrack;
    String albumImgUrl;
    ImageView albumImageView;

    String trackPreviewUrklKey="preview_url";

    private MediaPlayer mediaPlayer;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);



        albumImgUrl = getIntent().getStringExtra("ImageUrl");

        albumImageView=findViewById(R.id.albumImageInTracksView);
        Picasso.get().load(albumImgUrl).into(albumImageView);
        trackItemRecycler = findViewById(R.id.tracksListAlbum);
        tracksRecieverObject = (IndividualAlbumModel.Tracks) getIntent().getSerializableExtra("These are the tracks");
        trackInAlbumAdapter = new TrackInAlbumAdapter(tracksRecieverObject.getItems(),this,this::onCategoryClick);

        trackItemRecycler.setAdapter(trackInAlbumAdapter);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Hello");


    }
//    https://api.spotify.com/v1/tracks/3QxKPcQR9bNCjZPycvZJCb




    @Override
    public void onCategoryClick(int position) {
        String previewURLToMusicPlayer = tracksRecieverObject.getItems().get(position).getPreview_url();
        String trackNameToMusicPlayer = tracksRecieverObject.getItems().get(position).getName();
        String artistNameToMusicPlayer = tracksRecieverObject.getItems().get(position).getArtists()
                .get(0).getName();
        Intent i = new Intent(AlbumActivity.this, MusicPlayerActivity.class);
        i.putExtra(trackPreviewUrklKey,previewURLToMusicPlayer);
        i.putExtra("track name",trackNameToMusicPlayer);
        i.putExtra("artist name",artistNameToMusicPlayer);
        startActivity(i);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}