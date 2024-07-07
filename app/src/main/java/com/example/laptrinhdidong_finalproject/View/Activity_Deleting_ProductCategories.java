package com.example.laptrinhdidong_finalproject.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductCategoriesHandler;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class Activity_Deleting_ProductCategories extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "ProductCategories";
    private static final String idCategory = "CategoryID";
    private static final String nameCategory = "CategoryName";
    private static final String descriptionCategory = "CategoryDescription";
    private static final String imageCategory = "CategoryImage";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    ListView lvProductCate;
    ArrayList<ProductCategories> productCategoriesArrayList = new ArrayList<>();
    CustomAdapter_ListView_Deleting_ProductCategories adapterListViewProductCate;
    ProductCategoriesHandler productCategoriesHandler;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleting_product_cartegories);
        //---------------------
        addControl();
        //---------------------
        productCategoriesHandler = new ProductCategoriesHandler(Activity_Deleting_ProductCategories.this, DB_NAME, null, DB_VERSION);
        loadProductCategories();
        //---------------------
        addEvent();
    }

    void addControl() {
        lvProductCate = (ListView) findViewById(R.id.lvProductCate);
    }

    void addEvent() {
        lvProductCate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductCategories pc = productCategoriesArrayList.get(position);
                Intent intent = new Intent(Activity_Deleting_ProductCategories.this, Activity_Detail_Deleting_ProductCategories.class);
                intent.putExtra("pc", pc);
                startActivityForResult(intent, 1);
            }
        });
    }


    private void loadProductCategories() {
        productCategoriesArrayList = productCategoriesHandler.loadAllDataOfProductCategories();
        adapterListViewProductCate = new CustomAdapter_ListView_Deleting_ProductCategories(this, R.layout.layout_custom_adapter_listview_prouductcategories, productCategoriesArrayList);
        lvProductCate.setAdapter(adapterListViewProductCate);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Load lại danh sách khi quay lại từ Detail_Deleting_ProductCategories
            loadProductCategories();
        }
    }
}