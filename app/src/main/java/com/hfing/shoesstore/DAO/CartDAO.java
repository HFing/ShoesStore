package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.Cart;
import com.hfing.shoesstore.Model.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private DBHelper dbHelper;
    public CartDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    @SuppressLint("Range")
    public List<Cart> getAllCart(){
        ArrayList<Cart> carts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM cart";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do {
                    Cart cart = new Cart();
                    cart.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    cart.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                    carts.add(cart);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return carts!=null?carts:new ArrayList<>();
    }

    public long insertCart(Cart cart){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_CART_USER_ID, cart.getUser_id());
            result = db.insert(DBHelper.TABLE_CART, null, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }

    @SuppressLint("Range")
    public Cart getCartByUserId(int user_id){
        Cart cart = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM cart WHERE user_id = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(user_id)});
            if (cursor.moveToFirst()){
                cart = new Cart();
                cart.setId(cursor.getInt(cursor.getColumnIndex("id")));
                cart.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return cart;
    }
}
