package com.example.newspotifyclone.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.model.DifferentModelsObject;

import java.util.ArrayList;

public class DifferentItemsRecyclerAdapter extends RecyclerView.Adapter<DifferentItemsRecyclerAdapter.ViewHolder> {
    ArrayList<DifferentModelsObject> differentModelsObjects;
    DifferentModelsAdapter.DifferentItemCategoryInterface differentItemCategoryInterface;
    Context context;

    public DifferentItemsRecyclerAdapter(ArrayList<DifferentModelsObject> differentModelsObjects, DifferentModelsAdapter.DifferentItemCategoryInterface differentItemCategoryInterface, Context context) {
        this.differentModelsObjects = differentModelsObjects;
        this.differentItemCategoryInterface = differentItemCategoryInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public DifferentItemsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.different_models_cardview_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DifferentItemsRecyclerAdapter.ViewHolder holder, int position) {
        String name=differentModelsObjects.get(position).getName();
        holder.itemName.setText(name);
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
        return differentModelsObjects.size();
    }
    public interface DifferentItemCategoryInterface{
        void onDifferentCategoryItemClick();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.itemNameDifferentModelTextView);
        }
    }
}
