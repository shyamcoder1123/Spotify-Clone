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
import com.example.newspotifyclone.databinding.FragmentPremiumBinding;


public class PremiumFragment extends Fragment {

    FragmentPremiumBinding fragmentPremiumBinding;
    public String name = "premium";
    View premiumCard1View,premiumCard2View,premiumCard3View;
    TextView firstPremiumCardName,secondPremiumCardName,thirdPremiumCardName;
    ImageView premCardImgView1,premCardImgView2,premCardImgView3;
    public PremiumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPremiumBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_premium,container,false);
        View view=fragmentPremiumBinding.getRoot();
        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_premium, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        premiumCard1View=fragmentPremiumBinding.firstCard;
        premiumCard2View=fragmentPremiumBinding.secondCard;
        premiumCard3View=fragmentPremiumBinding.thirdCard;

        firstPremiumCardName=premiumCard1View.findViewById(R.id.premiumName);
        secondPremiumCardName=premiumCard2View.findViewById(R.id.premiumName);
        thirdPremiumCardName=premiumCard3View.findViewById(R.id.premiumName);
        premCardImgView1=premiumCard1View.findViewById(R.id.premCardImgView);
        premCardImgView2=premiumCard2View.findViewById(R.id.premCardImgView);
        premCardImgView3=premiumCard3View.findViewById(R.id.premCardImgView);

        premCardImgView1.setImageResource(R.drawable.gradient_a_b);
        premCardImgView2.setImageResource(R.drawable.gradient_a_b_c);
        premCardImgView3.setImageResource(R.drawable.gradient_e_f);
        firstPremiumCardName.setText(R.string.silver);
        firstPremiumCardName.setTextColor(android.graphics.Color.parseColor("#989898"));
        secondPremiumCardName.setText(R.string.gold);
        secondPremiumCardName.setTextColor(android.graphics.Color.parseColor("#F1CA24"));
        thirdPremiumCardName.setText(R.string.diamond);
        thirdPremiumCardName.setTextColor(android.graphics.Color.parseColor("#FAFDFD"));

    }

}