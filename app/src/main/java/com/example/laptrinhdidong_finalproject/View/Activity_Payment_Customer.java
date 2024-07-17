package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CartItemsHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.CartItems;
import com.example.laptrinhdidong_finalproject.Model.Carts;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;
import java.util.Map;

public class Activity_Payment_Customer extends AppCompatActivity {
    ListView lvPayItems;
    TextView tvCustomerName, tvCustomerPhone, tvOrderTotal;
    EditText edtShippingAddress;
    Button btnOrderCart;
    ImageButton btnBackToCart;
    ArrayList<CartItems> itemsArrayList = new ArrayList<>();
    CustomAdapter_ListView_Payment paymentAdapter;
    CartItemsHandler cartItemsHandler;
    private Map<String, Customer> customerInfoMap = CustomerHandler.getCustomerInfoMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_customer);
        addControl();
        addEvent();
        displayPayInfor();
    }
    void addControl()
    {
        lvPayItems = findViewById(R.id.lvPayItems);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerPhone = findViewById(R.id.tvCustomerPhone);
        tvOrderTotal = findViewById(R.id.tvOrderTotal);
        edtShippingAddress = findViewById(R.id.edtShippingAddress);
        btnOrderCart = findViewById(R.id.btnOrderCart);
        btnBackToCart = findViewById(R.id.btnBackToCart);
    }
    void addEvent()
    {
        btnOrderCart.setOnClickListener(v -> {
            Toast.makeText(this, "Nhìn cc", Toast.LENGTH_SHORT).show();
        });

        btnBackToCart.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
    void displayPayInfor()
    {
        cartItemsHandler = new CartItemsHandler(Activity_Payment_Customer.this);
        itemsArrayList = cartItemsHandler.loadCartItemsData();
        paymentAdapter = new CustomAdapter_ListView_Payment(Activity_Payment_Customer.this, R.layout.layout_custom_listview_payment, itemsArrayList);
        lvPayItems.setAdapter(paymentAdapter);

        Customer customer = customerInfoMap.get(Fragment_Home.getIdCus());
        if(customer != null)
        {
            tvCustomerName.setText(customer.getNameCustomer());
            tvCustomerPhone.setText(customer.getPhoneCustomer());
        }
        tvOrderTotal.setText(String.valueOf(cartItemsHandler.sumTotalForCarts()));
    }
}