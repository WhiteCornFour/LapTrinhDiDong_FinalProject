package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.R;

public class Activity_OTPGetPassword_Customer extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Customers";
    private static final String idCustomer = "CustomerID";
    private static final String nameCustomer = "CustomerName";
    private static final String emailCustomer = "CustomerEmail";
    private static final String phoneCustomer = "PhoneNumber";
    private static final String passwordCustomer = "LoginPassword";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    CustomerHandler customerHandler;

    SQLiteDatabase sqLiteDatabase;
    EditText edt_otp;
    Button btn_verify_otp;
    String receivedOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_get_password);

        edt_otp = findViewById(R.id.edt_otp);
        btn_verify_otp = findViewById(R.id.btn_verify_otp);


        receivedOtp = getIntent().getStringExtra("otp");

        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = edt_otp.getText().toString().trim();

                if (enteredOtp.equals(receivedOtp)) {
                    Toast.makeText(Activity_OTPGetPassword_Customer.this, "OTP verified successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    String sdt = intent.getStringExtra("sdt");
                    //Log.d("So dien thoai", sdt);
                    customerHandler = new CustomerHandler(Activity_OTPGetPassword_Customer.this,
                            DB_NAME, null, DB_VERSION);
                    String backPass = customerHandler.returnPassWord(sdt);
                    intent = new Intent(Activity_OTPGetPassword_Customer.this, Activity_ForgotPassword_Customer.class);
                    intent.putExtra("backPass", backPass);
                    startActivity(intent);
                    finish();
                    //Log.d("Back pass", backPass);
                } else {
                    Toast.makeText(Activity_OTPGetPassword_Customer.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
