package com.example.laptrinhdidong_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class forgotpasswordcustomers extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 1;
    Button btn_send_qmk_customer;
    EditText edt_username_customer;
    String generatedOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpasswordcustomers);

        addControl();
        addEvent();
        checkForSmsPermission();
    }

    void addControl() {
        btn_send_qmk_customer = findViewById(R.id.btn_send_qmk_customer);
        edt_username_customer = findViewById(R.id.edt_username_customer);
    }

    void addEvent() {
        btn_send_qmk_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edt_username_customer.getText().toString().trim();
                generatedOtp = generateOtp();

                if (phoneNumber.isEmpty()) {
                    Toast.makeText(forgotpasswordcustomers.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ContextCompat.checkSelfPermission(forgotpasswordcustomers.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    sendOtp(phoneNumber, generatedOtp);

                    Intent intent = new Intent(forgotpasswordcustomers.this, OTP_get_password.class);
                    intent.putExtra("otp", generatedOtp);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(forgotpasswordcustomers.this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
                }
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
        Toast.makeText(this, "OTP sent", Toast.LENGTH_SHORT).show();
    }

    //cấp số otp ngẫu nhiên
    private String generateOtp() {
        int randomPin = (int)(Math.random() * 9000) + 1000;
        return String.valueOf(randomPin);
    }

   // trả lời yêu cầu cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
