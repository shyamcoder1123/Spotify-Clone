package com.example.newspotifyclone.helper;

import static com.example.newspotifyclone.helper.ApplicationClass.ACTION_NEXT;
import static com.example.newspotifyclone.helper.ApplicationClass.ACTION_PREVIOUS;

public class NotificationActionHandler implements NotificationReciever.ActionClicked {
    NotificationReciever.ActionClicked actionClicked;

//    public NotificationActionHandler(NotificationReciever.ActionClicked actionClicked) {
//        this.actionClicked = actionClicked;
//    }

    @Override
    public void onActionClicked(String action) {
        // Handle the notification action here based on the 'action' parameter
        if (action.equals(ACTION_PREVIOUS)) {

            // Handle previous action
        } else if (action.equals(ACTION_NEXT)) {
            // Handle next action
        }
    }
}

