package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CartItemsHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CartsHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.OrderDetailsHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.OrdersHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.Utils;
import com.example.laptrinhdidong_finalproject.Model.CartItems;
import com.example.laptrinhdidong_finalproject.Model.Carts;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.Orders;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class Activity_Payment_Customer extends AppCompatActivity {
    ListView lvPayItems;
    TextView tvCustomerName, tvCustomerPhone, tvOrderTotal, tvConfirmName, tvConfirmPhone, tvConfirmAddress, tvConfirmTotal;
    EditText edtShippingAddress;
    Button btnOrderCart, btnConfirmOrder, btnCancelConfirm;
    ImageButton btnBackToCart;
    ArrayList<CartItems> itemsArrayList = new ArrayList<>();
    CustomAdapter_ListView_Payment paymentAdapter;
    CartItemsHandler cartItemsHandler;
    OrdersHandler ordersHandler;
    OrderDetailsHandler orderDetailsHandler;
    private final Map<String, Customer> customerInfoMap = CustomerHandler.getCustomerInfoMap();
    String cusID = Fragment_Home.getIdCus();
    Customer customer = customerInfoMap.get(cusID);
    double orderTotal = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_customer);
        cartItemsHandler = new CartItemsHandler(Activity_Payment_Customer.this);
        ordersHandler = new OrdersHandler(Activity_Payment_Customer.this);
        orderDetailsHandler = new OrderDetailsHandler(Activity_Payment_Customer.this);
        addControl();
        addEvent();
        displayPayInfor();
    }

    void addControl() {
        lvPayItems = findViewById(R.id.lvPayItems);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerPhone = findViewById(R.id.tvCustomerPhone);
        tvOrderTotal = findViewById(R.id.tvOrderTotal);
        edtShippingAddress = findViewById(R.id.edtShippingAddress);
        btnOrderCart = findViewById(R.id.btnOrderCart);
        btnBackToCart = findViewById(R.id.btnBackToCart);
    }
    String newOrderID = "";

    void addEvent() {
        btnOrderCart.setOnClickListener(v -> {
            if (!edtShippingAddress.getText().toString().isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String cartID = CartsHandler.getCustomerCartID();
                String orderDate = now.format(formatter);
                String shippingAddress = edtShippingAddress.getText().toString().trim();

                Orders newOrder = new Orders(cartID, orderDate, shippingAddress, orderTotal);
                newOrderID = ordersHandler.insertNewOrder(newOrder); // lấy id của order vừa thêm
                newOrder.setOrderID(newOrderID); // gán id cho order vừa thêm

                showConfirmDialog();
            } else {
                Toast.makeText(this, "Please fill in your address", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackToCart.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    void displayPayInfor() {
        itemsArrayList = cartItemsHandler.loadCartItemsData();
        paymentAdapter = new CustomAdapter_ListView_Payment(Activity_Payment_Customer.this, R.layout.layout_custom_listview_payment, itemsArrayList);
        lvPayItems.setAdapter(paymentAdapter);

        if (customer != null) {
            tvCustomerName.setText(customer.getNameCustomer());
            tvCustomerPhone.setText(customer.getPhoneCustomer());
        }
        tvOrderTotal.setText(String.valueOf(cartItemsHandler.sumTotalForCarts()));
        if (cartItemsHandler.sumTotalForCarts() != null) {
            orderTotal = cartItemsHandler.sumTotalForCarts();
        }
    }

    void showConfirmDialog() {
        Dialog confirmOrderDialog = new Dialog(Activity_Payment_Customer.this); // Tạo dialog thêm category

        // Sử dụng custom layout cho dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.layout_confirm_order_dialog, null);
        confirmOrderDialog.setContentView(customView);

        // Lấy kích thước màn hình
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;

        confirmOrderDialog.getWindow().setLayout(width, height); //Thiết lập kích thước hiển thị trên màn hình cho dialog
        confirmOrderDialog.show();
        addDialogEvent(confirmOrderDialog, customView);
    }

    void addDialogEvent(Dialog confirmOrderDialog, View customView) {
        tvConfirmName = customView.findViewById(R.id.tvConfirmName);
        tvConfirmPhone = customView.findViewById(R.id.tvConfirmPhone);
        tvConfirmAddress = customView.findViewById(R.id.tvConfirmAddress);
        tvConfirmTotal = customView.findViewById(R.id.tvConfirmTotal);
        btnConfirmOrder = customView.findViewById(R.id.btnConfirmOrder);
        btnCancelConfirm = customView.findViewById(R.id.btnCancelConfirm);

        tvConfirmName.setText(customer.getNameCustomer());
        tvConfirmPhone.setText(customer.getPhoneCustomer());
        tvConfirmAddress.setText(edtShippingAddress.getText().toString());
        tvConfirmTotal.setText(String.valueOf(orderTotal));

        btnConfirmOrder.setOnClickListener(v -> {
            // Sao chép dữ liệu từ cart items qua order detail
            ArrayList<CartItems> orderItems = cartItemsHandler.loadCartItemsData();
            orderDetailsHandler.insertNewOrderDetails(newOrderID, orderItems);
            cartItemsHandler.deleteCartItems(); // Xóa items trong cartItems

            // Nếu orders và order details được tạo, cart items xóa thành công
            confirmOrderDialog.dismiss();
            Toast.makeText(this, "Order successful.\nYour order will be delivered to you as soon as possible.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        btnCancelConfirm.setOnClickListener(v -> {
            ordersHandler.deleteOrder(newOrderID);
            confirmOrderDialog.dismiss();
        });
    }
}