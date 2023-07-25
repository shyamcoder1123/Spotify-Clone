package com.example.newspotifyclone;

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

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

