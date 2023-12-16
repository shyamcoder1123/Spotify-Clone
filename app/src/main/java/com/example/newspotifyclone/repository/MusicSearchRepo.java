package com.example.newspotifyclone.repository;

import static java.security.AccessController.getContext;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.newspotifyclone.TokenManager;
import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.example.newspotifyclone.model.SearchResultAlbums;
import com.example.newspotifyclone.model.SearchResultArtists;
import com.example.newspotifyclone.model.SearchResultsTracks;
import com.example.newspotifyclone.service.RetrofitInstance;
import com.example.newspotifyclone.service.SpotifyApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicSearchRepo {
    SpotifyApiService musicApiService;
    private static TokenManager tokenManager;
    private ArrayList<ArtistModels.Artist> artists = new ArrayList<>();
    private ArrayList<AlbumModel.Album> albums = new ArrayList<>();
    private ArrayList<IndividualAlbumModel.Tracks.TrackIndividual> tracks = new ArrayList<>();
    private MutableLiveData<List<AlbumModel.Album>> mutableAlbumLiveData = new MutableLiveData<>();
    private MutableLiveData<List<IndividualAlbumModel.Tracks.TrackIndividual>> mutableTrackLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ArtistModels.Artist>> mutableArtistLiveData = new MutableLiveData<>();
    private Application application;

    public MusicSearchRepo(Application application){
        this.application=application;
        musicApiService = RetrofitInstance.getService();
        tokenManager = TokenManager.getInstance(application);
    }
    public MutableLiveData<List<ArtistModels.Artist>> getSearchResultArtist(String query, String type){
        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                Call<SearchResultArtists> call = musicApiService.userSearchArtists(query,type,token);
                call.enqueue(new Callback<SearchResultArtists>() {
                    @Override
                    public void onResponse(Call<SearchResultArtists> call, Response<SearchResultArtists> response) {
                        SearchResultArtists result = response.body();
                        if(result!=null&&result.getArtists()!=null&&result.getArtists().getItems()!=null) {
                            artists = (ArrayList<ArtistModels.Artist>) result.getArtists().getItems();
                            mutableArtistLiveData.setValue(artists);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResultArtists> call, Throwable t) {

                    }
                });
            }
        });
        return mutableArtistLiveData;
    }

    public MutableLiveData<List<AlbumModel.Album>> getSearchResultAlbums(String query, String type){
        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                Call<SearchResultAlbums> call = musicApiService.userSearchAlbums(query,type,token);
                call.enqueue(new Callback<SearchResultAlbums>() {
                    @Override
                    public void onResponse(Call<SearchResultAlbums> call, Response<SearchResultAlbums> response) {
                        SearchResultAlbums result = response.body();
                        if(result!=null&&result.getAlbums()!=null&&result.getAlbums().getItems()!=null) {
                            albums = (ArrayList<AlbumModel.Album>) result.getAlbums().getItems();
                            mutableAlbumLiveData.setValue(albums);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResultAlbums> call, Throwable t) {

                    }
                });
            }
        });
        return mutableAlbumLiveData;
    }

    public MutableLiveData<List<IndividualAlbumModel.Tracks.TrackIndividual>> getSearchResultTracks(String query, String type){
        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                Call<SearchResultsTracks> call = musicApiService.userSearchTracks(query,type,token);
                call.enqueue(new Callback<SearchResultsTracks>() {
                    @Override
                    public void onResponse(Call<SearchResultsTracks> call, Response<SearchResultsTracks> response) {
                        SearchResultsTracks result = response.body();
                        if(result!=null&&result.getTracks()!=null&&result.getTracks().getItems()!=null) {
                            tracks = (ArrayList<IndividualAlbumModel.Tracks.TrackIndividual>) result.getTracks().getItems();
                            mutableTrackLiveData.setValue(tracks);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResultsTracks> call, Throwable t) {
//                        Toast.makeText(application.getApplicationContext(), "Please check your internet connection",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return mutableTrackLiveData;
    }

}
