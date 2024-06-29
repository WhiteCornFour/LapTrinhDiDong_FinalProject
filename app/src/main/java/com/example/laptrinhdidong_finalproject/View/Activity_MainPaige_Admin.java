package com.example.laptrinhdidong_finalproject.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.R;

public class Activity_MainPaige_Admin extends Activity {

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
        Intent intent = new Intent(this, Activity_Login_Admin.class);
        startActivity(intent);
        finish();
    }

}


