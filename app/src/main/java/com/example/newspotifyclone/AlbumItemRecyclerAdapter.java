package com.example.newspotifyclone;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.AlbumModel.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumItemRecyclerAdapter extends RecyclerView.Adapter<AlbumItemRecyclerAdapter.ViewHolder> {

    private ArrayList<AlbumModel.Album> categoryModelArrayList;
    private Context context;
    CategoryInterface categoryInterface;


    public AlbumItemRecyclerAdapter(ArrayList<AlbumModel.Album> categoryModelArrayList, Context context, CategoryInterface categoryInterface) {
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
        AlbumModel.Album albumModel = categoryModelArrayList.get(position);
        int albumLength = albumModel.getName().length();
        Log.e("This is onBind AlbumRecycler",albumModel.getName()+"");
        String albumString = albumLength<13?albumModel.getName()
                :albumModel.getName().substring(0,13)+"...";

        int artistLength = albumModel.getArtists().size();
        String artistString = artistLength<13?albumModel.getArtists().get(0).getName()
                :albumModel.getArtists().get(0).getName().substring(0,13)+"...";
//        holder.albumName.setText(albumString);
        holder.artistName.setText(artistString);

        Picasso.get().load(albumModel.getImages().get(0).getUrl()).centerCrop()
                .resize(150,150).into(holder.albumImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categoryInterface!=null){
                    categoryInterface.onCategoryClick(holder.getAdapterPosition());
                    Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public interface CategoryInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView artistName;
        ImageView albumImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            albumName = itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.artistName);
            albumImage=itemView.findViewById(R.id.songImage);
        }
    }
}

