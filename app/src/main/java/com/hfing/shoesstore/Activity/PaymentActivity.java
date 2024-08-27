package com.hfing.shoesstore.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hfing.shoesstore.Adapter.PaymentAdapter;
import com.hfing.shoesstore.DAO.CartDAO;
import com.hfing.shoesstore.DAO.CartItemDAO;
import com.hfing.shoesstore.DAO.OrderDAO;
import com.hfing.shoesstore.DAO.OrderDetailDAO;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.ProductSizeDAO;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.CartItem;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    TextView nameTxt, phoneTxt, txtAddress;
    ImageView editAddressBtn, backBtn;
    ListView listProductPayment;
    TextView totalFeeTxt, deliveryFeeTxt, taxTxt, totalPaymentTxt;
    Button placeOrderBtn;
    UsersDAO usersDAO;
    CartDAO cartDAO;
    CartItemDAO cartItemDAO;
    ProductDAO productDAO;
    ProductSizeDAO productSizeDAO;
    OrderDAO ordersDAO;
    OrderDetailDAO orderDetailDAO;
    List<CartItem> cartItems;
    int user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        usersDAO = new UsersDAO(this);
        cartDAO = new CartDAO(this);
        cartItemDAO = new CartItemDAO(this);
        productDAO = new ProductDAO(this);
        productSizeDAO = new ProductSizeDAO(this);
        ordersDAO = new OrderDAO(this);
        orderDetailDAO = new OrderDetailDAO(this);

        nameTxt = findViewById(R.id.nameTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        txtAddress = findViewById(R.id.txtAddress);
        editAddressBtn = findViewById(R.id.editAddressBtn);
        listProductPayment = findViewById(R.id.listProductPayment);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        deliveryFeeTxt = findViewById(R.id.deliveryTxt);
        taxTxt = findViewById(R.id.taxTxt);
        totalPaymentTxt = findViewById(R.id.TotalTxt);
        placeOrderBtn = findViewById(R.id.placeOrderBtn);
        backBtn = findViewById(R.id.backBtn);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", -1);
        if (user_id == -1) {
            Log.e("PaymentActivity", "Invalid user ID: " + user_id);
            finish();
            return;
        }

        int cartItemSize = intent.getIntExtra("cartItemSize", 0);
        int quantity = intent.getIntExtra("quantity", 0);
        User user = usersDAO.getUserById(user_id);
        if (user == null) {
            Log.e("PaymentActivity", "User not found with ID: " + user_id);
            finish();
            return;
        }

        nameTxt.setText(user.getName());
        phoneTxt.setText(user.getPhone());
        txtAddress.setText(user.getAddress());

        if (cartItemSize == 0 || quantity == 0) {
            Log.e("PaymentActivity", "Invalid cartItemSize or quantity: " + cartItemSize + ", " + quantity);
            finish();
            return;
        }

        cartItems = new ArrayList<>();

        if (cartItemSize == 1 && quantity == 1) {
            int product_id = intent.getIntExtra("product_id", -1);
            int product_size_id = intent.getIntExtra("product_size_id", -1);
            CartItem cartItem = new CartItem();
            cartItem.setCart_id(-1);
            cartItem.setProduct_id(product_id);
            cartItem.setProduct_size_id(product_size_id);
            cartItem.setQuantity(quantity);
            cartItems.add(cartItem);
            PaymentAdapter paymentAdapter = new PaymentAdapter(this, cartItems, productSizeDAO, productDAO);
            listProductPayment.setAdapter(paymentAdapter);

            placeOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(nameTxt.getText().toString().isEmpty() || phoneTxt.getText().toString().isEmpty() || txtAddress.getText().toString().isEmpty()) {
                        Toast.makeText(PaymentActivity.this, "Please fill all your information in all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    transferCartItemsToOrderHistory();
                    cartItems.clear();
                    Intent intent = new Intent(PaymentActivity.this, BaseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", user_id);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (cartItemSize > 1 && quantity > 1) {
            int cartId = intent.getIntExtra("cart_id", -1);
            if (cartId == -1) {
                Log.e("PaymentActivity", "Invalid cart ID: " + cartId);
                finish();
                return;
            }
            cartItems = cartItemDAO.getAllCartItemByCartId(cartDAO.getCartByUserId(user.getId()).getId());
            if (cartItems == null) {
                Log.e("PaymentActivity", "CartItems not found");
                finish();
                return;
            }
            PaymentAdapter paymentAdapter = new PaymentAdapter(this, cartItems, productSizeDAO, productDAO);
            listProductPayment.setAdapter(paymentAdapter);

            placeOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transferCartItemsToOrderHistory();
                    clearCart(cartId);
                    Intent intent = new Intent(PaymentActivity.this, BaseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", user_id);
                    startActivity(intent);
                    finish();
                }
            });
        }

        updateCartSummary();

        editAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditAddressDialog(user);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, BaseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", user_id);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateCartSummary() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            Product product = productDAO.getProductById(item.getProduct_id());
            subtotal += product.getPrice() * item.getQuantity();
        }

        double deliveryFee = 0;
        double totalTax = 0;
        double total = subtotal + deliveryFee + totalTax;



        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        totalFeeTxt.setText(formatter.format(subtotal));
        deliveryFeeTxt.setText(formatter.format(deliveryFee));
        taxTxt.setText(formatter.format(totalTax));
        totalPaymentTxt.setText(formatter.format(total));
    }

    private void transferCartItemsToOrderHistory() {
        Orders order = new Orders();
        order.setUser_id(user_id);
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        order.setOrder_date(currentDateTime);
        ordersDAO.insertOrder(order);
        Orders lastOrder = ordersDAO.getOrderByUserIdAndDate(user_id, currentDateTime);
        if(lastOrder != null){
            for (CartItem item : cartItems) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder_id(lastOrder.getId());
                orderDetail.setProduct_id(item.getProduct_id()); // Ensure product_id is set
                orderDetail.setProduct_size_id(item.getProduct_size_id());
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setUnit_price(productDAO.getProductById(item.getProduct_id()).getPrice());
                if(orderDetailDAO.insertOrderDetail(orderDetail)>0){
                    Log.d("OrderDetail", "OrderDetail inserted");
                }
                else{
                    Log.e("OrderDetail", "OrderDetail not inserted");
                }
            }
        }
        else
            Log.e("Order", "Order not found");

    }

    private void clearCart(int cartId) {
        cartItemDAO.deleteAllCartItemsByCartId(cartId);
        cartItems.clear();
    }

    private void showEditAddressDialog(User user) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_user_info);

        EditText nameEditText = dialog.findViewById(R.id.editName);
        EditText phoneEditText = dialog.findViewById(R.id.editPhone);
        EditText addressEditText = dialog.findViewById(R.id.editAddress);
        Button saveBtn = dialog.findViewById(R.id.btnSave);
        Button cancelBtn = dialog.findViewById(R.id.btnCancel);

        nameEditText.setText(user.getName());
        phoneEditText.setText(user.getPhone());
        addressEditText.setText(user.getAddress());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String newName = nameEditText.getText().toString();
                String newPhone = phoneEditText.getText().toString();
                String newAddress = addressEditText.getText().toString();

                if (!newName.isEmpty() && !newPhone.isEmpty() && !newAddress.isEmpty()) {
                    user.setName(newName);
                    user.setPhone(newPhone);
                    user.setAddress(newAddress);
                    usersDAO.updateUser(user);

                    nameTxt.setText(newName);
                    phoneTxt.setText(newPhone);
                    txtAddress.setText(newAddress);

                    dialog.dismiss();
                }
                else {
                    Toast.makeText(PaymentActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
