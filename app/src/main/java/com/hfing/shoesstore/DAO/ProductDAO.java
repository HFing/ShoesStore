package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.ProductSize;

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
        return products != null ? products : new ArrayList<>();
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

    public double getAvengerRating(String nameProduct){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        double rating = 0;
        try {
            String query = "SELECT "
                    + DBHelper.TABLE_PRODUCT + "." + DBHelper.COLUMN_PRODUCT_ID + " AS product_id, "
                    + DBHelper.TABLE_PRODUCT + "." + DBHelper.COLUMN_PRODUCT_NAME + " AS product_name, "
                    + "AVG(" + DBHelper.TABLE_REVIEW + "." + DBHelper.COLUMN_REVIEW_RATING + ") AS rating "
                    + "FROM " + DBHelper.TABLE_PRODUCT + " "
                    + "LEFT JOIN " + DBHelper.TABLE_REVIEW + " ON "
                    + DBHelper.TABLE_PRODUCT + "." + DBHelper.COLUMN_PRODUCT_ID
                    + " = " + DBHelper.TABLE_REVIEW + "." + DBHelper.COLUMN_REVIEW_PRODUCT_ID + " "
                    + "WHERE " + DBHelper.TABLE_PRODUCT + "." + DBHelper.COLUMN_PRODUCT_NAME + " = '" + nameProduct + "' "
                    + "GROUP BY " + DBHelper.TABLE_PRODUCT + "." + DBHelper.COLUMN_PRODUCT_ID + ", "
                    + DBHelper.TABLE_PRODUCT + "." + DBHelper.COLUMN_PRODUCT_NAME + ";";
            cursor = db.rawQuery(query, null);
            if (cursor == null)
                return 5;
            if (cursor.moveToFirst()) {
                rating = cursor.getDouble(2);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return rating != 0 ? rating : 5;
    }

    @SuppressLint("Range")
    public Product getProductById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Product product = new Product();
        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_PRODUCT + " WHERE " + DBHelper.COLUMN_PRODUCT_ID + " = " + id;
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                product.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_NAME)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_DESCRIPTION)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_PRICE)));
                product.setCreate_at(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_CREATE_AT)));
                product.setImage(cursor.getBlob(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_IMAGE)));
                product.setCategory_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCT_CATEGORY_ID)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return product != null ? product : new Product();
    }
    @SuppressLint("Range")
    public ArrayList<Product> getNewestProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM product ORDER BY create_at DESC LIMIT 4";
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

    @SuppressLint("Range")
    public ArrayList<Product> getProductsByCategoryId(int categoryId) {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM product WHERE " + DBHelper.COLUMN_PRODUCT_CATEGORY_ID + " = " + categoryId;
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
}
