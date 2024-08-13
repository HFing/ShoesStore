package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap selectedImageBitmap;

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

        //Chon anh tu thu vien
        imgProduct = findViewById(R.id.imgProduct);
        Button btnSelectImage = findViewById(R.id.btnSelectImageProduct);
        btnSelectImage.setOnClickListener(v -> openImageChooser());

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

        btnUpdateProduct=findViewById(R.id.bntUpdateProduct);
        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct();
            }
        });

        btnDeleteProduct=findViewById(R.id.bntDeleteProduct);
        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });

        lvProduct.setOnItemClickListener((adapterView, view, i, l) -> {
            Product product = products.get(i);
            edtNameProduct.setText(product.getName());
            DecimalFormat decimalFormat = new DecimalFormat("#");
            decimalFormat.setMaximumFractionDigits(0);
            edtPriceProduct.setText(decimalFormat.format(product.getPrice()));
            edtDescriptionProduct.setText(product.getDescription());
            id = product.getId();

            // Lấy danh mục của sản phẩm được chọn
            int categoryId = product.getCategory_id();
            ArrayAdapter<Category> categoryAdapter = (ArrayAdapter<Category>) spnCategoryProduct.getAdapter();
            for (int position = 0; position < categoryAdapter.getCount(); position++) {
                if (categoryAdapter.getItem(position).getId() == categoryId) {
                    spnCategoryProduct.setSelection(position);
                    break;
                }
            }
            // Hiển thị ảnh sản phẩm
            byte[] imageBytes = product.getImage();
            if (imageBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imgProduct.setImageBitmap(bitmap);
            } else {
                imgProduct.setImageResource(R.drawable.ic_launcher_background); // Ảnh mặc định nếu không có ảnh
            }
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
        if (selectedImageBitmap != null) {
            product.setImage(getImageBytes(selectedImageBitmap));
        } else {
            product.setImage(null);
        }

        if (productDAO.addProduct(product) > 0) {
            Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            reloadListView();
        } else {
            Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
        reloadListView();
    }

    private void updateProduct() {
        if (id == -1) {
            Toast.makeText(ProductActivity.this, "Vui lòng chọn sản phẩm cần cập nhật", Toast.LENGTH_SHORT).show();
            return;
        }
        Product product = new Product();
        product.setId(id);
        product.setName(edtNameProduct.getText().toString());
        product.setPrice(Double.parseDouble(edtPriceProduct.getText().toString()));
        product.setDescription(edtDescriptionProduct.getText().toString());
        product.setCategory_id(((Category) spnCategoryProduct.getSelectedItem()).getId());

        // Check if a new image is selected, otherwise use the existing image
        if (selectedImageBitmap != null) {
            product.setImage(getImageBytes(selectedImageBitmap));
        } else {
            Product existingProduct = productDAO.getProductById(id);
            product.setImage(existingProduct.getImage());
        }

        if (productDAO.updateProduct(product) > 0) {
            Toast.makeText(ProductActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
            reloadListView();
            selectedImageBitmap = null; // Reset selected image
        } else {
            Toast.makeText(ProductActivity.this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteProduct() {
        if (id == -1) {
            Toast.makeText(ProductActivity.this, "Vui lòng chọn sản phẩm cần xóa", Toast.LENGTH_SHORT).show();
            return;
        }
        if (productDAO.deleteProduct(id) > 0) {
            Toast.makeText(ProductActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
            reloadListView();
            selectedImageBitmap = null; // Reset selected image
        } else {
            Toast.makeText(ProductActivity.this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
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
        selectedImageBitmap = null;
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                imgProduct.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}