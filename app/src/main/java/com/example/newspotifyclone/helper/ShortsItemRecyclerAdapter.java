package com.example.newspotifyclone.helper;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.model.VideoObjectForShorts;

import java.util.ArrayList;

public class ShortsItemRecyclerAdapter extends RecyclerView.Adapter<ShortsItemRecyclerAdapter.ViewHolder> {
    ArrayList<VideoObjectForShorts> videoObjectsForShorts;
    Context context;

    public ShortsItemRecyclerAdapter(ArrayList<VideoObjectForShorts> videoObjectsForShorts, Context context) {
        this.videoObjectsForShorts = videoObjectsForShorts;
        this.context = context;
    }

    @NonNull
    @Override
    public ShortsItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shorts_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShortsItemRecyclerAdapter.ViewHolder holder, int position) {
        VideoObjectForShorts videoObjectForShorts = videoObjectsForShorts.get(position);
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.videoView.setClickable(false);
        holder.videoView.setFocusable(false);
        if(videoObjectForShorts.getLink()!=null){
            holder.videoView.setVideoPath(videoObjectForShorts.getLink());
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    holder.progressBar.setVisibility(View.GONE);
                    mediaPlayer.start();
                    float videoRatio = (float) mediaPlayer.getVideoWidth()/(float)mediaPlayer.getVideoHeight();
                    float screenRatio = (float) holder.videoView.getWidth()/(float)holder.videoView.getHeight();
                    float scale= videoRatio/screenRatio;
                    if(scale>=1f){
                        holder.videoView.setScaleX(scale);
                    }else{
                        holder.videoView.setScaleY(1f/scale);
                    }

                }
            });
            holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    mediaPlayer.start();
                }
            });
        }
        holder.likeForShorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("hgghjgy","hgguygyu");
                if(!videoObjectForShorts.isLike()){
                    holder.likeForShorts.setImageResource(R.drawable.like_clicked);
                    videoObjectForShorts.setLike(true);
                    videoObjectForShorts.setDislike(false);
                    holder.dislikeForShorts.setImageResource(R.drawable.dislike);
                }

                else {
                    holder.likeForShorts.setImageResource(R.drawable.like);
                    videoObjectForShorts.setLike(false);
                }
            }
        });
        holder.dislikeForShorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!videoObjectForShorts.isDislike()){
                    holder.dislikeForShorts.setImageResource(R.drawable.dislike_clicked);
                    videoObjectForShorts.setDislike(true);
                    videoObjectForShorts.setLike(false);
                    holder.likeForShorts.setImageResource(R.drawable.like);
                }
                else {
                    holder.dislikeForShorts.setImageResource(R.drawable.dislike);
                    videoObjectForShorts.setDislike(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoObjectsForShorts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        boolean like=false,dislike=false;
        SeekBar seekBar;
        ProgressBar progressBar;
        VideoView videoView;
        ImageView likeForShorts;
        ImageView dislikeForShorts;
        ImageView shareForShorts;
        ImageView commentForShorts;
        ImageView remixForShorts;
        ImageView audioForShorts;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            likeForShorts=itemView.findViewById(R.id.likeForShorts);
            dislikeForShorts=itemView.findViewById(R.id.dislikeForShorts);
            commentForShorts=itemView.findViewById(R.id.commentsForShorts);
            shareForShorts=itemView.findViewById(R.id.shareForShorts);
            remixForShorts=itemView.findViewById(R.id.remixForShorts);
            audioForShorts=itemView.findViewById(R.id.audioForShorts);
            videoView=itemView.findViewById(R.id.videoView);
            progressBar=itemView.findViewById(R.id.progressBarForShorts);
        }
    }
}
