package com.hfing.shoesstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.User;

import java.util.ArrayList;

public class UsersDAO {

    private final DBHelper dbHelper;

    public UsersDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<User>  getAllUsers() {
        // Get all users
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            String query = "SELECT * FROM user";
            Cursor cursor = db.rawQuery(query, null);
             cursor.moveToFirst();
             while (!cursor.isAfterLast()) {
                 User user = new User();
                 user.setId(cursor.getInt(0));
                 user.setUsername(cursor.getString(1));
                 user.setPassword(cursor.getString(2));
                 user.setName(cursor.getString(3));
                 user.setEmail(cursor.getString(4));
                 user.setAddress(cursor.getString(5));
                 user.setPhone(cursor.getString(6));
                 user.setGender(cursor.getInt(7));
                 user.setRole_id(cursor.getInt(8));
                 users.add(user);
                 cursor.moveToNext();
             }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    public long addUser(User user) {
        // Add a user
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USER_PASSWORD,user.getPassword());
        values.put(DBHelper.COLUMN_USER_USERNAME,user.getUsername());
        values.put(DBHelper.COLUMN_USER_NAME,user.getName());
        values.put(DBHelper.COLUMN_USER_EMAIL,user.getEmail());
        values.put(DBHelper.COLUMN_USER_PHONE,user.getPhone());
        values.put(DBHelper.COLUMN_USER_ADDRESS,user.getAddress());
        values.put(DBHelper.COLUMN_USER_GENDER,user.getGender());
        values.put(DBHelper.COLUMN_USER_ROLE_ID,user.getRole_id());
        return db.insert(dbHelper.TABLE_USER, null, values);
    }

    public long updateUser(User user) {
        // Update a user
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USER_PASSWORD,user.getPassword());
        values.put(DBHelper.COLUMN_USER_USERNAME,user.getUsername());
        values.put(DBHelper.COLUMN_USER_NAME,user.getName());
        values.put(DBHelper.COLUMN_USER_EMAIL,user.getEmail());
        values.put(DBHelper.COLUMN_USER_PHONE,user.getPhone());
        values.put(DBHelper.COLUMN_USER_ADDRESS,user.getAddress());
        values.put(DBHelper.COLUMN_USER_GENDER,user.getGender());
        values.put(DBHelper.COLUMN_USER_ROLE_ID,user.getRole_id());
        return db.update(dbHelper.TABLE_USER, values, dbHelper.COLUMN_USER_ID + " = " + user.getId(), null);
    }

    public long deleteUser(int id) {
        // Delete a user
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(dbHelper.TABLE_USER, dbHelper.COLUMN_USER_ID+ " = " + id, null);
    }
}
