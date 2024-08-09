package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hfing.shoesstore.Adapter.ProductAdapter;
import com.hfing.shoesstore.DAO.CategoryDAO;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.R;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    EditText edtNameProduct, edtPriceProduct, edtDescriptionProduct;
    Spinner spnCategoryProduct;
    Button btnAddProduct, btnUpdateProduct, btnDeleteProduct;
    ListView lvProduct;
    ImageView imgProduct;
    ProductDAO productDAO;
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    Integer id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        lvProduct = findViewById(R.id.lvProduct);
        edtNameProduct = findViewById(R.id.edtNameProduct);
        edtPriceProduct = findViewById(R.id.edtPriceProduct);
        edtDescriptionProduct = findViewById(R.id.edtDescriptionProduct);
        spnCategoryProduct = findViewById(R.id.spinnerCateProduct);
        imgProduct = findViewById(R.id.imgProduct);

        // hien list view
        productDAO = new ProductDAO(this);
        products = productDAO.getAllProducts();
        productAdapter = new ProductAdapter(this, products);
        lvProduct.setAdapter(productAdapter);

        btnAddProduct=findViewById(R.id.bntAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });


        lvProduct.setOnItemClickListener((adapterView, view, i, l) -> {
            Product product = products.get(i);
            edtNameProduct.setText(product.getName());
            edtPriceProduct.setText(String.valueOf(product.getPrice()));
            edtDescriptionProduct.setText(product.getDescription());
//            imgProduct.setImageBitmap(product.getImage());
            id = product.getId();
        });

        //lay danh sách danh muc va hien thi vao trong spinner
        CategoryDAO categoryDAO = new CategoryDAO(this);
        ArrayList<Category> categories = categoryDAO.getAllCategories();
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoryProduct.setAdapter(categoryAdapter);
    }

    private void addProduct(){
        if (edtNameProduct.getText().toString().isEmpty() || edtPriceProduct.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        Product product = new Product();
        product.setName(edtNameProduct.getText().toString());
        product.setPrice(Double.parseDouble(edtPriceProduct.getText().toString()));
        product.setDescription(edtDescriptionProduct.getText().toString());
        product.setCategory_id(((Category) spnCategoryProduct.getSelectedItem()).getId());
        product.setImage(null);

        if (productDAO.addProduct(product) > 0) {
            Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            reloadListView();
        } else {
            Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
        reloadListView();
    }

    private void reloadListView() {
        products.clear();
        products.addAll(productDAO.getAllProducts());
        productAdapter.notifyDataSetChanged();
        edtNameProduct.setText("");
        edtPriceProduct.setText("");
        edtDescriptionProduct.setText("");
        spnCategoryProduct.setSelection(0);
        imgProduct.setImageResource(R.drawable.ic_launcher_background);
    }
}