//package com.hfing.shoesstore.DAO;
//
//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.hfing.shoesstore.Model.DBHelper;
//import com.hfing.shoesstore.Model.OrderDetail;
//
//import java.util.ArrayList;
//
//public class OrderDetailDAO {
//    private SQLiteOpenHelper dbHelper;
//    private SQLiteDatabase database;
//
//    public OrderDetailDAO(Context context) {
//        dbHelper = new DBHelper(context);
//        database = dbHelper.getWritableDatabase();
//    }
//
//    public long insertOrderDetail(OrderDetail orderDetail) {
//        ContentValues values = new ContentValues();
//        values.put("order_id", orderDetail.getOrder_id());
//        values.put("product_size_id", orderDetail.getProduct_size_id());
//        values.put("quantity", orderDetail.getQuantity());
//        values.put("unit_price", orderDetail.getUnit_price());
//        return database.insert("OrderDetail", null, values);
//    }
//
//    public int updateOrderDetail(OrderDetail orderDetail) {
//        ContentValues values = new ContentValues();
//        values.put("product_size_id", orderDetail.getProduct_size_id());
//        values.put("quantity", orderDetail.getQuantity());
//        values.put("unit_price", orderDetail.getUnit_price());
//        return database.update("OrderDetail", values, "id = ?", new String[]{String.valueOf(orderDetail.getId())});
//    }
//
//    public int deleteOrderDetail(int id) {
//        return database.delete("OrderDetail", "id = ?", new String[]{String.valueOf(id)});
//    }
//
//    @SuppressLint("Range")
//    public OrderDetail getOrderDetail(int id) {
//        Cursor cursor = database.query("OrderDetail", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            orderDetail.setOrder_id(cursor.getInt(cursor.getColumnIndex("order_id")));
//            orderDetail.setProduct_size_id(cursor.getInt(cursor.getColumnIndex("product_size_id")));
//            orderDetail.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
//            orderDetail.setUnit_price(cursor.getDouble(cursor.getColumnIndex("unit_price")));
//            cursor.close();
//            return orderDetail;
//        }
//        return null;
//    }
//
//    @SuppressLint("Range")
//    public ArrayList<OrderDetail> getAllOrderDetails() {
//        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
//        Cursor cursor = database.query("OrderDetail", null, null, null, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                OrderDetail orderDetail = new OrderDetail();
//                orderDetail.setId(cursor.getInt(cursor.getColumnIndex("id")));
//                orderDetail.setOrder_id(cursor.getInt(cursor.getColumnIndex("order_id")));
//                orderDetail.setProduct_size_id(cursor.getInt(cursor.getColumnIndex("product_size_id")));
//                orderDetail.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
//                orderDetail.setUnit_price(cursor.getDouble(cursor.getColumnIndex("unit_price")));
//                orderDetails.add(orderDetail);
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        return orderDetails;
//    }
//}

package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    private ProductDAO productDAO;

    public OrderDetailDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        productDAO = new ProductDAO(context);
    }

    @SuppressLint("Range")
    public ArrayList<OrderDetail> getOrderDetailsByUserAndCategory(int userId, String category) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT Orders.id AS order_id, OrderDetail.product_id, OrderDetail.product_size_id, OrderDetail.quantity, OrderDetail.unit_price " +
                    "FROM Orders " +
                    "JOIN OrderDetail ON Orders.id = OrderDetail.order_id " +
                    "JOIN ProductSize ON OrderDetail.product_size_id = ProductSize.id " +
                    "JOIN Product ON ProductSize.product_id = Product.id " +
                    "JOIN Category ON Product.category_id = Category.id " +
                    "WHERE Orders.user_id = ? AND Category.name = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(userId), category});
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


    @SuppressLint("Range")
    public List<OrderDetail> getOrderDetailsByUserId(int userId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_ORDERDETAIL + " WHERE " + DBHelper.COLUMN_ORDERDETAIL_ORDER_ID + " IN (SELECT " + DBHelper.COLUMN_ORDERS_ID + " FROM " + DBHelper.TABLE_ORDERS + " WHERE " + DBHelper.COLUMN_ORDERS_USER_ID + " = ?)";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

            if (cursor.moveToFirst()) {
                do {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ORDERDETAIL_ID)));
                    orderDetail.setOrder_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ORDERDETAIL_ORDER_ID)));
                    orderDetail.setProduct_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ORDERDETAIL_PRODUCT_ID)));
                    orderDetail.setProduct_size_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID)));
                    orderDetail.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ORDERDETAIL_QUANTITY)));
                    orderDetail.setUnit_price(cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_ORDERDETAIL_UNIT_PRICE)));
                    orderDetails.add(orderDetail);
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

        return orderDetails;
    }



}