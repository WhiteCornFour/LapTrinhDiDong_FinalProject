package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Activity_Register extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Customers";
    private static final String idCustomer = "CustomerID";
    private static final String nameCustomer = "CustomerName";
    private static final String emailCustomer = "CustomerEmail";
    private static final String phoneCustomer = "PhoneNumber";
    private static final String passwordCustomer = "LoginPassword";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    EditText edtNameReg, edtPassReg, edtPassComfReg, edtEmailReg, edtPhoneNumberReg;
    Button btnRegis;
    TextView tvHaveAcc, tvForgotPass;

    CustomerHandler customerHandler;

    SQLiteDatabase sqLiteDatabase;
    ArrayList<Customer> customerArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControl();
        customerHandler = new CustomerHandler(Activity_Register.this, DB_NAME, null, DB_VERSION);
        //customerHandler.onCreate(sqLiteDatabase);
        customerArrayList = customerHandler.loadAllDataOfCustomer();
        //Log.d("customerArrayList", String.valueOf(customerArrayList.size()));
        addEvent();
    }

    void addControl() {
        edtNameReg = (EditText) findViewById(R.id.edtNameReg);
        edtPassReg = (EditText) findViewById(R.id.edtPassReg);
        edtPassComfReg = (EditText) findViewById(R.id.edtPassComfReg);
        edtEmailReg = (EditText) findViewById(R.id.edtEmailReg);
        edtPhoneNumberReg = (EditText) findViewById(R.id.edtPhoneNumberReg);
        btnRegis = (Button) findViewById(R.id.btnRegis);
        tvHaveAcc = (TextView) findViewById(R.id.tvHaveAcc);
        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
    }

    void addEvent() {
        tvHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Register.this, Activity_Login_Customer.class);
                startActivity(intent);
                finish();
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Register.this, Activity_ForgotPassword_Customer.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = generateMKH();
                String name = edtNameReg.getText().toString();
                String mail = edtEmailReg.getText().toString();
                String sdt = edtPhoneNumberReg.getText().toString();
                String pass = edtPassReg.getText().toString();
                String comfpass = edtPassComfReg.getText().toString();
                Boolean kq = validateInputs(name, pass, comfpass, mail, sdt);

                if (kq.equals(true))
                {
                    for (int i  = 0; i < customerArrayList.size(); i++)
                    {
                        if(customerArrayList.get(i).getPhoneCustomer().equals(sdt) ||
                                customerArrayList.get(i).getEmailCustomer().equals(mail))
                        {
                            Toast.makeText(Activity_Register.this, "This phone number or email is already in use!!!",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        }else
                        {
                            Customer c = new Customer(id , name, mail, sdt, pass);
                            customerHandler.insertRecordIntoCustomerTable(c);
                            resetEdt();
                            Toast.makeText(Activity_Register.this, "Registered successfully!!!", Toast.LENGTH_LONG).show();
                        }
                }


            }
        });
    }
    void resetEdt() {
        edtNameReg.requestFocus();
        edtNameReg.setText("");
        edtPassReg.setText("");
        edtPassComfReg.setText("");
        edtEmailReg.setText("");
        edtPhoneNumberReg.setText("");
    }
    public static String generateMKH() {
        // Lấy ngày và giờ hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Định dạng ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        // Tạo số ngẫu nhiên từ 1000 đến 9999
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        // Kết hợp ngày giờ và số ngẫu nhiên để tạo mã MKH
        String mkh = formattedDateTime + randomNumber;

        return mkh;
    }
    public boolean validateInputs(String name, String password, String confirmPassword, String email, String phoneNumber) {

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Input Your Name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra password có hơn 8 ký tự
        if (password.trim().isEmpty() || password.trim().length() <= 8) {
            Toast.makeText(this, "Password must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra số điện thoại có đủ 10 số
        if (phoneNumber.trim().isEmpty() || phoneNumber.trim().length() != 10) {
            Toast.makeText(this, "Phone number must have 10 number", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra email có đúng cấu trúc
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        if (email.trim().isEmpty() || !email.trim().matches(emailPattern)) {
            Toast.makeText(this, "Incorrect Email !", Toast.LENGTH_LONG).show();
            return false;
        }
        // Kiểm tra password và confirmPassword giống nhau
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Please checking your password and confirm password is correct", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
