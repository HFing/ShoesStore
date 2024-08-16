package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.hfing.shoesstore.Adapter.CategoryBaseViewAdapter;
import com.hfing.shoesstore.Adapter.ProductAdapterRCM;
import com.hfing.shoesstore.Adapter.BannerSliderAdapter;
import com.hfing.shoesstore.Adapter.RecycleViewOnItemClickListener;
import com.hfing.shoesstore.DAO.CategoryDAO;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements RecycleViewOnItemClickListener {

    private final UsersDAO  usersDAO = new UsersDAO(BaseActivity.this);
    private User user;
    private final ProductDAO productDAO = new ProductDAO(BaseActivity.this);
    private final CategoryDAO categoryDAO = new CategoryDAO(BaseActivity.this);
    ArrayList<Product> productListAfterFilter = new ArrayList<>();
    TextView nameOfUser;
    ViewPager2 viewpagerProductSlider;
    DotsIndicator dotsIndicator_ProductSlider_baseView;
    RecyclerView viewCategory_baseview, viewPopular;
    ProgressBar progressBarPopular;
    EditText searchEditText;
    ImageView searchBtn;
    LinearLayout orderHistoryLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        TextView noResultsTextView = findViewById(R.id.noResultsTextView);
        TextView headTextView = findViewById(R.id.textView8);
        //Xác định User đang thực hiện
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        //int id = 3;

        user = usersDAO.getUserById(id);

        if (user == null){
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
        }
        //Hiển thị tên người dùng
        nameOfUser = findViewById(R.id.textNameOfUser);
        nameOfUser.setText(user.getName());

        ArrayList<Product> products = productDAO.getAllProducts();

        //Hiển thị banner danh sách sản phẩm

        // Khởi tạo danh sách các ID của drawable
        List<Integer> imageResIds = new ArrayList<>();
        imageResIds.add(R.drawable.banner1);
        imageResIds.add(R.drawable.banner2);
        imageResIds.add(R.drawable.banner3);

        // Hiển thị banner danh sách sản phẩm
        viewpagerProductSlider = findViewById(R.id.viewpagerProductSlider);
        dotsIndicator_ProductSlider_baseView = findViewById(R.id.dotsIndicator_ProductSlider_baseView);

        BannerSliderAdapter bannerSliderAdapter = new BannerSliderAdapter(this, imageResIds);
        viewpagerProductSlider.setAdapter(bannerSliderAdapter);
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
        //Hiển thị danh sách danh mục
        viewCategory_baseview = findViewById(R.id.viewCategory_baseview);
        ArrayList<Category> categories = categoryDAO.getAllCategories();
        CategoryBaseViewAdapter categoryBaseViewAdapter = new CategoryBaseViewAdapter(this, categories);
        viewCategory_baseview.setAdapter(categoryBaseViewAdapter);
        viewCategory_baseview.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        ProgressBar progressBarCategory = findViewById(R.id.progressBarCategory);
        progressBarCategory.setVisibility(View.GONE);

        //Hiển thị trang chủ sản phẩm
        viewPopular = findViewById(R.id.viewPopular);
        progressBarPopular = findViewById(R.id.progressBarPopular);
        progressBarPopular.setVisibility(View.VISIBLE);

        ProductAdapterRCM productAdapter = new ProductAdapterRCM(this, products, this, productDAO);
        viewPopular.setAdapter(productAdapter);
        viewPopular.setLayoutManager(new GridLayoutManager(this, 2));

        // Hide progress bar after setting up the adapter
        progressBarPopular.setVisibility(View.GONE);


        searchEditText = findViewById(R.id.searchEditText);
        searchBtn = findViewById(R.id.searchBtn);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Gọi phương thức filterProducts khi văn bản thay đổi
                List<Product> filteredProducts = new ArrayList<>();
                for (Product product : products) {
                    if (product.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredProducts.add(product);
                    }
                }
                productListAfterFilter.clear();
                productListAfterFilter.addAll(filteredProducts);
                // Cập nhật adapter với danh sách sản phẩm đã lọc
                productAdapter.updateProducts(filteredProducts);

                if (s.toString().isEmpty()) {
                    headTextView.setText("Recommendation");
                    noResultsTextView.setVisibility(View.GONE);
                } else if (filteredProducts.isEmpty()) {
                    headTextView.setText("Tìm kiếm sản phẩm");
                    noResultsTextView.setVisibility(View.VISIBLE);
                } else {
                    headTextView.setText("Tìm kiếm sản phẩm");
                    noResultsTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });

        orderHistoryLayout = findViewById(R.id.orderhistory);
        orderHistoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, OrderListActivity.class);
                intent.putExtra("id", user.getId());
                startActivity(intent);
            }
        });

    }
    @Override
    public void onItemRecycleViewClick(int position) {
        Product product;
        if (productListAfterFilter.isEmpty()) {
            product = productDAO.getAllProducts().get(position);
        } else {
            product = productListAfterFilter.get(position);
        }
        Toast.makeText(this, "Item clicked at position: " + position, Toast.LENGTH_SHORT).show();
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("product_id", product.getId());
        detailIntent.putExtra("user_id", user.getId());
        startActivity(detailIntent);
    }
}