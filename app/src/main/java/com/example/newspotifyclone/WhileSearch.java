package com.example.newspotifyclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newspotifyclone.model.SearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WhileSearch extends Fragment {

    public WhileSearch() {
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
        return inflater.inflate(R.layout.fragment_while_search, container, false);
    }

    public void  getSearchResult(String track){

        String accessToken = "Bearer BQCergUO5mv6y8fN5jVu8ehK3KvgLQ-N-E2QC8NfymTikULIvEMQOgNraVQAOs4e8-_FQ7lV2-ANs_92pBkxZgS1oX40nGeocnmFijhq9tLg40v_BKM";
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        SpotifyApiService apiService = retrofit.create(SpotifyApiService.class);


        Call<SearchResult> call = apiService.searchTracks("Imagine","track",accessToken);
        Log.e("Till this point","search for next");
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                SearchResult searchResult = response.body();
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}