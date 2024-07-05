package com.example.laptrinhdidong_finalproject.Cotroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;
import com.example.laptrinhdidong_finalproject.View.Activity_Deleting_Products;
import com.example.laptrinhdidong_finalproject.View.Activity_UpdateProduct_Admin;
import com.example.laptrinhdidong_finalproject.View.CustomAdapterListViewFragment_Product;

import java.util.ArrayList;

public class Fragment_ProductManagement extends Fragment {

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

    Button btnInsertProduct, btnUpdateProduct, btnDeleteProduct;
    ListView lvProductManagement;

    ArrayList<Products> productArrayList = new ArrayList<>();

    CustomAdapterListViewFragment_Product customAdapter;

    com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler productHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_show_funtion_product_management, container, false);
        addControl(view);
        productHandler = new com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler(getContext(), DB_NAME, null, DB_VERSION);
        productArrayList = productHandler.loadAllDataOfProducts();
        customAdapter = new CustomAdapterListViewFragment_Product(getContext(),
                R.layout.layout_custom_adapter_lv_fragment_product, productArrayList);
        lvProductManagement.setAdapter(customAdapter);
        addEvent();
        return view;
    }

    void addControl(View view) {
        btnInsertProduct = view.findViewById(R.id.btnInsertProduct);
        btnUpdateProduct = view.findViewById(R.id.btnUpdateProduct);
        btnDeleteProduct = view.findViewById(R.id.btnDeleteProduct);
        lvProductManagement = view.findViewById(R.id.lvProductManagement);
    }

    void addEvent()
    {

        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Activity_UpdateProduct_Admin.class);
                startActivity(intent);
            }
        });

        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Activity_Deleting_Products.class);
                startActivity(intent);
            }
        });
    }
}
