package com.example.laptrinhdidong_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class OTP_get_password extends AppCompatActivity {

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
                        Toast.makeText(OTP_get_password.this, "OTP verified successfully!", Toast.LENGTH_SHORT).show();
                        // Proceed to next step or activity
                    } else {
                        Toast.makeText(OTP_get_password.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
