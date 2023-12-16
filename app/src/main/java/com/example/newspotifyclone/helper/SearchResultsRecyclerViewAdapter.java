package com.example.newspotifyclone.helper;

import android.content.Context;
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

public class SearchResultsRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ArtistModels.Artist> searchResultsArrayListArtist;
    private ArrayList<AlbumModel.Album> searchResultsArrayListAlbum;
    private ArrayList<IndividualAlbumModel.Tracks.TrackIndividual>
            searchResultsArrayListTrack;
    private Context context;
    String type;
    int i=0;
    private SearchResultItemInterface searchResultItemInterface;

    public SearchResultsRecyclerViewAdapter(ArrayList<ArtistModels.Artist>
            searchResultsArrayListArtist, ArrayList<AlbumModel.Album>
            searchResultsArrayListAlbum, ArrayList<IndividualAlbumModel.Tracks.TrackIndividual>
            searchResultsArrayListTrack, Context context, SearchResultItemInterface
            searchResultItemInterface, String type) {
        this.searchResultsArrayListArtist = searchResultsArrayListArtist;
        this.searchResultsArrayListAlbum = searchResultsArrayListAlbum;
        this.searchResultsArrayListTrack = searchResultsArrayListTrack;
        this.context = context;
        this.type=type;
        this.searchResultItemInterface = searchResultItemInterface;
    }

    @NonNull
    @Override
    public SearchResultsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(type.equals("all")){
            if(position<searchResultsArrayListArtist.size()){
                return 0;
            } else if (position<searchResultsArrayListAlbum.size()+searchResultsArrayListArtist.size()) {
                return 1;
            }else {
                return 2;
            }
        }
        else if(type.equals("album")){
            return 1;
        }
        return 10;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsRecyclerViewAdapter.ViewHolder holder, int position) {
        if(getItemViewType(position)==0){
            if(searchResultsArrayListArtist.size()!=0) {
                ArtistModels.Artist artist = searchResultsArrayListArtist.get(position);
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

            }
            i+=1;
        } else if (getItemViewType(position)==1) {
            if(searchResultsArrayListAlbum.size()!=0){
                AlbumModel.Album album = searchResultsArrayListAlbum.get(position-searchResultsArrayListArtist.size());
                int albumLength = album.getName().length();
                String albumString = albumLength<13?album.getName()
                        :album.getName().substring(0,13)+"...";
                holder.objectNameView.setText(albumString);
                holder.objectTypeView.setText("Album");
                try {
                    Picasso.get().load(album.getImages().get(0).getUrl())
                            .resize(90, 80).centerCrop().into(holder.objectImageView);
                }catch (Exception e){
                    RandomLinearGradient.setRandomLinearGradient(holder.objectImageView);
                    e.printStackTrace();
                }
            }
            i+=1;
        }else if(getItemViewType(position)==2){
            if(searchResultsArrayListTrack.size()!=0){
                IndividualAlbumModel.Tracks.TrackIndividual track = searchResultsArrayListTrack.get(position-(searchResultsArrayListArtist.size()+searchResultsArrayListAlbum.size()));
                int trackLength = track.getName().length();
                String trackString = trackLength<13?track.getName()
                        :track.getName().substring(0,13)+"...";
                holder.objectNameView.setText(trackString);
                holder.objectTypeView.setText("Track");
                try {
                    RandomLinearGradient.setRandomLinearGradient(holder.objectImageView);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else{
            throw new RuntimeException("No results found");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchResultItemInterface!=null){
                    if(getItemViewType(holder.getAdapterPosition())==0){
                        searchResultItemInterface.onCategoryClick(holder.getAdapterPosition(),0);
                    }
                    else if(getItemViewType(holder.getAdapterPosition())==1){
                        searchResultItemInterface.onCategoryClick(holder.getAdapterPosition()
                                - searchResultsArrayListArtist.size(),1);
                    }else {
                        searchResultItemInterface.onCategoryClick(holder.getAdapterPosition()
                                - (searchResultsArrayListArtist.size()+ searchResultsArrayListAlbum.size()),2);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(type.equals("all")) {
            return searchResultsArrayListArtist.size()+searchResultsArrayListAlbum.size()
                    +searchResultsArrayListTrack.size();
        }
        else if(type.equals("album")){
            return searchResultsArrayListAlbum.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView objectNameView;
        ImageView objectImageView;
        TextView objectTypeView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            objectNameView = itemView.findViewById(R.id.objectName);
            objectImageView = itemView.findViewById(R.id.objectImage);
            objectTypeView = itemView.findViewById(R.id.objectCategory);
        }
    }
    public interface SearchResultItemInterface{
        void onCategoryClick(int position,int itemViewType);
    }
}
