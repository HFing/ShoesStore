package com.hfing.shoesstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.io.Serializable;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etUserName, etUserEmail, etUserAddress, etUserPhone, etPassword, etUsername;
    private Spinner spUserGender;
    private Button btnSave;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etUserName = findViewById(R.id.etUserName);
        etUserEmail = findViewById(R.id.etUserEmail);
        etUserAddress = findViewById(R.id.etUserAddress);
        etUserPhone = findViewById(R.id.etUserPhone);
        etUsername = findViewById(R.id.etUsername);
        spUserGender = findViewById(R.id.spUserGender);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserGender.setAdapter(adapter);

        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id", -1);

        UsersDAO usersDAO = new UsersDAO(this);
        User currentUser = usersDAO.getUserById(userId);

        if (currentUser != null) {
            etUserName.setText(currentUser.getName());
            etUserEmail.setText(currentUser.getEmail());
            etUserAddress.setText(currentUser.getAddress());
            etUserPhone.setText(currentUser.getPhone());
            etUsername.setText(currentUser.getUsername());
            spUserGender.setSelection(currentUser.getGender() == 1 ? 0 : 1);
            etPassword.setText(currentUser.getPassword());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    User updatedUser = new User();
                    updatedUser.setId(userId);
                    updatedUser.setName(etUserName.getText().toString());
                    updatedUser.setEmail(etUserEmail.getText().toString());
                    updatedUser.setAddress(etUserAddress.getText().toString());
                    updatedUser.setPhone(etUserPhone.getText().toString());
                    updatedUser.setUsername(etUsername.getText().toString());
                    updatedUser.setGender(spUserGender.getSelectedItem().toString().equals("Male") ? 1 : 0);
                    updatedUser.setPassword(etPassword.getText().toString());

                    usersDAO.updateUser(updatedUser);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedUser", (Serializable) updatedUser);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    private boolean validateInputs() {
        if (etUserName.getText().toString().isEmpty()) {
            Toast.makeText(this, "User Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etUserEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "User Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etUserAddress.getText().toString().isEmpty()) {
            Toast.makeText(this, "User Address is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etUserPhone.getText().toString().isEmpty()) {
            Toast.makeText(this, "User Phone is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}