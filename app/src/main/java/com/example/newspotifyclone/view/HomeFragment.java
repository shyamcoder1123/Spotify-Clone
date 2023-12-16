package com.example.newspotifyclone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newspotifyclone.NetworkUtils;
import com.example.newspotifyclone.databinding.FragmentHomeBinding;
import com.example.newspotifyclone.helper.ArtistListRecyclerAdapter;
import com.example.newspotifyclone.helper.AlbumItemRecyclerAdapter;
import com.example.newspotifyclone.ApiManager;
import com.example.newspotifyclone.R;
import com.example.newspotifyclone.helper.DifferentItemsRecyclerAdapter;
import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.AlbumObject;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.model.DifferentModelsObject;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.example.newspotifyclone.model.SongModel;
import com.example.newspotifyclone.viewmodel.HomeFragmentMusicViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements AlbumItemRecyclerAdapter.CategoryInterface,ArtistListRecyclerAdapter.CategoryInterface,DifferentItemsRecyclerAdapter.DifferentItemCategoryInterface {

    FragmentHomeBinding fragmentHomeBinding;
    boolean areWeOnline=false;
    ArrayList<SongModel> songModelArrayList;
    IndividualAlbumModel.Tracks tracksObject;
    ArrayList<AlbumModel.Album> albumModelArrayList1,albumModelArrayList3,albumModelArrayList4,
            albumModelArrayList5;

    private RecyclerView recyclerView1,recyclerView2,recyclerView3,recyclerView4,recyclerView5,
            differentObjectsRecyclerView;
    View includeArtist,includeArtist1,includeArtist2;
    private HomeFragmentMusicViewModel homeFragmentMusicViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    TextView artistName,artistName1,artistName2;
    ImageView artistImage,artistImage1,artistImage2;

    DifferentItemsRecyclerAdapter differentItemsRecyclerAdapter;
    ArrayList<DifferentModelsObject> differentModelsObjectArrayList;

    AlbumItemRecyclerAdapter albumItemRecyclerAdapter1,albumItemRecyclerAdapter3,
            albumItemRecyclerAdapter4,albumItemRecyclerAdapter5;

    ArtistListRecyclerAdapter artistListRecyclerAdapter;
    ArrayList<ArtistModels.Artist> artistArrayList;

    ApiManager apiManager;

    public String name = "home";
    int i=0;
    View mainPart;

    View toolbarHomeFragment;
    ImageView settingsImageView;
    private OnSettingsClickListener settingsClickListener;
    private Handler handler = new Handler();
    private Runnable runnable;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SearchFragment0.OnSearchClickListener){
            settingsClickListener = (OnSettingsClickListener) context;
        }else {
            throw new RuntimeException(context.toString()+
                    " must implement onSearchClickListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        View view=fragmentHomeBinding.getRoot();
        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mainPart=fragmentHomeBinding.mainPartHome;
        mainPart.setVisibility(View.GONE);
        shimmerFrameLayout=fragmentHomeBinding.shimmerHome;
        homeFragmentMusicViewModel = new ViewModelProvider(this)
                .get(HomeFragmentMusicViewModel.class);

        differentObjectsRecyclerView=view.findViewById(R.id.itemNameDifferentModelRecycler);
        toolbarHomeFragment = fragmentHomeBinding.toolbarHomeFragment;

        settingsImageView=toolbarHomeFragment.findViewById(R.id.settingsImage);

        artistArrayList=new ArrayList<>();
        artistListRecyclerAdapter = new ArtistListRecyclerAdapter(artistArrayList,this.getContext(),this,"normal");

        recyclerView1=fragmentHomeBinding.albumRecycler1;
        recyclerView2=fragmentHomeBinding.popularArtistRecycler;
        ///This one is the recycler blow our artist---------------

        recyclerView3=fragmentHomeBinding.songRecyclerArtist;

        //----------------------------------------------------------

        recyclerView4=fragmentHomeBinding.songRecycler4;

        recyclerView5=fragmentHomeBinding.songRecycler5;

        includeArtist = fragmentHomeBinding.firstArtist1;
        artistName = includeArtist.findViewById(R.id.artistName);
        artistImage = includeArtist.findViewById(R.id.artistImage);
        includeArtist1 = fragmentHomeBinding.secondArtist;
        artistName1 = includeArtist1.findViewById(R.id.artistName);
        artistImage1 = includeArtist1.findViewById(R.id.artistImage);
        includeArtist2 = fragmentHomeBinding.thirdArtist;
        artistName2 = includeArtist2.findViewById(R.id.artistName);
        artistImage2 = includeArtist2.findViewById(R.id.artistImage);
        swipeRefreshLayout = fragmentHomeBinding.swipeRefreshLayoutHome;
        shimmerFrameLayout.startShimmer();
        swipeRefreshLayout.setEnabled(false);
        mainPart.setVisibility(View.GONE);
        startContentLoading();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(areWeOnline){
                    if(!NetworkUtils.isNetWorkAvailable(getContext())){
                        swipeRefreshLayout.setEnabled(false);
                        mainPart.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                        areWeOnline=false;
                    }
                    else {
                        mainPart.setVisibility(View.VISIBLE);
//                        getAlbumSuggestions("382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
//                        getArtist(getContext().getString(R.string.arijitId),artistName,artistImage);
//                        getArtist(getContext().getString(R.string.unknown1),artistName1,artistImage1);
//                        getArtist(getContext().getString(R.string.unknown2),artistName2,artistImage2);
//                        getArtistAlbumResult(getContext().getString(R.string.arijitId),albumModelArrayList3);
//                        getArtistAlbumResult(getContext().getString(R.string.unknown1),albumModelArrayList4);
//                        getArtistAlbumResult(getContext().getString(R.string.unknown2),albumModelArrayList5);
//                        getPopularArtists();
                        albumItemRecyclerAdapter1.notifyDataSetChanged();
                        albumItemRecyclerAdapter3.notifyDataSetChanged();
                        albumItemRecyclerAdapter4.notifyDataSetChanged();
                        albumItemRecyclerAdapter5.notifyDataSetChanged();
                        artistListRecyclerAdapter.notifyDataSetChanged();
                    }
                }else {
                    if(!NetworkUtils.isNetWorkAvailable(getContext())){
                        swipeRefreshLayout.setEnabled(false);
                        mainPart.setVisibility(View.GONE);
                    }else {
                        areWeOnline=true;
                        Toast.makeText(getContext(),"You are online",Toast.LENGTH_SHORT).show();
                        mainPart.setVisibility(View.VISIBLE);
//                        getAlbumSuggestions("382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
//                        getArtist(getContext().getString(R.string.arijitId),artistName,artistImage);
//                        getArtist(getContext().getString(R.string.unknown1),artistName1,artistImage1);
//                        getArtist(getContext().getString(R.string.unknown2),artistName2,artistImage2);
//                        getArtistAlbumResult(getContext().getString(R.string.arijitId),albumModelArrayList3);
//                        getArtistAlbumResult(getContext().getString(R.string.unknown1),albumModelArrayList4);
//                        getArtistAlbumResult(getContext().getString(R.string.unknown2),albumModelArrayList5);
//                        getPopularArtists();
                        albumItemRecyclerAdapter1.notifyDataSetChanged();
                        albumItemRecyclerAdapter3.notifyDataSetChanged();
                        albumItemRecyclerAdapter4.notifyDataSetChanged();
                        albumItemRecyclerAdapter5.notifyDataSetChanged();
                        artistListRecyclerAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                if(areWeOnline){
                    if(!NetworkUtils.isNetWorkAvailable(getContext())){
                        mainPart.setVisibility(View.GONE);
                        shimmerFrameLayout.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.startShimmer();
                        Toast.makeText(getContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                        areWeOnline=false;
                    }
                    else {
                        mainPart.setVisibility(View.VISIBLE);
//                        getAlbumSuggestions("382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
//                        getArtist(getContext().getString(R.string.arijitId),artistName,artistImage);
//                        getArtist(getContext().getString(R.string.unknown1),artistName1,artistImage1);
//                        getArtist(getContext().getString(R.string.unknown2),artistName2,artistImage2);
//                        getArtistAlbumResult(getContext().getString(R.string.arijitId),albumModelArrayList3);
//                        getArtistAlbumResult(getContext().getString(R.string.unknown1),albumModelArrayList4);
//                        getArtistAlbumResult(getContext().getString(R.string.unknown2),albumModelArrayList5);
//                        getPopularArtists();
//                        albumItemRecyclerAdapter1.notifyDataSetChanged();
//                        albumItemRecyclerAdapter3.notifyDataSetChanged();
//                        albumItemRecyclerAdapter4.notifyDataSetChanged();
//                        albumItemRecyclerAdapter5.notifyDataSetChanged();
//                        artistListRecyclerAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    if(!NetworkUtils.isNetWorkAvailable(getContext())){
                        swipeRefreshLayout.setEnabled(false);
                        mainPart.setVisibility(View.GONE);
                    }else {
                        areWeOnline=true;
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"You are online",Toast.LENGTH_SHORT).show();
                        mainPart.setVisibility(View.VISIBLE);
//                        getAlbumSuggestions("382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
//                        getArtist(getContext().getString(R.string.arijitId),artistName,artistImage);
//                        getArtist(getContext().getString(R.string.unknown1),artistName1,artistImage1);
//                        getArtist(getContext().getString(R.string.unknown2),artistName2,artistImage2);
//                        getArtistAlbumResult(getContext().getString(R.string.arijitId),albumModelArrayList3);
//                        getArtistAlbumResult(getContext().getString(R.string.unknown1),albumModelArrayList4);
//                        getArtistAlbumResult(getContext().getString(R.string.unknown2),albumModelArrayList5);
//                        getPopularArtists();
//                        albumItemRecyclerAdapter1.notifyDataSetChanged();
//                        albumItemRecyclerAdapter3.notifyDataSetChanged();
//                        albumItemRecyclerAdapter4.notifyDataSetChanged();
//                        albumItemRecyclerAdapter5.notifyDataSetChanged();
//                        artistListRecyclerAdapter.notifyDataSetChanged();
                    }
                }
                // Update UI here
                // Post the Runnable again after a delay
                handler.postDelayed(this, 8000);
            }
        };
    }

    private void getArtistMusic1(String id){
        homeFragmentMusicViewModel.getArtistAlbums(id).observe(getViewLifecycleOwner(), new Observer<List<AlbumModel.Album>>() {
            @Override
            public void onChanged(List<AlbumModel.Album> albumsFromLivedata) {
                albumModelArrayList3 = (ArrayList<AlbumModel.Album>) albumsFromLivedata;//provide proper arraylist
                displayArtistSongsInRecyclerView1();
            }
        });
    }
    private void getArtistMusic2(String id){
        homeFragmentMusicViewModel.getArtistAlbums(id).observe(getViewLifecycleOwner(), new Observer<List<AlbumModel.Album>>() {
            @Override
            public void onChanged(List<AlbumModel.Album> albumsFromLivedata) {
                albumModelArrayList4 = (ArrayList<AlbumModel.Album>) albumsFromLivedata;//provide proper arraylist
                displayArtistSongsInRecyclerView2();
            }
        });
    }
    private void getArtistMusic3(String id){
        homeFragmentMusicViewModel.getArtistAlbums(id).observe(getViewLifecycleOwner(), new Observer<List<AlbumModel.Album>>() {
            @Override
            public void onChanged(List<AlbumModel.Album> albumsFromLivedata) {
                albumModelArrayList5 = (ArrayList<AlbumModel.Album>) albumsFromLivedata;//provide proper arraylist
                displayArtistSongsInRecyclerView3();
            }
        });
    }

    private void displayArtistSongsInRecyclerView1() {//implement this method
        albumItemRecyclerAdapter3 = new AlbumItemRecyclerAdapter(albumModelArrayList3
                ,getContext(),this::onCategoryClick);

        recyclerView3.setAdapter(albumItemRecyclerAdapter3);
        albumItemRecyclerAdapter3.notifyDataSetChanged();
    }
    private void displayArtistSongsInRecyclerView2() {//implement this method

        albumItemRecyclerAdapter4 = new AlbumItemRecyclerAdapter(albumModelArrayList4
                ,getContext(),this::onCategoryClick);

        recyclerView4.setAdapter(albumItemRecyclerAdapter4);
        albumItemRecyclerAdapter4.notifyDataSetChanged();
    }
    private void displayArtistSongsInRecyclerView3() {//implement this method

        albumItemRecyclerAdapter5 = new AlbumItemRecyclerAdapter(albumModelArrayList5
                ,getContext(),this::onCategoryClick);

        recyclerView5.setAdapter(albumItemRecyclerAdapter5);
        albumItemRecyclerAdapter5.notifyDataSetChanged();
    }

    private void getArtist(String id){
        homeFragmentMusicViewModel.getArtistById(id).observe(getViewLifecycleOwner(), new Observer<ArtistModels.Artist>() {
            @Override
            public void onChanged(ArtistModels.Artist artist) {
                ArtistModels.Artist artistModel = artist;
                artistName.setText(artistModel.getName());
                Picasso.get().load(artistModel.getImages().get(0).getUrl()).into(artistImage);
            }
        });
    }

    private void getArrayOfArtists(){
        homeFragmentMusicViewModel.getArrayOfArtistByIds().observe(getViewLifecycleOwner(), new Observer<List<ArtistModels.Artist>>() {
            @Override
            public void onChanged(List<ArtistModels.Artist> artistsFromLiveData) {
                artistArrayList = (ArrayList<ArtistModels.Artist>) artistsFromLiveData;
                displayArtistsInRecyclerView();
            }
        });
    }
    private void displayArtistsInRecyclerView() {//implement this method
        artistListRecyclerAdapter = new ArtistListRecyclerAdapter(artistArrayList,this.getContext(),this,"normal");

        recyclerView2.setAdapter(artistListRecyclerAdapter);
        artistListRecyclerAdapter.notifyDataSetChanged();
    }

    private void getAlbumSuggestions(String ids){
        homeFragmentMusicViewModel.getAlbumSuggestions(ids).observe(getViewLifecycleOwner(), new Observer<AlbumObject>() {
            @Override
            public void onChanged(AlbumObject albumObject) {
                albumModelArrayList1 = albumObject.getAlbums();
                displayAlbumSuggestionsInRecyclerView();
            }
        });
    }
    private void displayAlbumSuggestionsInRecyclerView() {//implement this method
        albumItemRecyclerAdapter1=new AlbumItemRecyclerAdapter(albumModelArrayList1,getContext(),this::onCategoryClick);
        recyclerView1.setAdapter(albumItemRecyclerAdapter1);
        albumItemRecyclerAdapter1.notifyDataSetChanged();
    }


    @Override
    public void onStart() {
        super.onStart();
        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }


    //-----------------------------------------------------------------------
//    public void getAlbumSuggestions(String ids){
//        apiManager.getAlbumSuggestions(ids, new Callback<AlbumObject>() {
//            @Override
//            public void onResponse(Call<AlbumObject> call, Response<AlbumObject> response) {
//                AlbumObject albumObject = response.body();
//                if(albumObject!=null){
//                    if(albumObject.getAlbums()!=null){
//                        albumModelArrayList1.addAll(albumObject.getAlbums());
//                        recyclerView1.setAdapter(albumItemRecyclerAdapter1);
//                        albumItemRecyclerAdapter1.notifyDataSetChanged();
//
//                    }
//                    else{
//                        Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
//                        mainPart.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AlbumObject> call, Throwable t) {
//                t.printStackTrace();
//                mainPart.setVisibility(View.GONE);
//            }
//        });
//    }
//    public void  getArtist(String id, TextView artist, ImageView artistImg){
//        apiManager.recommendedArtistsApiCall(id, new Callback<ArtistModels.Artist>() {
//            @Override
//            public void onResponse(Call<ArtistModels.Artist> call, Response<ArtistModels.Artist> response) {
//                ArtistModels.Artist artistModel = response.body();
//                if(artistModel!=null){
//                    artist.setText(artistModel.getName());
//                    Picasso.get().load(artistModel.getImages().get(0).getUrl()).into(artistImg);
//                }
//                else{
//                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
//                    mainPart.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArtistModels.Artist> call, Throwable t) {
//                t.printStackTrace();
//                mainPart.setVisibility(View.GONE);
//            }
//        });
//    }
//    public void  getArtistAlbumResult(String artistId, ArrayList<AlbumModel.Album> albums){
//        apiManager.getArtistAlbumResult(artistId,new Callback<AlbumModel>() {
//            @Override
//            public void onResponse(Call<AlbumModel> call, Response<AlbumModel> response) {
//                AlbumModel albumModel = response.body();
//                if(albumModel!=null){
//                    albums.addAll(albumModel.getItems());
//                    i+=1;
//                    if(i%3==0){
//                        recyclerView3.setAdapter(albumItemRecyclerAdapter3);
//                        recyclerView4.setAdapter(albumItemRecyclerAdapter4);
//                        recyclerView5.setAdapter(albumItemRecyclerAdapter5);
//                        albumItemRecyclerAdapter3.notifyDataSetChanged();
//                        albumItemRecyclerAdapter3.notifyDataSetChanged();
//                        albumItemRecyclerAdapter3.notifyDataSetChanged();
//                        if(i==30)i=0;
////                        albumItemRecyclerAdapter5.notifyDataSetChanged();
//                    }
//
//
//                }
//                else{
//                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
//                    mainPart.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AlbumModel> call, Throwable t) {
//                t.printStackTrace();
//                mainPart.setVisibility(View.GONE);
//            }
//        });
//    }
//    --------------------------------------------------------------
//    On album click

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
                    i.putExtra("navigation_context", "home");
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
//    void getPopularArtists(){
//        apiManager.arrayOfArtistsApiCall(new Callback<ArrayOfArtistsModel>() {
//            @Override
//            public void onResponse(Call<ArrayOfArtistsModel> call, Response<ArrayOfArtistsModel> response) {
//                ArrayOfArtistsModel arrayOfArtistsModel = response.body();
//                swipeRefreshLayout.setEnabled(true);
//                if(arrayOfArtistsModel!=null){
//                    swipeRefreshLayout.setRefreshing(false);
//                    shimmerFrameLayout.stopShimmer();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    mainPart.setVisibility(View.VISIBLE);
//                    artistArrayList.addAll(arrayOfArtistsModel.getArtists());
//                    recyclerView2.setAdapter(artistListRecyclerAdapter);
//                    artistListRecyclerAdapter.notifyDataSetChanged();
//
//                }
//                else{
//                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
//                    mainPart.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayOfArtistsModel> call, Throwable t) {
//                mainPart.setVisibility(View.GONE);
//                t.printStackTrace();
//            }
//        });
//    }
    private void startContentLoading(){
        apiManager = new ApiManager(this.getContext());

        songModelArrayList = new ArrayList<>();
        albumModelArrayList3 = new ArrayList<>();
        albumModelArrayList4 = new ArrayList<>();
        albumModelArrayList5 = new ArrayList<>();


//        albumItemRecyclerAdapter3 = new AlbumItemRecyclerAdapter(albumModelArrayList3
//                ,this.getContext(),this::onCategoryClick);
//        albumItemRecyclerAdapter4 = new AlbumItemRecyclerAdapter(albumModelArrayList4
//                ,this.getContext(),this::onCategoryClick);
//        albumItemRecyclerAdapter5 = new AlbumItemRecyclerAdapter(albumModelArrayList5
//                ,this.getContext(),this::onCategoryClick);

//        getSongs();
        getAlbumSuggestions("382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
//        getArtist(getContext().getString(R.string.arijitId),artistName,artistImage);
//        getArtist(getContext().getString(R.string.unknown1),artistName1,artistImage1);
//        getArtist(getContext().getString(R.string.unknown2),artistName2,artistImage2);
//        getArtistAlbumResult(getContext().getString(R.string.arijitId),albumModelArrayList3);
//        getArtistAlbumResult(getContext().getString(R.string.unknown1),albumModelArrayList4);
//        getArtistAlbumResult(getContext().getString(R.string.unknown2),albumModelArrayList5);
//        getPopularArtists();
        getArrayOfArtists();
        getArtist(requireContext().getString(R.string.arijitId));
        getArtistMusic1(getContext().getString(R.string.arijitId));
        getArtistMusic2(requireContext().getString(R.string.unknown1));
        getArtistMusic3(requireContext().getString(R.string.unknown2));
        differentModelsObjectArrayList=new ArrayList<>();
        differentModelsObjectArrayList.add(new DifferentModelsObject("Music"));
        differentModelsObjectArrayList.add(new DifferentModelsObject("Albums"));
        differentModelsObjectArrayList.add(new DifferentModelsObject("Playlists"));
        differentModelsObjectArrayList.add(new DifferentModelsObject("Podcasts & Shows"));
        differentModelsObjectArrayList.add(new DifferentModelsObject("Artists"));
        differentModelsObjectArrayList.add(new DifferentModelsObject("Shorts"));



        differentItemsRecyclerAdapter=new DifferentItemsRecyclerAdapter(differentModelsObjectArrayList,this::onDifferentCategoryItemClick,getContext());
        differentObjectsRecyclerView.setAdapter(differentItemsRecyclerAdapter);
        settingsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsClickListener.onSettingsClickListener();
            }
        });
    }
    @Override
    public void onCategoryClick(int position, ArrayList<AlbumModel.Album> albums) {
        String category = albums.get(position).getId();
        getAlbumResultFunction(category);
    }


    @Override
    public void onCategoryClick(int position) {
        ArtistModels.Artist artist = artistArrayList.get(position);
        String artistId=artist.getId();
        Intent i = new Intent(this.getActivity(), ArtistActivity.class);
        i.putExtra("artist name",artist.getName());
        i.putExtra("artist id",artistId);
        i.putExtra("artistImageURL",artist.getImages().get(0).getUrl());
        i.putExtra("navigation_context", "home");
        startActivity(i);
    }

    @Override
    public void onAddArtistClick() {

    }

    @Override
    public void onDifferentCategoryItemClick() {
        Intent i = new Intent(getActivity(), ShortsActivity.class);
        startActivity(i);
    }

    public interface OnSettingsClickListener {
        void onSettingsClickListener();
    }
}