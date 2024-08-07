package com.hfing.shoesstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hfing.shoesstore.R;

public class IntroActivity extends AppCompatActivity {
    private Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void signup(View view) {
        startActivity(new Intent(this, SignUp.class));
    }




    public void login(View view) {
        startActivity(new Intent(this, LogIn.class));
    }
}