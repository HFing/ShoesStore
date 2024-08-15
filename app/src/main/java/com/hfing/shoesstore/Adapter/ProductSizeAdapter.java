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

    public ProductSizeAdapter(List<ProductSize> productSizes) {
        this.productSizes = productSizes;
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
    }

    @Override
    public int getItemCount() {
        return productSizes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sizeTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeTextView = itemView.findViewById(R.id.sizeTextView);
        }
    }
}
