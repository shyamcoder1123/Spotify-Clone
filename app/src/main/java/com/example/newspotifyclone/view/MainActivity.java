package com.example.newspotifyclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newspotifyclone.NetworkUtils;
import com.example.newspotifyclone.R;
import com.example.newspotifyclone.databinding.ActivityMainBinding;
import com.example.newspotifyclone.helper.ActionPlaying;
import com.example.newspotifyclone.view.HomeFragment;
import com.example.newspotifyclone.view.LibraryFragment;
import com.example.newspotifyclone.view.PremiumFragment;
import com.example.newspotifyclone.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements SearchFragment0.OnSearchClickListener,HomeFragment.OnSettingsClickListener, ActionPlaying {
    ActivityMainBinding activityMainBinding;
    private BroadcastReceiver reloadReciever;
    ProgressBar trackProgressBar;
    boolean isPlaying=false;
    Fragment selectedFragment;
    int selectedFragmentId;
    Stack<String> menuItems = new Stack<>();
    boolean areWeInTheSearch0Mode=true;
    boolean appLaunched=false;
    int menuItemsNumberIndicator;
    boolean continuosBackPress=false;
    int position;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        setContentView(R.layout.activity_main);

//                getWindow().setEnterTransition(new Explode());
        // Set an exit transition
//        getWindow().setExitTransition(new Explode());
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        getWindow().setReturnTransition(new Slide(Gravity.RIGHT));

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        String fragmentToShow = getIntent().getStringExtra("fragment_to_show");

        trackProgressBar=activityMainBinding.trackProgressMain;
        trackProgressBar.setVisibility(View.VISIBLE);
        reloadReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadContent();
            }
        };
        IntentFilter intentFilter = new IntentFilter("com.example.newspotifyclone.RELOAD_MAIN_ACTIVITY");
        registerReceiver(reloadReciever, intentFilter);
        if(NetworkUtils.isNetWorkAvailable(this)){
            loadContent();
            trackProgressBar.setVisibility(View.GONE);
        }
        else {
            trackProgressBar.setVisibility(View.GONE);
            Toast.makeText(this,getResources().getString(R.string.nointernet),Toast.LENGTH_LONG).show();
        }

        bottomNavigationView = activityMainBinding.bottomNav;
        bottomNavigationView.setOnItemSelectedListener(navListener);

        if ("fragment_library".equals(fragmentToShow)) {
            // Show the desired fragment in the activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainPart, new LibraryFragment())
                    .commit();

            MenuItem selectedItem = bottomNavigationView.getMenu().findItem(R.id.library);
            if (selectedItem != null) {
                selectedItem.setChecked(true);
            }
        }
    }

    @Override
    public void onBackPressed() {
//        if (!fragmentsBackStack.isEmpty()) {
//            // Pop the top fragment from the stack
//            Fragment previousFragment = fragmentsBackStack.remove(fragmentsBackStack.size() - 1);
//
//            // Perform the fragment transaction to go back to the previous fragment
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.mainPart, previousFragment)
//                    .commit();
//        } else {
//            // If the stack is empty, handle the back press as usual (e.g., exit the app)
//            super.onBackPressed();
//        }
        Log.e("selected menuIndicator,size", (menuItemsNumberIndicator+" "+menuItems.size()));
        if(!menuItems.isEmpty()&&menuItemsNumberIndicator==menuItems.size() && !continuosBackPress){
            Log.e("vb","hvhjhjhj");
            menuItemsNumberIndicator--;
            String popedItem=menuItems.pop();
            continuosBackPress=true;
            if(!areWeInTheSearch0Mode && Integer.parseInt(popedItem)==R.id.search){
                menuItemsNumberIndicator++;
                menuItems.add(popedItem);
                continuosBackPress=false;
            }
        }
        super.onBackPressed();
        if(!menuItems.isEmpty()){
            menuItemsNumberIndicator--;
            int selectedMenu=Integer.parseInt(menuItems.pop());
            MenuItem item = bottomNavigationView.getMenu().findItem(selectedMenu);
            if (item != null) {
                item.setChecked(true);
            }
            if(!areWeInTheSearch0Mode && selectedMenu==R.id.search){
                areWeInTheSearch0Mode=true;
                menuItems.add(selectedMenu+"");
                menuItemsNumberIndicator++;
            }
        }
        else{
            MenuItem item = bottomNavigationView.getMenu().findItem(R.id.home);
            if (item != null) {
                item.setChecked(true);
            }
        }
    }

    private final NavigationBarView.OnItemSelectedListener navListener = item -> {
        if(!appLaunched){
            menuItems.add(String.valueOf(R.id.home));
            appLaunched=!appLaunched;
            menuItemsNumberIndicator++;
        }
        selectedFragment = new HomeFragment();
        int itemId = item.getItemId();
        if(itemId == R.id.home){
            selectedFragment = new HomeFragment();
        }else if (itemId == R.id.search){
            selectedFragment = new SearchFragment0();
        }else if (itemId == R.id.library){
            selectedFragment = new LibraryFragment();
        }else
            selectedFragment = new PremiumFragment();

        if(selectedFragment!=null){
//            if(fragmentsBackStack.contains(selectedFragment)){
//                Log.e("hbgc","bhbh");
//                fragmentsBackStack.remove(selectedFragment);
//            }
//            fragmentsBackStack.add(selectedFragment);
            selectedFragmentId = itemId;
            continuosBackPress=false;
            menuItemsNumberIndicator++;
            menuItems.add(selectedFragmentId+"");
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.mainPart
                    ,selectedFragment).addToBackStack(null).commit();
        }
        return true;
    };
    private void loadContent(){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainPart
                ,new HomeFragment()).commit();
    }
    private void refresh(int millis){
        final Handler handler=new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadContent();
            }
        };
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reloadReciever);
    }

    @Override
    public void onSearchClickListener() {
        areWeInTheSearch0Mode=false;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainPart
                ,new SearchFragment(),"").addToBackStack(null).commit();
    }

    @Override
    public void onSettingsClickListener() {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainPart
                ,new SettingsFragment(),"").addToBackStack(null).commit();
    }

    @Override
    public void nextClicked() {
        if(position==3){
            position=0;
        }else position++;
    }

    @Override
    public void prevClicked() {
        if(position==0){
            position=3;
        }else position--;
    }

    @Override
    public void playClicked() {
        isPlaying=!isPlaying;
    }
}