package com.example.laptrinhdidong_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_TrangChu_Admin extends Activity {

    protected long backpressTime;
    TextView tvWelCum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu_adin);

        addControl();

        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("USERNAME");
            if (username != null && !username.isEmpty()) {
                tvWelCum.setText("Welcome, " + username + "!");
            }
        }
    }

    void addControl() {
        tvWelCum = findViewById(R.id.tvWelCum);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Activity_DangNhap_Admin.class);
        startActivity(intent);
        finish();
    }

}


