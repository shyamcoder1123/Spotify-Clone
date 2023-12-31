package com.example.newspotifyclone.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ArtistModels implements Serializable{
    private ArrayList<Artist> items;
    private String href;

    public ArrayList<Artist> getItems() {
        return items;
    }

    public void setItems(ArrayList<Artist> items) {
        this.items = items;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public static class Artist implements Serializable {
        External_urls External_urlsObject;
        private boolean isSelected;
        Followers FollowersObject;
        ArrayList< Object > genres = new ArrayList < Object > ();
        private String href;
        private String id;
        private ArrayList <Image> images;
        private String name;
        private float popularity;
        private String type;
        private String uri;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        // Getter Methods

        public External_urls getExternal_urls() {
            return External_urlsObject;
        }

        public Followers getFollowers() {
            return FollowersObject;
        }

        public String getHref() {
            return href;
        }

        public String getId() {
            return id;
        }
        public ArrayList<Image> getImages(){
            return images;
        }

        public String getName() {
            return name;
        }

        public float getPopularity() {
            return popularity;
        }

        public String getType() {
            return type;
        }

        public String getUri() {
            return uri;
        }

        // Setter Methods

        public void setExternal_urls(External_urls external_urlsObject) {
            this.External_urlsObject = external_urlsObject;
        }

        public void setFollowers(Followers followersObject) {
            this.FollowersObject = followersObject;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPopularity(float popularity) {
            this.popularity = popularity;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUri(String uri) {
            this.uri = uri;
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
    }
    public class Followers implements Serializable{
        private String href = null;
        private float total;


        // Getter Methods

        public String getHref() {
            return href;
        }

        public float getTotal() {
            return total;
        }

        // Setter Methods

        public void setHref(String href) {
            this.href = href;
        }

        public void setTotal(float total) {
            this.total = total;
        }
    }
    class External_urls implements Serializable{
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
}