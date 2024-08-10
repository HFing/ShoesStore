package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.Orders;

import java.util.ArrayList;

public class OrderDAO {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public OrderDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Orders> getOrderDetailsByUserAndCategory(int userId, String category) {
        ArrayList<Orders> orderDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT Orders.id, Orders.user_id, Product.name, OrderDetail.unit_price " +
                    "FROM Orders " +
                    "JOIN OrderDetail ON Orders.id = OrderDetail.order_id " +
                    "JOIN ProductSize ON OrderDetail.product_size_id = ProductSize.id " +
                    "JOIN Product ON ProductSize.product_id = Product.id " +
                    "JOIN Category ON Product.category_id = Category.id " +
                    "WHERE Orders.user_id = ? AND Category.name = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(userId), category});
            if (cursor.moveToFirst()) {
                do {
                    Orders order = new Orders();
                    order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    order.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                    order.setProductName(cursor.getString(cursor.getColumnIndex("name")));
                    order.setUnitPrice(cursor.getDouble(cursor.getColumnIndex("unit_price")));
                    orderDetails.add(order);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return orderDetails;
    }

    @SuppressLint("Range")
    public int getUserIdByName(String username) {
        int userId = -1;
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT id FROM User WHERE username = ?", new String[]{username});
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userId;
    }

    public ArrayList<String> getUsers() {
        ArrayList<String> users = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT username FROM User", null);
            if (cursor.moveToFirst()) {
                do {
                    users.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return users;
    }

    public ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT name FROM Category", null);
            if (cursor.moveToFirst()) {
                do {
                    categories.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return categories;
    }
}
