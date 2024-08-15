
package com.hfing.shoesstore.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.R;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {
    private List<Orders> orders;

    public OrderHistoryAdapter(List<Orders> orders) {
        this.orders = orders;
    }


    @NonNull
    @Override
    public OrderHistoryAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.OrderViewHolder holder, int position) {
        Orders order = orders.get(position);
        holder.textViewOrderId.setText("Order ID: " + order.getId());

        // Set product image if available
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderId;
        TextView textViewOrderDate;
        TextView textViewOrderTotal;
        ImageView imageViewProduct;
        Button btnRateProduct;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.tvProductName);
            textViewOrderDate = itemView.findViewById(R.id.tvProductSize);
            textViewOrderTotal = itemView.findViewById(R.id.tvProductPrice);
            imageViewProduct = itemView.findViewById(R.id.imgProduct);
            btnRateProduct = itemView.findViewById(R.id.btnRateProduct);
        }
    }
}