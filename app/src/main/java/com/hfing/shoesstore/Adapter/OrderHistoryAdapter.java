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
import com.hfing.shoesstore.DAO.OrderDAO;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.ProductSizeDAO;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.ProductSize;
import com.hfing.shoesstore.R;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {
    private List<OrderDetail> orderDetailList;
    private Context context;
    private ProductDAO productDAO;
    private ProductSizeDAO productSizeDAO;
    private OrderDAO orderDAO;

    public OrderHistoryAdapter(List<OrderDetail> orderDetailList, Context context) {
        this.orderDetailList = orderDetailList;
        this.context = context;
        this.productDAO = new ProductDAO(context);
        this.productSizeDAO = new ProductSizeDAO(context);
        this.orderDAO = new OrderDAO(context);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetailList.get(position);
        Product product = productDAO.getProductById(orderDetail.getProduct_id());
        ProductSize productSize = productSizeDAO.getProductSizeById(orderDetail.getProduct_size_id());
        Orders order = orderDAO.getOrderById(orderDetail.getOrder_id());

        holder.productNameTextView.setText(product.getName());
        holder.productSizeTextView.setText("Size: " + productSize.getSize());

        // Format the product price to remove trailing .0
        String formattedPrice = String.format("%.0f", orderDetail.getUnit_price());
        holder.productPriceTextView.setText("Total Amount: " + formattedPrice + " VND");

        holder.orderDateTextView.setText("Order Date: " + order.getOrder_date());
        holder.productQuantityTextView.setText("Quantity: " + String.valueOf(orderDetail.getQuantity()));

        // Load product image using Glide
        Glide.with(holder.productImageView.getContext())
                .load(product.getImage())
                .into(holder.productImageView);
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productSizeTextView;
        TextView productPriceTextView;
        TextView orderDateTextView;
        TextView productQuantityTextView;
        Button rateProductButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.imgProduct);
            productNameTextView = itemView.findViewById(R.id.tvProductName);
            productSizeTextView = itemView.findViewById(R.id.tvProductSize);
            productPriceTextView = itemView.findViewById(R.id.tvProductPrice);
            orderDateTextView = itemView.findViewById(R.id.tvOrderDate);
            productQuantityTextView = itemView.findViewById(R.id.tvProductQuantity);
            rateProductButton = itemView.findViewById(R.id.btnRateProduct);
        }
    }
}