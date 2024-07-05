package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import android.content.Intent;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Activity_UpdateProduct_Admin extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Products";
    private static final String PRODUCT_ID = "ProductID";
    private static final String CATEGORY_ID = "CategoryID";
    private static final String PRODUCT_NAME = "ProductName";
    private static final String PRODUCT_DESCRIPTION = "ProductDescription";
    private static final String PRODUCT_IMAGE = "ProductImage";
    private static final String INITIAL_PRICE = "InitialPrice";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    ProductsHandler productHandler;
    EditText edtProductID;
    EditText edtCategoryID;
    EditText edtProductName;
    EditText edtProductDescription;
    EditText edtInitialPrice;
    EditText edtSearchForUpdate;
    Button btnImportImageUpdateProduct, btnSearchForUpate, btnUpdateProductAD;
    ImageView imgUpdateProduct;
    ListView lvProductForUpdate;

    ArrayList<Products> productArrayList = new ArrayList<>();
    ArrayList<Products> productArrayListResult = new ArrayList<>();
    CustomAdapterListViewFragment_Product customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_admin);
        addControl();
        productHandler = new ProductsHandler(Activity_UpdateProduct_Admin.this, DB_NAME
        ,null, DB_VERSION);
        productArrayList = productHandler.loadAllDataOfProducts();
        loadDataLV(productArrayList);

        addEvent();
    }

    void loadDataLV(ArrayList<Products> productArrayList)
    {
        customAdapter = new CustomAdapterListViewFragment_Product(Activity_UpdateProduct_Admin.this,
                R.layout.layout_custom_adapter_lv_fragment_product, productArrayList);
        lvProductForUpdate.setAdapter(customAdapter);
    }
    void addControl()
    {
        edtProductID = (EditText) findViewById(R.id.edtProductID);
        edtCategoryID = (EditText) findViewById(R.id.edtCategoryID);
        edtProductName = (EditText) findViewById(R.id.edtProductName);
        edtProductDescription = (EditText) findViewById(R.id.edtProductDescription);
        edtInitialPrice = (EditText) findViewById(R.id.edtInitialPrice);
        edtSearchForUpdate = (EditText) findViewById(R.id.edtSearchForUpdate);
        btnImportImageUpdateProduct = (Button) findViewById(R.id.btnImportImageUpdateProduct);
        btnSearchForUpate = (Button) findViewById(R.id. btnSearchForUpate);
        btnUpdateProductAD = (Button) findViewById(R.id.btnUpdateProductAD);
        imgUpdateProduct = (ImageView) findViewById(R.id.imgUpdateProduct);
        lvProductForUpdate = (ListView) findViewById(R.id.lvProductForUpdate);
    }
    void addEvent()
    {
        btnSearchForUpate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchID = edtSearchForUpdate.getText().toString();
                productArrayListResult.clear();
                Products productResult = productHandler.returnResultForSearch(searchID);
                if (productResult == null)
                {
                    loadDataLV(productArrayList);
                    return;
                }
                productArrayListResult.add(productResult);
                loadDataLV(productArrayListResult);
            }
        });

        lvProductForUpdate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Products products = productArrayList.get(i);
                edtProductID.setText(products.getIdProduct());
                edtCategoryID.setText(products.getIdCategory());
                edtProductName.setText(products.getNameProduct());
                edtProductDescription.setText(products.getDescriptionProduct());
                edtInitialPrice.setText(String.valueOf(products.getInitialPrice()));
                byte[] imageBytes = products.getImageProduct();
                if (imageBytes != null && imageBytes.length > 0) {
                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imgUpdateProduct.setImageBitmap(imageBitmap);
                } else {
                    imgUpdateProduct.setImageResource(R.drawable.avtadmin);
                }
            }
        });

        btnImportImageUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        btnUpdateProductAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtProductID.getText().toString();
                String idCate = edtCategoryID.getText().toString();
                String name = edtProductName.getText().toString();
                String descrip = edtProductDescription.getText().toString();
                Bitmap image = getBitmapFromImageView(imgUpdateProduct);
                Float price = edtInitialPrice.getAlpha();
                if (id.isEmpty() || idCate.isEmpty() || name.isEmpty() || descrip.isEmpty())
                {
                    Toast.makeText(Activity_UpdateProduct_Admin.this, "Please select a product!", Toast.LENGTH_SHORT).show();
                }else
                {
                    int flag = 0;
                    for (int i  = 0; i < productArrayList.size(); i++) {
                        if (productArrayList.get(i).getIdProduct().equals(id)) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0)
                    {
                        Toast.makeText(Activity_UpdateProduct_Admin.this, "Data does not exist!!!",
                                Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Products p = new Products(id, idCate, name, descrip, price,getBytesFromBitmap(image));
                        productHandler.updateRecord(p);
                        productArrayList = productHandler.loadAllDataOfProducts();
                        customAdapter = new CustomAdapterListViewFragment_Product(Activity_UpdateProduct_Admin.this,
                                R.layout.layout_custom_adapter_lv_fragment_product, productArrayList);
                        lvProductForUpdate.setAdapter(customAdapter);
                    }
                }
            }
        });
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
                imgUpdateProduct.setImageBitmap(bitmap);

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

}