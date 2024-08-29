package com.hfing.shoesstore.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.hfing.shoesstore.R;

import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper {
        Context context;

        // Ten Database
        private static final String DATABASE_NAME = "shoesstore.db";
        private static final int DATABASE_VERSION = 1;

        // Bang Role
        public static final String TABLE_ROLE = "Role";
        public static final String COLUMN_ROLE_ID = "id";
        public static final String COLUMN_ROLE_NAME = "name";

        private static final String CREATE_TABLE_ROLE = ""
                        + "CREATE TABLE " + TABLE_ROLE + "("
                        + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_ROLE_NAME + " TEXT"
                        + ")";

        // Bang User
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
                        + COLUMN_USER_EMAIL + " TEXT NOT NULL UNIQUE,"
                        + COLUMN_USER_ADDRESS + " TEXT,"
                        + COLUMN_USER_PHONE + " TEXT,"
                        + COLUMN_USER_GENDER + " INTEGER CHECK (" + COLUMN_USER_GENDER + " IN (0, 1)),"
                        + COLUMN_USER_ROLE_ID + " INTEGER,"
                        + "FOREIGN KEY(" + COLUMN_USER_ROLE_ID + ") REFERENCES "
                        + TABLE_ROLE + "(" + COLUMN_ROLE_ID + ")"
                        + ")";

        // Bang Category
        public static final String TABLE_CATEGORY = "Category";
        public static final String COLUMN_CATEGORY_ID = "id";
        public static final String COLUMN_CATEGORY_NAME = "name";

        private static final String CREATE_TABLE_CATEGORY = ""
                        + "CREATE TABLE " + TABLE_CATEGORY + "("
                        + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_CATEGORY_NAME + " TEXT"
                        + ")";

        // Bảng Product
        public static final String TABLE_PRODUCT = "Product";
        public static final String COLUMN_PRODUCT_ID = "id";
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_DESCRIPTION = "description";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_IMAGE = "image";
        public static final String COLUMN_PRODUCT_CREATE_AT = "create_at";
        public static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";

        private static final String CREATE_TABLE_PRODUCT = ""
                        + "CREATE TABLE " + TABLE_PRODUCT + "("
                        + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_PRODUCT_NAME + " TEXT NOT NULL,"
                        + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
                        + COLUMN_PRODUCT_PRICE + " REAL NOT NULL,"
                        + COLUMN_PRODUCT_IMAGE + " BLOB,"
                        + COLUMN_PRODUCT_CREATE_AT + " TEXT,"
                        + COLUMN_PRODUCT_CATEGORY_ID + " INTEGER,"
                        + "FOREIGN KEY(" + COLUMN_PRODUCT_CATEGORY_ID + ") REFERENCES "
                        + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + ")"
                        + ")";

        // Bảng ProductSize
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

        // Bảng Cart
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

        // Bảng CartItem
        public static final String TABLE_CARTITEM = "CartItem";
        public static final String COLUMN_CARTITEM_ID = "id";
        public static final String COLUMN_CARTITEM_CART_ID = "cart_id";
        public static final String COLUMN_CARTITEM_PRODUCT_ID = "product_id";
        public static final String COLUMN_CARTITEM_PRODUCT_SIZE_ID = "product_size_id";
        public static final String COLUMN_CARTITEM_QUANTITY = "quantity";

        private static final String CREATE_TABLE_CARTITEM = ""
                        + "CREATE TABLE " + TABLE_CARTITEM + "("
                        + COLUMN_CARTITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_CARTITEM_CART_ID + " INTEGER NOT NULL,"
                        + COLUMN_CARTITEM_PRODUCT_ID + " INTEGER NOT NULL,"
                        + COLUMN_CARTITEM_PRODUCT_SIZE_ID + " INTEGER NOT NULL,"
                        + COLUMN_CARTITEM_QUANTITY + " INTEGER NOT NULL,"
                        + "FOREIGN KEY(" + COLUMN_CARTITEM_CART_ID + ") REFERENCES "
                        + TABLE_CART + "(" + COLUMN_CART_ID + "),"
                        + "FOREIGN KEY(" + COLUMN_CARTITEM_PRODUCT_ID + ") REFERENCES "
                        + TABLE_PRODUCT + "(" + COLUMN_PRODUCT_ID + "),"
                        + "FOREIGN KEY(" + COLUMN_CARTITEM_PRODUCT_SIZE_ID + ") REFERENCES "
                        + TABLE_PRODUCTSIZE + "(" + COLUMN_PRODUCTSIZE_ID + ")"
                        + ")";

        // Bảng Orders
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

        // Bảng OrderDetail
        public static final String TABLE_ORDERDETAIL = "OrderDetail";
        public static final String COLUMN_ORDERDETAIL_ID = "id";
        public static final String COLUMN_ORDERDETAIL_ORDER_ID = "order_id";
        public static final String COLUMN_ORDERDETAIL_PRODUCT_ID = "product_id";
        public static final String COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID = "product_size_id";
        public static final String COLUMN_ORDERDETAIL_QUANTITY = "quantity";
        public static final String COLUMN_ORDERDETAIL_UNIT_PRICE = "unit_price";

        private static final String CREATE_TABLE_ORDERDETAIL = ""
                        + "CREATE TABLE " + TABLE_ORDERDETAIL + "("
                        + COLUMN_ORDERDETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_ORDERDETAIL_ORDER_ID + " INTEGER NOT NULL,"
                        + COLUMN_ORDERDETAIL_PRODUCT_ID + " INTEGER NOT NULL,"
                        + COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID + " INTEGER NOT NULL,"
                        + COLUMN_ORDERDETAIL_QUANTITY + " INTEGER NOT NULL,"
                        + COLUMN_ORDERDETAIL_UNIT_PRICE + " REAL NOT NULL,"
                        + "FOREIGN KEY(" + COLUMN_ORDERDETAIL_ORDER_ID + ") REFERENCES "
                        + TABLE_ORDERS + "(" + COLUMN_ORDERS_ID + "),"
                        + "FOREIGN KEY(" + COLUMN_ORDERDETAIL_PRODUCT_ID + ") REFERENCES "
                        + TABLE_PRODUCT + "(" + COLUMN_PRODUCT_ID + "),"
                        + "FOREIGN KEY(" + COLUMN_ORDERDETAIL_PRODUCT_SIZE_ID + ") REFERENCES "
                        + TABLE_PRODUCTSIZE + "(" + COLUMN_PRODUCTSIZE_ID + ")"
                        + ")";

        // Bảng Review
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

        // Bảng FavoriteDAO
        public static final String TABLE_FAVORITE = "Favorite";
        public static final String COLUMN_FAVORITE_ID = "id";
        public static final String COLUMN_FAVORITE_USER_ID = "user_id";
        public static final String COLUMN_FAVORITE_PRODUCT_ID = "product_id";

        private static final String CREATE_TABLE_FAVORITE = ""
                        + "CREATE TABLE " + TABLE_FAVORITE + "("
                        + COLUMN_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_FAVORITE_USER_ID + " INTEGER NOT NULL,"
                        + COLUMN_FAVORITE_PRODUCT_ID + " INTEGER NOT NULL,"
                        + "FOREIGN KEY(" + COLUMN_FAVORITE_USER_ID + ") REFERENCES "
                        + TABLE_USER + "(" + COLUMN_USER_ID + "),"
                        + "FOREIGN KEY(" + COLUMN_FAVORITE_PRODUCT_ID + ") REFERENCES "
                        + TABLE_PRODUCT + "(" + COLUMN_PRODUCT_ID + ")"
                        + ")";

        public DBHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
                this.context = context;
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
                db.execSQL(CREATE_TABLE_FAVORITE);
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
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);

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
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
                onCreate(db);
        }

        private void addTempData(SQLiteDatabase db) {

                db.execSQL("INSERT INTO Role (name) VALUES ('admin');");
                db.execSQL("INSERT INTO Role (name) VALUES ('user');");

                db.execSQL("INSERT INTO User (username, password, name, email, address, phone, gender, role_id) VALUES ('admin1', 'admin123', 'John Smith', 'admin1@gmail.com', 'abc', '0982828272', 1, 1);");
                db.execSQL("INSERT INTO User (username, password, name, email, address, phone, gender, role_id) VALUES ('admin2', 'admin123', 'Emily Johnson', 'admin2@gmail.com', 'bac', '0982828273', 0, 1);");
                db.execSQL("INSERT INTO User (username, password, name, email, address, phone, gender, role_id) VALUES ('user1', 'user123', 'Michael Brown', 'user1@gmail.com', 'abc', '0982828274', 0, 2);");
                db.execSQL("INSERT INTO User (username, password, name, email, address, phone, gender, role_id) VALUES ('user2', 'user123', 'Sophia Davis', 'user2@gmail.com', 'abc', '0982828275', 1, 2);");

                db.execSQL("INSERT INTO Category (name) VALUES ('NIKE');");
                db.execSQL("INSERT INTO Category (name) VALUES ('ADIDAS');");
                db.execSQL("INSERT INTO Category (name) VALUES ('BITIS');");

                insertProductWithImage(db, "Nike AIR JORDAN ELEVATELOW", "The Nike AIR JORDAN ELEVATELOW is designed for ultimate comfort and style on the court. Its lightweight construction allows for quick movements, while the cushioning provides excellent support during jumps and landings. The shoe’s breathable mesh ensures your feet stay cool, even during intense games. Durable materials guarantee long-lasting performance. With a sleek design, it’s perfect for both sports and casual wear.", 4500000.0, "2024-04-03", 1,
                                R.drawable.img_product_air_jordan_elevatelow, "png");
                insertProductWithImage(db, "Bitis Hunter Core 3D Airmesh", "The Bitis Hunter Core 3D Airmesh offers a perfect blend of comfort and durability for daily wear. Its 3D Airmesh technology ensures maximum breathability, keeping your feet fresh all day long. The cushioned sole provides excellent support, making it ideal for walking and running. The stylish design adds a modern touch to your outfit. Built with high-quality materials, this shoe is designed to last.", 3500000.0, "2023-12-31", 3,
                                R.drawable.img_product_bitis_hunter_core_3d_airmesh, "jpg");
                insertProductWithImage(db, "Bitis Hunter Street", "Bitis Hunter Street is a versatile shoe designed for urban living. Its comfortable fit and cushioned insole make it perfect for long walks in the city. The street-inspired design adds a fashionable edge to your look, while the durable materials ensure it can withstand the demands of city life. With excellent traction, it provides stability on various surfaces. This shoe is both stylish and functional.", 3800000.0, "2024-07-02", 3,
                                R.drawable.img_product_bitis_hunter_street, "jpg");
                insertProductWithImage(db, "Bitis Hunter X", "The Bitis Hunter X combines cutting-edge technology with a sleek design, making it ideal for sports enthusiasts. Its advanced cushioning system provides exceptional comfort, reducing impact during high-intensity activities. The lightweight construction allows for easy movement, while the durable outer material ensures long-term use. Perfect for both indoor and outdoor activities, this shoe delivers top-notch performance.", 2500000.0, "2024-07-02", 3,
                                R.drawable.img_product_bitis_hunter_x, "jpg");
                insertProductWithImage(db, "Nike Pegasus", "The Nike Pegasus is engineered for superior performance and comfort. Its responsive cushioning system enhances energy return, making it perfect for long-distance running. The shoe’s breathable upper keeps your feet cool, while the durable outsole provides excellent traction on various surfaces. With its modern design, the Nike Pegasus is not only a great running shoe but also a stylish addition to your sportswear collection.", 5000000.0, "2024-07-02", 1,
                                R.drawable.img_product_nike_pegasus, "png");
                insertProductWithImage(db, "NIKE ZOOM VOMERO", "The NIKE ZOOM VOMERO offers a plush, cushioned ride for runners seeking comfort over long distances. Its Zoom Air units provide responsive cushioning, absorbing impact and returning energy with every step. The breathable mesh upper keeps your feet ventilated, while the durable outsole ensures grip and stability on different terrains. Ideal for runners who prioritize comfort, this shoe delivers a smooth and supportive experience.", 4150000.0, "2024-07-02", 1,
                                R.drawable.img_product_nike_zoom_vomero, "png");
                insertProductWithImage(db, "NIKE PEGASUS EASYON OLY", "The NIKE PEGASUS EASYON OLY is designed for athletes who demand both performance and convenience. The innovative easy-on design allows you to slip them on quickly without sacrificing a secure fit. The shoe features responsive cushioning for a smooth ride, and the breathable upper ensures your feet stay cool during intense workouts. With its sleek look and reliable performance, this shoe is perfect for both training and casual wear.", 5250000.0, "2024-03-02", 1,
                                R.drawable.img_product_pegasus_easyon_oly, "png");
                insertProductWithImage(db, "Nike Pegasus Trail", "The Nike Pegasus Trail is your go-to shoe for off-road adventures. Its rugged outsole provides excellent grip on uneven terrain, while the cushioned midsole ensures a comfortable ride even on long trails. The reinforced upper offers protection against the elements, making it ideal for outdoor activities. Lightweight and durable, the Nike Pegasus Trail combines functionality with a stylish design, perfect for trail runners.'The Nike Pegasus Trail is your go-to shoe for off-road adventures. Its rugged outsole provides excellent grip on uneven terrain, while the cushioned midsole ensures a comfortable ride even on long trails. The reinforced upper offers protection against the elements, making it ideal for outdoor activities. Lightweight and durable, the Nike Pegasus Trail combines functionality with a stylish design, perfect for trail runners.", 5050000.0, "2024-05-02", 1,
                                R.drawable.img_product_pegasus_trail, "png");
                insertProductWithImage(db, "Adidas Response CL", "The Adidas Response CL is crafted for those who seek comfort and performance in their footwear. Featuring a cushioned midsole, it provides excellent support during running or walking. The breathable upper ensures your feet stay cool, while the durable outsole offers traction on various surfaces. Its classic design is complemented by modern technology, making it a reliable choice for everyday wear.", 3150000.0, "2024-08-02", 2,
                                R.drawable.img_product_response_cl, "jpg");
                insertProductWithImage(db, "Adidas Rivalry Low Be", "The Adidas Rivalry Low Be is a stylish sneaker that blends retro design with modern comfort. The leather upper provides a premium look, while the cushioned insole ensures comfort for all-day wear. Its durable rubber outsole offers great traction, making it suitable for various activities. This shoe is perfect for those who appreciate classic style with a contemporary twist, ideal for both casual and athletic settings.", 4150000.0, "2024-07-02", 2,
                                R.drawable.img_product_rivalry_low_be, "jpg");
                insertProductWithImage(db, "Adidas Superstar", "The Adidas Superstar is an iconic sneaker known for its timeless design and superior comfort. The leather upper and signature rubber shell toe offer durability and style. Its cushioned footbed provides comfort for extended wear, making it a favorite for both athletes and casual wearers. The versatile design pairs well with any outfit, making the Adidas Superstar a must-have in any sneaker collection.", 10050000.0, "2024-08-02", 2,
                                R.drawable.img_product_superstar, "jpg");

                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (1, 39, 75);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (1, 40, 92);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (1, 41, 66);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (1, 42, 55);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (1, 43, 88);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (1, 44, 71);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (1, 45, 94);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (2, 39, 83);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (2, 40, 65);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (2, 41, 57);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (2, 42, 91);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (2, 43, 76);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (2, 44, 53);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (2, 45, 99);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (3, 39, 54);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (3, 40, 89);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (3, 41, 75);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (3, 42, 93);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (3, 43, 78);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (3, 44, 67);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (3, 45, 85);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (4, 39, 64);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (4, 40, 52);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (4, 41, 97);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (4, 42, 78);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (4, 43, 66);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (4, 44, 82);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (4, 45, 91);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (5, 39, 55);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (5, 40, 86);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (5, 41, 73);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (5, 42, 92);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (5, 43, 64);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (5, 44, 57);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (5, 45, 79);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (6, 39, 58);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (6, 40, 71);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (6, 41, 90);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (6, 42, 85);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (6, 43, 62);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (6, 44, 80);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (6, 45, 93);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (7, 39, 74);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (7, 40, 99);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (7, 41, 81);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (7, 42, 67);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (7, 43, 93);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (7, 44, 55);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (7, 45, 79);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (8, 39, 88);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (8, 40, 65);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (8, 41, 57);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (8, 42, 91);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (8, 43, 76);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (8, 44, 53);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (8, 45, 99);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (9, 39, 54);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (9, 40, 89);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (9, 41, 75);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (9, 42, 93);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (9, 43, 78);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (9, 44, 67);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (9, 45, 85);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (10, 39, 64);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (10, 40, 52);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (10, 41, 97);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (10, 42, 78);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (10, 43, 66);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (10, 44, 82);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (10, 45, 91);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (11, 39, 55);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (11, 40, 86);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (11, 41, 73);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (11, 42, 92);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (11, 43, 64);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (11, 44, 57);");
                db.execSQL("INSERT INTO ProductSize (product_id, size, quantity) VALUES (11, 45, 79);");

                db.execSQL("INSERT INTO Cart (user_id) VALUES (3);");
                db.execSQL("INSERT INTO Cart (user_id) VALUES (4);");

                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (1, 1, 4, 1);");
                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (1, 2, 8, 1);");
                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (1, 3, 12, 1);");
                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (1, 4, 16, 1);");
                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (1, 5, 20, 1);");
                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (2, 6, 5, 2);");
                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (2, 7, 9, 2);");
                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (2, 8, 13, 2);");
                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (2, 9, 17, 2);");
                db.execSQL("INSERT INTO CartItem (cart_id, product_id, product_size_id, quantity) VALUES (2, 10, 21, 2);");

                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (3, '2024-08-07');");
                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (3, '2024-08-08');");
                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (3, '2024-08-09');");
                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (3, '2024-08-10');");
                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (3, '2024-08-11');");
                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (4, '2024-08-07');");
                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (4, '2024-08-08');");
                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (4, '2024-08-09');");
                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (4, '2024-08-10');");
                db.execSQL("INSERT INTO Orders (user_id, order_date) VALUES (4, '2024-08-11');");

                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (1, 1, 4, 1, 4500000.0);");
                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (2, 2, 8, 1, 3500000.0);");
                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (3, 3, 12, 1, 3800000.0);");
                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (4, 4, 16, 1, 2500000.0);");
                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (5, 5, 20, 1, 5000000.0);");
                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (6, 6, 5, 2, 4150000.0);");
                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (7, 7, 9, 2, 5250000.0);");
                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (8, 8, 13, 2, 5050000.0);");
                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (9, 9, 17, 2, 3150000.0);");
                db.execSQL("INSERT INTO OrderDetail (order_id, product_id, product_size_id, quantity, unit_price) VALUES (10, 10, 21, 2, 4150000.0);");

                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (3, 3, 4, 'Good quality.', '2024-08-07');");
                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (3, 4, 5, 'Very comfortable.', '2024-08-08');");
                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (3, 5, 3, 'Not bad.', '2024-08-09');");
                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (3, 6, 4, 'Satisfying purchase.', '2024-08-10');");
                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (3, 7, 5, 'Excellent!', '2024-08-11');");

                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (4, 8, 4, 'Worth the price.', '2024-08-07');");
                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (4, 9, 3, 'Average product.', '2024-08-08');");
                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (4, 10, 5, 'Highly recommended.', '2024-08-09');");
                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (4, 11, 4, 'Great purchase.', '2024-08-10');");
                db.execSQL("INSERT INTO Review (user_id, product_id, rating, comment, date) VALUES (4, 1, 5, 'Amazing quality.', '2024-08-11');");
        }

        public byte[] getBytesFromDrawable(int drawableId, String typeImage) {
                BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(drawableId);
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (typeImage.equals("png")) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                } else {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                }
                return stream.toByteArray();
        }

        private void insertProductWithImage(SQLiteDatabase db, String name, String description, double price, String createAt, int categoryId, int imageResourceId, String imageType) {
                try {
                        byte[] image = getBytesFromDrawable(imageResourceId, imageType);
                        String query = "INSERT INTO Product (name, description, price, create_at, category_id, image) VALUES (?, ?, ?, ?, ?, ?)";
                        SQLiteStatement statement = db.compileStatement(query);
                        statement.bindString(1, name);
                        statement.bindString(2, description);
                        statement.bindDouble(3, price);
                        statement.bindString(4, createAt);
                        statement.bindLong(5, categoryId);
                        statement.bindBlob(6, image);
                        statement.executeInsert();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
