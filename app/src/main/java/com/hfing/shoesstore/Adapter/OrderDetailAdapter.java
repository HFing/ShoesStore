package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hfing.shoesstore.Activity.ReviewActivity;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.ProductSizeDAO;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.ProductSize;
import com.hfing.shoesstore.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private List<OrderDetail> orderDetailList;
    private Context context;
    private ProductDAO productDAO;
    private ProductSizeDAO productSizeDAO;

    public OrderDetailAdapter(List<OrderDetail> orderDetailList, Context context) {
        this.orderDetailList = orderDetailList;
        this.context = context;
        this.productDAO = new ProductDAO(context);
        this.productSizeDAO = new ProductSizeDAO(context);
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produc, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetailList.get(position);

        Product product = productDAO.getProductById(orderDetail.getProduct_id());
        ProductSize productSize = productSizeDAO.getProductSizeById(orderDetail.getProduct_size_id());

        if (product != null && productSize != null) {
            holder.productNameTextView.setText(product.getName());
            holder.productSizeTextView.setText("Size: " + productSize.getSize());

            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            holder.productPriceTextView.setText("Total Amount: " + formatter.format(product.getPrice()));

            holder.productQuantityTextView.setText("Quantity: " + String.valueOf(orderDetail.getQuantity()));

            Glide.with(holder.productImageView.getContext())
                    .load(product.getImage())
                    .into(holder.productImageView);

            holder.rateProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReviewActivity.class);
                    intent.putExtra("product_id", orderDetail.getProduct_id());
                    context.startActivity(intent);
                }
            });
        } else {
            holder.productNameTextView.setText("Unknown Product");
            holder.productSizeTextView.setText("Size: N/A");
            holder.productPriceTextView.setText("Total Amount: N/A");
            holder.productQuantityTextView.setText("Quantity: N/A");
//            holder.productImageView.setImageResource(R.drawable.placeholder_image);
        }
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productSizeTextView;
        TextView productPriceTextView;
        TextView productQuantityTextView;
        ImageView productImageView;
        Button rateProductButton;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.tvProductName);
            productSizeTextView = itemView.findViewById(R.id.tvProductSize);
            productPriceTextView = itemView.findViewById(R.id.tvProductPrice);
            productQuantityTextView = itemView.findViewById(R.id.tvProductQuantity);
            productImageView = itemView.findViewById(R.id.imgProduct);
            rateProductButton = itemView.findViewById(R.id.btnRateProduct);
        }
    }
}