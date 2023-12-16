package com.example.newspotifyclone.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.newspotifyclone.TokenManager;
import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.AlbumObject;
import com.example.newspotifyclone.model.ArrayOfArtistsModel;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.service.RetrofitInstance;
import com.example.newspotifyclone.service.SpotifyApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicHomeRepo {
    SpotifyApiService musicaApiService;
    private static TokenManager tokenManager;
    private ArrayList<AlbumModel.Album> albumModelArrayList1,albumModelArrayList3,albumModelArrayList4,
            albumModelArrayList5;

    private ArrayList<AlbumModel.Album> albums = new ArrayList<>();
    private AlbumObject albumSuggestions = new AlbumObject();
    private ArrayList<ArtistModels.Artist> artists = new ArrayList<>();
    private ArtistModels.Artist artist = new ArtistModels.Artist();
    private MutableLiveData<List<AlbumModel.Album>> mutableAlbumLiveData = new MutableLiveData<>();
    private MutableLiveData<ArtistModels.Artist> mutableArtistLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ArtistModels.Artist>> mutableArtistsLiveData = new MutableLiveData<>();
    private MutableLiveData<AlbumObject> mutableAlbumSuggestionLiveData = new MutableLiveData<>();
    private Application application;

    public MusicHomeRepo(Application application){
        this.application=application;
        musicaApiService = RetrofitInstance.getService();
        tokenManager = TokenManager.getInstance(application);
    }

    public MutableLiveData<List<AlbumModel.Album>> getArtistAlbumResult(String artistId){

        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                String URL = "v1/artists/"+artistId+"/"+"albums";
                Call<AlbumModel> call = musicaApiService.searchArtistAlbum(URL,token);
                call.enqueue(new Callback<AlbumModel>() {
                    @Override
                    public void onResponse(Call<AlbumModel> call, Response<AlbumModel> response) {
                        AlbumModel result = response.body();
                        if(result!=null&&result.getItems()!=null){
                            albums = (ArrayList<AlbumModel.Album>) result.getItems();
                            mutableAlbumLiveData.setValue(albums);
                        }
                    }

                    @Override
                    public void onFailure(Call<AlbumModel> call, Throwable t) {

                    }
                });
            }
        });
        return mutableAlbumLiveData;
    }

    public MutableLiveData<ArtistModels.Artist> getArtistById(String artistId){

        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                String URL = "v1/artists/"+artistId;
                Call<ArtistModels.Artist> call = musicaApiService.recommendedArtist(URL,token);
                call.enqueue(new Callback<ArtistModels.Artist>() {
                    @Override
                    public void onResponse(Call<ArtistModels.Artist> call, Response<ArtistModels.Artist> response) {
                        ArtistModels.Artist result = response.body();
                        if(result!=null){
                            artist = (ArtistModels.Artist) result;
                            mutableArtistLiveData.setValue(artist);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArtistModels.Artist> call, Throwable t) {

                    }
                });
            }
        });
        return mutableArtistLiveData;
    }

    public MutableLiveData<List<ArtistModels.Artist>> getArrayOfArtistByIds(){

        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                String URL = "v1/artists?ids=2CIMQHirSU0MQqyYHq0eOx%2C57dN52uHvrHOxijzpIgu3E%2C1vCWHaC5f2uS3yhpwWbIA6%2C4YRxDV8wJFPHPTeXepOstw%2C0oOet2f43PA68X5RxKobEy%2C1qFp8zDvsXyCsC5dqz8X4S";
                Call<ArrayOfArtistsModel> call = musicaApiService.artists(URL,token);
                call.enqueue(new Callback<ArrayOfArtistsModel>() {
                    @Override
                    public void onResponse(Call<ArrayOfArtistsModel> call, Response<ArrayOfArtistsModel> response) {
                        ArrayOfArtistsModel result = response.body();
                        if(result!=null){
                            artists = (ArrayList<ArtistModels.Artist>) result.getArtists();
                            mutableArtistsLiveData.setValue(artists);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayOfArtistsModel> call, Throwable t) {

                    }
                });
            }
        });
        return mutableArtistsLiveData;
    }

    public MutableLiveData<AlbumObject> getAlbumSuggestions(String ids){

        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                Call<AlbumObject> call = musicaApiService.albumSuggestions(ids,token);
                call.enqueue(new Callback<AlbumObject>() {
                    @Override
                    public void onResponse(Call<AlbumObject> call, Response<AlbumObject> response) {
                        AlbumObject result = response.body();
                        if(result!=null){
                            albumSuggestions = (AlbumObject) result;
                            mutableAlbumSuggestionLiveData.setValue(albumSuggestions);
                        }
                    }

                    @Override
                    public void onFailure(Call<AlbumObject> call, Throwable t) {

                    }
                });
            }
        });
        return mutableAlbumSuggestionLiveData;
    }
}
