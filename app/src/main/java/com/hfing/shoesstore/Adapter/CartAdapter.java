package com.hfing.shoesstore.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.DAO.CartItemDAO;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.ProductSizeDAO;
import com.hfing.shoesstore.Model.CartItem;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.ProductSize;
import com.hfing.shoesstore.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private ProductSizeDAO productSizeDAO;
    private CartItemDAO cartItemDAO;
    private ProductDAO productDAO;
    private OnQuantityChangeListener onQuantityChangeListener;

    public CartAdapter(List<CartItem> cartItems, ProductSizeDAO productSizeDAO, CartItemDAO cartItemDAO, ProductDAO productDAO, OnQuantityChangeListener onQuantityChangeListener) {
        this.cartItems = cartItems;
        this.productSizeDAO = productSizeDAO;
        this.cartItemDAO = cartItemDAO;
        this.productDAO = productDAO;
        this.onQuantityChangeListener = onQuantityChangeListener;
        combineIdenticalProducts();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        Product product = productDAO.getProductById(cartItem.getProduct_id());
        ProductSize productSize = productSizeDAO.getProductSizeById(cartItem.getProduct_size_id());

        holder.tvProductName.setText(product.getName());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.tvProductPrice.setText("Price: " + formatter.format(product.getPrice()));
        holder.tvProductSize.setText("Size: " + productSize.getSize());
        holder.tvProductQuantity.setText("Quantity: " + cartItem.getQuantity());

        byte[] productImage = product.getImage();
        if (productImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
            holder.imgProduct.setImageBitmap(bitmap);
        } else {
            holder.imgProduct.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.btnIncrease.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemDAO.updateCartItem(cartItem);
            notifyItemChanged(position);
            onQuantityChangeListener.onQuantityChanged();
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItemDAO.updateCartItem(cartItem);
                notifyItemChanged(position);
                onQuantityChangeListener.onQuantityChanged();
            } else {
                cartItemDAO.deleteCartItem(cartItem.getId());
                cartItems.remove(position);
                notifyItemRemoved(position);
                onQuantityChangeListener.onQuantityChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void combineIdenticalProducts() {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item1 = cartItems.get(i);
            for (int j = i + 1; j < cartItems.size(); j++) {
                CartItem item2 = cartItems.get(j);
                if (item1.getProduct_id() == item2.getProduct_id() && item1.getProduct_size_id() == item2.getProduct_size_id()) {
                    item1.setQuantity(item1.getQuantity() + item2.getQuantity());
                    cartItemDAO.updateCartItem(item1);
                    cartItemDAO.deleteCartItem(item2.getId());
                    cartItems.remove(j);
                    j--;
                }
            }
        }
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductPrice, tvProductSize, tvProductQuantity;
        Button btnIncrease, btnDecrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductSize = itemView.findViewById(R.id.tvProductSize);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }
}