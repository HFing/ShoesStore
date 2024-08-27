package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public OrderDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Orders> getAllOrders() {
        ArrayList<Orders> orders = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM Orders", null);
            if (cursor.moveToFirst()) {
                do {
                    Orders order = new Orders();
                    order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    order.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                    order.setOrder_date(cursor.getString(cursor.getColumnIndex("order_date")));
                    orders.add(order);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return orders;
    }

    public void insertOrder(Orders order) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", order.getUser_id());
        values.put("order_date", order.getOrder_date());
        db.insert("Orders", null, values);
    }

    @SuppressLint("Range")
    public Orders getOrderByUserIdAndDate(int userId, String date) {
        Cursor cursor = database.query("Orders", null, "user_id = ? AND order_date = ?", new String[]{String.valueOf(userId), date}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Orders order = new Orders();
            order.setId(cursor.getInt(cursor.getColumnIndex("id")));
            order.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
            order.setOrder_date(cursor.getString(cursor.getColumnIndex("order_date")));
            cursor.close();
            return order;
        }
        return null;
    }


    @SuppressLint("Range")
    public Orders getOrderById(int id) {
        Cursor cursor = database.query("Orders", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Orders order = new Orders();
            order.setId(cursor.getInt(cursor.getColumnIndex("id")));
            order.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
            order.setOrder_date(cursor.getString(cursor.getColumnIndex("order_date")));
            cursor.close();
            return order;
        }
        return null;
    }

    @SuppressLint("Range")
    public ArrayList<OrderDetail> getOrderDetailsByDateRangeAndUser(int userId, String startDate, String endDate) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT Orders.id AS order_id, OrderDetail.product_id, OrderDetail.product_size_id, OrderDetail.quantity, OrderDetail.unit_price " +
                    "FROM Orders " +
                    "JOIN OrderDetail ON Orders.id = OrderDetail.order_id " +
                    "JOIN ProductSize ON OrderDetail.product_size_id = ProductSize.id " +
                    "JOIN Product ON ProductSize.product_id = Product.id " +
                    "JOIN Category ON Product.category_id = Category.id " +
                    "WHERE Orders.user_id = ? AND Orders.order_date BETWEEN ? AND ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(userId), startDate, endDate});
            if (cursor.moveToFirst()) {
                do {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder_id(cursor.getInt(cursor.getColumnIndex("order_id")));
                    orderDetail.setProduct_id(cursor.getInt(cursor.getColumnIndex("product_id")));
                    orderDetail.setProduct_size_id(cursor.getInt(cursor.getColumnIndex("product_size_id")));
                    orderDetail.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                    orderDetail.setUnit_price(cursor.getDouble(cursor.getColumnIndex("unit_price")));
                    orderDetails.add(orderDetail);
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

}
