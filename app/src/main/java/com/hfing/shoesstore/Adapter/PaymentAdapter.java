package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfing.shoesstore.DAO.CartItemDAO;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.ProductSizeDAO;
import com.hfing.shoesstore.Model.CartItem;
import com.hfing.shoesstore.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class PaymentAdapter extends BaseAdapter {
    private List<CartItem> cartItems;
    private ProductSizeDAO productSizeDAO;
    private ProductDAO productDAO;
    private LayoutInflater inflater;

    public PaymentAdapter(Context context, List<CartItem> cartItems, ProductSizeDAO productSizeDAO, ProductDAO productDAO) {
        this.cartItems = cartItems;
        this.productSizeDAO = productSizeDAO;
        this.productDAO = productDAO;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int i) {
        return cartItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product_payment_listview, null);
            holder = new ViewHolder();
            holder.productImage = convertView.findViewById(R.id.productImage);
            holder.productName = convertView.findViewById(R.id.productName);
            holder.productPrice = convertView.findViewById(R.id.unitPrice);
            holder.productSize = convertView.findViewById(R.id.productSize);
            holder.productQuantity = convertView.findViewById(R.id.quantity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CartItem cartItem = cartItems.get(position);
        byte[] productImage = productDAO.getProductById(cartItem.getProduct_id()).getImage();
        if (productImage != null) {
            holder.productImage.setImageBitmap(BitmapFactory.decodeByteArray(productImage, 0, productImage.length));
        }
        else {
            holder.productImage.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.productName.setText(productDAO.getProductById(cartItem.getProduct_id()).getName());
        holder.productPrice.setText(String.valueOf(productDAO.getProductById(cartItem.getProduct_id()).getPrice()));
        holder.productSize.setText(String.valueOf(productSizeDAO.getProductSizeById(cartItem.getProduct_size_id()).getSize()));
        holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));
        return convertView;
    }

    private static class ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productSize;
        TextView productQuantity;
    }
}
