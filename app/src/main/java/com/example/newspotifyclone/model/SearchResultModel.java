package com.example.newspotifyclone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// SearchResult.java
public class SearchResultModel {
//    private Map<String, List<Object>> searchData=new HashMap<>();
    private ArrayList<Object> searchData;

    public ArrayList<Object> getSearchData() {
        return searchData;
    }

    public void setSearchData(ArrayList<Object> searchData) {
        this.searchData = searchData;
    }
}