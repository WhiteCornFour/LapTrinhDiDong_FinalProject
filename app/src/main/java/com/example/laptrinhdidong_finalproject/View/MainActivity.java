package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.R;

public class MainActivity extends AppCompatActivity {

    TextView tvTTLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        //----------------------------
        Intent intent = getIntent();
        String fn = intent.getStringExtra("fullname");
        tvTTLogin.setText("Xin chào " + fn);
    }

    void addControl() {
        tvTTLogin = (TextView) findViewById(R.id.tvTTLogin);
    }
}