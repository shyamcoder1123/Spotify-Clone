package com.example.newspotifyclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.model.SongModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongItemRecyclerAdapter extends RecyclerView.Adapter<SongItemRecyclerAdapter.ViewHolder> {

    private ArrayList<SongModel> categoryModelArrayList;
    private Context context;
    CategoryInterface categoryInterface;

    public SongItemRecyclerAdapter(ArrayList<SongModel> categoryModelArrayList, Context context, CategoryInterface categoryInterface) {
        this.categoryModelArrayList = categoryModelArrayList;
        this.context = context;
        this.categoryInterface = categoryInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongModel songModel = categoryModelArrayList.get(position);
        int songLength = songModel.getSongName().length();
        String songString = songLength<20?songModel.getSongName()
                :songModel.getSongName().substring(0,20)+"...";

        int artistLength = songModel.getArtistName().length();
        String artistString = artistLength<20?songModel.getArtistName()
                :songModel.getArtistName().substring(0,10)+"...";
//        holder.songName.setText(songString);
        holder.artistName.setText(artistString);

        Picasso.get().load(songModel.getSongImage()).centerCrop().resize(150,150).into(holder.songImage);

    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public interface CategoryInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView songName;
        TextView artistName;
        ImageView songImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            songName = itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.artistName);
            songImage=itemView.findViewById(R.id.songImage);
        }
    }
}
