package com.example.newspotifyclone.helper;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ArtistItemDecoration extends RecyclerView.ItemDecoration {

    private int selectedPosition = -1;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        if (position == selectedPosition) {
            Log.e("this is the position : ",position+"");
            outRect.top = 5;
        }
    }
}

