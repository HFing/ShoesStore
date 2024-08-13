package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.R;

import java.util.List;

public class ProductAdapterRCM extends RecyclerView.Adapter<ProductAdapterRCM.ProductViewHolder> {
    private Context context;
    private List<Product> products;

    public ProductAdapterRCM(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
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
        holder.priceTxt.setText(String.format("VND-%.2f", product.getPrice()));
        holder.ratingTxt.setText("5"); // Assuming a static rating for now

        byte[] imageBytes = product.getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.pic.setImageBitmap(bitmap);
        } else {
            holder.pic.setImageResource(R.drawable.ic_launcher_background); // Default image if none
        }
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
}