package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.R;

import java.io.IOException;
import java.io.InputStream;

public class Activity_Login_Customer extends AppCompatActivity {

    EditText edtPasswordLogin, edtFullNameLogin;
    Button btnLoginCustomer;

    TextView tvRegisterLogin, tvForgetPasswordLogin, tvLoginAD;

    String filename = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);
        addControl();
        //----------------------
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String fn = sharedPreferences.getString("tên đầy đủ", null);
        String ps = sharedPreferences.getString("mật khẩu", null);
        edtFullNameLogin.setText(fn);
        edtPasswordLogin.setText(ps);
        //----------------------
        addEvent();
    }

    void addControl() {
        edtPasswordLogin = (EditText) findViewById(R.id.edtPasswordLogin);
        edtFullNameLogin = (EditText) findViewById(R.id.edtFullNameLogin);
        btnLoginCustomer = (Button) findViewById(R.id.btnLoginCustomer);
        tvRegisterLogin = (TextView) findViewById(R.id.tvRegisterLogin);
        tvForgetPasswordLogin = (TextView) findViewById(R.id.tvForgetPasswordLogin);
        tvLoginAD = (TextView) findViewById(R.id.tvLoginAD);
    }

    void addEvent() {
        btnLoginCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = edtFullNameLogin.getText().toString();
                String password = edtPasswordLogin.getText().toString();

//                if (!validateInputs(fullName, password)) {
//                    return;
//                }

                boolean isValid = false;
                try {
                    isValid = validateLoginInfo(filename, fullName, password);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (isValid) {
                    Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Activity_Login_Customer.this, MainActivity.class);
                    intent.putExtra("fullname", fullName);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    resetEdt();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                }
            }
        });

        tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, Activity_Register.class);
                startActivity(intent);
            }
        });

        tvForgetPasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tvLoginAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login_Customer.this, Activity_DangNhap_Admin.class);
                startActivity(intent);
            }
        });
    }

    boolean validateLoginInfo(String filename, String inputUsername, String inputPassword) throws IOException {
        InputStream inputStream = openFileInput(filename);
        int size = inputStream.available();
        byte[] data = new byte[size];
        inputStream.read(data);
        String kq = new String(data);
        inputStream.close();

        String[] dstaikhoan = kq.split("==");
        for (String s : dstaikhoan) {
            String[] thongTinTaiKhoan = s.split(";;");
            if (thongTinTaiKhoan.length >= 2) {
                String username = thongTinTaiKhoan[0].trim();
                String password = thongTinTaiKhoan[1].trim();
                if (username.equals(inputUsername) && password.equals(inputPassword)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateInputs(String username, String password) {
        // Kiểm tra username có hơn 8 ký tự
        if (username.trim().isEmpty() || username.trim().length() <= 8) {
            Toast.makeText(this, "Username phải có hơn 8 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra password có hơn 8 ký tự
        if (password.trim().isEmpty() || password.trim().length() <= 8) {
            Toast.makeText(this, "Password phải có hơn 8 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //---------------------------
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String fn = edtFullNameLogin.getText().toString();
        String ps = edtPasswordLogin.getText().toString();

        editor.putString("tên đầy đủ", fn);
        editor.putString("mật khẩu", ps);
        editor.apply();
    }

    void resetEdt() {
        edtFullNameLogin.setText("");
        edtPasswordLogin.setText("");
    }
}
