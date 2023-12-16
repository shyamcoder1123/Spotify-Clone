package com.example.newspotifyclone.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.example.newspotifyclone.R;
public class CommentPopUpFragment extends BottomSheetDialogFragment {

    public CommentPopUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment_pop_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.TRANSPARENT);
        getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getActivity(), R.color.navigation_bar_color));
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        // Find and set up your EditText and Button here
        EditText commentEditText = view.findViewById(R.id.commentEditText);
        Button submitCommentButton = view.findViewById(R.id.submitCommentButton);
//        view.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.round_top_corners));
        // Set up click listener for the submit button
        submitCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle comment submission here
                String comment = commentEditText.getText().toString();
                // You can send the comment to your server or perform any other action
                // Dismiss the dialog when done
                getView().setVisibility(View.GONE);
//                getParentFragmentManager().beginTransaction().remove(getContext()).commit()
// To remove the comment fragment from the view hierarchy
//                getSupportFragmentManager().beginTransaction()
//                        .remove(commentFragment)
//                        .commit();
                dismiss();
            }
        });
    }
}