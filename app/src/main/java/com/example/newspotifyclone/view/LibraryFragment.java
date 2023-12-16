package com.example.newspotifyclone.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newspotifyclone.ApiManager;
import com.example.newspotifyclone.R;
import com.example.newspotifyclone.databinding.FragmentLibraryBinding;
import com.example.newspotifyclone.helper.ArtistLibraryRecyclerAdapter;
import com.example.newspotifyclone.helper.ArtistListRecyclerAdapter;
import com.example.newspotifyclone.model.ArrayOfArtistsModel;
import com.example.newspotifyclone.model.ArtistModels;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment implements ArtistLibraryRecyclerAdapter.ArtistItemInterface,ArtistListRecyclerAdapter.CategoryInterface {
    FragmentLibraryBinding fragmentLibraryBinding;
    public String name = "library";
    ImageView toggleButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<ArtistModels.Artist> artistsArray=new ArrayList<>();
    ApiManager apiManager;
    RecyclerView artistRecyclerView;
    HashMap<String, ArtistModels.Artist> nameArtistHashMap=new HashMap<>();
    private final Object lock = new Object();
    ArtistLibraryRecyclerAdapter artistLibraryRecyclerAdapter;
    ArtistListRecyclerAdapter artistListRecyclerAdapter;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "MY_SHARED_PREF";

    private boolean isGridView = false;

    public LibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLibraryBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_library,container,false);
        View view=fragmentLibraryBinding.getRoot();
        return view;
        // Inflate the layout for this fragment
//        return rootView=inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        artistRecyclerView=fragmentLibraryBinding.artistRecyclerLibrary;
        toggleButton=fragmentLibraryBinding.cardTypeView;
//        addArtistView=view.findViewById(R.id.addArtistCard);

        swipeRefreshLayout = fragmentLibraryBinding.swipeRefreshLayout;
        artistLibraryRecyclerAdapter = new ArtistLibraryRecyclerAdapter(artistsArray,getContext()
                ,LibraryFragment.this);
        artistListRecyclerAdapter = new ArtistListRecyclerAdapter(artistsArray,getContext(),LibraryFragment.this,"normal");
        apiManager=new ApiManager(this.getContext());
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,getContext().MODE_PRIVATE);
        loadData();

