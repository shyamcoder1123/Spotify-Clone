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
import android.widget.Button;
import android.widget.ImageView;

import com.example.newspotifyclone.model.ArtistModel;
import com.example.newspotifyclone.model.SongModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements SongItemRecyclerAdapter.CategoryInterface{

    ArrayList<SongModel> songModelArrayList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;


    SongItemRecyclerAdapter songItemRecyclerAdapter;
    ArrayList<ArtistModel> artistModelArrayList;
    RecyclerView artistRecyclerView;

    ArtistAdapter artistAdapter;
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

        recyclerView3=view.findViewById(R.id.songRecycler3);

        recyclerView4=view.findViewById(R.id.songRecycler4);

        recyclerView5=view.findViewById(R.id.songRecycler5);

        songModelArrayList = new ArrayList<>();
        artistModelArrayList = new ArrayList<>();

        songItemRecyclerAdapter = new SongItemRecyclerAdapter(songModelArrayList
                ,this.getContext(),this::onCategoryClick);

        recyclerView.setAdapter(songItemRecyclerAdapter);
        recyclerView2.setAdapter(songItemRecyclerAdapter);
        recyclerView3.setAdapter(songItemRecyclerAdapter);
        recyclerView4.setAdapter(songItemRecyclerAdapter);
        recyclerView5.setAdapter(songItemRecyclerAdapter);

//        try {
//            getSearchResult("Imagine");
//        }catch (Exception e){
//            e.printStackTrace();
//        }


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

        artistAdapter = new ArtistAdapter(artistModelArrayList,this.getContext(),this::onCategoryClick);
        artistRecyclerView=view.findViewById(R.id.artistRecyclerView);
        artistRecyclerView.setAdapter(artistAdapter);
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
        artistModelArrayList.add(new ArtistModel("Arijit Sing","https://th.bing.com/th/id/OIP.qhr1h8MQBAgglvemzYi6tgHaEK?w=329&h=185&c=7&r=0&o=5&dpr=1.3&pid=1.7"));


    }


    public void  getSearchResult(String track){

        String accessToken = "Bearer BQCmpS2zM-JxtXacQUw9evKr7D8vgPOa9gWPNBYQ-Y3ITDDwSuO60nU1dyYEpIY9JmOEfDuoxHUBpGjKHp_Jhdm_LrPxp26jSHKhktNpWBRE2LTcLeg";
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        SpotifyApiService apiService = retrofit.create(SpotifyApiService.class);


        Call<SearchResult> call = apiService.searchTracks("Imagine","track",accessToken);
        Log.e("Till this point","search for next");
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                SearchResult searchResult = response.body();
                Log.e("This is the response",searchResult.getTracks().getItems().get(0).getName()+"");
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onCategoryClick(int position) {

    }
}