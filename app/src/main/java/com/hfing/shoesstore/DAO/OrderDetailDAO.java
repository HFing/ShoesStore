package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    public OrderDetailDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        productDAO = new ProductDAO(context);
        orderDAO = new OrderDAO(context);
    }

    public long insertOrderDetail(OrderDetail orderDetail) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ORDERDETAIL_ORDER_ID, orderDetail.getOrder_id());
        values.put(DBHelper.COLUMN_ORDERDETAIL_PRODUCT_ID, orderDetail.getProduct_id());
        values.put(DBHelper.COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID, orderDetail.getProduct_size_id());
        values.put(DBHelper.COLUMN_ORDERDETAIL_QUANTITY, orderDetail.getQuantity());
        values.put(DBHelper.COLUMN_ORDERDETAIL_UNIT_PRICE, orderDetail.getUnit_price());
        return database.insert("OrderDetail", null, values);
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

    @SuppressLint("Range")
    public List<Orders> getOrdersByUserId(int userId) {
        List<Orders> ordersList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_ORDERS + " WHERE " + DBHelper.COLUMN_ORDERS_USER_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

            if (cursor.moveToFirst()) {
                do {
                    Orders order = new Orders();
                    order.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ORDERS_ID)));
                    order.setUser_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ORDERS_USER_ID)));
                    order.setOrder_date(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ORDERS_DATE)));
                    ordersList.add(order);
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

        return ordersList;
    }

    @SuppressLint("Range")
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_ORDERDETAIL + " WHERE " + DBHelper.COLUMN_ORDERDETAIL_ORDER_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});

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


    @SuppressLint("Range")
    public List<OrderDetail> getOrderDetailsByUserAndCategory(User user, Category category) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_ORDERDETAIL + " WHERE " + DBHelper.COLUMN_ORDERDETAIL_ORDER_ID + " IN (SELECT " + DBHelper.COLUMN_ORDERS_ID + " FROM " + DBHelper.TABLE_ORDERS + " WHERE " + DBHelper.COLUMN_ORDERS_USER_ID + " = ?) AND " + DBHelper.COLUMN_ORDERDETAIL_PRODUCT_ID + " IN (SELECT " + DBHelper.COLUMN_PRODUCT_ID + " FROM " + DBHelper.TABLE_PRODUCT + " WHERE " + DBHelper.COLUMN_PRODUCT_CATEGORY_ID + " = ?)";
            cursor = db.rawQuery(query, new String[]{String.valueOf(user.getId()), String.valueOf(category.getId())});

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

    @SuppressLint("Range")
    public List<OrderDetail> getOrderDetailsByUserCategoryAndDate(User user, Category category, String startDate, String endDate) {
        List<OrderDetail> orderDetails = getOrderDetailsByUserAndCategory(user, category);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<OrderDetail> result = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            Orders order = orderDAO.getOrderById(orderDetail.getOrder_id());
            LocalDate orderDate = LocalDate.parse(order.getOrder_date());
            if ((orderDate.isEqual(start) || orderDate.isAfter(start)) && (orderDate.isEqual(end) || orderDate.isBefore(end))) {
                result.add(orderDetail);
            }
        }
        return result;
    }

}