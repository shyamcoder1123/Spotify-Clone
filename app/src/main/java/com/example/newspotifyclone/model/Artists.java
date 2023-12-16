package com.example.newspotifyclone.model;

import com.example.newspotifyclone.model.AlbumModel;

import java.util.ArrayList;
public class Artists {
    private String href;

    public ArrayList<AlbumModel.Album.Artist> getItems() {
        return items;
    }

    public void setItems(ArrayList<AlbumModel.Album.Artist> items) {
        this.items = items;
    }

    ArrayList<AlbumModel.Album.Artist> items = new ArrayList <> ();
    private float limit;
    private String next;
    private float offset;
    private String previous = null;
    private float total;


    // Getter Methods

    public String getHref() {
        return href;
    }

    public float getLimit() {
        return limit;
    }

    public String getNext() {
        return next;
    }

    public float getOffset() {
        return offset;
    }

    public String getPrevious() {
        return previous;
    }

    public float getTotal() {
        return total;
    }

    // Setter Methods

    public void setHref(String href) {
        this.href = href;
    }

    public void setLimit(float limit) {
        this.limit = limit;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}

