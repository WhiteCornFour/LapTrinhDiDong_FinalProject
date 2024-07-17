package com.example.laptrinhdidong_finalproject.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.R;

public class Activity_ForgotPassword_Customer extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Customers";
    private static final String idCustomer = "CustomerID";
    private static final String nameCustomer = "CustomerName";
    private static final String emailCustomer = "CustomerEmail";
    private static final String phoneCustomer = "PhoneNumber";
    private static final String passwordCustomer = "LoginPassword";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";
    private static final int SMS_PERMISSION_CODE = 1;
    Button btn_send_qmk_customer;
    EditText edt_username_customer;
    String generatedOtp;

    TextView tvBackPass, tv_sign_in_qmk_customer, tv_qmk_customer;
    CustomerHandler customerHandler;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpasswordcustomers);

        addControl();
        tv_qmk_customer.setText("Opps !!!" + "\nForgot your password ?");
        customerHandler = new CustomerHandler(Activity_ForgotPassword_Customer.this,
                DB_NAME, null, DB_VERSION);
        customerHandler.onCreate(sqLiteDatabase);
        addEvent();
        checkForSmsPermission();
        Intent intent = getIntent();
        String kq = intent.getStringExtra("backPass");
        tvBackPass.setText("Your password: " + kq);

    }

    void addControl() {
        btn_send_qmk_customer = findViewById(R.id.btn_send_qmk_customer);
        edt_username_customer = findViewById(R.id.edt_username_customer);
        tvBackPass = (TextView) findViewById(R.id.tvBackPass);
        tv_sign_in_qmk_customer = (TextView) findViewById(R.id.tv_sign_in_qmk_customer);
        tv_qmk_customer = findViewById(R.id.tv_qmk_customer);
    }

    void addEvent() {
        btn_send_qmk_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edt_username_customer.getText().toString().trim();
                generatedOtp = generateOtp();

                if (phoneNumber.isEmpty()) {
                    Toast.makeText(Activity_ForgotPassword_Customer.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean kq = customerHandler.verifyPhoneNumberAndSendOTP(phoneNumber);
                if (kq)
                {
                    if (ContextCompat.checkSelfPermission(Activity_ForgotPassword_Customer.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        sendOtp(phoneNumber, generatedOtp);

                        Intent intent = new Intent(Activity_ForgotPassword_Customer.this, Activity_OTPGetPassword_Customer.class);
                        intent.putExtra("otp", generatedOtp);
                        intent.putExtra("sdt", phoneNumber);
                        startActivity(intent);
                    } else {
                        ActivityCompat.requestPermissions(Activity_ForgotPassword_Customer.this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
                    }
                }else
                {
                    Toast.makeText(Activity_ForgotPassword_Customer.this,
                            "Please enter the correct phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_sign_in_qmk_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_ForgotPassword_Customer.this,
                        Activity_Login_Customer.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkForSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }
    }

    private void sendOtp(String phoneNumber, String otp) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, "Your OTP is: " + otp, null, null);
        Toast.makeText(this, "OTP Sent", Toast.LENGTH_SHORT).show();
    }

    //cấp số otp ngẫu nhiên
    private String generateOtp() {
        int randomPin = (int)(Math.random() * 9000) + 1000;
        Log.d("Ma pin", String.valueOf(randomPin));
        return String.valueOf(randomPin);
    }

    // trả lời yêu cầu cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}