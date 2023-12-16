package com.example.newspotifyclone.view;

import static com.example.newspotifyclone.helper.ApplicationClass.ACTION_NEXT;
import static com.example.newspotifyclone.helper.ApplicationClass.ACTION_PLAY;
import static com.example.newspotifyclone.helper.ApplicationClass.ACTION_PREVIOUS;
import static com.example.newspotifyclone.helper.ApplicationClass.CHANNEL_ID_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.newspotifyclone.MusicPlayerClickHandler;
import com.example.newspotifyclone.R;
import com.example.newspotifyclone.databinding.ActivityMusicPlayerBinding;
import com.example.newspotifyclone.helper.ActionPlaying;
import com.example.newspotifyclone.helper.NotificationReciever;
import com.example.newspotifyclone.helper.RandomLinearGradient;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity implements ActionPlaying, ServiceConnection, NotificationReciever.ActionClicked {
    ActivityMusicPlayerBinding activityMusicPlayerBinding;
    MusicPlayerClickHandler musicPlayerClickHandler;
    private static final String TAG = "MusicPlayerActivity";
    boolean isPlaying=false;
    private MediaPlayer mediaPlayer;
    String trackPreviewURL,trackName,artistName,albumImageURL,albumName,trackString,artistString;
    ProgressBar trackProgressBar;
    ImageView previous,next,playPause,imageView,trackImageView,customBack;

    TextView trackNameTextView,artistNameTextView,titleMusicPlayerView;

    long trackImageColor;
    int position;
    IndividualAlbumModel.Tracks tracksRecieverObject;
    View musicControlLayout;
    Button lyricsButtonView;
    SeekBar seekBar;
    Thread updateSeekbar;
    MediaSessionCompat mediaSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        activityMusicPlayerBinding = DataBindingUtil.setContentView(this, R.layout.activity_music_player);

        musicPlayerClickHandler = new MusicPlayerClickHandler(this);
        activityMusicPlayerBinding.setMyClickHandlerLikeClick(musicPlayerClickHandler);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        mediaSession=new MediaSessionCompat(this,"tag");
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(MusicPlayerActivity.this,
                    Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MusicPlayerActivity.this,new String[]
                        {Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }
        mediaSession = new MediaSessionCompat(this,"playerAudio");
        customBack=activityMusicPlayerBinding.customBack;
        imageView = activityMusicPlayerBinding.heartImageView;

        trackProgressBar = activityMusicPlayerBinding.trackProgress;
        trackProgressBar.setVisibility(View.VISIBLE);


//        musicControlLayout=findViewById(R.id.musicController);
        musicControlLayout = activityMusicPlayerBinding.musicController;

        seekBar = musicControlLayout.findViewById(R.id.seekBar);

        previous = musicControlLayout.findViewById(R.id.previous);
        playPause = musicControlLayout.findViewById(R.id.play_pause);
        next = musicControlLayout.findViewById(R.id.next);


//        trackNameTextView=findViewById(R.id.trackNameMusicPlayer);
        trackNameTextView=activityMusicPlayerBinding.trackNameMusicPlayer;
        artistNameTextView=activityMusicPlayerBinding.artistNameMusicPlayer;
        trackImageView = activityMusicPlayerBinding.songImageMusicPlayer;
        titleMusicPlayerView= activityMusicPlayerBinding.titleMusicPlayer;
        lyricsButtonView= activityMusicPlayerBinding.lyricsMusicPlayer;


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        albumName=getIntent().getStringExtra("album name");
        trackPreviewURL = getIntent().getStringExtra("preview_url");
        trackName = getIntent().getStringExtra("track name");
        artistName = getIntent().getStringExtra("artist name");
        albumImageURL=getIntent().getStringExtra("albumImage");
        tracksRecieverObject = (IndividualAlbumModel.Tracks) getIntent().getSerializableExtra("These are the tracks");
        trackImageColor = getIntent().getLongExtra("track Color Filter",0);


        try {
            showNotification();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        customBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        playAudio(trackPreviewURL);
        trackImageView.setColorFilter((int)(trackImageColor));
        lyricsButtonView.setBackgroundColor((int)trackImageColor);

        titleMusicPlayerView.setText("Playing From "+albumName);
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

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playClicked();
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
                prevClicked();
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=0){
                    position-=1;
                }else {
                    position= tracksRecieverObject.getItems().size()-1;
                }
                RandomLinearGradient.setRandomLinearGradient(trackImageView);
                trackImageColor=RandomLinearGradient.getGradientColors();
                lyricsButtonView.setBackgroundColor((int)trackImageColor);
                String previewURL=tracksRecieverObject.getItems().get(position).getPreview_url();
                trackName=tracksRecieverObject.getItems().get(position).getName();
                artistName=tracksRecieverObject.getItems().get(position).getArtists().get(0).getName();
                playAudio(previewURL);
                seekBar.setMax(mediaPlayer.getDuration());
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClicked();
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=tracksRecieverObject.getItems().size()-1){
                    position+=1;
                }else {
                    position=0;
                }
                RandomLinearGradient.setRandomLinearGradient(trackImageView);
                trackImageColor=RandomLinearGradient.getGradientColors();
                lyricsButtonView.setBackgroundColor((int)trackImageColor);
                String previewURL=tracksRecieverObject.getItems().get(position).getPreview_url();
                seekBar.setProgress(0);
                trackName=tracksRecieverObject.getItems().get(position).getName();
                artistName=tracksRecieverObject.getItems().get(position).getArtists().get(0).getName();
                playAudio(previewURL);
                seekBar.setMax(mediaPlayer.getDuration());
            }
        });

    }

    public void playAudio(String previewURL){
        int trackLength = trackName.length();
        trackString = trackLength<25?trackName
                :trackName.substring(0,25)+"...";

        int artistLength = artistName.length();
        artistString = artistLength<25?artistName
                :artistName.substring(0,25)+"...";

        artistNameTextView.setText(artistString);
        trackNameTextView.setText(trackString);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
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
        else if(mediaPlayer!=null && mediaPlayer.isPlaying()){

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

    @Override
    public void nextClicked() {
        if(position==3){
            position=0;
        }else position++;
    }

    @Override
    public void prevClicked() {
        if(position==0){
            position=3;
        }else position--;
    }

    @Override
    public void playClicked() {
        isPlaying=!isPlaying;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public void showNotification() throws IOException {
        Intent i = new Intent(this, MusicPlayerActivity.class);
        i.putExtra("album name",albumName);
        i.putExtra("preview_url",trackPreviewURL);
        i.putExtra("track name",trackName);
        i.putExtra("artist name",artistName);
        i.putExtra("albumImage",albumImageURL);
        i.putExtra("These are the tracks",tracksRecieverObject);
        i.putExtra("track Color Filter",trackImageColor);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent prevIntent = new Intent(this, NotificationReciever.class).setAction(ACTION_PREVIOUS);
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this, 1, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(this, NotificationReciever.class).setAction(ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 2, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, NotificationReciever.class);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 3, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int playIcon = isPlaying ? R.drawable.play_black : R.drawable.play_f;
        String playActionLabel = isPlaying ? "Pause" : "Play";
//        Log.d(TAG, "showNotification: "+albumImageURL);
        if(albumImageURL!=null){
            Picasso.get()
                    .load(albumImageURL)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Notification notification = new NotificationCompat.Builder(MusicPlayerActivity.this, CHANNEL_ID_2)
                                    .setSmallIcon(R.drawable.spotify1)
                                    .setLargeIcon(bitmap)
                                    .setContentTitle("Currently playing " + trackName).setContentText("Go to music player for more information")
                                    .addAction(R.drawable.left_black, "previous", prevPendingIntent)
                                    .addAction(playIcon, playActionLabel, playPendingIntent)
                                    .addAction(R.drawable.right_black, "next", nextPendingIntent)
                                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                            .setShowActionsInCompactView()
                                            .setMediaSession(mediaSession.getSessionToken()))
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setContentIntent(contentIntent)
                                    .setOnlyAlertOnce(true)
                                    .build();

                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(0, notification);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }

    }

    @Override
    public void onActionClicked(String action) {
        if(action.equals(ACTION_PREVIOUS)){
            if(position!=0){
                position-=1;
            }else {
                position= tracksRecieverObject.getItems().size()-1;
            }
            RandomLinearGradient.setRandomLinearGradient(trackImageView);
            trackImageColor=RandomLinearGradient.getGradientColors();
            lyricsButtonView.setBackgroundColor((int)trackImageColor);
            String previewURL=tracksRecieverObject.getItems().get(position).getPreview_url();
            trackName=tracksRecieverObject.getItems().get(position).getName();
            artistName=tracksRecieverObject.getItems().get(position).getArtists().get(0).getName();
            playAudio(previewURL);
            seekBar.setMax(mediaPlayer.getDuration());
        } else if (action.equals(ACTION_NEXT)) {
            if(position!=tracksRecieverObject.getItems().size()-1){
                position+=1;
            }else {
                position=0;
            }
            RandomLinearGradient.setRandomLinearGradient(trackImageView);
            trackImageColor=RandomLinearGradient.getGradientColors();
            lyricsButtonView.setBackgroundColor((int)trackImageColor);
            String previewURL=tracksRecieverObject.getItems().get(position).getPreview_url();
            seekBar.setProgress(0);
            trackName=tracksRecieverObject.getItems().get(position).getName();
            artistName=tracksRecieverObject.getItems().get(position).getArtists().get(0).getName();
            playAudio(previewURL);
            seekBar.setMax(mediaPlayer.getDuration());
        }
    }
}