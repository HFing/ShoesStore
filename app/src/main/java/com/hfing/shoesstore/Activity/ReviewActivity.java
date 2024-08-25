package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hfing.shoesstore.DAO.ReviewDAO;
import com.hfing.shoesstore.Model.Review;
import com.hfing.shoesstore.R;

public class ReviewActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText editTextComment;
    private Button buttonSubmit;
    private ImageView backBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        int productId = getIntent().getIntExtra("product_id", -1);

        ratingBar = findViewById(R.id.ratingBar);
        editTextComment = findViewById(R.id.editTextComment);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the rating and comment
                float rating = ratingBar.getRating();
                String comment = editTextComment.getText().toString();

                // Save the review to the database
                Review review = new Review();
                review.setRating((int) rating);
                review.setComment(comment);
                review.setProduct_id(productId);
                ReviewDAO reviewDAO = new ReviewDAO(ReviewActivity.this);
                reviewDAO.addReview(review);
                finish();
            }
        });
    }
}