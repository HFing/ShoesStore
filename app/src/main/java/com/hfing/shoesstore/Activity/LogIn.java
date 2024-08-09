package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.R;

public class LogIn extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LogIn.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LogIn.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                UsersDAO usersDAO = new UsersDAO(LogIn.this);
                if (usersDAO.checkUser(email, password)) {
                    Toast.makeText(LogIn.this, "Login successful", Toast.LENGTH_SHORT).show();

                    int roleId = usersDAO.getUserRole(email);
                    Intent intent;
                    if (roleId == 1) { // Assuming 1 is for admin
                        intent = new Intent(LogIn.this, DashboardActivity.class); // Replace AdminActivity with your admin activity
                    } else {
                        intent = new Intent(LogIn.this, DetailActivity.class); // Replace UserActivity with your user activity
                    }
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LogIn.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}