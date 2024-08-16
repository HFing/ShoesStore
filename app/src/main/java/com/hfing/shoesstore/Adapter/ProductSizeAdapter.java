package com.hfing.shoesstore.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.Model.ProductSize;
import com.hfing.shoesstore.R;

import java.util.List;

public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.ViewHolder> {

    private List<ProductSize> productSizes;
    private int selectedPosition = -1;
    private OnSizeClickListener onSizeClickListener;

    public ProductSizeAdapter(List<ProductSize> productSizes) {
        this.productSizes = productSizes;
    }

    public void setOnSizeClickListener(OnSizeClickListener onSizeClickListener) {
        this.onSizeClickListener = onSizeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_size, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductSize productSize = productSizes.get(position);
        holder.sizeTextView.setText(String.valueOf(productSize.getSize()));
        if (selectedPosition == position){
            holder.itemView.setBackgroundResource(R.drawable.purple_bg);
        }else{
            holder.itemView.setBackgroundResource(R.drawable.white_bg);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                if (onSizeClickListener != null){
                    onSizeClickListener.onSizeSelected(productSize);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productSizes.size();
    }

    public interface OnSizeClickListener {
        void onSizeSelected(ProductSize productSize);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sizeTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeTextView = itemView.findViewById(R.id.sizeTextView);
        }
    }
}
