package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class Activity_Deleting_Products extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    ListView lvProducts;
    Button btnDeleteProducts, btnClearAllProducts, btnCheckAllProducts;

    ArrayList<Products> productsArrayList = new ArrayList<>();
    CustomAdapter_ListView_Products adapterListViewProducts;
    ProductsHandler productsHandler;
    SQLiteDatabase sqLiteDatabase;

    boolean[] checkedStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleting_products);

        addControl();
        setupProductsListView();
        addEvent();
    }

    void addControl() {
        lvProducts = findViewById(R.id.lvProducts);
        btnDeleteProducts = findViewById(R.id.btnDeleteProducts);
        btnClearAllProducts = findViewById(R.id.btnClearAllProducts);
        btnCheckAllProducts = findViewById(R.id.btnCheckAllProducts);
    }

    void setupProductsListView() {
        productsHandler = new ProductsHandler(Activity_Deleting_Products.this, DB_NAME, null, DB_VERSION);
        productsArrayList = productsHandler.loadAllDataOfProducts();

        checkedStates = new boolean[productsArrayList.size()];

        adapterListViewProducts = new CustomAdapter_ListView_Products(Activity_Deleting_Products.this,
                R.layout.layout_custom_adapter_listview_products, productsArrayList, checkedStates);
        lvProducts.setAdapter(adapterListViewProducts);
        updateButtonStates();
    }

    void addEvent() {
        btnDeleteProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = createAlertDialogDeleteProducts();
                alertDialog.show();
            }
        });

        btnClearAllProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllProducts(false);
                Toast.makeText(Activity_Deleting_Products.this, "All products unchecked", Toast.LENGTH_SHORT).show();
            }
        });

        btnCheckAllProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllProducts(true);
                Toast.makeText(Activity_Deleting_Products.this, "All products checked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAllProducts(boolean check) {
        if (checkedStates != null) {
            for (int i = 0; i < checkedStates.length; i++) {
                checkedStates[i] = check;
            }
            adapterListViewProducts.notifyDataSetChanged();
        }
    }

    private void deleteSelectedProducts() {
        ArrayList<Products> productsToDelete = new ArrayList<>();
        for (int i = 0; i < checkedStates.length; i++) {
            if (checkedStates[i]) {
                productsToDelete.add(productsArrayList.get(i));
            }
        }
        for (Products product : productsToDelete) {
            productsHandler.deleteProducts(product);
            productsArrayList.remove(product);
        }
        adapterListViewProducts.notifyDataSetChanged();
        Toast.makeText(Activity_Deleting_Products.this, "Deleted selected products", Toast.LENGTH_SHORT).show();
        updateButtonStates();
    }

    private AlertDialog createAlertDialogDeleteProducts() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Deleting_Products.this);
        builder.setTitle("Delete Products");
        builder.setMessage("Are you sure to delete the selected products?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteSelectedProducts();
                setupProductsListView();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }


    private void updateButtonStates() {
        if (productsArrayList.isEmpty()) {
            btnCheckAllProducts.setEnabled(false);
            btnCheckAllProducts.setTextColor(getResources().getColor(R.color.disabled_color));
            btnClearAllProducts.setEnabled(false);
            btnClearAllProducts.setTextColor(getResources().getColor(R.color.disabled_color));
            btnDeleteProducts.setEnabled(false);
            btnDeleteProducts.setTextColor(getResources().getColor(R.color.disabled_color));
        } else {
            btnCheckAllProducts.setEnabled(true);
//            btnCheckAll.setTextColor(getResources().getColor(android.R.color.black));
            btnClearAllProducts.setEnabled(true);
//            btnClearAll.setTextColor(getResources().getColor(android.R.color.black));
            btnDeleteProducts.setEnabled(true);
//            btnDeleteProduct.setTextColor(getResources().getColor(android.R.color.black));
        }
    }
}
