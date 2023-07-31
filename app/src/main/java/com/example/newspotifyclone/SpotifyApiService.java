package com.example.newspotifyclone;

import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.ArtistModel;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.example.newspotifyclone.model.SearchResult;

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
    Call<SearchResult> searchTracks(@Query("q") String query, @Query("type") String type,
                                    @Header("Authorization") String accessToken);

    @GET("v1/artists/4YRxDV8wJFPHPTeXepOstw")
    Call<ArtistModel> searchArtist(@Header("Authorization") String accessToken);

    @GET("v1/artists/4YRxDV8wJFPHPTeXepOstw/albums")
    Call<AlbumModel> searchArtistAlbum(@Header("Authorization") String accessToken);

    @GET
    Call<IndividualAlbumModel> getTracksFromAlbums(@Url String albumId, @Header("Authorization") String accessToken);

//    @GET
//    Call<IndividualTrackModel> getTrackImages(@Url String trackId, @Header("Authorization") String accessToken);

}
