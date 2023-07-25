package com.example.newspotifyclone.model;

public class ArtistModel {
    String artistName;
    String artistImage;

    public ArtistModel(String artistName, String artistImage) {
        this.artistName = artistName;
        this.artistImage=artistImage;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistImage() {
        return artistImage;
    }

    public void setArtistImage(String artistImage) {
        this.artistImage = artistImage;
    }
}
