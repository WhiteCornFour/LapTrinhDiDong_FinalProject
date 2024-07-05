package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductHandler;
import com.example.laptrinhdidong_finalproject.Model.Product;
import com.example.laptrinhdidong_finalproject.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

    ProductHandler productHandler;
    EditText edtProductID, edtCategoryID, edtProductName,
            edtProductDescription, edtInitialPrice, edtSearchForUpdate;
    Button btnImportImageUpdateProduct, btnSearchForUpate, btnUpdateProductAD;
    ImageView imgUpdateProduct;
    ListView lvProductForUpdate;

    ArrayList<Product> productArrayList = new ArrayList<>();
    ArrayList<Product> productArrayListResult = new ArrayList<>();
    CustomAdapterListViewFragment_Product customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_admin);
        addControl();
        productHandler = new ProductHandler(Activity_UpdateProduct_Admin.this, DB_NAME
        ,null, DB_VERSION);
        productArrayList = productHandler.loadAllDataOfProducts();
        loadDataLV(productArrayList);

        addEvent();
    }

    void loadDataLV(ArrayList<Product> productArrayList)
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
                if(searchID.trim().isEmpty())
                {
                    Toast.makeText(Activity_UpdateProduct_Admin.this, "Please enter product code!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Product productResult = productHandler.returnResultForRearch(searchID);
                if (productResult == null)
                {
                    Toast.makeText(Activity_UpdateProduct_Admin.this, "Product ID does not exist!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                productArrayListResult.add(productResult);
                loadDataLV(productArrayListResult);
            }
        });
    }
}