//
        if (artistsArray.size() == 0) {
//            getPopularArtists();
        } else {
            artistLibraryRecyclerAdapter.notifyDataSetChanged();
            artistListRecyclerAdapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                isGridView=!isGridView;
                toggleViewType();
            }
        });
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleViewType();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        loadData();
        isGridView=!isGridView;
        toggleViewType();
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
        isGridView=!isGridView;
        toggleViewType();
    }

    public void  getArtist(String id){
        apiManager.recommendedArtistsApiCall(id, new Callback<ArtistModels.Artist>() {
            @Override
            public void onResponse(Call<ArtistModels.Artist> call, Response<ArtistModels.Artist> response) {
                ArtistModels.Artist artistModel = response.body();
                if(artistModel!=null){
                    if(artistModel.getName()!=null){
                        artistsArray.add(artistModel);
                        artistLibraryRecyclerAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArtistModels.Artist> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
//    void getPopularArtists(){
//        apiManager.arrayOfArtistsApiCall(new Callback<ArrayOfArtistsModel>() {
//            @Override
//            public void onResponse(Call<ArrayOfArtistsModel> call, Response<ArrayOfArtistsModel> response) {
//                ArrayOfArtistsModel arrayOfArtistsModel = response.body();
//                if(arrayOfArtistsModel!=null){
//                    for(ArtistModels.Artist artistInResult : arrayOfArtistsModel.getArtists()){
//                        if(nameArtistHashMap.containsKey(artistInResult.getName())){
//                            continue;
//                        }
//                        else {
//                            artistsArray.add(artistInResult);
//                        }
//                    }
//                    artistsArray.addAll(arrayOfArtistsModel.getArtists());
//                    saveData();
//                    if(isGridView){
//                        artistRecyclerView.setAdapter(artistListRecyclerAdapter);
//                        artistListRecyclerAdapter.notifyDataSetChanged();
//                    }else {
//                        artistRecyclerView.setAdapter(artistLibraryRecyclerAdapter);
//                        artistLibraryRecyclerAdapter.notifyDataSetChanged();
//                    }
//
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//                else{
//                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayOfArtistsModel> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
    @Override
    public void onCategoryClick(int position) {
        ArtistModels.Artist artist = artistsArray.get(position);
        String artistId=artist.getId();
        Intent i = new Intent(this.getContext(), ArtistActivity.class);
        i.putExtra("artist id",artistId);
        i.putExtra("artistImageURL",artist.getImages().get(0).getUrl());
        i.putExtra("artist name",artist.getName());
        startActivity(i);
    }

    @Override
    public void onCategoryLongClick(int position) {
        deleteData(artistsArray.get(position));

    }

    @Override
    public void onAddArtistClick() {
        Intent i= new Intent(getContext(), AddArtistActivity.class);
        startActivity(i);
    }

    public void toggleViewType() {
        isGridView = !isGridView;
        if (isGridView) {
            toggleButton.setImageResource(R.drawable.view_list_bullet);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3); // span count for grid
            artistRecyclerView.setLayoutManager(gridLayoutManager);
            for(ArtistModels.Artist artist:artistsArray){
                artist.setSelected(false);
            }
            artistRecyclerView.setAdapter(artistListRecyclerAdapter);
            artistListRecyclerAdapter.notifyDataSetChanged();

        } else {
            toggleButton.setImageResource(R.drawable.menu);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            artistRecyclerView.setLayoutManager(linearLayoutManager);
            for(ArtistModels.Artist artist:artistsArray){
                artist.setSelected(false);
            }
            artistRecyclerView.setAdapter(artistLibraryRecyclerAdapter);
            artistLibraryRecyclerAdapter.notifyDataSetChanged();
        }

    }
    public void saveData(){
        Gson gson = new Gson();
        ArrayList<ArtistModels.Artist> forSave = new ArrayList<>();
        for(ArtistModels.Artist artist:artistsArray){
            if(!forSave.contains(artist)){
                forSave.add(artist);
            }
        }
        String json = gson.toJson(forSave);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("artistIds",json);
        editor.apply();
        loadData();
    }

    private void loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("artistIds",null);
        Type type = new TypeToken<ArrayList<ArtistModels.Artist>>(){
        }.getType();
        artistsArray.clear();
        nameArtistHashMap.clear();

        if(json==null){
//            getPopularArtists();
        }else {
            artistsArray.addAll(gson.fromJson(json,type));
        }
        for(ArtistModels.Artist artist:artistsArray){
            nameArtistHashMap.put(artist.getName(),artist);
        }
        artistsArray.clear();
        for(Map.Entry<String, ArtistModels.Artist> entry:nameArtistHashMap.entrySet()){
            artistsArray.add(entry.getValue());
        }
        if(artistsArray.size()!=0){
            artistLibraryRecyclerAdapter.notifyDataSetChanged();
            artistListRecyclerAdapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }
    private void deleteData(ArtistModels.Artist artist){
        synchronized (lock) {
            String name = artist.getName();
            Gson gson = new Gson();
            String json = sharedPreferences.getString("artistIds", null);
            Type type = new TypeToken<ArrayList<ArtistModels.Artist>>() {
            }.getType();
            artistsArray.clear();
            artistsArray.addAll(gson.fromJson(json, type));
            if (artistsArray.size() != 0) {
                for(ArtistModels.Artist artistInArray : artistsArray){
                    String anotherName=artistInArray.getName();
                    if(anotherName!=null){
                        if(anotherName.equals(name)){
                            artistsArray.remove(artistInArray);
                            break;
                        }
                    }

                }
                saveData();

            }
        }
    }

}