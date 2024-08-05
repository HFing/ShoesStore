package com.hfing.shoesstore.Activity.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "shoesstore.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_ROLE = "role";
    private static final String TABLE_USER_ROLE = "user_role";

    // User Table Columns
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    // Role Table Columns
    private static final String COLUMN_ROLE_ID = "id";
    private static final String COLUMN_ROLE_NAME = "role_name";

    // UserRole Table Columns
    private static final String COLUMN_USER_ROLE_ID = "id";
    private static final String COLUMN_USER_ROLE_USER_ID = "user_id";
    private static final String COLUMN_USER_ROLE_ROLE_ID = "role_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_USERNAME + " TEXT NOT NULL, "
                + COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                + COLUMN_USER_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_ROLE_TABLE = "CREATE TABLE " + TABLE_ROLE + " ("
                + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROLE_NAME + " TEXT NOT NULL)";
        db.execSQL(CREATE_ROLE_TABLE);

        String CREATE_USER_ROLE_TABLE = "CREATE TABLE " + TABLE_USER_ROLE + " ("
                + COLUMN_USER_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_ROLE_USER_ID + " INTEGER, "
                + COLUMN_USER_ROLE_ROLE_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_USER_ROLE_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), "
                + "FOREIGN KEY (" + COLUMN_USER_ROLE_ROLE_ID + ") REFERENCES " + TABLE_ROLE + "(" + COLUMN_ROLE_ID + "))";
        db.execSQL(CREATE_USER_ROLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    //CRUD
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addRole(Role role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROLE_NAME, role.getRoleName());
        db.insert(TABLE_ROLE, null, values);
        db.close();
    }

    public void addUserRole(int userId, int roleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ROLE_USER_ID, userId);
        values.put(COLUMN_USER_ROLE_ROLE_ID, roleId);
        db.insert(TABLE_USER_ROLE, null, values);
        db.close();
    }
}
