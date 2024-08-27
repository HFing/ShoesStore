package com.hfing.shoesstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hfing.shoesstore.R;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.User;

public class IntroActivity extends AppCompatActivity {
    private Button startBtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private LinearLayout linearLayoutGG;
    private UsersDAO usersDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        usersDAO = new UsersDAO(this);
        linearLayoutGG = findViewById(R.id.googleSignInBtn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        linearLayoutGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGG();
            }
        });
    }

    void signInGG() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1000 == requestCode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    User user = new User();
                    user.setEmail(account.getEmail());
                    user.setName(account.getDisplayName());
                    user.setUsername(account.getEmail());
                    user.setPassword("123456");
                    user.setAddress("");
                    user.setPhone("");
                    user.setGender(0);

                    int userId = (int) usersDAO.addUser(user);
                    navigateToActivity(userId);
                }
            } catch (ApiException e) {
                Toast.makeText(this, "Failed to sign in", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToActivity(int userId) {
        finish();
        Intent intent = new Intent(this, BaseActivity.class);
        intent.putExtra("id", userId);
        startActivity(intent);
    }

    public void signup(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    public void login(View view) {
        startActivity(new Intent(this, LogIn.class));
    }
}