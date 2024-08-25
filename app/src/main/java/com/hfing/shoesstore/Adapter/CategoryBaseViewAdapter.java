package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.R;

import java.util.ArrayList;

public class CategoryBaseViewAdapter extends RecyclerView.Adapter<CategoryBaseViewAdapter.ViewHolder> {

    private ArrayList<Category> categories;
    private Context context;
    private OnCategoryClickListener onCategoryClickListener;

    public CategoryBaseViewAdapter(Context context, ArrayList<Category> categories, OnCategoryClickListener onCategoryClickListener) {
        this.context = context;
        this.categories = categories;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_list_item, parent, false);
        return new ViewHolder(view, onCategoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.textView.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        OnCategoryClickListener onCategoryClickListener;

        public ViewHolder(@NonNull View itemView, OnCategoryClickListener onCategoryClickListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvCategoryName);
            this.onCategoryClickListener = onCategoryClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCategoryClickListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }
}