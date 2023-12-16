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
import com.example.newspotifyclone.model.IndividualAlbumModel;

import java.util.ArrayList;

public class TrackInAlbumAdapter extends RecyclerView.Adapter<TrackInAlbumAdapter.ViewHolder> {


    private ArrayList<IndividualAlbumModel.Tracks.TrackIndividual> trackIndividual;
    private Context context;
    private TrackInterface trackInterface;
    long itsColor;

    public TrackInAlbumAdapter(ArrayList<IndividualAlbumModel.Tracks.TrackIndividual> trackIndividual, Context context, TrackInterface trackInterface){
        this.trackInterface=trackInterface;
        this.context=context;
        this.trackIndividual=trackIndividual;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_in_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //add null safety

        int trackLength = trackIndividual.get(position).getName().length();
        String trackString = trackLength<13?trackIndividual.get(position).getName()
                :trackIndividual.get(position).getName().substring(0,13)+"...";

        int artistLength = trackIndividual.get(position).getArtists().get(0).getName().length();
        String artistString = artistLength<13?trackIndividual.get(position).getArtists().get(0).getName()
                :trackIndividual.get(position).getArtists().get(0).getName().substring(0,13)+"...";
        holder.trackName.setText(trackString);
        holder.trackArtistName.setText(artistString);


        //for random linear gradients for image view using setColorFilter()
        RandomLinearGradient.setRandomLinearGradient(holder.trackImage);
        itsColor = RandomLinearGradient.getGradientColors();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trackInterface!=null && trackIndividual.get(holder.getAdapterPosition()).getPreview_url()!=null){
                    trackInterface.onCategoryClick(holder.getAdapterPosition(),itsColor);

                }else {
                    Toast.makeText(context, "You can't play this song",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return trackIndividual.size();
    }

    public interface TrackInterface{
        void onCategoryClick(int position, long itsColor);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView trackArtistName;
        TextView trackName;
        ImageView trackImage;
        ImageView trackVerticalMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trackImage = itemView.findViewById(R.id.trackImage);
            trackArtistName = itemView.findViewById(R.id.trackArtistName);
            trackName = itemView.findViewById(R.id.trackName);
            trackVerticalMore = itemView.findViewById(R.id.trackVerticalMore);
        }
    }
}

