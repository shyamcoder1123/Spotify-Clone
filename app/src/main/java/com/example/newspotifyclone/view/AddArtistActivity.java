package com.example.newspotifyclone.view;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newspotifyclone.ApiManager;
import com.example.newspotifyclone.NetworkUtils;
import com.example.newspotifyclone.R;
import com.example.newspotifyclone.databinding.ActivityAddArtistBinding;
import com.example.newspotifyclone.helper.ArtistItemDecoration;
import com.example.newspotifyclone.helper.ArtistLibraryRecyclerAdapter;
import com.example.newspotifyclone.helper.ArtistListRecyclerAdapter;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.model.SearchResultArtists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddArtistActivity extends AppCompatActivity implements ArtistListRecyclerAdapter.CategoryInterface {

    ActivityAddArtistBinding activityAddArtistBinding;
    //Getting and saving selected data to LibraryFragment
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "MY_SHARED_PREF";

    int searchViewTextEmptyChecker=0;
    RecyclerView artistRecycler;
    TextView done;
    ArrayList<ArtistModels.Artist> artistArrayList;
    ArtistItemDecoration itemDecoration = new ArtistItemDecoration();
    ArtistListRecyclerAdapter artistListRecyclerAdapter;
    ApiManager apiManager;
    ArrayList<ArtistModels.Artist> artistsSearch=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artist);

        activityAddArtistBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_artist);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        done = activityAddArtistBinding.doneTextView;
        artistRecycler = activityAddArtistBinding.artistInAddArtistRecycler;
        artistArrayList = new ArrayList<>();
        artistListRecyclerAdapter = new ArtistListRecyclerAdapter(artistArrayList,this,this,"normal");
        apiManager = new ApiManager(this);
        String sushinId = "1qFp8zDvsXyCsC5dqz8X4S",chithraId = "2IUtwMti1OiT3lkW6RubgH",
        sidSriramId = "7qjJw7ZM2ekDSahLXPjIlN",rahmanId = "1mYsTxnqsietFxj1OgoGbG",
        ankitId = "0E02VcvA5p1ndkLdqWD5JB", justinId = "1uNFoZAHBGtllmzznpCI3s",
        mmkeeravaniId = "12l1SqSNsg2mI2IcXpPWjR", sujathaId = "2JEjaa7hWhE1BbL3OcoeFR", anirudhId = "4zCH9qm4R2DADamUHMCa6O", vijId="0aUQnP4HhUQXcurZl9GJIA",
                auroraId="1WgXqy2Dd70QQOU7Ay074N", shwetaId="1rdQOMFFtoskDXXUVjiGo9";
        getArtist(sushinId);
        getArtist(chithraId);
        getArtist(sidSriramId);
        getArtist(rahmanId);
        getArtist(ankitId);
        getArtist(justinId);
        getArtist(mmkeeravaniId);
        getArtist(sujathaId);
        getArtist(anirudhId);
        getArtist(auroraId);
        getArtist(vijId);
        getArtist(shwetaId);
        if(!NetworkUtils.isNetWorkAvailable(this)){
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        View customSearch=findViewById(R.id.searchBarForArtist);
        EditText customSearchEditText = customSearch.findViewById(com.google.android.material.R.id.search_bar_text_view);
        customSearchEditText.setHint("Search");

        TextWatcher textWatcher = new TextWatcher() {
            private final Handler handler = new Handler();
            private Runnable runnable;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()==0){
                    handler.removeCallbacks(runnable);
                    runnable = () -> {
                        artistsSearch.clear();
//                        searchResultsRecyclerViewAdapter.notifyDataSetChanged();
//                        searchProgressBar.setVisibility(View.GONE);
                    };
                    handler.postDelayed(runnable, 100);
                }
                else if(editable.toString().length()!=0){
//                    searchProgressBar.setVisibility(View.VISIBLE);

                    handler.removeCallbacks(runnable);
                    runnable = () -> {
                        String searchViewText = customSearchEditText.getText().toString();
//                        getUserSearchResults(searchViewText, "artist");
                    };
                    handler.postDelayed(runnable, 50);
                }
                searchViewTextEmptyChecker=customSearchEditText.getText().toString().length();
            }
        };
