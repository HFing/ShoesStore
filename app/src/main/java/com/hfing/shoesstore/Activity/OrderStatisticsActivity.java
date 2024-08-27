package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hfing.shoesstore.Adapter.OrderAdapter;
import com.hfing.shoesstore.DAO.CategoryDAO;
import com.hfing.shoesstore.DAO.OrderDAO;
import com.hfing.shoesstore.DAO.OrderDetailDAO;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderStatisticsActivity extends AppCompatActivity {

    ListView lvOrders;
    Spinner spinnerUser, spinnerCategory;
    EditText edtStartDate, edtEndDate;
    Button btnFetchOrders;
    OrderDAO orderDAO;
    UsersDAO usersDAO;
    CategoryDAO categoryDAO;
    OrderDetailDAO orderDetailDAO;
    ArrayList<OrderDetail> orderDetails;
    OrderAdapter orderAdapter;
    Calendar calendar;

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
        usersDAO = new UsersDAO(this);
        orderDAO = new OrderDAO(this);
        categoryDAO = new CategoryDAO(this);
        orderDetailDAO = new OrderDetailDAO(this);
        orderDetails = new ArrayList<>();
        calendar = Calendar.getInstance();

        // Populate spinner with user data
        ArrayList<String> users = new ArrayList<>();
        for (User user : usersDAO.getAllUsers()) {
            users.add(user.getUsername() + " - " + user.getName());
        }
        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(userAdapter);

        // Populate spinner with category data
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : categoryDAO.getAllCategories()) {
            categories.add(category.getName());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Initialize date picker
        initializeDatePicker();

        btnFetchOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedUser = spinnerUser.getSelectedItemPosition();
                int selectedCategory = spinnerCategory.getSelectedItemPosition();
                String startDate = edtStartDate.getText().toString();
                String endDate = edtEndDate.getText().toString();
                User user = usersDAO.getAllUsers().get(selectedUser);
                Category category = categoryDAO.getCategoryById(selectedCategory + 1);

                if (user != null && category != null) {
                    if (startDate.isEmpty()) {
                        orderDetails = (ArrayList<OrderDetail>) orderDetailDAO.getOrderDetailsByUserAndCategory(user, category);
                    } else {
                        orderDetails = (ArrayList<OrderDetail>) orderDetailDAO.getOrderDetailsByUserCategoryAndDate(user, category, startDate, endDate);
                    }
                } else {
                    orderDetails.clear();
                    Toast.makeText(OrderStatisticsActivity.this, "Invalid user or category selection", Toast.LENGTH_SHORT).show();
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

    private void initializeDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog(edtStartDate);
            }
        });

        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog(edtEndDate);
            }
        });

        edtEndDate.setText(sdf.format(calendar.getTime()));
    }

    private void showDatePickDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(OrderStatisticsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayofMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                editText.setText(sdf.format(selectedDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}