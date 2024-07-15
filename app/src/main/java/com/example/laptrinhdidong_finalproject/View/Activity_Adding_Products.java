package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductCategoriesHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Activity_Adding_Products extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Products";
    private static final String idProduct = "ProductID";
    private static final String idCategory = "CategoryID";
    private static final String nameProduct = "ProductName";
    private static final String descriptionProduct = "ProductDescription";
    private static final String priceProduct = "InitialPrice";
    private static final String productImage = "ProductImage";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    ImageView imgAddingProducts;
    Button btnAddingProductsImg, btnAddingProducts;
    EditText edtAddingIDProducts, edtAddingNameProducts, edtAddingPriceProducts, edtAddingDescriptionProducts;
    Spinner spinnerCategoryID;
    ListView lvProductForAdding;

    ArrayList<ProductCategories> productCategoriesArrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterSpinner;
    ProductCategoriesHandler productCategoriesHandler;
    ArrayList<Products> productsArrayList = new ArrayList<>();
    ProductsHandler productsHandler;
    CustomAdapter_ListView_Fragment_Product customAdapterListViewFragment_product;


    SQLiteDatabase sqLiteDatabase;
    String CateIDFromSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_products);
        //-------------------
        addControl();
        //-------------------
        productCategoriesHandler = new ProductCategoriesHandler(Activity_Adding_Products.this, DB_NAME, null, DB_VERSION);
        productCategoriesArrayList = productCategoriesHandler.loadAllDataOfProductCategories();
        productsHandler = new ProductsHandler(Activity_Adding_Products.this, DB_NAME, null, DB_VERSION);
        productsArrayList = productsHandler.loadAllDataOfProducts();
        ArrayList<String> dataSpinner = productCategoriesHandler.importSpinnerData(productCategoriesArrayList);
        arrayAdapterSpinner = new ArrayAdapter<>(Activity_Adding_Products.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dataSpinner);
        spinnerCategoryID.setAdapter(arrayAdapterSpinner);
        customAdapterListViewFragment_product = new CustomAdapter_ListView_Fragment_Product(Activity_Adding_Products.this, R.layout.layout_custom_adapter_lv_fragment_product, productsArrayList);
        lvProductForAdding.setAdapter(customAdapterListViewFragment_product);
        addEvent();
    }

    void addControl() {
        imgAddingProducts = (ImageView) findViewById(R.id.imgAddingProducts);
        btnAddingProducts = (Button) findViewById(R.id.btnAddingProducts);
        btnAddingProductsImg = (Button) findViewById(R.id.btnAddingProductsImg);
        edtAddingIDProducts = (EditText) findViewById(R.id.edtAddingIDProducts);
        edtAddingNameProducts = (EditText) findViewById(R.id.edtAddingNameProducts);
        edtAddingPriceProducts = (EditText) findViewById(R.id.edtAddingPriceProducts);
        edtAddingDescriptionProducts = (EditText) findViewById(R.id.edtAddingDescriptionProducts);
        spinnerCategoryID = (Spinner) findViewById(R.id.spinnerCategoryID);
        lvProductForAdding = (ListView) findViewById(R.id.lvProductForAdding);
    }

    void addEvent() {
        btnAddingProductsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        spinnerCategoryID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cateId = arrayAdapterSpinner.getItem(position);
                CateIDFromSpinner = cateId.split(" - ")[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddingProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idPro = edtAddingIDProducts.getText().toString();
                String idCate = CateIDFromSpinner;
                String namePro = edtAddingNameProducts.getText().toString();
                String desPro = edtAddingDescriptionProducts.getText().toString();
                Bitmap image = getBitmapFromImageView(imgAddingProducts);
                if (image == null) {
                    return; // Stop execution if image size is too large
                }
                Float pricePro = Float.parseFloat(edtAddingPriceProducts.getText().toString());

                if (idPro.isEmpty() || idCate.isEmpty() || namePro.isEmpty() || desPro.isEmpty()) {
                    Toast.makeText(Activity_Adding_Products.this, "Please fill in all the blanks before submitting!!!", Toast.LENGTH_LONG).show();
                } else {
                    boolean isExisting = false;
                    for (Products product : productsArrayList) {
                        if (product.getIdProduct().equals(idPro)) {
                            Toast.makeText(Activity_Adding_Products.this, "This ID Product has existed! Try another ID!!!", Toast.LENGTH_SHORT).show();
                            isExisting = true;
                            break;
                        }
                    }
                    if (!isExisting) {
                        Products pc = new Products(idPro, idCate, namePro, desPro, pricePro, getBytesFromBitmap(image));
                        productsHandler.insertProducts(pc);
                        Toast.makeText(Activity_Adding_Products.this, "Adding Success", Toast.LENGTH_SHORT).show();
                        // Cập nhật danh sách sản phẩm sau khi thêm thành công
                        productsArrayList.add(pc);
                        productsArrayList = productsHandler.loadAllDataOfProducts();
                        customAdapterListViewFragment_product = new CustomAdapter_ListView_Fragment_Product(Activity_Adding_Products.this, R.layout.layout_custom_adapter_lv_fragment_product, productsArrayList);
                        lvProductForAdding.setAdapter(customAdapterListViewFragment_product);
                        resetEdt();
                    }
                }
            }
        });
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
                imgAddingProducts.setImageBitmap(bitmap);

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
//        if (!Activity_Updating_Products.isImageSizeUnderLimit(bitmap, 100)) {
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

    void resetEdt() {
        edtAddingIDProducts.setText("");
        edtAddingNameProducts.setText("");
        edtAddingDescriptionProducts.setText("");
        edtAddingPriceProducts.setText("");
        imgAddingProducts.setImageResource(R.drawable.bobaimg);
    }
}