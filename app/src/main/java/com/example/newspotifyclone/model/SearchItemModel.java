package com.example.newspotifyclone.model;

public class SearchItemModel {
    private String searchItemName;
    private String urlToImage;

    public SearchItemModel(String searchItemName, String urlToImage) {
        this.searchItemName = searchItemName;
        this.urlToImage = urlToImage;
    }

    public String getSearchItemName() {
        return searchItemName;
    }

    public void setSearchItemName(String searchItemName) {
        this.searchItemName = searchItemName;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
