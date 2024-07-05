package com.example.laptrinhdidong_finalproject.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.AdminHandler;
import com.example.laptrinhdidong_finalproject.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Activity_Login_Admin extends Activity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Admin";
    private static final String adminID = "AdminID";
    private static final String adminName = "AdminName";
    private static final String loginAccount = "LoginAccount";
    private static final String loginPassword = "LoginPassword";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    AdminHandler adminHandler;

    SQLiteDatabase sqLiteDatabase;
    EditText edtAccAD, edtPassAD;
    Button btnLoginAD;
    TextView tvBackToLoginCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap_admin);
        addControl();
        adminHandler = new AdminHandler(Activity_Login_Admin.this, DB_NAME, null, DB_VERSION);

        addEvent();
    }

    void addControl() {
        edtAccAD = (EditText) findViewById(R.id.edtAccAD);
        edtPassAD = (EditText) findViewById(R.id.edtPassAD);
        btnLoginAD = (Button) findViewById(R.id.btnLoginAD);
        tvBackToLoginCustomer = (TextView) findViewById(R.id.tvBackToLoginCustomer);
    }

    void addEvent() {
        tvBackToLoginCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Admin.this,
                        Activity_Login_Customer.class);
                startActivity(intent);
                finish();
            }
        });
        btnLoginAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String us = edtAccAD.getText().toString();
                String pass = edtPassAD.getText().toString();
                //if (validateInputs(us, pass)) {
                boolean isValid = adminHandler.validateLoginAdmin(us, pass);
                if (isValid) {
                    Toast.makeText(Activity_Login_Admin.this, "Login success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Activity_Login_Admin.this,
                            Activity_MainPaige_Admin.class);
                    intent.putExtra("us", us);
                    intent.putExtra("pass", pass);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Account or Password", Toast.LENGTH_LONG).show();
                }
            }
            //}
        });
    }
    public boolean validateInputs(String us, String pass) {
        // Kiểm tra username có hơn 8 ký tự
        if (us.trim().isEmpty() || us.trim().length() <= 8) {
            Toast.makeText(this, "Account must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra password có hơn 8 ký tự
        if (pass.trim().isEmpty() || pass.trim().length() <= 8) {
            Toast.makeText(this, "Password must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
