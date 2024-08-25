package com.hfing.shoesstore.Activity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        userId = getIntent().getIntExtra("id", -1);
        favoriteDAO = new FavoriteDAO(this);

        recyclerView = findViewById(R.id.recyclerViewWishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadFavorites();
    }

    private void loadFavorites() {
        List<Product> favoriteProducts = favoriteDAO.getFavoritesByUserId(userId);
        wishListAdapter = new WishListAdapter(favoriteProducts, this, userId);
        recyclerView.setAdapter(wishListAdapter);
    }
}