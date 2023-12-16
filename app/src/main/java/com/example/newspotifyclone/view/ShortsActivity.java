package com.example.newspotifyclone.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.databinding.ActivityShortsBinding;
import com.example.newspotifyclone.helper.ShortsVideoAdapter;
import com.example.newspotifyclone.model.VideoObjectForShorts;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ShortsActivity extends AppCompatActivity implements ShortsVideoAdapter.CommentInterface {
    ActivityShortsBinding activityShortsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts);

        activityShortsBinding= DataBindingUtil.setContentView(this,R.layout.activity_shorts);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);


        ShortsVideoAdapter shortsVideoAdapter;
        List<VideoObjectForShorts> videoObjectsForShorts=new ArrayList<>();
        final ViewPager2 viewPager2= activityShortsBinding.shortsViewPager;
        VideoObjectForShorts videoObjectForShorts1 = new VideoObjectForShorts(false,false,"SSChannel","new content");
        videoObjectForShorts1.setLink("android.resource://"+getPackageName()+"/"+R.raw.first);
        videoObjectsForShorts.add(videoObjectForShorts1);
        VideoObjectForShorts videoObjectForShorts2 = new VideoObjectForShorts(false,false,"SSChannel","new content");
        videoObjectForShorts2.setLink("https://drive.google.com/uc?export=download&id=1EhPGUNqAOSHpzmLb4nQ_qGJaGqjp-Clj");
        videoObjectsForShorts.add(videoObjectForShorts2);
        VideoObjectForShorts videoObjectForShorts3 = new VideoObjectForShorts(false,false,"SSChannel","new content guyguhuihuhuhuiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiidghbdffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffdvccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
        videoObjectForShorts3.setLink("https://drive.google.com/uc?export=download&id=1V25YxlkXgX1QeTVFXGTAeyQpu2sTPVJ8");
        videoObjectsForShorts.add(videoObjectForShorts3);
        VideoObjectForShorts videoObjectForShorts4 = new VideoObjectForShorts(false,false,"SS","new content");
        videoObjectForShorts4.setLink("https://drive.google.com/uc?export=download&id=1i251d_QgEaiTEMWWewCPFWfNUQF44_YD");
        videoObjectsForShorts.add(videoObjectForShorts4);
        shortsVideoAdapter= new ShortsVideoAdapter(videoObjectsForShorts,this::onCommentClick);
        viewPager2.setAdapter(shortsVideoAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCommentClick() {
//        CommentPopUpFragment commentDialog = new CommentPopUpFragment();
//        commentDialog.show(getSupportFragmentManager(), commentDialog.getTag());
        // Create a BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ShortsActivity.this, R.style.AppBottomSheetDialogTheme);

// Inflate the layout for your Bottom Sheet content
        View bottomSheetView = LayoutInflater.from(ShortsActivity.this).inflate(R.layout.fragment_comment_pop_up, null);

// Customize the Bottom Sheet content (e.g., set text, click listeners, etc.)
// ...

// Set the view for the Bottom Sheet
        bottomSheetDialog.setContentView(bottomSheetView);

// Show the Bottom Sheet
        bottomSheetDialog.show();

    }
}