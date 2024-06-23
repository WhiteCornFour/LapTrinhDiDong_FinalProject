package com.example.laptrinhdidong_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Login_Customer extends AppCompatActivity {

    EditText edtPasswordLogin, edtFullNameLogin;
    Button btnLoginCustomer;

    TextView tvRegisterLogin, tvForgetPasswordLogin, tvLoginAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);
        addControl();
        //----------------------
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
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Activity_Login_Customer.this, MainActivity.class);
                intent.putExtra("fullname", edtFullNameLogin.getText().toString());
                intent.putExtra("password", edtPasswordLogin.getText().toString());
                startActivity(intent);
            }
        });

        tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, Activity_Register.class);
                startActivity(intent);
            }
        });

        tvForgetPasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tvLoginAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, Activity_DangNhap_Admin.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //---------------------------
        SharedPreferences sharedPreferences =getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String fn = edtFullNameLogin.getText().toString();
        String ps = edtPasswordLogin.getText().toString();

        editor.putString("tên đầy đủ", fn);
        editor.putString("mật khẩu", ps);

    }
}