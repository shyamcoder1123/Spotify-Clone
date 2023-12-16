package com.example.newspotifyclone.helper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.model.VideoObjectForShorts;
import com.example.newspotifyclone.view.CommentPopUpFragment;
import com.example.newspotifyclone.view.MusicVisualizerView;
import com.example.newspotifyclone.view.MusicVisualizerView;

import java.util.List;

public class ShortsVideoAdapter extends RecyclerView.Adapter<ShortsVideoAdapter.VideoViewHolder>{
    List<VideoObjectForShorts> videoObjectsForShorts;
    CommentInterface commentInterface;
    static Thread updateSeekbar;

    public ShortsVideoAdapter(List<VideoObjectForShorts> videoObjectsForShorts,CommentInterface commentInterface) {
        this.videoObjectsForShorts = videoObjectsForShorts;
        this.commentInterface=commentInterface;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.shorts_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setVideoData(videoObjectsForShorts.get(position),commentInterface);
        holder.commentForShorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentInterface.onCommentClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoObjectsForShorts.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        SeekBar seekBar;
        TextView channelNameTextView,contentTextView;
        ProgressBar progressBar;
        VideoView videoView;
        ImageView likeForShorts,dislikeForShorts,shareForShorts,commentForShorts,remixForShorts,
                audioForShorts;

        View playPause;
        boolean isPlaying=true;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            likeForShorts=itemView.findViewById(R.id.likeForShorts);
            dislikeForShorts=itemView.findViewById(R.id.dislikeForShorts);
            commentForShorts=itemView.findViewById(R.id.commentsForShorts);
            shareForShorts=itemView.findViewById(R.id.shareForShorts);
            remixForShorts=itemView.findViewById(R.id.remixForShorts);
            audioForShorts=itemView.findViewById(R.id.audioForShorts);
            videoView=itemView.findViewById(R.id.videoView);
            progressBar=itemView.findViewById(R.id.progressBarForShorts);
            seekBar=itemView.findViewById(R.id.seekBarForShorts);
            channelNameTextView=itemView.findViewById(R.id.channelNameTextView);
            contentTextView=itemView.findViewById(R.id.contentTextView);
            playPause =itemView.findViewById(R.id.play_pauseForShorts);
            progressBar.setVisibility(View.VISIBLE);
        }
        void setVideoData(VideoObjectForShorts videoObjectForShorts,CommentInterface commentInterface){
            if(videoObjectForShorts.getLink()!=null){
                videoView.setVideoPath(videoObjectForShorts.getLink());
                channelNameTextView.setText(videoObjectForShorts.getChannelName());
                contentTextView.setText(videoObjectForShorts.getContent());
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        seekBar.setMax(videoView.getDuration());
                        progressBar.setVisibility(View.GONE);
                        mediaPlayer.start();
                        float videoRatio = (float) mediaPlayer.getVideoWidth()/(float)mediaPlayer.getVideoHeight();
                        float screenRatio = (float) videoView.getWidth()/(float)videoView.getHeight();
                        float scale= videoRatio/screenRatio;
                        if(scale>=1f){
                            videoView.setScaleX(scale);
                        }else{
                            videoView.setScaleY(1f/scale);
                        }

                    }
                });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
                likeForShorts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!videoObjectForShorts.isLike()){
                            likeForShorts.setImageResource(R.drawable.like_clicked);
                            videoObjectForShorts.setLike(true);
                            videoObjectForShorts.setDislike(false);
                            dislikeForShorts.setImageResource(R.drawable.dislike);
                        }

                        else {
                            likeForShorts.setImageResource(R.drawable.like);
                            videoObjectForShorts.setLike(false);
                        }
                    }
                });
                dislikeForShorts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!videoObjectForShorts.isDislike()){
                            dislikeForShorts.setImageResource(R.drawable.dislike_clicked);
                            videoObjectForShorts.setDislike(true);
                            videoObjectForShorts.setLike(false);
                            likeForShorts.setImageResource(R.drawable.like);
                        }
                        else {
                            dislikeForShorts.setImageResource(R.drawable.dislike);
                            videoObjectForShorts.setDislike(false);
                        }
                    }
                });


                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        videoView.seekTo(seekBar.getProgress());
                        Log.e("hghgh",seekBar.getProgress()+"");
                    }
                });
                updateSeekbar = new Thread(){
                    @Override
                    public void run() {
                        try {
                            while (videoView!=null){
                                if(videoView.isPlaying()){
                                    int currentPosition=videoView.getCurrentPosition();
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

                shareForShorts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.putExtra(Intent.EXTRA_TEXT,videoObjectForShorts.getLink());
                        i.setType("text/plain");

                        Intent shareIntent = Intent.createChooser(i, null);
                        itemView.getContext().startActivity(shareIntent);
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isPlaying){
                            videoView.pause();
                            playPause.setVisibility(View.VISIBLE);
                            isPlaying=false;
                        }else {
                            videoView.start();
                            playPause.setVisibility(View.INVISIBLE);
                            isPlaying=true;
                        }

                    }
                });

            }
        }
    }
    public interface CommentInterface{
        void onCommentClick();
    }
}
