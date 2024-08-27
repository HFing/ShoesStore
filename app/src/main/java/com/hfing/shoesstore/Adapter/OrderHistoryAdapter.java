
package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.DAO.OrderDetailDAO;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.R;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {
    private List<Orders> ordersList;
    private Context context;
    private OrderDetailDAO orderDetailDAO;

    public OrderHistoryAdapter(List<Orders> ordersList, Context context) {
        this.ordersList = ordersList;
        this.context = context;
        this.orderDetailDAO = new OrderDetailDAO(context);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Orders order = ordersList.get(position);
        holder.orderDateTextView.setText( "Order Date: "+order.getOrder_date());

        // Lấy danh sách chi tiết đơn hàng
        List<OrderDetail> orderDetails = orderDetailDAO.getOrderDetailsByOrderId(order.getId());
        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(orderDetails, context);
        holder.recyclerViewOrderDetails.setAdapter(orderDetailAdapter);
        holder.recyclerViewOrderDetails.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderDateTextView;
        RecyclerView recyclerViewOrderDetails;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDateTextView = itemView.findViewById(R.id.tvOrderDate);
            recyclerViewOrderDetails = itemView.findViewById(R.id.recyclerViewOrderDetails);
            recyclerViewOrderDetails.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}