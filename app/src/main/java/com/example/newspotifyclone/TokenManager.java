package com.example.newspotifyclone;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenManager {
    private static TokenManager instnce;
    private static final  String CLIENT_ID = "f7c95f9bf6af41ab835da61507f8ba2f";
    private static final  String CLIENT_SECRET = "f109a6b695564e4188ceb49647661581";

    private static final String SHARED_PREF_NAME = "MY_SHARED_PREF";
    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";
    private final SharedPreferences sharedPreferences;
    private String accessToken;

    private long apiRequestedTime;

    private TokenManager(Context context){

        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);
    }
    public static synchronized TokenManager getInstance(Context context){
        if(instnce==null){
            instnce = new TokenManager(context);
        }
        return instnce;
    }
    public String getAccessToken(){
        if(accessToken==null || isAccessTokenExpired()){
            getAccessTokenRetrofitRequest();
        }
        return sharedPreferences.getString(ACCESS_TOKEN_KEY,null);
    }
    public void saveAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN_KEY,"Bearer "+accessToken);
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

    private void getAccessTokenRetrofitRequest(){
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
                        String accessToken = accessTokenResponse.getAccessToken();
                        saveAccessToken(accessToken);
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
}
