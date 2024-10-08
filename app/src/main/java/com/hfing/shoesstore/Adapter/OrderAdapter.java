//package com.hfing.shoesstore.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.hfing.shoesstore.Model.Orders;
//import com.hfing.shoesstore.R;
//
//import java.util.List;
//
//public class OrderAdapter extends BaseAdapter {
//
//    private final List<Orders> orders;
//    private final Context context;
//
//    public OrderAdapter(List<Orders> orders, Context context) {
//        this.orders = orders;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return orders.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return orders.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return orders.get(position).getId();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//            convertView = inflater.inflate(R.layout.order_list_item, parent, false);
//        }
//
//        Orders order = orders.get(position);
//        TextView tvOrderId = convertView.findViewById(R.id.tvOrderId);
//        TextView tvUserId = convertView.findViewById(R.id.tvUserId);
//        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
//        TextView tvProductPrice = convertView.findViewById(R.id.tvProductPrice);
//
//        tvOrderId.setText(String.valueOf(order.getId()));
//        tvUserId.setText(String.valueOf(order.getUser_id()));
//        tvProductName.setText(order.getProductName());
//        tvProductPrice.setText(String.valueOf(order.getUnitPrice()));
//
//        return convertView;
//    }
//}

//package com.hfing.shoesstore.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.hfing.shoesstore.DAO.OrderDAO;
//import com.hfing.shoesstore.DAO.OrderDetailDAO;
//import com.hfing.shoesstore.DAO.ProductDAO;
//import com.hfing.shoesstore.DAO.ProductSizeDAO;
//import com.hfing.shoesstore.Model.OrderDetail;
//import com.hfing.shoesstore.Model.Orders;
//import com.hfing.shoesstore.Model.Product;
//import com.hfing.shoesstore.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class OrderAdapter extends BaseAdapter {
//
//    private final List<OrderDetail> orderDetails;
//    private final Context context;
//    private final ProductDAO productDAO;
//    private final OrderDAO orderDAO;
//    private final ProductSizeDAO productSizeDAO;
//
//    public OrderAdapter(List<OrderDetail> orderDetails, Context context) {
//        this.orderDetails = orderDetails;
//        this.context = context;
//        productDAO = new ProductDAO(context);
//        orderDAO = new OrderDAO(context);
//        productSizeDAO = new ProductSizeDAO(context);
//    }
//
//    @Override
//    public int getCount() {
//        return orderDetails.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return orderDetails.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return orderDetails.get(position).getId();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//            convertView = inflater.inflate(R.layout.order_list_item, parent, false);
//        }
//
//        OrderDetail orderDetail = orderDetails.get(position);
//        TextView tvOrderId = convertView.findViewById(R.id.tvOrderId);
//        TextView tvProductId = convertView.findViewById(R.id.tvProductId);
//        TextView tvProductSizeId = convertView.findViewById(R.id.tvProductSizeId);
//        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
//        TextView tvUnitPrice = convertView.findViewById(R.id.tvUnitPrice);
//        TextView tvDate = convertView.findViewById(R.id.tvDate);
//
//        tvOrderId.setText(String.valueOf(orderDetail.getOrder_id()));
//        Product product = productDAO.getProductById(orderDetail.getProduct_id());
//        tvProductId.setText(product.getName());
//        tvProductSizeId.setText(String.valueOf(orderDetail.getProduct_size_id()));
//        tvQuantity.setText(String.valueOf(orderDetail.getQuantity()));
//        tvUnitPrice.setText(String.valueOf(orderDetail.getUnit_price()));
//        Orders order = orderDAO.getOrderById(orderDetail.getOrder_id());
//        tvDate.setText(order.getOrder_date());
//
//        return convertView;
//    }
//}

package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hfing.shoesstore.DAO.OrderDAO;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.ProductSizeDAO;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.ProductSize;
import com.hfing.shoesstore.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends BaseAdapter {

    private final List<OrderDetail> orderDetails;
    private final Context context;
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final ProductSizeDAO productSizeDAO;

    public OrderAdapter(List<OrderDetail> orderDetails, Context context) {
        this.orderDetails = orderDetails;
        this.context = context;
        productDAO = new ProductDAO(context);
        orderDAO = new OrderDAO(context);
        productSizeDAO = new ProductSizeDAO(context);
    }

    @Override
    public int getCount() {
        return orderDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return orderDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderDetails.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.order_list_item, parent, false);
        }

        OrderDetail orderDetail = orderDetails.get(position);
        TextView tvOrderId = convertView.findViewById(R.id.tvOrderId);
        TextView tvProductId = convertView.findViewById(R.id.tvProductId);
        TextView tvProductSizeId = convertView.findViewById(R.id.tvProductSizeId);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        TextView tvUnitPrice = convertView.findViewById(R.id.tvUnitPrice);
        TextView tvDate = convertView.findViewById(R.id.tvDate);

        tvOrderId.setText("OrderID: " + String.valueOf(orderDetail.getOrder_id()));
        Product product = productDAO.getProductById(orderDetail.getProduct_id());
        tvProductId.setText(product.getName());
        ProductSize productSize = productSizeDAO.getProductSizeById(orderDetail.getProduct_size_id());
        tvProductSizeId.setText("Size: " + String.valueOf(productSize.getSize()));
        tvQuantity.setText(String.valueOf("Quantity:" + orderDetail.getQuantity()));
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvUnitPrice.setText("Price: " + formatter.format(orderDetail.getUnit_price()));
        Orders order = orderDAO.getOrderById(orderDetail.getOrder_id());
        tvDate.setText("OrderDate: " + order.getOrder_date());

        tvOrderId.setText(tvOrderId.getText(), TextView.BufferType.SPANNABLE);
        tvProductId.setText(tvProductId.getText(), TextView.BufferType.SPANNABLE);
        tvProductSizeId.setText(tvProductSizeId.getText(), TextView.BufferType.SPANNABLE);
        tvQuantity.setText(tvQuantity.getText(), TextView.BufferType.SPANNABLE);
        tvUnitPrice.setText(tvUnitPrice.getText(), TextView.BufferType.SPANNABLE);
        tvDate.setText(tvDate.getText(), TextView.BufferType.SPANNABLE);


        return convertView;
    }
}