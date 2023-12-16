package com.example.newspotifyclone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newspotifyclone.ApiManager;
import com.example.newspotifyclone.databinding.FragmentSearchBinding;
import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.NetworkUtils;
import com.example.newspotifyclone.R;
import com.example.newspotifyclone.helper.SearchResultsRecyclerViewAdapter;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.example.newspotifyclone.model.SearchResultAlbums;
import com.example.newspotifyclone.model.SearchResultArtists;
import com.example.newspotifyclone.model.SearchResultsTracks;
import com.example.newspotifyclone.viewmodel.SearchFragmentMusicViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements SearchResultsRecyclerViewAdapter.SearchResultItemInterface{
    FragmentSearchBinding fragmentSearchBinding;
    public String name = "search";
    ApiManager apiManager;
    SearchFragmentMusicViewModel searchFragmentMusicViewModel;
    int searchViewTextEmptyChecker=0;
    IndividualAlbumModel.Tracks tracksObject;
    ArrayList<AlbumModel.Album> albumsSearch=new ArrayList<>();
    ArrayList<ArtistModels.Artist> artistsSearch=new ArrayList<>();
    ArrayList<IndividualAlbumModel.Tracks.TrackIndividual> tracksSearch=new ArrayList<>();
    TextView textAtCentreScreenView;
    TextView textBelowAtcentreScreenView;

    SearchResultsRecyclerViewAdapter searchResultsRecyclerViewAdapter;
    View searchViewCustom;
    EditText searchEditText;
    int i=1;

    ProgressBar searchProgressBar;
    RecyclerView searchResultsRecyclerView;
    int searchResultIndicator;
    int j=0;
    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);
        View view=fragmentSearchBinding.getRoot();
        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        creatingViews();
//        apiManager = new ApiManager(this.getContext());
        searchFragmentMusicViewModel = new ViewModelProvider(this).get(SearchFragmentMusicViewModel.class);
        if(!NetworkUtils.isNetWorkAvailable(requireContext())){
            textAtCentreScreenView.setText(getResources().getString(R.string.nointernet));
        }

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
                        albumsSearch.clear();
                        artistsSearch.clear();
                        tracksSearch.clear();
                        searchResultsRecyclerViewAdapter.notifyDataSetChanged();
                        searchProgressBar.setVisibility(View.GONE);
                        textAtCentreScreenView.setVisibility(View.VISIBLE);
                        textBelowAtcentreScreenView.setVisibility(View.VISIBLE);
                    };
                    handler.postDelayed(runnable, 100);
                }
                else if(editable.toString().length()!=0){
                    textAtCentreScreenView.setVisibility(View.GONE);
                    textBelowAtcentreScreenView.setVisibility(View.GONE);
                    searchProgressBar.setVisibility(View.VISIBLE);

                    handler.removeCallbacks(runnable);
                    runnable = () -> {
                        searchResultIndicator = 0;
                        String searchViewText = searchEditText.getText().toString();
                        if(NetworkUtils.isNetWorkAvailable(requireContext())){
                            getUserSearchResults(searchViewText);
                        }else {
                            textAtCentreScreenView.setVisibility(View.VISIBLE);
                            textAtCentreScreenView.setText(getResources().getString(R.string.nointernet));
                            searchProgressBar.setVisibility(View.GONE);
                        }
//                        getUserSearchResults(searchViewText, "artist");
//                        getUserSearchResults(searchViewText, "track");
                    };
                    handler.postDelayed(runnable, 50);
                }
                searchViewTextEmptyChecker=searchEditText.getText().toString().length();
            }
        };
        searchEditText.addTextChangedListener(textWatcher);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void creatingViews() {
        searchViewCustom=fragmentSearchBinding.customSearchBar;
        searchEditText=searchViewCustom.findViewById(R.id.search_bar_text_view);
        searchEditText.requestFocus();
        textAtCentreScreenView = fragmentSearchBinding.playWhatYouLove;
        textBelowAtcentreScreenView = fragmentSearchBinding.textBelowPlayWhatYouLove;
        searchResultsRecyclerView = fragmentSearchBinding.searchResultsRecyclerView;
        showSoftKeyboard();
        searchProgressBar=fragmentSearchBinding.searchProgressBar;
        searchProgressBar.setVisibility(View.GONE);
    }

    public void getUserSearchResults(String query){
        albumsSearch=new ArrayList<>();
        artistsSearch = new ArrayList<>();
        tracksSearch = new ArrayList<>();
        searchFragmentMusicViewModel.getSearchResultAlbums(query, "album").observe(getViewLifecycleOwner(), new Observer<List<AlbumModel.Album>>() {
            @Override
            public void onChanged(List<AlbumModel.Album> albumsFromLiveData) {
                textAtCentreScreenView.setVisibility(View.GONE);
                albumsSearch=(ArrayList<AlbumModel.Album>)albumsFromLiveData;
                displaySearchResults();
            }
        });
        searchFragmentMusicViewModel.getSearchResultTracks(query, "track").observe(getViewLifecycleOwner(), new Observer<List<IndividualAlbumModel.Tracks.TrackIndividual>>() {
            @Override
            public void onChanged(List<IndividualAlbumModel.Tracks.TrackIndividual> trackIndividualsFromLiveData) {
                textAtCentreScreenView.setVisibility(View.GONE);
                tracksSearch=(ArrayList<IndividualAlbumModel.Tracks.TrackIndividual>) trackIndividualsFromLiveData;
                displaySearchResults();
            }
        });
        searchFragmentMusicViewModel.getSearchResultArtist(query, "artist").observe(getViewLifecycleOwner(), new Observer<List<ArtistModels.Artist>>() {
            @Override
            public void onChanged(List<ArtistModels.Artist> artistsFromLiveData) {
                textAtCentreScreenView.setVisibility(View.GONE);
                artistsSearch = (ArrayList<ArtistModels.Artist>)artistsFromLiveData;
            }
        });
    }
    public void displaySearchResults(){
        searchResultsRecyclerViewAdapter = new SearchResultsRecyclerViewAdapter(artistsSearch,albumsSearch,tracksSearch,getContext(),SearchFragment.this::onCategoryClick,"all");
        searchResultsRecyclerView.setAdapter(searchResultsRecyclerViewAdapter);
        searchProgressBar.setVisibility(View.GONE);
    }
