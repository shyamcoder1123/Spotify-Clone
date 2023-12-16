package com.example.newspotifyclone.service;

import com.example.newspotifyclone.AccessTokenResponse;
import com.example.newspotifyclone.model.AlbumObject;
import com.example.newspotifyclone.model.ArrayOfArtistsModel;
import com.example.newspotifyclone.model.Artists;
import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.example.newspotifyclone.model.SearchResultAlbums;
import com.example.newspotifyclone.model.SearchResultArtists;
import com.example.newspotifyclone.model.SearchResultsTracks;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface SpotifyApiService {

    @FormUrlEncoded
    @POST("api/token")
    Call<AccessTokenResponse> getAccessTokenRetrofit(
            @Field("grant_type") String grandType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );
    @GET("v1/search")
    Call<SearchResultAlbums> userSearchAlbums(@Query("q") String query, @Query("type") String type,
                                        @Header("Authorization") String accessToken);
    @GET("v1/search")
    Call<SearchResultArtists> userSearchArtists(@Query("q") String query, @Query("type") String type,
                                              @Header("Authorization") String accessToken);
    @GET("v1/search")
    Call<SearchResultsTracks> userSearchTracks(@Query("q") String query, @Query("type") String type,
                                                @Header("Authorization") String accessToken);

    @GET
    Call<ArtistModels.Artist> recommendedArtist(@Url String url, @Header("Authorization") String accessToken);

    @GET
    Call<AlbumModel> searchArtistAlbum(@Url String url, @Header("Authorization") String accessToken);
    @GET("v1/albums")
    Call<AlbumObject> albumSuggestions(@Query("ids") String ids, @Header("Authorization") String accessToken);

    @GET
    Call<IndividualAlbumModel> getTracksFromAlbums(@Url String URL, @Header("Authorization") String accessToken);

    @GET
    Call<ArrayOfArtistsModel> artists(@Url String url, @Header("Authorization") String accessToken);
//    @GET
//    Call<IndividualTrackModel> getTrackImages(@Url String trackId, @Header("Authorization") String accessToken);

}
