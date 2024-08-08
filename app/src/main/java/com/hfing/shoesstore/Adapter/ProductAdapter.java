package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hfing.shoesstore.DAO.CategoryDAO;
import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    List<Product> products;
    Context context;
    public ProductAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.products = products;
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return products.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CategoryDAO categoryDAO = new CategoryDAO(context);
        Category category = categoryDAO.getCategoryById(products.get(i).getCategory_id());
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.product_list_item, viewGroup, false);
        Product product = products.get(i);
        TextView textView = view.findViewById(R.id.tvProductName);
        textView.setText(product.getName());
        textView.setText(product.getDescription());
        textView.setText(String.valueOf(product.getPrice()));
        textView.setText(category.getName());
        return textView;
    }
}
