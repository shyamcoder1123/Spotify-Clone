package com.example.newspotifyclone.model;

import java.util.List;

// SearchResult.java
public class SearchResult {
    private Tracks tracks;

    public Tracks getTracks() {
        return tracks;
    }
}

// Tracks.java
class Tracks {
    private List<Track> items;

    public List<Track> getItems() {
        return items;
    }
}

// Track.java
class Track {
    private String name;
    private String id;
    // Add other properties as needed
    private String artist;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

