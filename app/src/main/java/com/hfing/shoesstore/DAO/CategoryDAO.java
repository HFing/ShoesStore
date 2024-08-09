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
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Category", null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }
     public long addCategory(Category category) {
       SQLiteDatabase db = dbHelper.getWritableDatabase();
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
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Category WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            Category category = new Category();
            category.setId(cursor.getInt(0));
            category.setName(cursor.getString(1));
            cursor.close();
            db.close();
            return category;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return null; // Trả về null nếu không tìm thấy danh mục
        }
    }
}
