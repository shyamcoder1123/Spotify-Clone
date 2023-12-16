package com.example.newspotifyclone.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.AlbumObject;
import com.example.newspotifyclone.model.ArtistModels;
import com.example.newspotifyclone.repository.MusicHomeRepo;

import java.util.List;

public class HomeFragmentMusicViewModel extends AndroidViewModel {
    private MusicHomeRepo musicHomeRepository;

    public HomeFragmentMusicViewModel(@NonNull Application application) {
        super(application);
        this.musicHomeRepository = new MusicHomeRepo(application);
    }

    public LiveData<List<AlbumModel.Album>> getArtistAlbums(String artistId){
        return musicHomeRepository.getArtistAlbumResult(artistId);
    }
    public LiveData<ArtistModels.Artist> getArtistById(String artistId){
        return musicHomeRepository.getArtistById(artistId);
    }

    public LiveData<List<ArtistModels.Artist>> getArrayOfArtistByIds(){
        return musicHomeRepository.getArrayOfArtistByIds();
    }
    public LiveData<AlbumObject> getAlbumSuggestions(String ids){
        return musicHomeRepository.getAlbumSuggestions(ids);
    }
}
