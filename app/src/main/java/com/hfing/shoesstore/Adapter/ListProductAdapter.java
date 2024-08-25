package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.DAO.FavoriteDAO;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.R;

import java.text.NumberFormat;
import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> products;
    private OnItemClickListener onItemClickListener;
    private FavoriteDAO favoriteDAO;
    private int userId;

    public ListProductAdapter(Context context, List<Product> products, OnItemClickListener onItemClickListener, int userId) {
        this.context = context;
        this.products = products;
        this.onItemClickListener = onItemClickListener;
        this.favoriteDAO = new FavoriteDAO(context);
        this.userId = userId;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.titleTxt.setText(product.getName());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
        holder.priceTxt.setText(formatter.format(product.getPrice()));

        byte[] imageBytes = product.getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.pic.setImageBitmap(bitmap);
        } else {
            holder.pic.setImageResource(R.drawable.ic_launcher_background); // Default image if none
        }

        // Check if the product is in the favorites list
        if (favoriteDAO.isFavorite(userId, product.getId())) {
            holder.favoriteBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_filled, context.getTheme()));
        } else {
            holder.favoriteBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border, context.getTheme()));
        }

        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(product);
        });

        holder.favoriteBtn.setOnClickListener(v -> {
            if (favoriteDAO.isFavorite(userId, product.getId())) {
                favoriteDAO.removeFavorite(userId, product.getId());
                holder.favoriteBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border, context.getTheme()));
            } else {
                favoriteDAO.addFavorite(userId, product.getId());
                holder.favoriteBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_filled, context.getTheme()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt;
        ImageView pic, favoriteBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            pic = itemView.findViewById(R.id.pic);
            favoriteBtn = itemView.findViewById(R.id.favoriteBtn);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void updateProducts(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }
}