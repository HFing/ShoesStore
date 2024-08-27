package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

public class ProfileActivity extends AppCompatActivity {
    User user;
    private Button backBtn;
    private TextView btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        int user_id = intent.getIntExtra("user_id", -1);

        UsersDAO usersDAO = new UsersDAO(this);
        User currentUser = usersDAO.getUserById(user_id);

        if (currentUser == null) {
            // Handle user not found
            finish();
            return;
        }

        user = currentUser;

        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvUserEmail = findViewById(R.id.tvUserEmail);
        TextView tvUserAddress = findViewById(R.id.tvUserAddress);
        TextView tvUserPhone = findViewById(R.id.tvUserPhone);
        TextView tvUserGender = findViewById(R.id.tvUserGender);


        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, BaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", user.getId());
                startActivity(intent);
                finish();
            }
        });

        tvUserName.setText(currentUser.getName());
        tvUserEmail.setText(currentUser.getEmail());
        tvUserAddress.setText(currentUser.getAddress());
        tvUserPhone.setText(currentUser.getPhone());
        tvUserGender.setText(currentUser.getGender() == 1 ? "Male" : "Female");

        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                editIntent.putExtra("user_id", user.getId());
                startActivityForResult(editIntent, 1);
            }
        });

        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogOutConfirmationDialog();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            User updatedUser = (User) data.getSerializableExtra("updatedUser");
            if (updatedUser != null) {
                user = updatedUser;
                TextView tvUserName = findViewById(R.id.tvUserName);
                TextView tvUserEmail = findViewById(R.id.tvUserEmail);
                TextView tvUserAddress = findViewById(R.id.tvUserAddress);
                TextView tvUserPhone = findViewById(R.id.tvUserPhone);
                TextView tvUserGender = findViewById(R.id.tvUserGender);

                tvUserName.setText(user.getName());
                tvUserEmail.setText(user.getEmail());
                tvUserAddress.setText(user.getAddress());
                tvUserPhone.setText(user.getPhone());
                tvUserGender.setText(user.getGender() == 1 ? "Male" : "Female");
            }
        }
    }

    private void showLogOutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Do you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Navigate to IntroActivity
                        Intent intent = new Intent(ProfileActivity.this, IntroActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}