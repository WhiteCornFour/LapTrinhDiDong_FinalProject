package com.example.laptrinhdidong_finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Activity_DangNhap_Admin extends Activity {

    EditText edtUSA, edtPWA;
    Button btnLoginA;
    String filename = "usa_pwa.txt";
    protected long backpressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap_admin);

        addControl();
        addEvent();

        try {
            // Tạo thông tin đăng nhập ban đầu nếu tệp chưa tồn tại
            saveCredentials("admin", "123", filename);
            saveCredentials("admin2", "321", filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addControl() {
        edtUSA = findViewById(R.id.edtUSA);
        edtPWA = findViewById(R.id.edtPWA);
        btnLoginA = findViewById(R.id.btnLoginA);
    }

    void addEvent() {
        btnLoginA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUSA.getText().toString().trim();
                String password = edtPWA.getText().toString().trim();

                String loginMessage = validateLogin(username, password, filename);
                Toast.makeText(Activity_DangNhap_Admin.this, loginMessage, Toast.LENGTH_SHORT).show();

                if (loginMessage.equals("Login successful")) {
                    // Chuyển sang Activity_TrangChu_Admin và truyền tên đăng nhập
                    Intent intent = new Intent(Activity_DangNhap_Admin.this, Activity_TrangChu_Admin.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    finish(); // Đóng Activity hiện tại sau khi chuyển trang
                }
            }
        });

        backpressTime = 0;
    }

    @Override
    public void onBackPressed() {
        if (backpressTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Nhấn back một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
        backpressTime = System.currentTimeMillis();
    }

    String validateLogin(String username, String password, String fileName) {
        try {
            String[] credentials = readCredentials(username, fileName);
            if (credentials != null && credentials[1].equals(password)) {
                return "Login successful";
            } else {
                return "Invalid username or password";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while reading credentials";
        }
    }

    void saveCredentials(String username, String password, String fileName) throws IOException {
        FileOutputStream fos = openFileOutput(fileName, Context.MODE_APPEND);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        osw.write(username + ";;" + password + "==\n");
        osw.close();
        fos.close();
    }

    String[] readCredentials(String username, String fileName) throws IOException {
        FileInputStream fis = openFileInput(fileName);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";;");
            if (parts.length == 2 && parts[0].equals(username)) {
                String password = parts[1].replaceAll("==", "");
                return new String[]{username, password};
            }
        }

        br.close();
        isr.close();
        fis.close();

        return null;
    }
}
