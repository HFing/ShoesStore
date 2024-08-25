package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {
    private DBHelper dbHelper;
    private ProductDAO productDAO;

    public FavoriteDAO(Context context) {
        dbHelper = new DBHelper(context);
        productDAO = new ProductDAO(context);
    }

    public void addFavorite(int userId, int productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("product_id", productId);
        db.insert("Favorite", null, values);
        db.close();
    }

    public void removeFavorite(int userId, int productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Favorite", "user_id=? AND product_id=?", new String[]{String.valueOf(userId), String.valueOf(productId)});
        db.close();
    }

    public List<Product> getFavoritesByUserId(int userId) {
        List<Product> favoriteProducts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Favorite", new String[]{"product_id"}, "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int productId = cursor.getInt(cursor.getColumnIndex("product_id"));
                Product product = productDAO.getProductById(productId);
                if (product != null) {
                    favoriteProducts.add(product);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return favoriteProducts;
    }

    public boolean hasFavorites() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM Favorite";
        Cursor cursor = db.rawQuery(query, null);
        boolean hasData = false;
        if (cursor.moveToFirst()) {
            hasData = cursor.getInt(0) > 0;
        }
        cursor.close();
        db.close();
        return hasData;
    }


    public boolean isFavorite(int userId, int productId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM Favorite WHERE user_id=? AND product_id=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(productId)});
        boolean isFavorite = false;
        if (cursor.moveToFirst()) {
            isFavorite = cursor.getInt(0) > 0;
        }
        cursor.close();
        db.close();
        return isFavorite;
    }
}