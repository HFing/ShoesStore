package com.hfing.shoesstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.Category;
import com.hfing.shoesstore.Model.DBHelper;

import java.util.ArrayList;

public class CategoryDAO {
    private  final DBHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            String query = "SELECT * FROM category";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                categories.add(category);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
     public long addCategory(Category category) {
       SQLiteDatabase db = dbHelper.getWritableDatabase();
//        try {
//            String query = "INSERT INTO category(name) VALUES(?)";
//            db.execSQL(query, new String[]{category.getName()});
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
         ContentValues values = new ContentValues();
         values.put("name", category.getName());
         return db.insert(dbHelper.TABLE_CATEGORY, null,values);
    }
    public long updateCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        return db.update(dbHelper.TABLE_CATEGORY, values, dbHelper.COLUMN_CATEGORY_ID + " = " + category.getId(), null);
    }
    public long deleteCategory(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(dbHelper.TABLE_CATEGORY, dbHelper.COLUMN_CATEGORY_ID + " = " + id, null);
    }
    public Category getCategoryById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Category category = new Category();
        try {
            String query = "SELECT * FROM category WHERE id = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            category.setId(cursor.getInt(0));
            category.setName(cursor.getString(1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return category;
    }
}
