package com.example.laptrinhdidong_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Activity_Register extends AppCompatActivity {

    EditText edtUserReg, edtPassReg, edtPassComfReg, edtEmailReg, edtPhoneNumberReg;
    Button btnRegis;
    TextView tvHaveAcc, tvForgotPass;
    String fileName = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addControl();
        addEvent();
    }

    void writeFileTxt(String fileName, String st) throws IOException {
        OutputStream outputStream = openFileOutput(fileName, MODE_APPEND);
        outputStream.write(st.getBytes());
        outputStream.close();
    }

    void addControl() {
        edtUserReg = findViewById(R.id.edtUserReg);
        edtPassReg = findViewById(R.id.edtPassReg);
        edtPassComfReg = findViewById(R.id.edtPassComfReg);
        edtEmailReg = findViewById(R.id.edtEmailReg);
        edtPhoneNumberReg = findViewById(R.id.edtPhoneNumberReg);
        btnRegis = findViewById(R.id.btnRegis);
        tvHaveAcc = findViewById(R.id.tvHaveAcc);
        tvForgotPass = findViewById(R.id.tvForgotPass);
    }

    public boolean validateInputs(String username, String password, String confirmPassword, String email, String phoneNumber) {
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
        // Kiểm tra số điện thoại có đủ 10 số
        if (phoneNumber.trim().isEmpty() || phoneNumber.trim().length() != 10) {
            Toast.makeText(this, "Số điện thoại phải có đúng 10 số", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra email có đúng cấu trúc
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        if (email.trim().isEmpty() || !email.trim().matches(emailPattern)) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_LONG).show();
            return false;
        }
        // Kiểm tra password và confirmPassword giống nhau
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Vui lòng kiểm tra lại mật khẩu và xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public boolean readFileText(String fileName, String mail, int sdt) throws IOException {
        InputStream inputStream = openFileInput(fileName);
        int size = inputStream.available();
        byte[] data = new byte[size];
        inputStream.read(data);
        String kq = new String(data);
        inputStream.close();

        String[] taikhoan = kq.split("==");
        for (String s : taikhoan) {
            String[] thongTinTaiKhoan = s.split(";;");
            if (thongTinTaiKhoan.length == 4) {
                String existingMail = thongTinTaiKhoan[2];
                int existingSdt = Integer.parseInt(thongTinTaiKhoan[3]);

                // Kiểm tra nếu dữ liệu trùng khớp
                if (existingMail.equals(mail) || existingSdt == sdt) {
                    Toast.makeText(this, "Số điện thoại hoặc Email đã tồn tại!!!", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
        return false;
    }

    void addEvent() {
        tvHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Register.this, Activity_Login_Customer.class);
                startActivity(intent);
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String us = edtUserReg.getText().toString();
                String pass = edtPassReg.getText().toString();
                String comfpass = edtPassComfReg.getText().toString();
                String mail = edtEmailReg.getText().toString();
                int sdt = Integer.parseInt(edtPhoneNumberReg.getText().toString());
                if (!validateInputs(us, pass, comfpass, mail, String.valueOf(sdt))) {
                    return;
                }
                try {
                    boolean kq = readFileText(fileName, mail, sdt);
                    if (kq) {
                        return;
                    }
                    String newEntry = "==\r\n" + us + ";;" + pass + ";;" + mail + ";;" + sdt;
                    writeFileTxt(fileName, newEntry);
                    Toast.makeText(Activity_Register.this,
                            "Đăng ký thành công!!!", Toast.LENGTH_LONG).show();
                    resetEdt();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Activity_Register.this,
                            "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void resetEdt() {
        edtUserReg.setText("");
        edtPassReg.setText("");
        edtPassComfReg.setText("");
        edtEmailReg.setText("");
        edtPhoneNumberReg.setText("");
    }
}
