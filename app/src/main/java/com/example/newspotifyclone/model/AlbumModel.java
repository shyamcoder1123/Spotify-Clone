package com.example.newspotifyclone.model;

import java.util.ArrayList;

public class AlbumModel {

    ArrayList<Album> items;
    public ArrayList<Album> getItems() {
        return items;
    }

    public void setItems(ArrayList<Album> items) {
        this.items = items;
    }
    public class Album{

        private ArrayList<Artist> artists;

        private ArrayList <Image> images;
        private String name;
        String id;
        String spotifyLink;
        String albumLink;
        int totalTracks;

        public ArrayList<Artist> getArtists() {
            return artists;
        }

        public void setArtists(ArrayList<Artist> artists) {
            this.artists = artists;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Image> getImages() {
            return images;
        }

        public void setImages(ArrayList<Image> images) {
            this.images = images;
        }

        public String getSpotifyLink() {
            return spotifyLink;
        }

        public void setSpotifyLink(String spotifyLink) {
            this.spotifyLink = spotifyLink;
        }

        public String getAlbumLink() {
            return albumLink;
        }

        public void setAlbumLink(String albumLink) {
            this.albumLink = albumLink;
        }

        public int getTotalTracks() {
            return totalTracks;
        }

        public void setTotalTracks(int totalTracks) {
            this.totalTracks = totalTracks;
        }

        public class Image{
            private int width;
            private int height;
            private String url;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

        }
        public class Artist{
            private ArtistModels.External_urls External_urlsObject;
            private String href;
            private String id;
            private String type;
            private String name;
            public ArtistModels.External_urls getExternal_urls() {
                return External_urlsObject;
            }

            public void setExternal_urls(ArtistModels.External_urls external_urlsObject) {
                this.External_urlsObject = external_urlsObject;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }
    }


}
