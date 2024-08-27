package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.DAO.FavoriteDAO;
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
    private FavoriteDAO favoriteDAO;
    private int userId;

    public ProductAdapterRCM(Context context, List<Product> products, RecycleViewOnItemClickListener listener, ProductDAO productDAO, int userId) {
        this.context = context;
        this.products = products;
        this.listener = listener;
        this.productDAO = productDAO;
        this.favoriteDAO = new FavoriteDAO(context);
        this.userId = userId;
    }

    @Override
    public int getItemCount() {
        return Math.min(products.size(), 6);
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

        // Check if the product is in the favorites list
        if (favoriteDAO.isFavorite(userId, product.getId())) {
            holder.imageView6.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_filled, context.getTheme()));
        } else {
            holder.imageView6.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border, context.getTheme()));
        }

        holder.itemView.setOnClickListener(v -> {
            Log.d("ProductAdapter", "Clicked position: " + position);
            listener.onItemRecycleViewClick(position, products);
        });

        holder.imageView6.setOnClickListener(v -> {
            if (favoriteDAO.isFavorite(userId, product.getId())) {
                favoriteDAO.removeFavorite(userId, product.getId());
                holder.imageView6.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border, context.getTheme()));
            } else {
                favoriteDAO.addFavorite(userId, product.getId());
                holder.imageView6.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_filled, context.getTheme()));
            }
        });
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, ratingTxt;
        ImageView pic, imageView6;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            ratingTxt = itemView.findViewById(R.id.ratingTxt);
            pic = itemView.findViewById(R.id.pic);
            imageView6 = itemView.findViewById(R.id.imageView6);
        }
    }

    public void updateProducts(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }
}