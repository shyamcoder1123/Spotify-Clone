package com.example.newspotifyclone.model;

public class SongModel {
    private String songName;
    private String artistName;
    private String songImage;

    public SongModel(String songName, String artistName, String songImage) {
        this.songName = songName;
        this.artistName = artistName;
        this.songImage = songImage;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }
}
