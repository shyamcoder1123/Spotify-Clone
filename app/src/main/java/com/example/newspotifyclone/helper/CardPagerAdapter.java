package com.example.newspotifyclone.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.model.PremiumItem;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter {

    private List<Integer> cardLayouts;
    private Context context;
    LayoutInflater layoutInflater;
    int newPosition;

    ArrayList<Integer>items = new ArrayList<>();


    public CardPagerAdapter(Context context) {
        this.context=context;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        LayoutInflater inflater = layoutInflater.from(container.getContext());
        View cardView = layoutInflater.inflate(R.layout.free_premium_card, null);
        TextView freePremTextView = cardView.findViewById(R.id.freeTextViewPremium);
        items.add(1);
        items.add(2);
        items.add(3);
        items.add(4);
        ViewPager vp=(ViewPager) container;
        freePremTextView.setText(items.get(position)+"");
        vp.addView(cardView,0);
        return cardView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp=(ViewPager)container;
        View view = (View)object;
        vp.removeView(view);
//        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
