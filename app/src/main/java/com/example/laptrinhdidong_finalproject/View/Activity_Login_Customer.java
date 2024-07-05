package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Activity_Login_Customer extends AppCompatActivity {

    EditText edtPasswordLogin, edtFullNameLogin;
    Button btnLoginCustomer;
    TextView tvRegisterLogin, tvForgetPasswordLogin, tvLoginAD;
    CustomerHandler customerHandler;
    SQLiteDatabase sqLiteDatabase;

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Customer";
    private static final String idCustomer = "idCustomer";
    private static final String nameCustomer = "nameCustomer";
    private static final String emailCustomer = "emailCustomer";
    private static final String phoneCustomer = "phoneCustomer";
    private static final String accountCustomer = "accountCustomer";
    private static final String passwordCustomer = "passwordCustomer";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);
        addControl();
        //----------------------
        customerHandler = new CustomerHandler(Activity_Login_Customer.this, DB_NAME, null, DB_VERSION);
        customerHandler.onCreate(sqLiteDatabase);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String fn = sharedPreferences.getString("tên đầy đủ", null);
        String ps = sharedPreferences.getString("mật khẩu", null);
        edtFullNameLogin.setText(fn);
        edtPasswordLogin.setText(ps);
        //----------------------
        addEvent();
    }

    void addControl() {
        edtPasswordLogin = (EditText) findViewById(R.id.edtPasswordLogin);
        edtFullNameLogin = (EditText) findViewById(R.id.edtFullNameLogin);
        btnLoginCustomer = (Button) findViewById(R.id.btnLoginCustomer);
        tvRegisterLogin = (TextView) findViewById(R.id.tvRegisterLogin);
        tvForgetPasswordLogin = (TextView) findViewById(R.id.tvForgetPasswordLogin);
        tvLoginAD = (TextView) findViewById(R.id.tvLoginAD);
    }

    void addEvent() {
        btnLoginCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edtFullNameLogin.getText().toString().trim();
                String password = edtPasswordLogin.getText().toString().trim();

//            if (validateInputs(fullName, password)) {
                boolean isValid = customerHandler.validateLogin(fullName, password);
                if (isValid) {
                    Toast.makeText(Activity_Login_Customer.this, "Login success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Activity_Login_Customer.this, MainActivity.class);
                    intent.putExtra("fullname", fullName);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    resetEdt();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                }
//            }
            }
        });


        tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, Activity_Register.class);
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

    public boolean validateInputs(String username, String password) {
        // Kiểm tra username có hơn 8 ký tự
        if (username.trim().isEmpty() || username.trim().length() <= 8) {
            Toast.makeText(this, "Username must have at least 8 letters", Toast.LENGTH_SHORT).show();
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

        String fn = edtFullNameLogin.getText().toString();
        String ps = edtPasswordLogin.getText().toString();

        editor.putString("tên đầy đủ", fn);
        editor.putString("mật khẩu", ps);
        editor.apply();
    }

    void resetEdt() {
        edtFullNameLogin.setText("");
        edtPasswordLogin.setText("");
    }
}

