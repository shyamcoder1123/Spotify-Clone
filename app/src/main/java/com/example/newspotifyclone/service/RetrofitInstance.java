package com.example.newspotifyclone.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    // Act as a central configuration point for defining how http requests
    // should be handled.
    private static Retrofit retrofit = null;
    private static String BASE_URL = "https://api.spotify.com/";

    public static SpotifyApiService getService(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SpotifyApiService.class);
    }
}
