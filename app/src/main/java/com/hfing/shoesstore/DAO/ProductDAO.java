package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProductDAO {
    private  final DBHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new DBHelper(context);
    }
    @SuppressLint("Range")
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM product";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    product.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_ID)));
                    product.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_NAME)));
                    product.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_DESCRIPTION)));
                    product.setPrice(cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_PRICE)));
                    product.setCreate_at(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_CREATE_AT)));
                    product.setImage(cursor.getBlob(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_IMAGE)));
                    product.setCategory_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_CATEGORY_ID)));
                    products.add(product);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return products;
    }

    public long addProduct (Product product){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("create_at", product.getCreate_at());
        values.put("category_id", product.getCategory_id());
        values.put("create_at", product.getCreate_at());
        values.put("image", product.getImage());

        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        values.put("create_at", currentDateTime);
        return db.insert(dbHelper.TABLE_PRODUCT, null, values);
    }

    public long updateProduct(Product product){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("create_at", product.getCreate_at());
        values.put("category_id", product.getCategory_id());
        values.put("create_at", product.getCreate_at());
        values.put("image", product.getImage());
        return db.update(dbHelper.TABLE_PRODUCT, values, dbHelper.COLUMN_CATEGORY_ID + " = " + product.getId(), null);

    }

    public long deleteProduct(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(dbHelper.TABLE_PRODUCT, dbHelper.COLUMN_PRODUCT_ID + " = " + id, null);
    }
}
