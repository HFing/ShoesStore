package com.hfing.shoesstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

public class OrderListActivity extends AppCompatActivity {

    private final UsersDAO usersDAO = new UsersDAO(OrderListActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        User user = usersDAO.getUserById(id);

        if (user == null){
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}