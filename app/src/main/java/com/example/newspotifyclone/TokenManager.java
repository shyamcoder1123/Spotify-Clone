package com.example.newspotifyclone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.example.newspotifyclone.service.SpotifyApiService;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.CountDownLatch;

public class TokenManager {
    private static TokenManager instance;

    private android.os.Handler handler = new Handler();
    private Runnable runnable;
    private static final  String CLIENT_ID = "YOUR_SPOTIFY_CLIENT_ID";
    private static final  String CLIENT_SECRET = "YOUR_SPOTIFY_CLIENT_SECRET ";

    private static final String SHARED_PREF_NAME = "MY_SHARED_PREF";
    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";
    private final SharedPreferences sharedPreferences;
    private String accessToken;

    private long apiRequestedTime;

    private TokenManager(Context context){

        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);
        accessToken=sharedPreferences.getString(ACCESS_TOKEN_KEY, null);
    }
    public static synchronized TokenManager getInstance(Context context){
        if(instance==null){
            instance = new TokenManager(context);
        }
        return instance;
    }

    public void getAccessToken(TokenCallback callback) {
        if (accessToken == null || isAccessTokenExpired()) {
            getAccessTokenRetrofitRequest(callback);
        }else {
            callback.onTokenFetched(accessToken);
        }

//        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null);
    }

    public void saveAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN_KEY,accessToken);
        editor.apply();
    }

    public void setApiRequestedTime(long apiRequestedTime) {
        if(apiRequestedTime!=0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("apiRequestedTime",Long.toString(apiRequestedTime));
            editor.apply();
        }
        this.apiRequestedTime = apiRequestedTime;
    }
    public long getApiRequestedTime() {
        String apiRequestedTimeString = sharedPreferences.getString("apiRequestedTime",null);
        return Long.parseLong(apiRequestedTimeString);
    }

    private void getAccessTokenRetrofitRequest(final TokenCallback callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://accounts.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        SpotifyApiService apiService = retrofit.create(SpotifyApiService.class);
        Call<AccessTokenResponse> call = apiService.getAccessTokenRetrofit(
                "client_credentials",CLIENT_ID,CLIENT_SECRET);

        call.enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if(response.isSuccessful()){
                    AccessTokenResponse accessTokenResponse = response.body();
                    if(accessTokenResponse!=null){
                        accessToken = "Bearer "+accessTokenResponse.getAccessToken();
                        saveAccessToken(accessToken);

                        callback.onTokenFetched(accessToken);
                    }
                    //do something
                }
            }
            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {

            }
        });
    }
    public boolean isAccessTokenExpired(){
        long currentTimeMillis = System.currentTimeMillis();
        if(apiRequestedTime!=0 && (Math.abs(currentTimeMillis-getApiRequestedTime())>3500000)){
            apiRequestedTime = currentTimeMillis;
            setApiRequestedTime(apiRequestedTime);
            return true;
        } else return apiRequestedTime == 0;
    }
    public interface TokenCallback {
        void onTokenFetched(String token);
    }

}
