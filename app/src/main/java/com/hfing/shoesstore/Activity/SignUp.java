package com.hfing.shoesstore.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

public class SignUp extends AppCompatActivity {

    private EditText edtUserName, edtName, edtEmail, edtPassword;
    private Button btnSignUp;
    private Switch switchPolicy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        edtUserName = findViewById(R.id.edt_username_signup);
        edtName = findViewById(R.id.edt_name_signup);
        edtEmail = findViewById(R.id.edt_email_signup);
        edtPassword = findViewById(R.id.edt_password_signup);
        btnSignUp = findViewById(R.id.btn_signup);
        switchPolicy = findViewById(R.id.switch_policy);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersDAO usersDAO = new UsersDAO(SignUp.this);
                User users = new User();

                String userName = edtUserName.getText().toString();
                String fullName = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if (userName.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!switchPolicy.isChecked()) {
                    Toast.makeText(SignUp.this, "Please accept the policy and terms", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (usersDAO.checkUsernameExists(userName))
                    Toast.makeText(SignUp.this, "Username already exists", Toast.LENGTH_SHORT).show();

                if (usersDAO.checkEmailExists(email))
                    Toast.makeText(SignUp.this, "Email already exists", Toast.LENGTH_SHORT).show();


                users.setUsername(userName);
                users.setName(fullName);
                users.setEmail(email);
                users.setPassword(password);
                users.setRole_id(2);

                if (usersDAO.addUser(users) > 0) {
                    Toast.makeText(SignUp.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });

    }
}