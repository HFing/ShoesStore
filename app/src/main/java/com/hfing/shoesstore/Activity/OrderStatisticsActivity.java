//package com.hfing.shoesstore.Activity;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.hfing.shoesstore.Adapter.OrderAdapter;
//import com.hfing.shoesstore.DAO.OrderDAO;
//import com.hfing.shoesstore.Model.Orders;
//import com.hfing.shoesstore.R;
//
//import java.util.ArrayList;
//
//public class OrderStatisticsActivity extends AppCompatActivity {
//
//    ListView lvOrders;
//    Spinner spinnerUser, spinnerCategory;
//    EditText edtStartDate, edtEndDate;
//    Button btnFetchOrders;
//    OrderDAO orderDAO;
//    ArrayList<Orders> orders;
//    OrderAdapter orderAdapter;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_statistics);
//
//        lvOrders = findViewById(R.id.lvOrders);
//        spinnerUser = findViewById(R.id.spinnerUser);
//        spinnerCategory = findViewById(R.id.spinnerCategory);
//        edtStartDate = findViewById(R.id.edtStartDate);
//        edtEndDate = findViewById(R.id.edtEndDate);
//        btnFetchOrders = findViewById(R.id.btnFetchOrders);
//        orderDAO = new OrderDAO(this);
//        orders = new ArrayList<>();
//
//        // Populate spinner with user data
//        ArrayList<String> users = orderDAO.getUsers();
//        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
//        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerUser.setAdapter(userAdapter);
//
//        // Populate spinner with category data
//        ArrayList<String> categories = orderDAO.getCategories();
//        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
//        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategory.setAdapter(categoryAdapter);
//
//        btnFetchOrders.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String selectedUser = spinnerUser.getSelectedItem().toString();
//                String selectedCategory = spinnerCategory.getSelectedItem().toString();
//
//                if (!selectedUser.isEmpty() && !selectedCategory.isEmpty()) {
//                    int userId = orderDAO.getUserIdByName(selectedUser);
//                    orders = orderDAO.getOrderDetailsByUserAndCategory(userId, selectedCategory);
//                } else {
//                    orders.clear();
//                }
//
//                if (orders.isEmpty()) {
//                    Toast.makeText(OrderStatisticsActivity.this, "No orders found for selected user and category", Toast.LENGTH_SHORT).show();
//                    orderAdapter = new OrderAdapter(new ArrayList<>(), OrderStatisticsActivity.this);
//                } else {
//                    orderAdapter = new OrderAdapter(orders, OrderStatisticsActivity.this);
//                }
//                lvOrders.setAdapter(orderAdapter);
//            }
//        });
//    }
//}

//package com.hfing.shoesstore.Activity;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.hfing.shoesstore.Adapter.OrderAdapter;
//import com.hfing.shoesstore.DAO.OrderDAO;
//import com.hfing.shoesstore.Model.OrderDetail;
//import com.hfing.shoesstore.R;
//
//import java.util.ArrayList;
//
//public class OrderStatisticsActivity extends AppCompatActivity {
//
//    ListView lvOrders;
//    Spinner spinnerUser, spinnerCategory;
//    EditText edtStartDate, edtEndDate;
//    Button btnFetchOrders;
//    OrderDAO orderDAO;
//    ArrayList<OrderDetail> orderDetails;
//    OrderAdapter orderAdapter;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_statistics);
//
//        lvOrders = findViewById(R.id.lvOrders);
//        spinnerUser = findViewById(R.id.spinnerUser);
//        spinnerCategory = findViewById(R.id.spinnerCategory);
//        edtStartDate = findViewById(R.id.edtStartDate);
//        edtEndDate = findViewById(R.id.edtEndDate);
//        btnFetchOrders = findViewById(R.id.btnFetchOrders);
//        orderDAO = new OrderDAO(this);
//        orderDetails = new ArrayList<>();
//
//        // Populate spinner with user data
//        ArrayList<String> users = orderDAO.getUsers();
//        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
//        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerUser.setAdapter(userAdapter);
//
//        // Populate spinner with category data
//        ArrayList<String> categories = orderDAO.getCategories();
//        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
//        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategory.setAdapter(categoryAdapter);
//
//        btnFetchOrders.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String selectedUser = spinnerUser.getSelectedItem().toString();
//                String selectedCategory = spinnerCategory.getSelectedItem().toString();
//                String startDate = edtStartDate.getText().toString();
//                String endDate = edtEndDate.getText().toString();
//
//                if (!selectedUser.isEmpty() && !selectedCategory.isEmpty()) {
//                    int userId = orderDAO.getUserIdByName(selectedUser);
//                    orderDetails = orderDAO.getOrderDetailsByUserAndCategory(userId, selectedCategory);
//                } else {
//                    orderDetails.clear();
//                }
//
//                if (orderDetails.isEmpty()) {
//                    Toast.makeText(OrderStatisticsActivity.this, "No orders found for selected user and category", Toast.LENGTH_SHORT).show();
//                    orderAdapter = new OrderAdapter(new ArrayList<>(), OrderStatisticsActivity.this);
//                } else {
//                    orderAdapter = new OrderAdapter(orderDetails, OrderStatisticsActivity.this);
//                }
//                lvOrders.setAdapter(orderAdapter);
//
//            }
//        });
//    }
//}

package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hfing.shoesstore.Adapter.OrderAdapter;
import com.hfing.shoesstore.DAO.OrderDAO;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.R;

import java.util.ArrayList;

public class OrderStatisticsActivity extends AppCompatActivity {

    ListView lvOrders;
    Spinner spinnerUser, spinnerCategory;
    EditText edtStartDate, edtEndDate;
    Button btnFetchOrders;
    OrderDAO orderDAO;
    ArrayList<OrderDetail> orderDetails;
    OrderAdapter orderAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_statistics);

        lvOrders = findViewById(R.id.lvOrders);
        spinnerUser = findViewById(R.id.spinnerUser);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        edtStartDate = findViewById(R.id.edtStartDate);
        edtEndDate = findViewById(R.id.edtEndDate);
        btnFetchOrders = findViewById(R.id.btnFetchOrders);
        orderDAO = new OrderDAO(this);
        orderDetails = new ArrayList<>();

        // Populate spinner with user data
        ArrayList<String> users = orderDAO.getUsers();
        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(userAdapter);

        // Populate spinner with category data
        ArrayList<String> categories = orderDAO.getCategories();
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        btnFetchOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedUser = spinnerUser.getSelectedItem().toString();
                String selectedCategory = spinnerCategory.getSelectedItem().toString();
                String startDate = edtStartDate.getText().toString();
                String endDate = edtEndDate.getText().toString();

                if (!selectedUser.isEmpty() && !selectedCategory.isEmpty()) {
                    int userId = orderDAO.getUserIdByName(selectedUser);
                    if (startDate.isEmpty() || endDate.isEmpty()) {
                        orderDetails = orderDAO.getOrderDetailsByUserAndCategory(userId, selectedCategory);
                    } else {
                        orderDetails = orderDAO.getOrderDetailsByDateRangeAndUser(userId, startDate, endDate);
                    }
                } else {
                    orderDetails.clear();
                }

                if (orderDetails.isEmpty()) {
                    Toast.makeText(OrderStatisticsActivity.this, "No orders found for the selected criteria", Toast.LENGTH_SHORT).show();
                    orderAdapter = new OrderAdapter(new ArrayList<>(), OrderStatisticsActivity.this);
                } else {
                    orderAdapter = new OrderAdapter(orderDetails, OrderStatisticsActivity.this);
                }
                lvOrders.setAdapter(orderAdapter);
            }
        });
    }
}