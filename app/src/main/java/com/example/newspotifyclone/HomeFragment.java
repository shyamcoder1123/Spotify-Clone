package com.example.newspotifyclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.ArtistModel;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.example.newspotifyclone.model.SongModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements AlbumItemRecyclerAdapter.CategoryInterface {

    TokenManager tokenManager;
    String accessToken;
    ArrayList<SongModel> songModelArrayList;
    IndividualAlbumModel.Tracks tracksObject;
    ArrayList<AlbumModel.Album> albumModelArrayList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    View includeArtist;
    TextView artistName;
    ImageView artistImage;


    SongItemRecyclerAdapter songItemRecyclerAdapter;
    AlbumItemRecyclerAdapter albumItemRecyclerAdapter;
//    ArrayList<ArtistModel> artistModelArrayList;
//    RecyclerView artistRecyclerView;

//    ArtistAdapter artistAdapter;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.songRecycler);

        recyclerView2=view.findViewById(R.id.songRecycler2);
        ///This one is the recycler blow our artist---------------

        recyclerView3=view.findViewById(R.id.songRecyclerArtist);

        //----------------------------------------------------------

        recyclerView4=view.findViewById(R.id.songRecycler4);

        recyclerView5=view.findViewById(R.id.songRecycler5);

        songModelArrayList = new ArrayList<>();
        albumModelArrayList = new ArrayList<>();
//        ---------------------------------------------------------------
//        -----------------------------------------------------------------

        tokenManager = TokenManager.getInstance(getContext()) ;
        accessToken = tokenManager.getAccessToken();

        Log.e("This is the token",accessToken+"");

//        -----------------------------------------------------------------

//        ----------------------------------------------------------------

        songItemRecyclerAdapter = new SongItemRecyclerAdapter(songModelArrayList
                ,this.getContext(),this::onCategoryClick);

        albumItemRecyclerAdapter = new AlbumItemRecyclerAdapter(albumModelArrayList
                ,this.getContext(),this::onCategoryClick);

        recyclerView.setAdapter(songItemRecyclerAdapter);
        recyclerView2.setAdapter(songItemRecyclerAdapter);

        recyclerView3.setAdapter(albumItemRecyclerAdapter);

        recyclerView4.setAdapter(songItemRecyclerAdapter);
        recyclerView5.setAdapter(songItemRecyclerAdapter);



        View includedMusicBar=view.findViewById(R.id.musicbar_item);
        ImageView playButton=includedMusicBar.findViewById(R.id.music_bar_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MusicPlayerActivity.class);
                startActivity(i);
            }
        });

        getSongs();

        includeArtist = view.findViewById(R.id.first_artist);
        artistName = includeArtist.findViewById(R.id.artistName);
        artistImage = includeArtist.findViewById(R.id.artistImage);

        getSearchResult("Arijit");
        getArtistAlbumResult("album");
        getArtist();
    }
    private void getSongs(){
        songModelArrayList.add(new SongModel("Tum Hi Ho","Arijit Singh","https://th.bing.com/th/id/OIP.QMqIE6Mf9tEGpF3MZXY3ZgHaEK?w=307&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7"));
        songModelArrayList.add(new SongModel("Pookale Satru", "Shreya Ghoshal & Haricharan","https://th.bing.com/th/id/OIP.LhAM717VSy1VbUR9MydZYgHaDJ?w=335&h=149&c=7&r=0&o=5&dpr=1.3&pid=1.7"));
        songModelArrayList.add(new SongModel("Sama Javara Gamana","Sid Sriram","https://th.bing.com/th/id/OIP.QOLt4XPUeZqBNSPtwaFolgHaE8?w=249&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7"));
        songModelArrayList.add(new SongModel("Bhula Dena","Mustafa Zahid","https://th.bing.com/th/id/OIP.dxddNv-4rODZTjtOqyU_vQHaHa?w=182&h=183&c=7&r=0&o=5&dpr=1.3&pid=1.7"));
        songModelArrayList.add(new SongModel("Sun Raha Hai","Ankit Tiwari","https://th.bing.com/th/id/OIP.pxxIuh00J01SWicGXWxiSwHaEK?w=312&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7" ));


        //songItemRecyclerAdapter.notifyDataSetChanged();
    }
    private void getArtist(){
//        artistModelArrayList.add(new ArtistModel("Arijit Sing","https://th.bing.com/th/id/OIP.qhr1h8MQBAgglvemzYi6tgHaEK?w=329&h=185&c=7&r=0&o=5&dpr=1.3&pid=1.7"));

    }


    public void  getSearchResult(String track){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        SpotifyApiService apiService = retrofit.create(SpotifyApiService.class);


        Call<ArtistModel> call = apiService.searchArtist(accessToken);
        Log.e("Till this point","search for next");
        call.enqueue(new Callback<ArtistModel>() {
            @Override
            public void onResponse(Call<ArtistModel> call, Response<ArtistModel> response) {
                Log.e("This is the response",response+"");
                ArtistModel artistModel = response.body();
                if(artistModel!=null){
                    artistName.setText(artistModel.getName());
                    Picasso.get().load(artistModel.getImages().get(0).getUrl()).into(artistImage);
                    Log.e("This is the response",response+"");
                }
                else{
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArtistModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void  getArtistAlbumResult(String track){

//        https://api.spotify.com/,https://api.spotify.com/
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        SpotifyApiService apiService = retrofit.create(SpotifyApiService.class);


        Call<AlbumModel> call = apiService.searchArtistAlbum(accessToken);
        Log.e("Till this point","search for next");
        call.enqueue(new Callback<AlbumModel>() {
            @Override
            public void onResponse(Call<AlbumModel> call, Response<AlbumModel> response) {


                AlbumModel albumModel = response.body();


                if(albumModel!=null){
                    for(AlbumModel.Album album: albumModel.getItems()){
                        albumModelArrayList.add(album);
                        Log.e("New Album", albumModelArrayList.get(0).getId()+"");
                    }
                    albumItemRecyclerAdapter.notifyDataSetChanged();
//
//                Picasso.get().load(artistModel.getImages().get(0).getUrl()).into(artistImage);
                    Log.e("This is the album id response",albumModel.getItems().get(0)+"");
                }
                else{
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AlbumModel> call, Throwable t) {
                t.printStackTrace();
                Log.e("This is the album response",t.getMessage()+"");
            }
        });

    }

//    --------------------------------------------------------------
//    On album click

    public void  getAlbumResult(String albumId){

//        https://api.spotify.com/,https://api.spotify.com/
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spotify.com/v1/albums/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        SpotifyApiService apiService = retrofit.create(SpotifyApiService.class);

        Call<IndividualAlbumModel> call = apiService.getTracksFromAlbums(albumId,accessToken);
        Log.e("Till this point","search for next");
        call.enqueue(new Callback<IndividualAlbumModel>() {
            @Override
            public void onResponse(Call<IndividualAlbumModel> call, Response<IndividualAlbumModel> response) {


                IndividualAlbumModel individualAlbumModel = response.body();


                if(individualAlbumModel!=null){
                    tracksObject=individualAlbumModel.getTracks();
                    Intent i = new Intent(getContext(), AlbumActivity.class);
                    i.putExtra("These are the tracks",tracksObject);
                    i.putExtra("ImageUrl",individualAlbumModel.getImages().get(0).getUrl());
                    startActivity(i);
                    Log.e("New Track",tracksObject.getItems().get(0).getName());
                }
                else{
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<IndividualAlbumModel> call, Throwable t) {
                t.printStackTrace();
                Log.e("This is the album response",t.getMessage()+"");
            }
        });

    }

    @Override
    public void onCategoryClick(int position) {
        String category = albumModelArrayList.get(position).getId();
        Log.e("This is the album id",category);
        getAlbumResult(category);

    }
}