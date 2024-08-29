package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hfing.shoesstore.DAO.FavoriteDAO;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {
    private List<Product> productList;
    private Context context;
    private FavoriteDAO favoriteDAO;
    private int userId;

    public WishListAdapter(List<Product> productList, Context context, int userId) {
        this.productList = productList;
        this.context = context;
        this.userId = userId;
        this.favoriteDAO = new FavoriteDAO(context);
    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wish_list, parent, false);
        return new WishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.tvProductPrice.setText("Price: " + format.format(product.getPrice()));


        Glide.with(context).load(product.getImage()).into(holder.imgProduct);

        holder.btnDeleteWishList.setOnClickListener(v -> {
            favoriteDAO.removeFavorite(userId, product.getId());
            productList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productList.size());
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class WishListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductPrice, tvProductSize, tvProductQuantity;
        Button btnDeleteWishList;

        public WishListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductSize = itemView.findViewById(R.id.tvProductSize);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            btnDeleteWishList = itemView.findViewById(R.id.btnDeleteWishList);
        }
    }
}