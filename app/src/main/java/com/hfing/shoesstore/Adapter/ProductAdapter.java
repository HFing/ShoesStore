package com.hfing.shoesstore.Adapter;

import android.content.Context;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter {
    private final List<Product> products;
    private final Context context;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.product_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tvProductName);
            holder.tvDescription = convertView.findViewById(R.id.tvProductDescription);
            holder.tvPrice = convertView.findViewById(R.id.tvProductPrice);
            holder.tvCategory = convertView.findViewById(R.id.tvProductCategory);
            holder.imgProduct = convertView.findViewById(R.id.imgProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);
        holder.tvName.setText(product.getName());
        String description = product.getDescription();
        String[] words = description.split("\\s+");
        if (words.length > 15) {
            description = String.join(" ", Arrays.copyOfRange(words, 0, 15)) + " ...";
        }
        holder.tvDescription.setText(description);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.tvPrice.setText(formatter.format(product.getPrice()));

        byte[] imageBytes = product.getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imgProduct.setImageBitmap(bitmap);
        } else {
            holder.imgProduct.setImageResource(R.drawable.ic_launcher_background); // Default image if no image
        }

        CategoryDAO categoryDAO = new CategoryDAO(context);
        Category category = categoryDAO.getCategoryById(product.getCategory_id());
        if (category != null) {
            holder.tvCategory.setText(category.getName());
        } else {
            holder.tvCategory.setText("Unknown Category");
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvDescription;
        TextView tvPrice;
        TextView tvCategory;
        ImageView imgProduct;
    }
}