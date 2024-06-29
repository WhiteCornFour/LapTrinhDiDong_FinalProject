package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.R;

public class Activity_OTPGetPassword_Customer extends AppCompatActivity {

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
                        // Proceed to next step or activity
                    } else {
                        Toast.makeText(Activity_OTPGetPassword_Customer.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
