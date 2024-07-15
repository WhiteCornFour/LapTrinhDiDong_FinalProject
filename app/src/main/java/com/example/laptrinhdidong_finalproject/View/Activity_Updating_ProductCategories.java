package com.example.laptrinhdidong_finalproject.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductCategoriesHandler;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

    public class Activity_Updating_ProductCategories extends AppCompatActivity {
        private static final String DB_NAME = "drinkingmanager";
        private static final int DB_VERSION = 1;
        private static final int PICK_IMAGE_REQUEST = 1;

        private static final String TABLE_NAME = "ProductCategories";
        private static final String categoryID = "CategoryID";
        private static final String categoryName = "CategoryName";
        private static final String categoryDescription = "CategoryDescription";
        private static final String categoryImage = "CategoryImage";
        private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

        ListView lv_product_types;
        EditText edt_category_id, edt_category_name, edt_category_description;
        ImageView img_category;
        Button btn_save_category, btn_select_image;
        CustomAdapter_ListView_Updating_ProductCategories adapter;
        ArrayList<ProductCategories> categoryList;
        ProductCategoriesHandler productCategoriesHandler;
        SQLiteDatabase sqLiteDatabase;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_product_type_admin);

            addControl();
            addEvent();
            productCategoriesHandler = new ProductCategoriesHandler(Activity_Updating_ProductCategories.this,
                    DB_NAME, null, DB_VERSION);
            categoryList = productCategoriesHandler.loadAllDataOfProductCategories();
            productCategoriesHandler.onCreate(sqLiteDatabase);

            adapter = new CustomAdapter_ListView_Updating_ProductCategories(this, categoryList);
            lv_product_types.setAdapter(adapter);
        }

        void addControl() {
            lv_product_types = findViewById(R.id.lv_product_types);
            edt_category_id = findViewById(R.id.edt_category_id);
            edt_category_name = findViewById(R.id.edt_category_name);
            edt_category_description = findViewById(R.id.edt_category_description);
            img_category = findViewById(R.id.img_category);
            btn_save_category = findViewById(R.id.btn_save_category);
            btn_select_image = findViewById(R.id.btn_select_image);
        }

        void addEvent() {
            lv_product_types.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProductCategories selectedCategory = categoryList.get(position);
                    edt_category_id.setText(selectedCategory.getIdCategory());
                    edt_category_name.setText(selectedCategory.getNameCategory());
                    edt_category_description.setText(selectedCategory.getDescriptionCategory());

                    // Chuyển đổi byte[] thành Bitmap và hiển thị
                    byte[] imageBlob = selectedCategory.getImageCategory();
                    if (imageBlob != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
                        img_category.setImageBitmap(bitmap);
                    } else {
                        img_category.setImageResource(R.drawable.hinh_anh_dang_nhap);
                    }
                }
            });

            btn_save_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String categoryID = edt_category_id.getText().toString().trim();
                    String categoryName = edt_category_name.getText().toString().trim();
                    String categoryDescription = edt_category_description.getText().toString().trim();
                    byte[] categoryImage = imageViewToByteArray(img_category);

                    if (categoryID.isEmpty() || categoryName.isEmpty() || categoryDescription.isEmpty() || categoryImage == null) {
                        Toast.makeText(Activity_Updating_ProductCategories.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean kq = false;
                        for (ProductCategories category : categoryList) {
                            if (category.getIdCategory().equals(categoryID)) {
                                kq = true;
                                break;
                            }
                        }
                        if (!kq) {
                            Toast.makeText(Activity_Updating_ProductCategories.this, "Category not found", Toast.LENGTH_SHORT).show();
                        } else {
                            ProductCategories updatedCategory = new ProductCategories(categoryID, categoryName, categoryDescription, categoryImage);
                            boolean updated = productCategoriesHandler.updateProductCategor(updatedCategory);
                            if (updated) {
                                Toast.makeText(Activity_Updating_ProductCategories.this, "Update successful", Toast.LENGTH_SHORT).show();
                                categoryList = productCategoriesHandler.loadAllDataOfProductCategories();
                                adapter = new CustomAdapter_ListView_Updating_ProductCategories(Activity_Updating_ProductCategories.this, categoryList);
                                lv_product_types.setAdapter(adapter);
                            } else {
                                Toast.makeText(Activity_Updating_ProductCategories.this, "Update failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
            btn_select_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImageChooser();
                }
            });
        }

        private void openImageChooser() {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    img_category.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


            // Chuyển đổi ImageView thành mảng byte[]
        private byte[] imageViewToByteArray(ImageView imageView) {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
    }
