package com.example.newspotifyclone.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.databinding.FragmentProfileBinding;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding fragmentProfileBinding;
    View accountView,privacyView, avatarView, chatsView,notificationsView,storageView,
            appLanguageView,helpView,inviteView;
    ImageView profileImage;
    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfileBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        View view=fragmentProfileBinding.getRoot();
        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileImage = fragmentProfileBinding.profileImageSettings;
        Picasso.get().load("https://www.emmegi.co.uk/wp-content/uploads/2019/01/User-Icon.jpg").centerCrop().resize(80,80).into(profileImage);
        accountView = fragmentProfileBinding.account;
        TextView accTextView = accountView.findViewById(R.id.itemTextView);
        accTextView.setText(R.string.account);
        TextView accInformationView = accountView.findViewById(R.id.information);
        accInformationView.setText(R.string.security_notifications_change_number);
        ImageView accImage = accountView.findViewById(R.id.itemImage);
        accImage.setImageResource(R.drawable.key);

        avatarView = fragmentProfileBinding.avatar;

        TextView avtTextView = avatarView.findViewById(R.id.itemTextView);
        avtTextView.setText(R.string.avatar);
        TextView avtInformationView = avatarView.findViewById(R.id.information);
        avtInformationView.setText(R.string.create_edit_profile_photo);
        ImageView avtImage = avatarView.findViewById(R.id.itemImage);
        avtImage.setImageResource(R.drawable.user_avatar_profile);

        privacyView = view.findViewById(R.id.privacy);
        TextView priTextView = privacyView.findViewById(R.id.itemTextView);
        priTextView.setText(R.string.privacy);
        TextView priInformationView = privacyView.findViewById(R.id.information);
        priInformationView.setText(R.string.block_contacts_disappearing_messages);
        ImageView priImage = privacyView.findViewById(R.id.itemImage);
        priImage.setImageResource(R.drawable.lock_keyhole);

        chatsView = view.findViewById(R.id.chats);
        TextView chaTextView = chatsView.findViewById(R.id.itemTextView);
        chaTextView.setText(R.string.chats);
        TextView chaInformationView = chatsView.findViewById(R.id.information);
        chaInformationView.setText(R.string.theme_wallpapers_chat_history);
        ImageView chatImage = chatsView.findViewById(R.id.itemImage);
        chatImage.setImageResource(R.drawable.chat);

        notificationsView = view.findViewById(R.id.notification);
        TextView notiTextView = notificationsView.findViewById(R.id.itemTextView);
        notiTextView.setText(R.string.notifications);
        TextView notiInformationView = notificationsView.findViewById(R.id.information);
        notiInformationView.setText(R.string.message_group_call_tones);
        ImageView notiImage = notificationsView.findViewById(R.id.itemImage);
        notiImage.setImageResource(R.drawable.notification);

        storageView = view.findViewById(R.id.storageAndData);
        TextView storageTextView = storageView.findViewById(R.id.itemTextView);
        storageTextView.setText(R.string.storage_and_data);
        TextView storageInformationView = storageView.findViewById(R.id.information);
        storageInformationView.setText(R.string.network_usage_auto_download);
        ImageView strImage = storageView.findViewById(R.id.itemImage);
        strImage.setImageResource(R.drawable.storage_badged);

        appLanguageView = view.findViewById(R.id.appLanguage);
        TextView appTextView = appLanguageView.findViewById(R.id.itemTextView);
        appTextView.setText(R.string.app_language);
        TextView appInformationView = appLanguageView.findViewById(R.id.information);
        appInformationView.setText(R.string.english_device_s_language);
        ImageView appImage = appLanguageView.findViewById(R.id.itemImage);
        appImage.setImageResource(R.drawable.global);

        helpView = view.findViewById(R.id.help);
        TextView helpTextView = helpView.findViewById(R.id.itemTextView);
        helpTextView.setText(R.string.help);
        TextView helpInformationView = helpView.findViewById(R.id.information);
        helpInformationView.setText(R.string.help_centre_contact_us_privacy_policy);
        ImageView helpImage = helpView.findViewById(R.id.itemImage);
        helpImage.setImageResource(R.drawable.help);

        inviteView = view.findViewById(R.id.inviteAFriend);
        TextView invTextView = inviteView.findViewById(R.id.itemTextView);
        invTextView.setText(R.string.invite_a_friend);
        TextView invInformationView = inviteView.findViewById(R.id.information);
        invInformationView.setText("");
        ImageView invImage = inviteView.findViewById(R.id.itemImage);
        invImage.setImageResource(R.drawable.people);
    }
}