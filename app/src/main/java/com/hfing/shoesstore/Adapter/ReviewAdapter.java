// In ReviewAdapter.java
package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.Review;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final List<Review> reviews;
    private final Context context;

    public ReviewAdapter(List<Review> reviews, Context context) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_list, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        UsersDAO usersDAO = new UsersDAO(context);
        User user = usersDAO.getUserById(review.getUser_id());
        holder.nameTxt.setText(user.getName());
        holder.ratingTxt.setText(String.valueOf(review.getRating()));
        holder.commentTxt.setText(review.getComment());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt, ratingTxt, commentTxt;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            ratingTxt = itemView.findViewById(R.id.ratingTxt);
            commentTxt = itemView.findViewById(R.id.commentTxt);
        }
    }
}