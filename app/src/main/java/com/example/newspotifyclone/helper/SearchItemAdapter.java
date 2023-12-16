package com.example.newspotifyclone.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.model.AlbumModel;
import com.example.newspotifyclone.model.SearchItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {
    ArrayList<SearchItemModel> searchItemModelArrayList;
    Context context;
    CategoryInterface categoryInterface;

    public SearchItemAdapter(ArrayList<SearchItemModel> searchItemModelArrayList, Context context, CategoryInterface categoryInterface) {
        this.searchItemModelArrayList = searchItemModelArrayList;
        this.context = context;
        this.categoryInterface = categoryInterface;
    }

    @NonNull
    @Override
    public SearchItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemAdapter.ViewHolder holder, int position) {
        SearchItemModel searchItemModel = searchItemModelArrayList.get(position);
        String searchItemName = searchItemModel.getSearchItemName();
        String searchItemImage = searchItemModel.getUrlToImage();

        RandomLinearGradient.setRandomLinearGradient(holder.searchCardBackgroundImage);

        holder.itemNameTextView1.setText(searchItemName);
        Picasso.get().load(searchItemImage).resize(300,300).centerCrop().into(holder.itemImageView1);
    }

    @Override
    public int getItemCount() {
        return searchItemModelArrayList.size();
    }
    public interface CategoryInterface{
        void onCategoryClick(int position, ArrayList<SearchItemModel> searchGridItems);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView1;
        ImageView itemImageView1;
        ImageView searchCardBackgroundImage;
//        TextView itemNameTextView2;
//        ImageView itemImageView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView1 = itemView.findViewById(R.id.searchItemText1);
            itemImageView1 = itemView.findViewById(R.id.searchItemImage1);
//            itemNameTextView2 = itemView.findViewById(R.id.searchItemText2);
//            itemImageView2 = itemView.findViewById(R.id.searchItemImage2);
            searchCardBackgroundImage = itemView.findViewById(R.id.searchCardBackgroundImage);
        }
    }
}
