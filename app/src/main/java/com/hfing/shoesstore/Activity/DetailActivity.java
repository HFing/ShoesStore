package com.hfing.shoesstore.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.hfing.shoesstore.Adapter.ProductImageAdapter;
import com.hfing.shoesstore.Adapter.ProductSizeAdapter;
import com.hfing.shoesstore.Adapter.ReviewAdapter;
import com.hfing.shoesstore.DAO.CartDAO;
import com.hfing.shoesstore.DAO.CartItemDAO;
import com.hfing.shoesstore.DAO.FavoriteDAO;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.ProductSizeDAO;
import com.hfing.shoesstore.DAO.ReviewDAO;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.Cart;
import com.hfing.shoesstore.Model.CartItem;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.ProductSize;
import com.hfing.shoesstore.Model.Review;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    Button btnAddToCart;
    TextView titleTxt, priceTxt, descriptionTxt, ratingTxt;
    User user;
    UsersDAO userDAO = new UsersDAO(DetailActivity.this);
    Product product;
    ProductDAO productDAO = new ProductDAO(DetailActivity.this);
    ProductSizeDAO productSizeDAO = new ProductSizeDAO(DetailActivity.this);
    ReviewDAO reviewDAO = new ReviewDAO(DetailActivity.this);
    ImageView backBtn;
    ViewPager2 slider;
    RecyclerView sizeList;
    RecyclerView reviewList;
    ProductSize selectedProductSize;
    ImageButton cartBtn;

    ImageView favBtn;
    FavoriteDAO favoriteDAO = new FavoriteDAO(DetailActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        int user_id = intent.getIntExtra("user_id", -1);
        int product_id = intent.getIntExtra("product_id", -1);
        user = userDAO.getUserById(user_id);
        product = productDAO.getProductById(product_id);

        if (user == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (product == null) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        titleTxt = findViewById(R.id.titleTxt);
        priceTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        ratingTxt = findViewById(R.id.ratingTxt);
        btnAddToCart = findViewById(R.id.addToCartBtn);
        backBtn = findViewById(R.id.backBtn);
        cartBtn = findViewById(R.id.cartBtn);
        slider = findViewById(R.id.slider);
        sizeList = findViewById(R.id.sizeList);
        reviewList = findViewById(R.id.reviewList);

        titleTxt.setText(product.getName());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        priceTxt.setText(formatter.format(product.getPrice()));
        descriptionTxt.setText(product.getDescription());
        ratingTxt.setText(Double.toString(productDAO.getAvengerRating(product.getName())));

        List<Product> products = new ArrayList<>();
        products.add(product);
        ProductImageAdapter productImageAdapter = new ProductImageAdapter(DetailActivity.this, products);
        slider.setAdapter(productImageAdapter);

        List<ProductSize> productSizes = productSizeDAO.getProductSizesByProduct(product);
        ProductSizeAdapter productSizeAdapter = new ProductSizeAdapter(productSizes);
        sizeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sizeList.setAdapter(productSizeAdapter);

        productSizeAdapter.setOnSizeClickListener(new ProductSizeAdapter.OnSizeClickListener() {
            @Override
            public void onSizeSelected(ProductSize productSize) {
                selectedProductSize = productSize;
            }
        });

        List<Review> reviews = reviewDAO.getReviewsByProductId(product_id);
        ReviewAdapter reviewAdapter = new ReviewAdapter(reviews, DetailActivity.this);
        reviewList.setLayoutManager(new LinearLayoutManager(this));
        reviewList.setAdapter(reviewAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCartAndNavigate(user.getId(), product.getId());
            }
        });

//        cartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent cartIntent = new Intent(DetailActivity.this, CartActivity.class);
//                cartIntent.putExtra("user_id", user.getId());
//                startActivity(cartIntent);
//            }
//        });


        favBtn = findViewById(R.id.favBtn);
        updateFavoriteIcon();

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavorite(user.getId(), product.getId());
            }
        });

    }
    private void toggleFavorite(int userId, int productId) {
        if (favoriteDAO.isFavorite(userId, productId)) {
            favoriteDAO.removeFavorite(userId, productId);
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            favoriteDAO.addFavorite(userId, productId);
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        }
        updateFavoriteIcon();
    }

    private void updateFavoriteIcon() {
        if (favoriteDAO.isFavorite(user.getId(), product.getId())) {
            favBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_filled, getTheme()));
        } else {
            favBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border, getTheme()));
        }
    }
    private void addToCartAndNavigate(int user_id, int product_id) {
        if (selectedProductSize == null) {
            Toast.makeText(DetailActivity.this, "Please select a size", Toast.LENGTH_SHORT).show();
            return;
        }
        int quantity = 1;
        CartDAO cartDAO = new CartDAO(DetailActivity.this);
        CartItemDAO cartItemDAO = new CartItemDAO(DetailActivity.this);
        Cart cart = cartDAO.getCartByUserId(user_id);
        if (cart == null) {
            cart = new Cart();
            cart.setUser_id(user_id);
            cartDAO.insertCart(cart);
            cart = cartDAO.getCartByUserId(user_id);
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart_id(cart.getId());
        cartItem.setProduct_id(product_id);
        cartItem.setProduct_size_id(selectedProductSize.getId());
        cartItem.setQuantity(quantity);
        if (cartItemDAO.addCartItem(cartItem) > 0) {
            Intent cartIntent = new Intent(DetailActivity.this, CartActivity.class);
            cartIntent.putExtra("user_id", user_id);
            startActivity(cartIntent);
        } else {
            Toast.makeText(DetailActivity.this, "Add to cart failed", Toast.LENGTH_SHORT).show();
        }
    }

}
