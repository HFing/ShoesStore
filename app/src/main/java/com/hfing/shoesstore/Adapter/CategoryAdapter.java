package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private final List<Category> categories;
    private final Context context;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.category_list_item, parent, false);
        Category category = categories.get(position);

        TextView textView = convertView.findViewById(R.id.tvCategoryName);
        textView.setText(category.getName());
        return textView;
    }
}