//        customSearchEditText.addTextChangedListener(textWatcher);

        Animation slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_top);
        for (int i = 0; i < artistRecycler.getChildCount(); i++) {
            artistRecycler.getChildAt(i).startAnimation(slideInAnimation);
        }

        artistRecycler.addItemDecoration(itemDecoration);
        ArrayList<ArtistModels.Artist> artistsSelected = new ArrayList<>();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<artistArrayList.size();i++){
                    if(artistArrayList.get(i).isSelected()){
                        artistsSelected.add(artistArrayList.get(i));
                    }
                }
                loadData(artistsSelected);
                saveData(artistsSelected);
                Intent i = new Intent(AddArtistActivity.this, MainActivity.class);
                i.putExtra("fragment_to_show", "fragment_library");
                finish();
                startActivity(i);
            }
        });
    }

    public void  getArtist(String id){
        apiManager.recommendedArtistsApiCall(id, new Callback<ArtistModels.Artist>() {
            @Override
            public void onResponse(Call<ArtistModels.Artist> call, Response<ArtistModels.Artist> response) {
                ArtistModels.Artist artistModel = response.body();
                if(artistModel!=null){
                    if(artistModel.getName()!=null){
                        artistArrayList.add(artistModel);
                        artistRecycler.setAdapter(artistListRecyclerAdapter);
                        artistListRecyclerAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    Toast.makeText(AddArtistActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArtistModels.Artist> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        ArtistModels.Artist selectedArtist = artistArrayList.get(position);
        Log.e("sushin",artistArrayList.get(0).getName()+" "+artistArrayList.get(0).isSelected()+"");
        selectedArtist.setSelected(!selectedArtist.isSelected());
        itemDecoration.setSelectedPosition(position);
        artistListRecyclerAdapter.notifyDataSetChanged();
    }

//    public void getUserSearchResults(String query, String type){
//        artistsSearch=new ArrayList<>();
//        apiManager.getSearchResultArtist(query, type,new Callback<SearchResultArtists>() {
//            @Override
//            public void onResponse(Call<SearchResultArtists> call, Response<SearchResultArtists> response) {
//                if(response.isSuccessful()){
//                    SearchResultArtists searchResultModel = response.body();
//                    if(searchResultModel!=null){
//                        if(searchResultModel.getArtists().getItems().size()==0){
//
//                        }else{
////                            textAtCentreScreenView.setVisibility(View.GONE);
//                            ArrayList<ArtistModels.Artist> artists = searchResultModel.getArtists().getItems();
//                            artistsSearch.clear();
//                            artistsSearch.addAll(artists);
//                        }
//                    }
//
//                }else{
//                    Toast.makeText(AddArtistActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SearchResultArtists> call, Throwable t) {
//                Toast.makeText(AddArtistActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onAddArtistClick() {

    }

    public void saveData(ArrayList<ArtistModels.Artist> artistsSelected){
        Gson gson = new Gson();
        String json = gson.toJson(artistsSelected);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("artistIds",json);
        editor.apply();
    }

    private void loadData(ArrayList<ArtistModels.Artist> artistsSelected) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("artistIds",null);
        Type type = new TypeToken<ArrayList<ArtistModels.Artist>>(){
        }.getType();
        ArrayList<ArtistModels.Artist> artistsArray=new ArrayList<>();
        artistsArray.clear();
        HashMap<String, ArtistModels.Artist> nameArtistHashMap = new HashMap<>();
        nameArtistHashMap.clear();

        artistsArray.addAll(gson.fromJson(json,type));
        for(ArtistModels.Artist artist:artistsArray){
            nameArtistHashMap.put(artist.getName(),artist);
        }
        for(ArtistModels.Artist artist:artistsSelected){
            nameArtistHashMap.put(artist.getName(),artist);
        }
        artistsSelected.clear();
        for(Map.Entry<String, ArtistModels.Artist> entry:nameArtistHashMap.entrySet()){
            artistsSelected.add(entry.getValue());
        }

    }
}