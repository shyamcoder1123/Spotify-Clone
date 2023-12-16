package com.example.newspotifyclone.helper;

import static com.example.newspotifyclone.helper.ApplicationClass.ACTION_NEXT;
import static com.example.newspotifyclone.helper.ApplicationClass.ACTION_PREVIOUS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReciever extends BroadcastReceiver {

    ActionClicked actionClicked;

    public NotificationReciever(ActionClicked actionClicked) {
        this.actionClicked = actionClicked;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int position;
        String action = intent.getAction();

        if (action != null) {
            if (action.equals(ACTION_PREVIOUS)) {
                actionClicked.onActionClicked(ACTION_PREVIOUS);
                // Handle the previous (backward) action here
                // Move to the previous song in your list
            } else if (action.equals(ACTION_NEXT)) {
                actionClicked.onActionClicked(ACTION_NEXT);
                // Handle the next (forward) action here
                // Move to the next song in your list
            }
        }
    }

    public void setActionClicked(ActionClicked actionClicked) {
        this.actionClicked = actionClicked;
    }

    public interface ActionClicked{
        void onActionClicked(String action);
    }
}
