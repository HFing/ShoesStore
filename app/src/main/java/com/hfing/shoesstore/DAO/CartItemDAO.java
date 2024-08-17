package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.CartItem;
import com.hfing.shoesstore.Model.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {
    private final DBHelper dbHelper;
    public CartItemDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    @SuppressLint("Range")
    public List<CartItem> getCartItems() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + dbHelper.TABLE_CARTITEM;
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    CartItem cartItem = new CartItem();
                    cartItem.setId(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_ID)));
                    cartItem.setCart_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_CART_ID)));
                    cartItem.setProduct_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_PRODUCT_ID)));
                    cartItem.setProduct_size_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_PRODUCT_SIZE_ID)));
                    cartItem.setQuantity(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_QUANTITY)));
                    cartItems.add(cartItem);
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return cartItems!= null ? cartItems : new ArrayList<>();
    }

    public long addCartItem(CartItem cartItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_CARTITEM_CART_ID, cartItem.getCart_id());
            values.put(dbHelper.COLUMN_CARTITEM_PRODUCT_ID, cartItem.getProduct_id());
            values.put(dbHelper.COLUMN_CARTITEM_PRODUCT_SIZE_ID, cartItem.getProduct_size_id());
            values.put(dbHelper.COLUMN_CARTITEM_QUANTITY, cartItem.getQuantity());
            result = db.insert(dbHelper.TABLE_CARTITEM, null, values);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            db.close();
        }
        return result;
    }

    public long updateCartItem(CartItem cartItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_CARTITEM_CART_ID, cartItem.getCart_id());
            values.put(dbHelper.COLUMN_CARTITEM_PRODUCT_ID, cartItem.getProduct_id());
            values.put(dbHelper.COLUMN_CARTITEM_PRODUCT_SIZE_ID, cartItem.getProduct_size_id());
            values.put(dbHelper.COLUMN_CARTITEM_QUANTITY, cartItem.getQuantity());
            result = db.update(dbHelper.TABLE_CARTITEM, values, dbHelper.COLUMN_CARTITEM_ID + " = ?", new String[]{String.valueOf(cartItem.getId())});
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            db.close();
        }
        return result;
    }

    public long deleteCartItem(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            result = db.delete(dbHelper.TABLE_CARTITEM, dbHelper.COLUMN_CARTITEM_ID + " = ?", new String[]{String.valueOf(id)});
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            db.close();
        }
        return result;
    }

    @SuppressLint("Range")
    public List<CartItem> getAllCartItemByCartId(int cartId) {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try{
            String query = "SELECT * FROM " + dbHelper.TABLE_CARTITEM + " WHERE " + dbHelper.COLUMN_CARTITEM_CART_ID + " = " + cartId;
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    CartItem cartItem = new CartItem();
                    cartItem.setId(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_ID)));
                    cartItem.setCart_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_CART_ID)));
                    cartItem.setProduct_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_PRODUCT_ID)));
                    cartItem.setProduct_size_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_PRODUCT_SIZE_ID)));
                    cartItem.setQuantity(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_CARTITEM_QUANTITY)));
                    cartItems.add(cartItem);
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return cartItems != null ? cartItems : new ArrayList<>();

    }

    public void deleteAllCartItemsByCartId(int cartId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete(dbHelper.TABLE_CARTITEM, dbHelper.COLUMN_CARTITEM_CART_ID + " = ?", new String[]{String.valueOf(cartId)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
    }
}
