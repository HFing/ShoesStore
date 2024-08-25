//package com.hfing.shoesstore.Activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.hfing.shoesstore.Adapter.CartAdapter;
//import com.hfing.shoesstore.DAO.CartDAO;
//import com.hfing.shoesstore.DAO.CartItemDAO;
//import com.hfing.shoesstore.DAO.ProductDAO;
//import com.hfing.shoesstore.DAO.ProductSizeDAO;
//import com.hfing.shoesstore.Model.Cart;
//import com.hfing.shoesstore.Model.CartItem;
//import com.hfing.shoesstore.Model.OrderDetail;
//import com.hfing.shoesstore.Model.Product;
//import com.hfing.shoesstore.R;
//
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class CartActivity extends AppCompatActivity implements CartAdapter.OnQuantityChangeListener {
//
//    private RecyclerView viewCart;
//    private CartAdapter cartAdapter;
//    private List<CartItem> cartItems;
//    private ProductSizeDAO productSizeDAO;
//    private CartItemDAO cartItemDAO;
//    private ProductDAO productDAO;
//    private ImageView backBtn;
//    private Button checkoutBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        checkoutBtn = findViewById(R.id.checkoutBtn);
//        backBtn = findViewById(R.id.backBtn);
//        viewCart = findViewById(R.id.viewCart);
//        viewCart.setLayoutManager(new LinearLayoutManager(this));
//
//        productSizeDAO = new ProductSizeDAO(this);
//        cartItemDAO = new CartItemDAO(this);
//        productDAO = new ProductDAO(this);
//
//        loadCartItems();
//
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    private void loadCartItems() {
//        Intent intent = getIntent();
//        int user_id = intent.getIntExtra("user_id", -1);
//        CartDAO cartDAO = new CartDAO(this);
//        Cart cart = cartDAO.getCartByUserId(user_id);
//        int cart_id = cart != null ? cart.getId() : -1;
//
//        if (cart_id != -1) {
//            cartItems = cartItemDAO.getAllCartItemByCartId(cart_id);
//        } else {
//            Toast.makeText(this, "Cart not found", Toast.LENGTH_SHORT).show();
//        }
//
//        cartAdapter = new CartAdapter(cartItems, productSizeDAO, cartItemDAO, productDAO, this);
//        viewCart.setAdapter(cartAdapter);
//
//        updateCartSummary();
//    }
//
//    private void updateCartSummary() {
//        double subtotal = 0;
//        for (CartItem item : cartItems) {
//            Product product = productDAO.getProductById(item.getProduct_id());
//            subtotal += product.getPrice() * item.getQuantity();
//        }
//
//        double deliveryFee = 30000;
//        double totalTax = 0;
//        double total = subtotal + deliveryFee + totalTax;
//
//        TextView subtotalTxt = findViewById(R.id.totalFeeTxt);
//        TextView deliveryFeeTxt = findViewById(R.id.deliveryTxt);
//        TextView totalTaxTxt = findViewById(R.id.taxTxt);
//        TextView totalTxt = findViewById(R.id.TotalTxt);
//
//        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        subtotalTxt.setText(formatter.format(subtotal));
//        deliveryFeeTxt.setText(formatter.format(deliveryFee));
//        totalTaxTxt.setText(formatter.format(totalTax));
//        totalTxt.setText(formatter.format(total));
//    }
//
//
//    @Override
//    public void onQuantityChanged() {
//        updateCartSummary();
//    }
//}

package com.hfing.shoesstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfing.shoesstore.Adapter.CartAdapter;
import com.hfing.shoesstore.DAO.CartDAO;
import com.hfing.shoesstore.DAO.CartItemDAO;
import com.hfing.shoesstore.DAO.OrderDAO;
import com.hfing.shoesstore.DAO.OrderDetailDAO;
import com.hfing.shoesstore.DAO.ProductDAO;
import com.hfing.shoesstore.DAO.ProductSizeDAO;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.Cart;
import com.hfing.shoesstore.Model.CartItem;
import com.hfing.shoesstore.Model.OrderDetail;
import com.hfing.shoesstore.Model.Orders;
import com.hfing.shoesstore.Model.Product;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnQuantityChangeListener {

    private RecyclerView viewCart;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private ProductSizeDAO productSizeDAO;
    private CartItemDAO cartItemDAO;
    private ProductDAO productDAO;
    private OrderDetailDAO orderDetailDAO;
    private ImageView backBtn;
    private Button checkoutBtn;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", -1);
        UsersDAO usersDAO = new UsersDAO(this);
        User user = usersDAO.getUserById(user_id);
        if (user == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        checkoutBtn = findViewById(R.id.checkoutBtn);
        backBtn = findViewById(R.id.backBtn);
        viewCart = findViewById(R.id.viewCart);
        viewCart.setLayoutManager(new LinearLayoutManager(this));

        productSizeDAO = new ProductSizeDAO(this);
        cartItemDAO = new CartItemDAO(this);
        productDAO = new ProductDAO(this);
        orderDetailDAO = new OrderDetailDAO(this);

        loadCartItems();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItems.size() == 0) {
                    Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                    intent.putExtra("user_id", user_id);
                    int quantity = 0;
                    for (CartItem cartItem : cartItems) {
                        quantity += cartItem.getQuantity();
                    }
                    intent.putExtra("quantity", quantity);
                    intent.putExtra("cartItemSize", cartItems.size());
                    intent.putExtra("cart_id", cartItems.get(0).getCart_id());
                    startActivity(intent);
                }
            }
        });
    }

    private void loadCartItems() {
        Intent intent = getIntent();
        int user_id = intent.getIntExtra("user_id", -1);
        CartDAO cartDAO = new CartDAO(this);
        Cart cart = cartDAO.getCartByUserId(user_id);
        int cart_id = cart != null ? cart.getId() : -1;

        if (cart_id != -1) {
            cartItems = cartItemDAO.getAllCartItemByCartId(cart_id);
        } else {
            Toast.makeText(this, "Cart not found", Toast.LENGTH_SHORT).show();
        }

        cartAdapter = new CartAdapter(cartItems, productSizeDAO, cartItemDAO, productDAO, this);
        viewCart.setAdapter(cartAdapter);

        updateCartSummary();
    }

    private void updateCartSummary() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            Product product = productDAO.getProductById(item.getProduct_id());
            subtotal += product.getPrice() * item.getQuantity();
        }

        double deliveryFee = 30000;
        double totalTax = 0;
        double total = subtotal + deliveryFee + totalTax;

        TextView subtotalTxt = findViewById(R.id.totalFeeTxt);
        TextView deliveryFeeTxt = findViewById(R.id.deliveryTxt);
        TextView totalTaxTxt = findViewById(R.id.taxTxt);
        TextView totalTxt = findViewById(R.id.TotalTxt);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        subtotalTxt.setText(formatter.format(subtotal));
        deliveryFeeTxt.setText(formatter.format(deliveryFee));
        totalTaxTxt.setText(formatter.format(totalTax));
        totalTxt.setText(formatter.format(total));
    }

    private void transferCartItemsToOrderHistory() {
        OrderDAO ordersDAO = new OrderDAO(this);
        Orders order = new Orders();
        order.setUser_id(user_id);
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        order.setOrder_date(currentDateTime);
        ordersDAO.insertOrder(order);
        Orders lastOrder = ordersDAO.getOrderByUserIdAndDate(user_id, currentDateTime);
        for (CartItem item : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder_id(lastOrder.getId());
            orderDetail.setProduct_id(item.getProduct_id()); // Ensure product_id is set
            orderDetail.setProduct_size_id(item.getProduct_size_id());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setUnit_price(productDAO.getProductById(item.getProduct_id()).getPrice());
            orderDetailDAO.insertOrderDetail(orderDetail);
        }
    }

    private void clearCart() {
        int cartId = getIntent().getIntExtra("user_id", -1);
        cartItemDAO.deleteAllCartItemsByCartId(cartId);
        cartItems.clear();
        cartAdapter.notifyDataSetChanged();
        updateCartSummary();
    }

    @Override
    public void onQuantityChanged() {
        updateCartSummary();
    }
}