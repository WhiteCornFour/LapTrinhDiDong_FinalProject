package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class Activity_Detail_Profile_Cus extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    ImageView imgBackPro, imgCusPro;
    EditText edtNamePro, edtEmailPro, edtPhoneNumberPro, edtPassPro;
    Button btnRegis;

    Customer customer;
    CustomerHandler customerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile_cus);
        addControl();
        customerHandler = new CustomerHandler(Activity_Detail_Profile_Cus.this,
                DB_NAME, null, DB_VERSION);
        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("customer");

        Bitmap bitmap = BitmapFactory.decodeByteArray(customer.getCustomerImage(),
                0, customer.getCustomerImage().length);
        if (bitmap == null)
        {
            imgCusPro.setImageResource(R.drawable.avtadmin);
        }
        else {
            imgCusPro.setImageBitmap(bitmap);
        }
        edtNamePro.setText(customer.getNameCustomer());
        edtEmailPro.setText(customer.getEmailCustomer());
        edtPhoneNumberPro.setText(customer.getPhoneCustomer());
        edtPassPro.setText(customer.getPasswordCustomer());


        addEvent();
    }

    void addControl()
    {
        imgBackPro = findViewById(R.id.imgBackPro);
        imgCusPro = findViewById(R.id.imgCusPro);
        edtNamePro = findViewById(R.id.edtNamePro);
        edtEmailPro = findViewById(R.id.edtEmailPro);
        edtPhoneNumberPro = findViewById(R.id.edtPhoneNumberPro);
        edtPassPro = findViewById(R.id.edtPassPro);
        btnRegis = findViewById(R.id.btnRegisPro);
    }

    void addEvent()
    {
        imgBackPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToHomeFragment();
            }
        });
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = customer.getIdCustomer();
                String name = edtNamePro.getText().toString();
                String email = edtEmailPro.getText().toString();
                String phone = edtPhoneNumberPro.getText().toString();
                String pass = edtPassPro.getText().toString();
                Bitmap image = getBitmapFromImageView(imgCusPro);
                if (image == null) {
                    Toast.makeText(Activity_Detail_Profile_Cus.this, "Please select an image.", Toast.LENGTH_SHORT).show();
                    return; // Stop execution if image is null
                }
                if (validateInputs(name, pass, email)) {
                    Customer c = new Customer(id, name, email, phone, pass, getBytesFromBitmap(image));
                    if (c.equals(customer)) {
                        return;
                    } else {
                        customerHandler.updateRecord(c);
                        Toast.makeText(Activity_Detail_Profile_Cus.this, "Update successful!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imgCusPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }
    private void returnToHomeFragment() {
        Intent intent = new Intent(Activity_Detail_Profile_Cus.this,
                MainActivity.class);
        intent.putExtra("idItem", "Profile");
        startActivity(intent);
        finish();
    }
    public Bitmap getBitmapFromImageView(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

//        if (!isImageSizeUnderLimit(bitmap, 100)) {
//            Toast.makeText(this, "Image size is too large! It should be less than 100KB.", Toast.LENGTH_SHORT).show();
//            return null;
//        }

        return bitmap;
    }
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Get the image from the gallery
            Uri selectedImage = data.getData();
            try {
                // Decode the image and display it in the ImageView
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                imgCusPro.setImageBitmap(bitmap);

                // Optional: Convert the bitmap to byte array if you need to store it in the database
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                // You can now use imageBytes to save the image in the database
                // For example, you can set it to the product object
                // products.setImageProduct(imageBytes);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean validateInputs(String name, String password, String email) {
        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Input Your Name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra password có hơn 8 ký tự
        if (password.trim().isEmpty() || password.trim().length() <= 8) {
            Toast.makeText(this, "Password must have at least 8 letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra email có đúng cấu trúc
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        if (email.trim().isEmpty() || !email.trim().matches(emailPattern)) {
            Toast.makeText(this, "Incorrect Email!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}