package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.R;

import java.text.NumberFormat;
import java.util.List;

public class ProductAdapterRCM extends RecyclerView.Adapter<ProductAdapterRCM.ProductViewHolder> {
    private Context context;
    private List<Product> products;
    private RecycleViewOnItemClickListener listener;
    private ProductDAO productDAO;

    public ProductAdapterRCM(Context context, List<Product> products, RecycleViewOnItemClickListener listener, ProductDAO productDAO) {
        this.context = context;
        this.products = products;
        this.listener = listener;
        this.productDAO = productDAO;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_recommended, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.titleTxt.setText(product.getName());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
        holder.priceTxt.setText(formatter.format(product.getPrice()));
        holder.ratingTxt.setText(String.valueOf(productDAO.getAvengerRating(product.getName()))); // Assuming a static rating for now

        byte[] imageBytes = product.getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.pic.setImageBitmap(bitmap);
        } else {
            holder.pic.setImageResource(R.drawable.ic_launcher_background); // Default image if none
        }

        holder.itemView.setOnClickListener(v -> listener.onItemRecycleViewClick(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, ratingTxt;
        ImageView pic;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            ratingTxt = itemView.findViewById(R.id.ratingTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
    public void updateProducts(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }
}