// OrderListActivity.java
package com.hfing.shoesstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.Adapter.OrderHistoryAdapter;
import com.hfing.shoesstore.DAO.OrderDetailDAO;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.util.Collections;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewOrders;
    private OrderHistoryAdapter orderHistoryAdapter;
    private OrderDetailDAO orderDetailDAO;
    private User user;
    private UsersDAO usersDAO = new UsersDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        orderDetailDAO = new OrderDetailDAO(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        User user = usersDAO.getUserById(id);

        if (user == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<Orders> ordersList = orderDetailDAO.getOrdersByUserId(id);
        Collections.reverse(ordersList);
        orderHistoryAdapter = new OrderHistoryAdapter(ordersList, this);
        recyclerViewOrders.setAdapter(orderHistoryAdapter);

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderListActivity.this, BaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
    }
}