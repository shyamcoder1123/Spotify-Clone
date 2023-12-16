package com.example.newspotifyclone;

import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.View;
import android.widget.ImageView;

public class MusicPlayerClickHandler {
    Context context;
    private boolean full = false;
    private AnimatedVectorDrawable emptyHeart;
    private AnimatedVectorDrawable fillHeart;
    public MusicPlayerClickHandler(Context context){
        this.context=context;

        emptyHeart
                = (AnimatedVectorDrawable)
                getDrawable(context,
                        R.drawable.avd_heart_empty);
        fillHeart
                = (AnimatedVectorDrawable)
                getDrawable(context,
                        R.drawable.avd_heart_fill);
    }

    public void onButton1Click(View view){
        animate((ImageView) view);
    }
    public void animate(ImageView imgView)
    {
        AnimatedVectorDrawable drawable
                = full
                ? emptyHeart
                : fillHeart;
        imgView.setImageDrawable(drawable);
        drawable.start();
        full = !full;
    }
}
