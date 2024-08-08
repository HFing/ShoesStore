package com.hfing.shoesstore.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hfing.shoesstore.Adapter.CategoryAdapter;
import com.hfing.shoesstore.DAO.CategoryDAO;
import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.R;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    Integer id = -1;
    Button btnAddCategory, btnUpdateCategory, btnDeleteCategory;
    EditText edtNameCategory;
    Context context= this;
    CategoryDAO categoryDAO = new CategoryDAO(context);
    ListView lvCate;
    ArrayList<Category> categories;
    CategoryAdapter categoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        lvCate= findViewById(R.id.lvCategory);
        edtNameCategory= findViewById(R.id.edtNameCategory);
        categories= categoryDAO.getAllCategories();
        categoryAdapter= new CategoryAdapter(context, categories);
        lvCate.setAdapter(categoryAdapter);
        btnAddCategory= findViewById(R.id.bntAddCate);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Category category= new Category();
                if (edtNameCategory.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter category name", Toast.LENGTH_SHORT).show();
                    return;
                }
                category.setName(edtNameCategory.getText().toString());
                if(categoryDAO.addCategory(category) > 0){
                    Toast.makeText(context, "Add category successfully", Toast.LENGTH_SHORT).show();
//                categoryDAO.addCategory(category);
                categories.clear();
                categories.addAll(categoryDAO.getAllCategories());
                categoryAdapter.notifyDataSetChanged();}
                else {
                    Toast.makeText(context, "Add category failed", Toast.LENGTH_SHORT).show();
                }
                reloadListView();
                edtNameCategory.setText("");
            }
        });
        lvCate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtNameCategory.setText(categories.get(i).getName());
                id= categories.get(i).getId();
            }
        });
        btnUpdateCategory = findViewById(R.id.bntUpdateCate);
        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadListView();
                Category category = new Category();
                if (edtNameCategory.getText().toString().isEmpty() || id == -1) {
                    Toast.makeText(context, "Please select category name", Toast.LENGTH_SHORT).show();
                    return;
                }
                category.setId(id);
                category.setName(edtNameCategory.getText().toString());
                if (categoryDAO.updateCategory(category) > 0 ) {
                    Toast.makeText(context, "Update category successfully", Toast.LENGTH_SHORT).show();

                    categoryAdapter.notifyDataSetChanged();
                }
                edtNameCategory.setText("");
                id = -1;
                reloadListView();
            }
        });

        btnDeleteCategory = findViewById(R.id.bntDeleteCate);
        btnDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadListView();
                if (id == -1) {
                    Toast.makeText(context, "Please select category name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (categoryDAO.deleteCategory(id) > 0) {
                    Toast.makeText(context, "Delete category successfully", Toast.LENGTH_SHORT).show();
                    categoryAdapter.notifyDataSetChanged();
                }
                edtNameCategory.setText("");
                id = -1;
                reloadListView();
            }
        });
    }
    public void reloadListView(){
        categories.clear();
        categories.addAll(categoryDAO.getAllCategories());
        categoryAdapter.notifyDataSetChanged();
    }
}