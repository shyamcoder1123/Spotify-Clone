package com.example.newspotifyclone;

import android.content.Context;
import android.util.Log;

import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.AlbumObject;
import com.example.newspotifyclone.model.ArrayOfArtistsModel;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.model.Artists;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.example.newspotifyclone.model.SearchResultAlbums;
import com.example.newspotifyclone.model.SearchResultArtists;
import com.example.newspotifyclone.model.SearchResultsTracks;
import com.example.newspotifyclone.service.SpotifyApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static final String BASE_URL = "https://api.spotify.com/";
    private  static SpotifyApiService spotifyApiService;
    private static TokenManager tokenManager;
    private static String accessToken;
    Context context;

    public ApiManager(Context context) {
        this.context = context;
        tokenManager = TokenManager.getInstance(context) ;
//        accessToken = tokenManager.getAccessToken();
    }

    private static SpotifyApiService getSpotifyApiService(){
        if(spotifyApiService==null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            spotifyApiService = retrofit.create(SpotifyApiService.class);
        }
        return spotifyApiService;
    }

    public void getAlbumResult(String albumId, Callback<IndividualAlbumModel> callback){
        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                // Use the fetched token to make your API call
                String startURL = "v1/albums/";
                Call<IndividualAlbumModel> call = getSpotifyApiService().getTracksFromAlbums(startURL + albumId, token);
                call.enqueue(callback);
            }
        });
    }

    public void getArtistAlbumResult(String artistId, Callback<AlbumModel> callback){

        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                String URL = "v1/artists/"+artistId+"/"+"albums";
                Call<AlbumModel> call = getSpotifyApiService().searchArtistAlbum(URL,token);
                call.enqueue(callback);
            }
        });

    }

    public void recommendedArtistsApiCall(String artistId, Callback<ArtistModels.Artist> callback){
        tokenManager.getAccessToken(new TokenManager.TokenCallback() {
            @Override
            public void onTokenFetched(String token) {
                String startURL = "v1/artists/";
                Call<ArtistModels.Artist> call = getSpotifyApiService().recommendedArtist(startURL+artistId,token);
                call.enqueue(callback);
            }
        });
    }
}
