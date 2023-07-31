package com.example.newspotifyclone.model;


import com.example.newspotifyclone.model.AlbumModel;

import java.io.Serializable;
import java.util.ArrayList;

public class IndividualAlbumModel implements Serializable{
    private ArrayList <AlbumModel.Album.Image> images;

    public ArrayList<AlbumModel.Album.Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<AlbumModel.Album.Image> images) {
        this.images = images;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getTotal_tracks() {
        return total_tracks;
    }

    public void setTotal_tracks(int total_tracks) {
        this.total_tracks = total_tracks;
    }


    public External_urls getExternal_urlsObject() {
        return External_urlsObject;
    }

    public void setExternal_urlsObject(External_urls external_urlsObject) {
        External_urlsObject = external_urlsObject;
    }

    public ArrayList<CopyRight> getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(ArrayList<CopyRight> copyrights) {
        this.copyrights = copyrights;
    }

    ArrayList<CopyRight> copyrights;
    String label;
    int total_tracks;

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    Tracks tracks;
    External_urls External_urlsObject;
    public External_urls getExternal_urls() {
        return External_urlsObject;
    }
    public void setExternal_urls(External_urls external_urlsObject) {
        this.External_urlsObject = external_urlsObject;
    }

    public class External_urls implements Serializable{
        private String spotify;


        // Getter Methods

        public String getSpotify() {
            return spotify;
        }

        // Setter Methods

        public void setSpotify(String spotify) {
            this.spotify = spotify;
        }
    }

    public class Artist implements Serializable{

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

        private String href;
        private String id;
        private String type;
        private String name;
    }
    public class Image implements Serializable{
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
    public class CopyRight implements Serializable{
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        String text;
        String type;
    }
    public class Tracks implements Serializable {
        String href;

        public ArrayList<TrackIndividual> getItems() {
            return items;
        }

        public void setItems(ArrayList<TrackIndividual> items) {
            this.items = items;
        }

        ArrayList<TrackIndividual> items;
        public class TrackIndividual implements Serializable{
            ArrayList<Artist> artists;
            String href;
            long duration_ms;
            String id;
            String name;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public ArrayList<Artist> getArtists() {
                return artists;
            }

            public void setArtists(ArrayList<Artist> artists) {
                this.artists = artists;
            }

            public long getDuration_ms() {
                return duration_ms;
            }

            public void setDuration_ms(long duration_ms) {
                this.duration_ms = duration_ms;
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

            public String getPreview_url() {
                return preview_url;
            }

            public void setPreview_url(String preview_url) {
                this.preview_url = preview_url;
            }

            String preview_url;
        }
    }

}
