package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CartsHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Model.Carts;
import com.example.laptrinhdidong_finalproject.R;

public class Activity_Login_Customer extends AppCompatActivity {

    EditText edtPasswordLogin, edtPhoneLogin;
    Button btnLoginCustomer;
    TextView tvRegisterLogin, tvForgetPasswordLogin, tvLoginAD;
    CustomerHandler customerHandler;
    SQLiteDatabase sqLiteDatabase;
    CartsHandler cartsHandler;

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);
        addControl();
        //----------------------
        customerHandler = new CustomerHandler(Activity_Login_Customer.this, DB_NAME, null, DB_VERSION);
        cartsHandler = new CartsHandler(Activity_Login_Customer.this);
        customerHandler.onCreate(sqLiteDatabase);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String pn = sharedPreferences.getString("số điện thoại", null);
        String ps = sharedPreferences.getString("mật khẩu", null);
        edtPhoneLogin.setText(pn);
        edtPasswordLogin.setText(ps);
        //----------------------
        addEvent();
    }

    void addControl() {
        edtPasswordLogin = (EditText) findViewById(R.id.edtPasswordLogin);
        edtPhoneLogin = (EditText) findViewById(R.id.edtPhoneLogin);
        btnLoginCustomer = (Button) findViewById(R.id.btnLoginCustomer);
        tvRegisterLogin = (TextView) findViewById(R.id.tvRegisterLogin);
        tvForgetPasswordLogin = (TextView) findViewById(R.id.tvForgetPasswordLogin);
        tvLoginAD = (TextView) findViewById(R.id.tvLoginAD);
    }

    void addEvent() {
        btnLoginCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhoneLogin.getText().toString().trim();
                String password = edtPasswordLogin.getText().toString().trim();

            if (validateInputs(phone, password)) {
                boolean isValid = customerHandler.validateLogin(phone, password);
                if (isValid) {
                    Toast.makeText(Activity_Login_Customer.this, "Login success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Activity_Login_Customer.this, MainActivity.class);
                    intent.putExtra("phonenumber", phone);
                    intent.putExtra("password", password);
                    //day Cus ID xuong local storage
                    String idCus = customerHandler.getIdCustomer(phone, password);
                    SharedPreferences sharedPreferences = getSharedPreferences("CustomerPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("idCus", idCus);
                    editor.apply();

                    // tạo cart mới cho customer nếu chưa có
                    Carts cart = new Carts(idCus);
                    cartsHandler.insertNewCart(cart);

                    startActivity(intent);
                    resetEdt();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                }
                }
            }
        });

        tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, Activity_Register_Customer.class);
                startActivity(intent);
                finish();
            }
        });

        tvForgetPasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, Activity_ForgotPassword_Customer.class);
                startActivity(intent);
                finish();
            }
        });
        tvLoginAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, Activity_Login_Admin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean validateInputs(String phonenumber, String password) {
        // Kiểm tra username có hơn 8 ký tự
        if (phonenumber.trim().isEmpty() || phonenumber.trim().length() != 10) {
            Toast.makeText(this, "Phone number must have 10 number", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra password có hơn 8 ký tự
        if (password.trim().isEmpty() || password.trim().length() <= 8) {
            Toast.makeText(this, "Password must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //---------------------------
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String pn = edtPhoneLogin.getText().toString();
        String ps = edtPasswordLogin.getText().toString();

        editor.putString("số điện thoại", pn);
        editor.putString("mật khẩu", ps);
        editor.apply();
    }

    void resetEdt() {
        edtPhoneLogin.setText("");
        edtPasswordLogin.setText("");
    }
}

