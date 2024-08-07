package com.hfing.shoesstore.DataResource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    //Ten Database
    private static final String DATABASE_NAME = "shoesstore.db";
    private static final int DATABASE_VERSION = 1;

    //Bang Role
    public static final String TABLE_ROLE = "Role";
    public static final String COLUMN_ROLE_ID = "id";
    public static final String COLUMN_ROLE_NAME = "name";

    private static final String CREATE_TABLE_ROLE = ""
            + "CREATE TABLE " + TABLE_ROLE + "("
            + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ROLE_NAME + " TEXT"
            + ")";


    //Bang User
    public static final String TABLE_USER = "User";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_ADDRESS = "address";
    public static final String COLUMN_USER_PHONE = "phone";
    public static final String COLUMN_USER_GENDER = "gender";
    public static final String COLUMN_USER_ROLE_ID = "role_id";

    private static final String CREATE_TABLE_USER = ""
            + "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_USERNAME + " TEXT NOT NULL UNIQUE,"
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_ADDRESS + " TEXT,"
            + COLUMN_USER_PHONE + " TEXT,"
            + COLUMN_USER_GENDER + " INTEGER CHECK (" + COLUMN_USER_GENDER + " IN (0, 1)),"
            + COLUMN_USER_ROLE_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_USER_ROLE_ID + ") REFERENCES "
            + TABLE_ROLE + "(" + COLUMN_ROLE_ID + ")"
            + ")";


    //Bang Category
    public static final String TABLE_CATEGORY = "Category";
    public static final String COLUMN_CATEGORY_ID = "id";
    public static final String COLUMN_CATEGORY_NAME = "name";

    private static final String CREATE_TABLE_CATEGORY = ""
            + "CREATE TABLE " + TABLE_CATEGORY + "("
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CATEGORY_NAME + " TEXT"
            + ")";

    //Bảng Product
    public static final String TABLE_PRODUCT = "Product";
    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_IMAGE = "image";
    public static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";

    private static final String CREATE_TABLE_PRODUCT = ""
            + "CREATE TABLE " + TABLE_PRODUCT + "("
            + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PRODUCT_NAME + " TEXT NOT NULL,"
            + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
            + COLUMN_PRODUCT_PRICE + " REAL NOT NULL,"
            + COLUMN_PRODUCT_IMAGE + " BLOB,"
            + COLUMN_PRODUCT_CATEGORY_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_PRODUCT_CATEGORY_ID + ") REFERENCES "
            + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + ")"
            + ")";

    //Bảng ProductSize
    public static final String TABLE_PRODUCTSIZE = "ProductSize";
    public static final String COLUMN_PRODUCTSIZE_ID = "id";
    public static final String COLUMN_PRODUCTSIZE_SIZE = "size";
    public static final String COLUMN_PRODUCTSIZE_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCTSIZE_QUANTITY = "quantity";

    private static final String CREATE_TABLE_PRODUCTSIZE = ""
            + "CREATE TABLE " + TABLE_PRODUCTSIZE + "("
            + COLUMN_PRODUCTSIZE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PRODUCTSIZE_SIZE + " INTEGER NOT NULL,"
            + COLUMN_PRODUCTSIZE_PRODUCT_ID + " INTEGER NOT NULL,"
            + COLUMN_PRODUCTSIZE_QUANTITY + " INTEGER NOT NULL,"
            + "FOREIGN KEY(" + COLUMN_PRODUCTSIZE_PRODUCT_ID + ") REFERENCES "
            + TABLE_PRODUCT + "(" + COLUMN_PRODUCT_ID + ")"
            + ")";

    //Bảng Cart
    public static final String TABLE_CART = "Cart";
    public static final String COLUMN_CART_ID = "id";
    public static final String COLUMN_CART_USER_ID = "user_id";

    private static final String CREATE_TABLE_CART = ""
            + "CREATE TABLE " + TABLE_CART + "("
            + COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CART_USER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY(" + COLUMN_CART_USER_ID + ") REFERENCES "
            + TABLE_USER + "(" + COLUMN_USER_ID + ")"
            + ")";

    //Bảng CartItem
    public static final String TABLE_CARTITEM = "CartItem";
    public static final String COLUMN_CARTITEM_ID = "id";
    public static final String COLUMN_CARTITEM_CART_ID = "cart_id";
    public static final String COLUMN_CARTITEM_PRODUCT_SIZE_ID = "product_size_id";
    public static final String COLUMN_CARTITEM_QUANTITY = "quantity";

    private static final String CREATE_TABLE_CARTITEM = ""
            + "CREATE TABLE " + TABLE_CARTITEM + "("
            + COLUMN_CARTITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CARTITEM_CART_ID + " INTEGER NOT NULL,"
            + COLUMN_CARTITEM_PRODUCT_SIZE_ID + " INTEGER NOT NULL,"
            + COLUMN_CARTITEM_QUANTITY + " INTEGER NOT NULL,"
            + "FOREIGN KEY(" + COLUMN_CARTITEM_CART_ID + ") REFERENCES "
            + TABLE_CART + "(" + COLUMN_CART_ID + "),"
            + "FOREIGN KEY(" + COLUMN_CARTITEM_PRODUCT_SIZE_ID + ") REFERENCES "
            + TABLE_PRODUCTSIZE + "(" + COLUMN_PRODUCTSIZE_ID + ")"
            + ")";

    //Bảng Orders
    public static final String TABLE_ORDERS = "Orders";
    public static final String COLUMN_ORDERS_ID = "id";
    public static final String COLUMN_ORDERS_USER_ID = "user_id";
    public static final String COLUMN_ORDERS_DATE = "order_date";

    private static final String CREATE_TABLE_ORDERS = ""
            + "CREATE TABLE " + TABLE_ORDERS + "("
            + COLUMN_ORDERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ORDERS_USER_ID + " INTEGER NOT NULL,"
            + COLUMN_ORDERS_DATE + " TEXT NOT NULL,"
            + "FOREIGN KEY(" + COLUMN_ORDERS_USER_ID + ") REFERENCES "
            + TABLE_USER + "(" + COLUMN_USER_ID + ")"
            + ")";

    //Bảng OrderDetail
    public static final String TABLE_ORDERDETAIL = "OrderDetail";
    public static final String COLUMN_ORDERDETAIL_ID = "id";
    public static final String COLUMN_ORDERDETAIL_ORDER_ID = "order_id";
    public static final String COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID = "product_size_id";
    public static final String COLUMN_ORDERDETAIL_QUANTITY = "quantity";
    public static final String COLUMN_ORDERDETAIL_UNIT_PRICE = "unit_price";

    private static final String CREATE_TABLE_ORDERDETAIL = ""
            + "CREATE TABLE " + TABLE_ORDERDETAIL + "("
            + COLUMN_ORDERDETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ORDERDETAIL_ORDER_ID + " INTEGER NOT NULL,"
            + COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID + " INTEGER NOT NULL,"
            + COLUMN_ORDERDETAIL_QUANTITY + " INTEGER NOT NULL,"
            + COLUMN_ORDERDETAIL_UNIT_PRICE + " REAL NOT NULL,"
            + "FOREIGN KEY(" + COLUMN_ORDERDETAIL_ORDER_ID + ") REFERENCES "
            + TABLE_ORDERS + "(" + COLUMN_ORDERS_ID + "),"
            + "FOREIGN KEY(" + COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID + ") REFERENCES "
            + TABLE_PRODUCTSIZE + "(" + COLUMN_PRODUCTSIZE_ID + ")"
            + ")";

    //Bảng Review
    public static final String TABLE_REVIEW = "Review";
    public static final String COLUMN_REVIEW_ID = "id";
    public static final String COLUMN_REVIEW_USER_ID = "user_id";
    public static final String COLUMN_REVIEW_PRODUCT_ID = "product_id";
    public static final String COLUMN_REVIEW_RATING = "rating";
    public static final String COLUMN_REVIEW_COMMENT = "comment";
    public static final String COLUMN_REVIEW_DATE = "date";

    private static final String CREATE_TABLE_REVIEW = ""
            + "CREATE TABLE " + TABLE_REVIEW + "("
            + COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_REVIEW_USER_ID + " INTEGER NOT NULL,"
            + COLUMN_REVIEW_PRODUCT_ID + " INTEGER NOT NULL,"
            + COLUMN_REVIEW_RATING + " INTEGER CHECK (" + COLUMN_REVIEW_RATING + " IN (1, 2, 3, 4, 5)),"
            + COLUMN_REVIEW_COMMENT + " TEXT,"
            + COLUMN_REVIEW_DATE + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_REVIEW_USER_ID + ") REFERENCES "
            + TABLE_USER + "(" + COLUMN_USER_ID + "),"
            + "FOREIGN KEY(" + COLUMN_REVIEW_PRODUCT_ID + ") REFERENCES "
            + TABLE_PRODUCT + "(" + COLUMN_PRODUCT_ID + ")"
            + ")";



    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ROLE);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_PRODUCTSIZE);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_CARTITEM);
        db.execSQL(CREATE_TABLE_ORDERS);
        db.execSQL(CREATE_TABLE_ORDERDETAIL);
        db.execSQL(CREATE_TABLE_REVIEW);
        addTempData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTSIZE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARTITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERDETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Downgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTSIZE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARTITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERDETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        onCreate(db);
    }

    private void addTempData(SQLiteDatabase db) {
        // Insert dữ liệu vào bảng Role
        db.execSQL("INSERT INTO " + TABLE_ROLE + "(" + COLUMN_ROLE_NAME + ") " + "VALUES ('admin');");
        db.execSQL("INSERT INTO " + TABLE_ROLE + "(" + COLUMN_ROLE_NAME + ") " + "VALUES ('user');");
        // Insert dữ liệu vào bảng User
        db.execSQL("INSERT INTO " + TABLE_USER + "(" + COLUMN_USER_USERNAME + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_EMAIL + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_PHONE + ", " + COLUMN_USER_GENDER + ", " + COLUMN_USER_ROLE_ID + ") " +
                "VALUES ('admin1', 'admin123', 'John Smith', 'admin1@gmail.com', 'abc', '0982828272', 1, 1);");
        db.execSQL("INSERT INTO " + TABLE_USER + "(" + COLUMN_USER_USERNAME + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_EMAIL + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_PHONE + ", " + COLUMN_USER_GENDER + ", " + COLUMN_USER_ROLE_ID + ") " +
                "VALUES ('admin2', 'admin123', 'Emily Johnson', 'admin2@gmail.com', 'bac', '0982828273', 0, 1);");
        db.execSQL("INSERT INTO " + TABLE_USER + "(" + COLUMN_USER_USERNAME + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_EMAIL + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_PHONE + ", " + COLUMN_USER_GENDER + ", " + COLUMN_USER_ROLE_ID + ") " +
                "VALUES ('user1', 'user123', 'Michael Brown', 'user1@gmail.com', 'abc', '0982828274', 0, 2);");
        db.execSQL("INSERT INTO " + TABLE_USER + "(" + COLUMN_USER_USERNAME + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_EMAIL + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_PHONE + ", " + COLUMN_USER_GENDER + ", " + COLUMN_USER_ROLE_ID + ") " +
                "VALUES ('user2', 'user123', 'Sophia Davis', 'user2@gmail.com', 'abc', '0982828275', 1, 2);");
        // Insert dữ liệu vào bảng Category
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_NAME + ") " +
                "VALUES ('Running Shoes');");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_NAME + ") " +
                "VALUES ('Basketball Shoes');");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_NAME + ") " +
                "VALUES ('Casual Shoes');");
        // Insert dữ liệu vào bảng Product
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + "(" + COLUMN_PRODUCT_NAME+ ", " + COLUMN_PRODUCT_DESCRIPTION + ", " + COLUMN_PRODUCT_PRICE + ", " + COLUMN_PRODUCT_CATEGORY_ID + ") " +
                "VALUES ('Nike Air Zoom Pegasus', 'Running shoe', 120.0, 1);");
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + "(" + COLUMN_PRODUCT_NAME+ ", " + COLUMN_PRODUCT_DESCRIPTION + ", " + COLUMN_PRODUCT_PRICE + ", " + COLUMN_PRODUCT_CATEGORY_ID + ") " +
                "VALUES ('Adidas Ultraboost', 'Running shoe', 180.0, 1);");
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + "(" + COLUMN_PRODUCT_NAME+ ", " + COLUMN_PRODUCT_DESCRIPTION + ", " + COLUMN_PRODUCT_PRICE + ", " + COLUMN_PRODUCT_CATEGORY_ID + ") " +
                "VALUES ('Air Jordan 1', 'Basketball shoe', 150.0, 2);");
        // Insert dữ liệu vào bảng ProductSize
        db.execSQL("INSERT INTO " + TABLE_PRODUCTSIZE + "(" + COLUMN_PRODUCTSIZE_PRODUCT_ID+ ", " + COLUMN_PRODUCTSIZE_SIZE+ ", " + COLUMN_PRODUCTSIZE_QUANTITY + ") " +
                "VALUES (1, 39, 100);");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTSIZE + "(" + COLUMN_PRODUCTSIZE_PRODUCT_ID+ ", " + COLUMN_PRODUCTSIZE_SIZE+ ", " + COLUMN_PRODUCTSIZE_QUANTITY + ") " +
                "VALUES (1, 43, 50);");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTSIZE + "(" + COLUMN_PRODUCTSIZE_PRODUCT_ID+ ", " + COLUMN_PRODUCTSIZE_SIZE+ ", " + COLUMN_PRODUCTSIZE_QUANTITY + ") " +
                "VALUES (2, 42, 200);");
        db.execSQL("INSERT INTO " + TABLE_PRODUCTSIZE + "(" + COLUMN_PRODUCTSIZE_PRODUCT_ID+ ", " + COLUMN_PRODUCTSIZE_SIZE+ ", " + COLUMN_PRODUCTSIZE_QUANTITY + ") " +
                "VALUES (3, 43, 150);");
        // Insert dữ liệu vào bảng Cart
        db.execSQL("INSERT INTO Cart (user_id) VALUES (3);");
        db.execSQL("INSERT INTO Cart (user_id) VALUES (4);");
        // Insert dữ liệu vào bảng CartItem
        db.execSQL("INSERT INTO " + TABLE_CARTITEM + "(" + COLUMN_CARTITEM_CART_ID+ ", " + COLUMN_CARTITEM_PRODUCT_SIZE_ID+ ", " + COLUMN_CARTITEM_QUANTITY + ") " +
                "VALUES (1, 1, 1);");
        db.execSQL("INSERT INTO " + TABLE_CARTITEM + "(" + COLUMN_CARTITEM_CART_ID+ ", " + COLUMN_CARTITEM_PRODUCT_SIZE_ID+ ", " + COLUMN_CARTITEM_QUANTITY + ") " +
                "VALUES (2, 3, 2);");
        // Insert dữ liệu vào bảng Orders
        db.execSQL("INSERT INTO " + TABLE_ORDERS + "(" + COLUMN_ORDERS_USER_ID+ ", " + COLUMN_ORDERS_DATE + ") " +
                "VALUES (2, '2024-08-01');");
        db.execSQL("INSERT INTO " + TABLE_ORDERS + "(" + COLUMN_ORDERS_USER_ID+ ", " + COLUMN_ORDERS_DATE + ") " +"" +
                "VALUES (3, '2024-08-02');");
        // Insert dữ liệu vào bảng OrderDetail
        db.execSQL("INSERT INTO " + TABLE_ORDERDETAIL + "(" + COLUMN_ORDERDETAIL_ORDER_ID + ", " + COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID + ", " + COLUMN_ORDERDETAIL_QUANTITY + ", " + COLUMN_ORDERDETAIL_UNIT_PRICE + ") " + 
                "VALUES (1, 1, 1, 120.0);");
        db.execSQL("INSERT INTO " + TABLE_ORDERDETAIL + "(" + COLUMN_ORDERDETAIL_ORDER_ID + ", " + COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID + ", " + COLUMN_ORDERDETAIL_QUANTITY + ", " + COLUMN_ORDERDETAIL_UNIT_PRICE + ") " +
                "VALUES (2, 2, 2, 180.0);");
        // Insert dữ liệu vào bảng Review
        db.execSQL("INSERT INTO " + TABLE_REVIEW + "(" + COLUMN_REVIEW_USER_ID + ", " + COLUMN_REVIEW_PRODUCT_ID + ", " + COLUMN_REVIEW_RATING + ", " + COLUMN_REVIEW_COMMENT + ", " + COLUMN_REVIEW_DATE + ") " + 
                "VALUES (2, 1, 5, 'Great shoe!', '2024-08-05');");
        db.execSQL("INSERT INTO " + TABLE_REVIEW + "(" + COLUMN_REVIEW_USER_ID + ", " + COLUMN_REVIEW_PRODUCT_ID + ", " + COLUMN_REVIEW_RATING + ", " + COLUMN_REVIEW_COMMENT + ", " + COLUMN_REVIEW_DATE + ") " + 
                "VALUES (3, 2, 4, 'Comfortable but pricey.', '2024-08-06');");
    }
}
