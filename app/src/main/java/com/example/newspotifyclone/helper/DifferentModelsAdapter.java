package com.example.newspotifyclone.helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.view.ShortsActivity;

public class DifferentModelsAdapter extends RecyclerView.Adapter<DifferentModelsAdapter.ViewHolder> {
    DifferentItemCategoryInterface differentItemCategoryInterface;
    Context context;

    public DifferentModelsAdapter(DifferentItemCategoryInterface differentItemCategoryInterface, Context context) {
        this.differentItemCategoryInterface = differentItemCategoryInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public DifferentModelsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.different_models_cardview_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DifferentModelsAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.getAdapterPosition()==5){
                    differentItemCategoryInterface.onDifferentCategoryItemClick();
//                    Intent i = new Intent(context, ShortsPageActivity.class);
//                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }
    public interface DifferentItemCategoryInterface{
        void onDifferentCategoryItemClick();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
