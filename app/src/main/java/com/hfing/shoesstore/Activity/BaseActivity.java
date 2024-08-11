package com.hfing.shoesstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.hfing.shoesstore.Adapter.ProductSliderAdapter;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    private UsersDAO usersDAO = new UsersDAO(BaseActivity.this);
    private User user;
    private ProductDAO productDAO = new ProductDAO(BaseActivity.this);
    TextView nameOfUser;
    ViewPager2 viewpagerProductSlider;
    DotsIndicator dotsIndicator_ProductSlider_baseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //Xác định User đang thực hiện
//        Intent intent = getIntent();
//        int id = intent.getIntExtra("id", -1);
        int id = 3;

        user = usersDAO.getUserById(id);

        if (user == null){
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
        }
        //Hiển thị tên người dùng
        nameOfUser = findViewById(R.id.textNameOfUser);
        nameOfUser.setText(user.getName());
        //Hiển thị danh sách sản phẩm
        viewpagerProductSlider = findViewById(R.id.viewpagerProductSlider);
        dotsIndicator_ProductSlider_baseView = findViewById(R.id.dotsIndicator_ProductSlider_baseView);

        ArrayList<Product> products = productDAO.getAllProducts();
        ProductSliderAdapter productSliderAdapter = new ProductSliderAdapter(this, products);
        viewpagerProductSlider.setAdapter(productSliderAdapter);
        //kết nối DotsIndicator với ViewPager2
        dotsIndicator_ProductSlider_baseView.setViewPager2(viewpagerProductSlider);
        //Hiển thị ProgressBar khi tải dữ liệu
        ProgressBar progressBarBaner = findViewById(R.id.progressBarBaner);
        viewpagerProductSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                progressBarBaner.setVisibility(View.GONE);
                dotsIndicator_ProductSlider_baseView.setVisibility(View.VISIBLE);
            }
        });

    }
}