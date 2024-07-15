package com.example.laptrinhdidong_finalproject.View;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class Activity_Deleting_Products extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "ProductCategories";
    private static final String idCategory = "CategoryID";
    private static final String nameCategory = "CategoryName";
    private static final String descriptionCategory = "CategoryDescription";
    private static final String imageCategory = "CategoryImage";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    EditText edtSearchDeleteBar;
    ListView lvProducts;
    Button btnDeleteProducts, btnClearAllProducts, btnCheckAllProducts, btnSearchForDeleteProduct;
    FrameLayout frameLayoutDeleteDetail;

    ArrayList<Products> productsArrayList = new ArrayList<>();
    ArrayList<Products> productsArrayListResult = new ArrayList<>();
    CustomAdapter_ListView_Deleting_Products adapterListViewProducts;
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
        btnSearchForDeleteProduct = findViewById(R.id.btnSearchForDeleteProduct);
        edtSearchDeleteBar = findViewById(R.id.edtSearchDeleteBar);
        frameLayoutDeleteDetail = findViewById(R.id.frameLayoutDeleteDetail);
    }

    void setupProductsListView() {
        productsHandler = new ProductsHandler(Activity_Deleting_Products.this, DB_NAME, null, DB_VERSION);
        productsArrayList = productsHandler.loadAllDataOfProducts();

        checkedStates = new boolean[productsArrayList.size()];

        loadDataLV(productsArrayList);
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

        btnSearchForDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchID = edtSearchDeleteBar.getText().toString();
                productsArrayListResult.clear();
                Products productResult = productsHandler.returnResultForSearch(searchID);
                if (productResult == null) {
                    loadDataLV(productsArrayList);
                    return;
                }
                productsArrayListResult.add(productResult);
                loadDataLV(productsArrayListResult);
            }
        });

        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Products selectedProduct = productsArrayList.get(position);
                openDeleteProductDialog(Gravity.CENTER, selectedProduct);
            }
        });

    }

    private void openDeleteProductDialog(int gravity, Products product) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_detail_deleting_products);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();

        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText edtDeletingProductID, edtDeletingProductName, edtDeletingProductDescription;
        ImageView imgDeletingProduct;
        Button btnConfirmDeleteProduct, btnCancelDeleting;

        edtDeletingProductID = dialog.findViewById(R.id.edtDeletingProductID);
        edtDeletingProductName = dialog.findViewById(R.id.edtDeletingProductName);
        edtDeletingProductDescription = dialog.findViewById(R.id.edtDeletingProductDescription);
        imgDeletingProduct = dialog.findViewById(R.id.imgDeletingProduct);
        btnCancelDeleting = dialog.findViewById(R.id.btnCancelDeleting);
        btnConfirmDeleteProduct = dialog.findViewById(R.id.btnConfirmDeleteProduct);

        edtDeletingProductID.setText(product.getIdProduct());
        edtDeletingProductName.setText(product.getNameProduct());
        edtDeletingProductDescription.setText(product.getDescriptionProduct());

        // Convert byte array to Bitmap
        byte[] imageBytes = product.getImageProduct();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imgDeletingProduct.setImageBitmap(bitmap);

        btnCancelDeleting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirmDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogDeleteAProduct(product).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void loadDataLV(ArrayList<Products> productsArrayList) {
        adapterListViewProducts = new CustomAdapter_ListView_Deleting_Products(Activity_Deleting_Products.this,
                R.layout.layout_custom_adapter_listview_products, productsArrayList, checkedStates);
        lvProducts.setAdapter(adapterListViewProducts);
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
        Toast.makeText(Activity_Deleting_Products.this, "Deleted selected products !!!", Toast.LENGTH_SHORT).show();
        setupProductsListView();
    }

    private AlertDialog createAlertDialogDeleteProducts() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Deleting_Products.this);
        builder.setTitle("Delete Products");
        builder.setMessage("Are you sure to delete the selected products?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteSelectedProducts();
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

    private AlertDialog createAlertDialogDeleteAProduct(Products product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Deleting_Products.this);
        builder.setTitle("Delete Product");
        builder.setMessage("Are you sure to delete this product?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                productsHandler.deleteProducts(product);
                setupProductsListView();
                Toast.makeText(Activity_Deleting_Products.this, "Product deleted !!!", Toast.LENGTH_SHORT).show();
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
