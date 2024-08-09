package com.hfing.shoesstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.Product;

import java.util.ArrayList;

public class ProductDAO {
    private  final DBHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new DBHelper(context);
    }
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            String query = "SELECT * FROM product";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setPrice(cursor.getDouble(3));
                product.setImage(cursor.getBlob(4));
                product.setCategory_id(cursor.getInt(5));
                products.add(product);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public long addProduct (Product product){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("category_id", product.getCategory_id());
        values.put("image", product.getImage());
        return db.insert(dbHelper.TABLE_PRODUCT, null, values);
    }

    public long updateProduct(Product product){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("category_id", product.getCategory_id());
        values.put("image", product.getImage());
        return db.update(dbHelper.TABLE_PRODUCT, values, dbHelper.COLUMN_CATEGORY_ID + " = " + product.getId(), null);

    }

    public long deleteProduct(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(dbHelper.TABLE_PRODUCT, dbHelper.COLUMN_PRODUCT_ID + " = " + id, null);
    }
}
