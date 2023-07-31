package com.example.newspotifyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    String trackPreviewURL;
    String trackName;
    String artistName;
    ProgressBar trackProgressBar;
    ImageView previous;
    ImageView next;
    ImageView playPause;

    TextView trackNameTextView;
    TextView artistNameTextView;

    View musicControlLayout;

    SeekBar seekBar;
    Thread updateSeekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        trackProgressBar=findViewById(R.id.trackProgress);
        trackProgressBar.setVisibility(View.VISIBLE);


        musicControlLayout=findViewById(R.id.musicController);
        seekBar = musicControlLayout.findViewById(R.id.seekBar);
        previous = musicControlLayout.findViewById(R.id.previous);
        playPause = musicControlLayout.findViewById(R.id.play_pause);
        next = musicControlLayout.findViewById(R.id.next);

        trackNameTextView=findViewById(R.id.trackNameMusicPlayer);
        artistNameTextView=findViewById(R.id.artistNameMusicPlayer);


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        trackPreviewURL = getIntent().getStringExtra("preview_url");
        trackName = getIntent().getStringExtra("track name");
        artistName = getIntent().getStringExtra("artist name");

        Log.e("This is the track",artistNameTextView+"");

        artistNameTextView.setText(artistName);
        trackNameTextView.setText(trackName);

        playAudio(trackPreviewURL);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        updateSeekbar = new Thread(){
            @Override
            public void run() {
                try {
                    while (mediaPlayer!=null){
                        if(mediaPlayer.isPlaying()){
                            int currentPosition=mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(currentPosition);
                        }
                        sleep(800);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        updateSeekbar.start();

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();

            }
        });

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playPause.setImageResource(R.drawable.play_f);
                }else {
                    mediaPlayer.start();
                    playPause.setImageResource(R.drawable.pause_1);
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();

            }
        });

    }
    public void playAudio(String previewURL){
        if(mediaPlayer!=null&& !mediaPlayer.isPlaying()){
            try{
                mediaPlayer.setDataSource(previewURL);
                mediaPlayer.prepareAsync();
            }catch (IOException e){
                e.printStackTrace();
            }
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    trackProgressBar.setVisibility(View.GONE);
                    mediaPlayer.start();
                }
            });
        }
        else if(mediaPlayer.isPlaying()){
            mediaPlayer.reset();

            try{
                mediaPlayer.setDataSource(previewURL);
                mediaPlayer.prepareAsync();
            }catch (IOException e){
                e.printStackTrace();
            }
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Intent i = new Intent(MusicPlayerActivity.this, MusicPlayerActivity.class);
                    startActivity(i);
                    mediaPlayer.start();
                }
            });
        }

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
            updateSeekbar.interrupt();
        }
    }
}