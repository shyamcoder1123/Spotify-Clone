package com.example.newspotifyclone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SpotifyApiService {
    @GET("v1/search")
    Call<SearchResult> searchTracks(@Query("q") String query, @Query("type") String type,
                                    @Header("Authorization") String accessToken);
}