//    public void getUserSearchResults(String query, String type){
//        i+=1;
//        j+=1;
//        if(type.equals("album")){
//            albumsSearch=new ArrayList<>();
//            apiManager.getSearchResultAlbums(query, type,new Callback<SearchResultAlbums>() {
//                @Override
//                public void onResponse(Call<SearchResultAlbums> call, Response<SearchResultAlbums> response) {
//                    if(response.isSuccessful()){
//                        SearchResultAlbums searchResultModel = response.body();
//                        i+=1;
//                        if(searchResultModel!=null){
//
//
//                            if(searchResultModel.getAlbums().getItems().size()==0){
//
//                            }else{
//                                textAtCentreScreenView.setVisibility(View.GONE);
//                                ArrayList<AlbumModel.Album> albums = searchResultModel.getAlbums().getItems();
//                                albumsSearch.clear();
//                                albumsSearch.addAll(albums);
//                                searchResultIndicator+=1;
//                            }
//
//                        }
//
//                    }else{
//                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<SearchResultAlbums> call, Throwable t) {
//                    Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else if (type.equals("artist")) {
//            artistsSearch=new ArrayList<>();
//            apiManager.getSearchResultArtist(query, type,new Callback<SearchResultArtists>() {
//                @Override
//                public void onResponse(Call<SearchResultArtists> call, Response<SearchResultArtists> response) {
//                    if(response.isSuccessful()){
//                        SearchResultArtists searchResultModel = response.body();
//                        if(searchResultModel!=null){
//                            if(searchResultModel.getArtists().getItems().size()==0){
//
//                            }else{
//                                textAtCentreScreenView.setVisibility(View.GONE);
//                                ArrayList<ArtistModels.Artist> artists = searchResultModel.getArtists().getItems();
//                                artistsSearch.clear();
//                                artistsSearch.addAll(artists);
//                                searchResultIndicator+=1;
//                            }
//                        }
//
//                    }else{
//                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<SearchResultArtists> call, Throwable t) {
//                    Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else if (type.equals("track")) {
//            tracksSearch = new ArrayList<>();
//            apiManager.getSearchResultTracks(query, type,new Callback<SearchResultsTracks>() {
//                @Override
//                public void onResponse(Call<SearchResultsTracks> call, Response<SearchResultsTracks> response) {
//                    if(response.isSuccessful()){
//                        SearchResultsTracks searchResultModel = response.body();
//                        if(searchResultModel!=null){
//                            if(searchResultModel.getTracks().getItems().size()==0){
//
//                            }else{
//                                textAtCentreScreenView.setVisibility(View.GONE);
//                                ArrayList<IndividualAlbumModel.Tracks.TrackIndividual> trackIndividuals = searchResultModel.getTracks().getItems();
//                                tracksSearch.clear();
//                                tracksSearch.addAll(trackIndividuals);
//                                searchResultIndicator+=1;
//                            }
//
//                        }
//
//
//                    }else{
//                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<SearchResultsTracks> call, Throwable t) {
//                    Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        if(searchResultIndicator==0){
//            if(searchEditText.getText().toString().length()==0){
//                textAtCentreScreenView.setVisibility(View.VISIBLE);
//            }else {
////                Log.e("size is ",searchEditText.getText().toString().length()+"");
////                textAtCentreScreenView.setText("Couldn't find anything");
//
//            }
//        }
//        else {
//            textAtCentreScreenView.setVisibility(View.GONE);
//        }
//        if(j%3==0 && j!=0){
//            searchResultsRecyclerViewAdapter = new SearchResultsRecyclerViewAdapter(artistsSearch,albumsSearch,tracksSearch,getContext(),SearchFragment.this::onCategoryClick,"all");
//            searchResultsRecyclerView.setAdapter(searchResultsRecyclerViewAdapter);
//            Handler handler = new Handler();
//            Runnable runnable;
////            handler.removeCallbacks(runnable);
//            runnable = () -> {
//                if(searchViewTextEmptyChecker==0){
//                    albumsSearch.clear();
//                    artistsSearch.clear();
//                    tracksSearch.clear();
//                    searchResultsRecyclerViewAdapter.notifyDataSetChanged();
//                    textAtCentreScreenView.setVisibility(View.VISIBLE);
//                    textBelowAtcentreScreenView.setVisibility(View.VISIBLE);
//                }
//                searchProgressBar.setVisibility(View.GONE);
//
//            };
//            handler.postDelayed(runnable, 1000);
//        }
//
//    }

    @Override
    public void onResume() {
        super.onResume();
//        showSoftKeyboard();
    }

    private void showSoftKeyboard(){
        InputMethodManager inputMethodManager=(InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager!=null){
            inputMethodManager.showSoftInput(searchEditText,InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void  getAlbumResultFunction(String albumId){
        apiManager.getAlbumResult(albumId, new Callback<IndividualAlbumModel>() {
            @Override
            public void onResponse(Call<IndividualAlbumModel> call, Response<IndividualAlbumModel> response) {
                IndividualAlbumModel individualAlbumModel = response.body();
                if(individualAlbumModel!=null){
                    tracksObject=individualAlbumModel.getTracks();
                    Intent i = new Intent(getContext(), AlbumActivity.class);
                    i.putExtra("This is the album name",individualAlbumModel.getName());
                    i.putExtra("These are the tracks",tracksObject);
                    i.putExtra("ImageUrl",individualAlbumModel.getImages().get(0).getUrl());
                    i.putExtra("navigation_context", "search");
                    startActivity(i);
                }
                else{
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IndividualAlbumModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onCategoryClick(int position,int itemType) {
        if(itemType==0){
            ArtistModels.Artist artist = artistsSearch.get(position);
            String artistId=artist.getId();
            Intent i = new Intent(this.getActivity(), ArtistActivity.class);
            i.putExtra("artist id",artistId);
            i.putExtra("artist name",artist.getName());
            i.putExtra("artistImageURL",artist.getImages().get(0).getUrl());
            i.putExtra("navigation_context", "search");
            startActivity(i);

        } else if (itemType==1) {
            AlbumModel.Album album=albumsSearch.get(position);
            String albumId=album.getId();
            getAlbumResultFunction(albumId);
        }else {
            IndividualAlbumModel.Tracks.TrackIndividual track = tracksSearch.get(position);
            String previewURLToMusicPlayer = track.getPreview_url();
            String trackNameToMusicPlayer = track.getName();
            String artistNameToMusicPlayer = track.getArtists()
                    .get(0).getName();
            String albumName = track.getAlbum().getName();
            Intent i = new Intent(this.getActivity(), MusicPlayerActivity.class);
            if(previewURLToMusicPlayer==null){
                Toast.makeText(getContext(),"Selected track doesn't have a preview URL",Toast.LENGTH_SHORT).show();
            }
            else {
                i.putExtra("preview_url",previewURLToMusicPlayer);
                i.putExtra("track name",trackNameToMusicPlayer);
                i.putExtra("track",track);
                i.putExtra("artist name",artistNameToMusicPlayer);
//            i.putExtra("track Color Filter",itsColor);
                i.putExtra("album name", albumName);
//            i.putExtra("These are the tracks",tracksSearch);
                i.putExtra("navigation_context", "search");
                startActivity(i);
            }

        }

    }

}