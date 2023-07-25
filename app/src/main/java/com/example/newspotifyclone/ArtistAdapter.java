package com.example.newspotifyclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.model.ArtistModel;
import com.example.newspotifyclone.model.ArtistModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private ArrayList<ArtistModel> artistModelArrayList;
    private Context context;
    private ArtistInterface artistInterface;

    public ArtistAdapter(ArrayList<ArtistModel> artistModelArrayList, Context context, ArtistInterface artistInterface){
        this.artistInterface=artistInterface;
        this.context=context;
        this.artistModelArrayList=artistModelArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArtistModel artistModel = artistModelArrayList.get(position);
        holder.artistNameView.setText(artistModel.getArtistName());
        Picasso.get().load(artistModel.getArtistImage())
                .into(holder.artistImageView);
    }

    @Override
    public int getItemCount() {
        return artistModelArrayList.size();
    }

    public interface ArtistInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView artistNameView;
        ImageView artistImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artistImageView = itemView.findViewById(R.id.artistImage);
            artistNameView = itemView.findViewById(R.id.artistName);
        }
    }
}
