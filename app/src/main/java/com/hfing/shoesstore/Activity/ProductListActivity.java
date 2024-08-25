package com.hfing.shoesstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.Adapter.ListProductAdapter;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private ProductDAO productDAO;
    private List<Product> products;
    private int userId; // Khai báo userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id", -1); // Khởi tạo userId
        if (userId == -1) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        productDAO = new ProductDAO(this);
        products = productDAO.getAllProducts();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewProductList);
        ListProductAdapter productAdapter = new ListProductAdapter(this, products, new ListProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent detailIntent = new Intent(ProductListActivity.this, DetailActivity.class);
                detailIntent.putExtra("product_id", product.getId());
                detailIntent.putExtra("user_id", userId);
                startActivity(detailIntent);
            }
        }, userId);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProductListActivity.this, BaseActivity.class);
        intent.putExtra("id", userId); // Truyền userId khi quay lại BaseActivity
        startActivity(intent);
        finish();
    }
}