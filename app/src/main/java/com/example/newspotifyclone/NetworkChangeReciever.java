package com.example.newspotifyclone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(NetworkUtils.isNetWorkAvailable(context.getApplicationContext())){
            Intent reloadIntent = new Intent("com.example.newspotifyclone.RELOAD_MAIN_ACTIVITY");
            context.sendBroadcast(reloadIntent);
        }
    }
}
