package com.hfing.shoesstore.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.Role;

import java.util.ArrayList;

public class RoleDAO {

    private final DBHelper dbHelper;

    public RoleDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<Role> getAllRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            String query = "SELECT * FROM role";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Role role = new Role();
                role.setId(cursor.getInt(0));
                role.setName(cursor.getString(1));
                roles.add(role);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return roles;
    }

    public Role getRoleNameById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            String query = "SELECT id, name FROM role WHERE id = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                Role role = new Role();
                role.setId(cursor.getInt(0));
                role.setName(cursor.getString(1));
                cursor.close();
                return role;
            } else {
                cursor.close();
                return null; // or return a default Role object
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}