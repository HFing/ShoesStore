package com.hfing.shoesstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfing.shoesstore.Model.DBHelper;
import com.hfing.shoesstore.Model.Review;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReviewDAO {
    private final DBHelper dbHelper;
    private final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");

    public ReviewDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    @SuppressLint("Range")
    public List<Review> getAllReviews() {
        ArrayList<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM review";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Review review = new Review();
                    review.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_ID)));
                    review.setRating(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_RATING)));
                    review.setComment(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_COMMENT)));
                    review.setUser_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_USER_ID)));
                    review.setProduct_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_PRODUCT_ID)));
                    reviews.add(review);
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
        return reviews != null ? reviews : new ArrayList<>();
    }

    public long addReview(Review review) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_REVIEW_RATING, review.getRating());
            values.put(DBHelper.COLUMN_REVIEW_COMMENT, review.getComment());
            values.put(DBHelper.COLUMN_REVIEW_USER_ID, review.getUser_id());
            values.put(DBHelper.COLUMN_REVIEW_PRODUCT_ID, review.getProduct_id());
            result = db.insert(DBHelper.TABLE_REVIEW, null, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return result;
    }

    public long updateReview(Review review) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_REVIEW_RATING, review.getRating());
            values.put(DBHelper.COLUMN_REVIEW_COMMENT, review.getComment());
            values.put(DBHelper.COLUMN_REVIEW_USER_ID, review.getUser_id());
            values.put(DBHelper.COLUMN_REVIEW_PRODUCT_ID, review.getProduct_id());
            result = db.update(DBHelper.TABLE_REVIEW, values, DBHelper.COLUMN_REVIEW_ID + " = ?", new String[]{String.valueOf(review.getId())});
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return result;
    }

    public long deleteReview(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        try {
            result = db.delete(DBHelper.TABLE_REVIEW, DBHelper.COLUMN_REVIEW_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
        return result;
    }

    @SuppressLint("Range")
    public List<Review> getReviewsByProductId(int product_id) {
        ArrayList<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM review WHERE product_id = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(product_id)});
            if (cursor.moveToFirst()) {
                do {
                    Review review = new Review();
                    review.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_ID)));
                    review.setRating(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_RATING)));
                    review.setComment(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_COMMENT)));
                    review.setUser_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_USER_ID)));
                    review.setProduct_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_REVIEW_PRODUCT_ID)));
                    reviews.add(review);
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

        Collections.sort(reviews, new Comparator<Review>() {
            @Override
            public int compare(Review review1, Review review2) {
                try {
                    return dateFormater.parse(review2.getDate()).compareTo(dateFormater.parse(review1.getDate()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return reviews != null ? reviews : new ArrayList<>();
    }
}
