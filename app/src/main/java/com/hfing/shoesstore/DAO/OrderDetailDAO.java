package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.OrderDetail;

import java.util.ArrayList;

public class OrderDetailDAO {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public OrderDetailDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insertOrderDetail(OrderDetail orderDetail) {
        ContentValues values = new ContentValues();
        values.put("order_id", orderDetail.getOrder_id());
        values.put("product_size_id", orderDetail.getProduct_size_id());
        values.put("quantity", orderDetail.getQuantity());
        values.put("unit_price", orderDetail.getUnit_price());
        return database.insert("OrderDetail", null, values);
    }

    public int updateOrderDetail(OrderDetail orderDetail) {
        ContentValues values = new ContentValues();
        values.put("product_size_id", orderDetail.getProduct_size_id());
        values.put("quantity", orderDetail.getQuantity());
        values.put("unit_price", orderDetail.getUnit_price());
        return database.update("OrderDetail", values, "id = ?", new String[]{String.valueOf(orderDetail.getId())});
    }

    public int deleteOrderDetail(int id) {
        return database.delete("OrderDetail", "id = ?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public OrderDetail getOrderDetail(int id) {
        Cursor cursor = database.query("OrderDetail", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(cursor.getInt(cursor.getColumnIndex("id")));
            orderDetail.setOrder_id(cursor.getInt(cursor.getColumnIndex("order_id")));
            orderDetail.setProduct_size_id(cursor.getInt(cursor.getColumnIndex("product_size_id")));
            orderDetail.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
            orderDetail.setUnit_price(cursor.getDouble(cursor.getColumnIndex("unit_price")));
            cursor.close();
            return orderDetail;
        }
        return null;
    }

    @SuppressLint("Range")
    public ArrayList<OrderDetail> getAllOrderDetails() {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        Cursor cursor = database.query("OrderDetail", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(cursor.getInt(cursor.getColumnIndex("id")));
                orderDetail.setOrder_id(cursor.getInt(cursor.getColumnIndex("order_id")));
                orderDetail.setProduct_size_id(cursor.getInt(cursor.getColumnIndex("product_size_id")));
                orderDetail.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                orderDetail.setUnit_price(cursor.getDouble(cursor.getColumnIndex("unit_price")));
                orderDetails.add(orderDetail);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return orderDetails;
    }
}