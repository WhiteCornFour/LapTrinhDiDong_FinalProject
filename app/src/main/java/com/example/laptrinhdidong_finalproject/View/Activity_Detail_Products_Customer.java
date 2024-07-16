package com.example.laptrinhdidong_finalproject.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CartsHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.Carts;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Activity_Detail_Products_Customer extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Customers";
    private static final String idCustomer = "CustomerID";
    private static final String nameCustomer = "CustomerName";
    private static final String emailCustomer = "CustomerEmail";
    private static final String phoneCustomer = "PhoneNumber";
    private static final String passwordCustomer = "LoginPassword";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";
    ImageView imgBackGroundDetail;
    TextView tvProductName, tvProductPrice, tvProductDescription, tvTotalOrder, tvProductQuantity;
    RadioButton rdbSizeS, rdbSizeM, rdbSizeL;
    RadioGroup rGSize;
    Button btnMinusQuantity, btnPlusQuantity, btnAddingIntoCart, btnCancelAddingCart;

    boolean isSizeS = false;
    boolean isSizeM = false;
    boolean isSizeL = false;
    float price = 0.0F;
    String sizeValue = "";
    int countQuantity = 1;
    String idCus = "";
    String idPro = "";
    public static String idCart = "";
    ArrayList<Customer> customerArrayList = new ArrayList<>();
    CustomerHandler customerHandler;
    private static final int CART_ID_LENGTH = 10; // Độ dài của mã giỏ hàng
    private static final String PREFS_NAME = "CartPrefs";
    private static final String CART_ID_KEY = "cart_id";
    private static final Set<String> generatedIDs = new HashSet<>(); // Để lưu các mã đã tạo

    ArrayList<Carts> cartsArrayList = new ArrayList<>();
    CartsHandler cartsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_products_customer);
        addControl();

        cartsHandler = new CartsHandler(this,DB_NAME, null, DB_VERSION);

        //lay chi tiet san pham tu Fragment Home
        Intent intent = getIntent();
        Products pr = (Products) intent.getExtras().getSerializable("product");
        byte[] imageBytes = pr.getImageProduct();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgBackGroundDetail.setImageBitmap(bitmap);
        } else {
            imgBackGroundDetail.setImageResource(R.drawable.background);
        }
        tvProductName.setText(pr.getNameProduct());
        tvProductDescription.setText(pr.getDescriptionProduct());
        tvProductPrice.setText(String.valueOf(pr.getInitialPrice()));
        idPro = pr.getIdProduct();
        price = pr.getInitialPrice();
        idCus = intent.getStringExtra("idCus");
        //tao ma cart moi chi khi ma cart chua duoc tao
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        idCart = sharedPreferences.getString(CART_ID_KEY, null);
        if (idCart == null || idCart.isEmpty()) {
            idCart = generateUniqueCartID();
            saveCartIDToLocal(idCart);
        }
        Log.d("ID Customer from Detail bundle is:" ,idCus);
        Log.d("ID Cart from Detail bundle is:" ,idCart);
        addEvent();
    }

    private void saveCartIDToLocal(String cartID) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CART_ID_KEY, cartID);
        editor.apply();
    }

    public static String generateUniqueCartID() {
        String newID;
        Random random = new Random();

        do {
            StringBuilder sb = new StringBuilder(CART_ID_LENGTH);
            for (int i = 0; i < CART_ID_LENGTH; i++) {
                int digit = random.nextInt(10); // Lấy số ngẫu nhiên từ 0 đến 9
                sb.append(digit);
            }
            newID = sb.toString();
        } while (generatedIDs.contains(newID)); // Lặp lại nếu mã đã tồn tại

        generatedIDs.add(newID); // Thêm mã mới vào tập hợp để đảm bảo không bị trùng

        return newID;
    }

    public static void main(String[] args) {
        // Kiểm tra việc tạo mã giỏ hàng không trùng nhau
        for (int i = 0; i < 10; i++) {
            String cartID = generateUniqueCartID();
            System.out.println("Generated Cart ID: " + cartID);
        }
    }

    void addControl() {
        imgBackGroundDetail = findViewById(R.id.imgBackGroundDetail);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        tvTotalOrder = findViewById(R.id.tvTotalOrder);
        tvProductQuantity = findViewById(R.id.tvProductQuantity);
        rdbSizeS = findViewById(R.id.rdbSizeS);
        rdbSizeM = findViewById(R.id.rdbSizeM);
        rdbSizeL = findViewById(R.id.rdbSizeL);
        btnMinusQuantity = findViewById(R.id.btnMinusQuantity);
        btnPlusQuantity = findViewById(R.id.btnPlusQuantity);
        rGSize = findViewById(R.id.rGSize);
        btnAddingIntoCart = findViewById(R.id.btnAddingIntoCart);
        btnCancelAddingCart = findViewById(R.id.btnCancelAddingCart);
    }

    void addEvent() {
        rdbSizeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSizeS) {
                    rdbSizeS.setChecked(false);
                    rdbSizeM.setEnabled(true);
                    rdbSizeL.setEnabled(true);
                } else {
                    rdbSizeS.setChecked(true);
                    rdbSizeM.setEnabled(false);
                    rdbSizeL.setEnabled(false);
                    sizeValue = "S";
                    int quantity = Integer.parseInt(tvProductQuantity.getText().toString());
                    String st = String.valueOf(price * quantity);
                    tvTotalOrder.setText(st);
                }
                isSizeS = !isSizeS;
                updateTotalCart();
            }
        });
        rdbSizeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSizeM) {
                    rdbSizeM.setChecked(false);
                    rdbSizeS.setEnabled(true);
                    rdbSizeL.setEnabled(true);
                } else {
                    rdbSizeM.setChecked(true);
                    rdbSizeS.setEnabled(false);
                    rdbSizeL.setEnabled(false);
                    sizeValue = "M";
                    int quantity = Integer.parseInt(tvProductQuantity.getText().toString());
                    String st = String.valueOf(price * quantity + 3);
                    tvTotalOrder.setText(st);
                }
                isSizeM = !isSizeM;
                updateTotalCart();
            }
        });
        rdbSizeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSizeL) {
                    rdbSizeL.setChecked(false);
                    rdbSizeM.setEnabled(true);
                    rdbSizeS.setEnabled(true);
                } else {
                    rdbSizeL.setChecked(true);
                    rdbSizeM.setEnabled(false);
                    rdbSizeS.setEnabled(false);
                    sizeValue = "L";
                    int quantity = Integer.parseInt(tvProductQuantity.getText().toString());
                    String st = String.valueOf(price * quantity + 5);
                    tvTotalOrder.setText(st);
                }
                isSizeL = !isSizeL;
                updateTotalCart();
            }
        });
        btnMinusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countQuantity <= 1) {
                    countQuantity = 1;
                    tvProductQuantity.setText(String.valueOf(countQuantity));
                    Toast.makeText(Activity_Detail_Products_Customer.this,
                            "Can't not have quantity under 1 item.", Toast.LENGTH_SHORT).show();
                } else {
                    countQuantity -= 1;
                    tvProductQuantity.setText(String.valueOf(countQuantity));
                }
                updateTotalCart();
            }
        });
        btnPlusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countQuantity += 1;
                tvProductQuantity.setText(String.valueOf(countQuantity));
                updateTotalCart();
            }
        });

        btnAddingIntoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cartId = idCart;
                String productId = idPro;
                String customerId = idCus;
                String sizeProduct = sizeValue;
                int productQuantity = Integer.parseInt(tvProductQuantity.getText().toString());
                double cartUnitPrice = Double.parseDouble(tvTotalOrder.getText().toString());
                Carts carts = new Carts(cartId, customerId, productId, sizeProduct, productQuantity, cartUnitPrice);
                cartsHandler.insertRecordIntoCartsTable(carts);
                Toast.makeText(Activity_Detail_Products_Customer.this, "Adding cart successful!!", Toast.LENGTH_SHORT).show();
//                loadFragment(new Fragment_Home());
                returnToHomeFragment();
            }
        });

        btnCancelAddingCart.setOnClickListener(v -> returnToHomeFragment());
    }

    //update tong tien cart sau moi su kien
    void updateTotalCart() {
        int quantity = Integer.parseInt(tvProductQuantity.getText().toString());
        float totalPrice = price * quantity;

        if (sizeValue.equals("L")) {
            totalPrice += quantity * 5;
        } else if (sizeValue.equals("M")) {
            totalPrice += quantity * 3;
        }

        tvTotalOrder.setText(String.valueOf(totalPrice));
    }

    private void returnToHomeFragment() {
        Intent intent = new Intent(Activity_Detail_Products_Customer.this, MainActivity.class);
        intent.putExtra("idItem", "Home");
        startActivity(intent);
    }
}
