package com.example.newspotifyclone.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.model.ArtistModels;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistListRecyclerAdapter extends RecyclerView.Adapter<ArtistListRecyclerAdapter.ViewHolder> {
    ArrayList<ArtistModels.Artist> popularArtistArrayList;
    Context context;
    CategoryInterface categoryInterface;
    String type;

    public ArtistListRecyclerAdapter(ArrayList<ArtistModels.Artist> popularArtistArrayList,
                                     Context context, CategoryInterface categoryInterface, String type) {
        this.popularArtistArrayList = popularArtistArrayList;
        this.context = context;
        this.categoryInterface = categoryInterface;
        this.type = type;
    }

    @NonNull
    @Override
    public ArtistListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (type.equals("normal")) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_artist_for_add_artist, parent, false);
        } else if (type.equals("shimmer")) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_artists_layout_for_shimmer, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistListRecyclerAdapter.ViewHolder holder, int position) {
        ArtistModels.Artist artist = popularArtistArrayList.get(position);
        if(artist.getName()!=null){
            Log.e("names",artist.getName());
            holder.artistNameView.setText(artist.getName());
            try {
                Picasso.get().load(artist.getImages().get(0).getUrl()).resize(80, 80)
                        .centerCrop().into(holder.artistImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (artist.isSelected()) {
            holder.artistSelectedIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.artistSelectedIndicator.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryInterface.onCategoryClick(holder.getAdapterPosition());

            }
        });
        }else {
            holder.artistNameView.setText("Add artists");
            holder.artistImageView.setImageResource(R.drawable.plus_for_add);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryInterface.onAddArtistClick();
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return popularArtistArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView artistNameView;
        ImageView artistImageView;
        View artistSelectedIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artistNameView = itemView.findViewById(R.id.popularArtistName);
            artistImageView = itemView.findViewById(R.id.popularArtistImage);
            artistSelectedIndicator = itemView.findViewById(R.id.selectionItemForArtist);
        }
    }

    public interface CategoryInterface {
        void onCategoryClick(int position);
        void onAddArtistClick();
    }
}
