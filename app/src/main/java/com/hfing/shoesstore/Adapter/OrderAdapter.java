package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.R;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private final List<Orders> orders;
    private final Context context;

    public OrderAdapter(List<Orders> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.order_list_item, parent, false);
        }

        Orders order = orders.get(position);
        TextView tvOrderId = convertView.findViewById(R.id.tvOrderId);
        TextView tvUserId = convertView.findViewById(R.id.tvUserId);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
        TextView tvProductPrice = convertView.findViewById(R.id.tvProductPrice);

        tvOrderId.setText(String.valueOf(order.getId()));
        tvUserId.setText(String.valueOf(order.getUser_id()));
        tvProductName.setText(order.getProductName());
        tvProductPrice.setText(String.valueOf(order.getUnitPrice()));

        return convertView;
    }
}