package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfing.shoesstore.DAO.CategoryDAO;
import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter {
    List<Product> products;
    Context context;
    public ProductAdapter(Context context, List<Product> products) {
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
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.product_list_item, viewGroup, false);
        }

        Product product = products.get(i);

        TextView tvName = view.findViewById(R.id.tvProductName);
        TextView tvDescription = view.findViewById(R.id.tvProductDescription);
        TextView tvPrice = view.findViewById(R.id.tvProductPrice);
        TextView tvCategory = view.findViewById(R.id.tvProductCategory);
        ImageView imgProduct = view.findViewById(R.id.imgProduct);

        tvName.setText(product.getName());
        tvDescription.setText(product.getDescription());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvPrice.setText(formatter.format(product.getPrice()));
        byte[] imageBytes = product.getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgProduct.setImageBitmap(bitmap);
        } else {
            imgProduct.setImageResource(R.drawable.ic_launcher_background); // Ảnh mặc định nếu không có ảnh
        }

        CategoryDAO categoryDAO = new CategoryDAO(context);
        Category category = categoryDAO.getCategoryById(product.getCategory_id());
        if (category != null) {
            tvCategory.setText(category.getName());
        } else {
            tvCategory.setText("Unknown Category");
        }

        return view;
    }
}
