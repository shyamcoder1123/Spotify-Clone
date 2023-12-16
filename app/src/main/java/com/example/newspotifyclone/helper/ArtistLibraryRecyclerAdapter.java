package com.example.newspotifyclone.helper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.example.newspotifyclone.model.SearchResultArtists;
import com.example.newspotifyclone.model.SearchResultModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistLibraryRecyclerAdapter extends RecyclerView.Adapter<ArtistLibraryRecyclerAdapter.ViewHolder> {
    private ArrayList<ArtistModels.Artist> arrayListArtist;
    private Context context;
    private ArtistItemInterface artistItemInterface;

    public ArtistLibraryRecyclerAdapter(ArrayList<ArtistModels.Artist> arrayListArtist,
                                        Context context,
                                        ArtistLibraryRecyclerAdapter.ArtistItemInterface
                                                artistItemInterface) {
        this.arrayListArtist = arrayListArtist;
        this.context = context;
        this.artistItemInterface = artistItemInterface;
    }

    @NonNull
    @Override
    public ArtistLibraryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_card_library,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArtistModels.Artist artist = arrayListArtist.get(position);
        Log.e("size now",arrayListArtist.size()+"");
        if(artist.getId()!=null){
            int artistLength = artist.getName().length();
            String artistString = artistLength<13?artist.getName()
                    :artist.getName().substring(0,13)+"...";
            holder.objectNameView.setText(artistString);
            holder.objectTypeView.setText("Artist");
            try {
                Picasso.get().load(artist.getImages().get(0).getUrl())
                        .resize(90, 80).centerCrop().into(holder.objectImageView);
            }catch (Exception e){
                RandomLinearGradient.setRandomLinearGradient(holder.objectImageView);
                e.printStackTrace();
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    artistItemInterface.onCategoryClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    artistItemInterface.onCategoryLongClick(holder.getAdapterPosition());
                    return true;
                }
            });
        }
        else {
            holder.objectNameView.setText("Add artists");
            holder.objectTypeView.setText("");
            holder.objectImageView.setImageResource(R.drawable.plus_for_add);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    artistItemInterface.onAddArtistClick();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return arrayListArtist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView objectNameView;
        ImageView objectImageView;
        TextView objectTypeView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            objectNameView = itemView.findViewById(R.id.artistNameLibrary);
            objectImageView = itemView.findViewById(R.id.artistImageLibrary);
            objectTypeView = itemView.findViewById(R.id.artistCategoryLibrary);
        }
    }
    public interface ArtistItemInterface{
        void onCategoryClick(int position);
        void onCategoryLongClick(int position);
        void onAddArtistClick();
    }
}

