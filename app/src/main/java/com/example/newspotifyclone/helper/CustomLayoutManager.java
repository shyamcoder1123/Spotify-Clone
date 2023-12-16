package com.example.newspotifyclone.helper;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManager extends LinearLayoutManager {
    private static final float SCROLL_FACTOR = 0.5f; // Adjust the scroll speed as needed

    public CustomLayoutManager(Context context) {
        super(context);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollVerticallyBy((int) (dy * SCROLL_FACTOR), recycler, state);
        // Apply additional custom scroll behavior here if needed
        return scrolled;
    }
}

