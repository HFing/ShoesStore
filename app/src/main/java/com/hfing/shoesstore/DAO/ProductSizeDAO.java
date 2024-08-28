package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.ProductSize;

import java.util.ArrayList;
import java.util.List;

public class ProductSizeDAO {
    private final DBHelper dbHelper;

    public ProductSizeDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    @SuppressLint("Range")
    public List<ProductSize> getAllProductSizes() {
        ArrayList<ProductSize> productSizes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM product_size";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ProductSize productSize = new ProductSize();
                    productSize.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_ID)));
                    productSize.setSize(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_SIZE))));
                    productSize.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_QUANTITY)));
                    productSize.setProduct_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_PRODUCT_ID)));
                    productSizes.add(productSize);
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
        return productSizes != null ? productSizes : new ArrayList<>();
    }

    public long addProductSize(ProductSize productSize) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_PRODUCTSIZE_SIZE, productSize.getSize());
            values.put(DBHelper.COLUMN_PRODUCTSIZE_QUANTITY, productSize.getQuantity());
            values.put(DBHelper.COLUMN_PRODUCTSIZE_PRODUCT_ID, productSize.getProduct_id());
            result = db.insert(DBHelper.TABLE_PRODUCTSIZE, null, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return result;
    }

    public long updateProductSize(ProductSize productSize) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_PRODUCTSIZE_SIZE, productSize.getSize());
            values.put(DBHelper.COLUMN_PRODUCTSIZE_QUANTITY, productSize.getQuantity());
            values.put(DBHelper.COLUMN_PRODUCTSIZE_PRODUCT_ID, productSize.getProduct_id());
            result = db.update(DBHelper.TABLE_PRODUCTSIZE, values, DBHelper.COLUMN_PRODUCTSIZE_ID + " = ?", new String[]{String.valueOf(productSize.getId())});
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return result;
    }

    public long deleteProductSize(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            result = db.delete(DBHelper.TABLE_PRODUCTSIZE, DBHelper.COLUMN_PRODUCTSIZE_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return result;
    }

    @SuppressLint("Range")
    public ProductSize getProductSizeById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        ProductSize productSize = null;
        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_PRODUCTSIZE + " WHERE " + DBHelper.COLUMN_PRODUCTSIZE_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                productSize = new ProductSize();
                productSize.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_ID)));
                productSize.setSize(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_SIZE))));
                productSize.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_QUANTITY)));
                productSize.setProduct_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_PRODUCT_ID)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return productSize!= null ? productSize : new ProductSize();
    }

    @SuppressLint("Range")
    public List<ProductSize> getProductSizesByProduct(Product product) {
        ArrayList<ProductSize> productSizes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_PRODUCTSIZE + " WHERE " + DBHelper.COLUMN_PRODUCTSIZE_PRODUCT_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(product.getId())});
            if (cursor.moveToFirst()) {
                do {
                    ProductSize productSize = new ProductSize();
                    productSize.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_ID)));
                    productSize.setSize(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_SIZE))));
                    productSize.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_QUANTITY)));
                    productSize.setProduct_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_PRODUCT_ID)));
                    productSizes.add(productSize);
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
        return productSizes != null ? productSizes : new ArrayList<>();
    }


    @SuppressLint("Range")
    public List<ProductSize> getProductSizesByProductId(int productId) {
        List<ProductSize> productSizes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + DBHelper.TABLE_PRODUCTSIZE + " WHERE " + DBHelper.COLUMN_PRODUCTSIZE_PRODUCT_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});
            if (cursor.moveToFirst()) {
                do {
                    ProductSize productSize = new ProductSize();
                    productSize.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_ID)));
                    productSize.setSize(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_SIZE)));
                    productSize.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_QUANTITY)));
                    productSize.setProduct_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCTSIZE_PRODUCT_ID)));
                    productSizes.add(productSize);
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
        return productSizes != null ? productSizes : new ArrayList<>();
    }


}
