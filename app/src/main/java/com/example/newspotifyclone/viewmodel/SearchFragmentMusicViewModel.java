package com.example.newspotifyclone.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.model.IndividualAlbumModel;
import com.example.newspotifyclone.repository.MusicHomeRepo;
import com.example.newspotifyclone.repository.MusicSearchRepo;

import java.util.List;

public class SearchFragmentMusicViewModel extends AndroidViewModel {

    private MusicSearchRepo musicSearchRepository;

    public SearchFragmentMusicViewModel(@NonNull Application application) {
        super(application);
        this.musicSearchRepository = new MusicSearchRepo(application);
    }

    public LiveData<List<IndividualAlbumModel.Tracks.TrackIndividual>> getSearchResultTracks(String query,String type){
        return musicSearchRepository.getSearchResultTracks(query, type);
    }
    public LiveData<List<AlbumModel.Album>> getSearchResultAlbums(String query, String type){
        return musicSearchRepository.getSearchResultAlbums(query, type);
    }
    public LiveData<List<ArtistModels.Artist>> getSearchResultArtist(String query, String type){
        return musicSearchRepository.getSearchResultArtist(query, type);
    }
}
