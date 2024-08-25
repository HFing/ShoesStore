package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hfing.shoesstore.Adapter.WishListAdapter;
import com.hfing.shoesstore.DAO.FavoriteDAO;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.R;
import java.util.List;

public class WishListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WishListAdapter wishListAdapter;
    private FavoriteDAO favoriteDAO;
    private int userId;
    private TextView emptyTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        userId = getIntent().getIntExtra("id", -1);
        favoriteDAO = new FavoriteDAO(this);

        recyclerView = findViewById(R.id.recyclerViewWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyTextView = findViewById(R.id.emptyTextView);

        loadFavorites();

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WishListActivity.this, BaseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadFavorites() {
        List<Product> favoriteProducts = favoriteDAO.getFavoritesByUserId(userId);
        if (favoriteProducts.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            wishListAdapter = new WishListAdapter(favoriteProducts, this, userId);
            recyclerView.setAdapter(wishListAdapter);
        }
    }
}