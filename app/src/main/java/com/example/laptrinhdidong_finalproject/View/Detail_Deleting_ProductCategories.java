package com.example.laptrinhdidong_finalproject.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductCategoriesHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class Detail_Deleting_ProductCategories extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Products";
    private static final String idProduct = "ProductID";
    private static final String idCategory = "CategoryID";
    private static final String nameProduct = "ProductName";
    private static final String descriptionProduct = "ProductDescription";
    private static final Float priceProduct = 0.0f;

    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    ImageView imgDetailPC;
    TextView tvDetailCateName, tvDetailCateID, tvDetailCateDescription;
    Button btnDeletePC, btnCheckAll, btnClearAll, btnDeleteProduct;
    ListView lvDetailProductDelete;

    ArrayList<Products> productsArrayList = new ArrayList<>();
    CustomAdapter_ListView_Products adapterListViewProducts;
    ProductsHandler productsHandler;
    SQLiteDatabase sqLiteDatabase;
    boolean[] checkedStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deleting_product_categories);
        //----------------------
        addControl();
        intentFromDeleteToDetailDeleteProductCate();
        setupProductListView();
        //----------------------

        addEvent();
    }

    void addControl() {
        imgDetailPC = (ImageView) findViewById(R.id.imgDetailPC);
        tvDetailCateName = (TextView) findViewById(R.id.tvDetailCateName);
        tvDetailCateID = (TextView) findViewById(R.id.tvDetailCateID);
        tvDetailCateDescription = (TextView) findViewById(R.id.tvDetailCateDescription);
        btnDeletePC = (Button) findViewById(R.id.btnDeletePC);
        btnDeleteProduct = (Button) findViewById(R.id.btnDeleteProduct);
        btnCheckAll = (Button) findViewById(R.id.btnCheckAll);
        btnClearAll = (Button) findViewById(R.id.btnClearAll);
        lvDetailProductDelete = (ListView) findViewById(R.id.lvDetailProductDelete);
    }

    void intentFromDeleteToDetailDeleteProductCate() {
        Intent intent = getIntent();
        ProductCategories pc = (ProductCategories) intent.getExtras().getSerializable("pc");

        // Chuyển đổi byte[] thành Bitmap
        byte[] imageBytes = pc.getImageCategory();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        // Thiết lập hình ảnh vào ImageView
        imgDetailPC.setImageBitmap(bitmap);
        tvDetailCateName.setText(pc.getNameCategory());
        tvDetailCateID.setText(pc.getIdCategory());
//        tvDetailCateDescription.setText(pc.getDescriptionCategory());

    }

    private void setupProductListView() {
        Intent intent = getIntent();
        ProductCategories pc = (ProductCategories) intent.getExtras().getSerializable("pc");
        String categoryId = pc.getIdCategory();

        productsHandler = new ProductsHandler(Detail_Deleting_ProductCategories.this, DB_NAME, null, DB_VERSION);
        productsArrayList = productsHandler.loadProductsByCategory(categoryId);

        checkedStates = new boolean[productsArrayList.size()];
        adapterListViewProducts = new CustomAdapter_ListView_Products(Detail_Deleting_ProductCategories.this,
                R.layout.layout_custom_adapter_listview_products, productsArrayList, checkedStates);
        lvDetailProductDelete.setAdapter(adapterListViewProducts);

        updateButtonStates();
    }

    void addEvent() {
        btnDeletePC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = createAlertDialogDeleteProductsCate();
                alertDialog.show();
            }
        });

        btnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllProducts(true);
                Toast.makeText(Detail_Deleting_ProductCategories.this, "All products checked", Toast.LENGTH_SHORT).show();
            }
        });

        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllProducts(false);
                Toast.makeText(Detail_Deleting_ProductCategories.this, "All products unchecked", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = createAlertDialogDeleteProducts();
                alertDialog.show();
                Toast.makeText(Detail_Deleting_ProductCategories.this, "Delete products selected", Toast.LENGTH_SHORT).show();
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
        checkedStates = new boolean[productsArrayList.size()];
        adapterListViewProducts.notifyDataSetChanged();
        updateButtonStates();
        refreshActivity();
    }


    AlertDialog createAlertDialogDeleteProductsCate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Detail_Deleting_ProductCategories.this);
        builder.setTitle("Delete Category");

        if (!productsArrayList.isEmpty()) {
            builder.setMessage("Cannot delete category because there are products in it.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        } else {
            builder.setMessage("Are you sure to delete this category?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String cateID = tvDetailCateID.getText().toString();
                    ProductCategoriesHandler productCategoriesHandler = new ProductCategoriesHandler(Detail_Deleting_ProductCategories.this, DB_NAME, null, DB_VERSION);
                    productCategoriesHandler.deleteProductCarte(cateID);
                    Toast.makeText(Detail_Deleting_ProductCategories.this, "Category deleted", Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
        }

        return builder.create();
    }


    AlertDialog createAlertDialogDeleteProducts() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Detail_Deleting_ProductCategories.this);
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

    private void updateButtonStates() {
        if (productsArrayList.isEmpty()) {
            btnCheckAll.setEnabled(false);
            btnCheckAll.setTextColor(getResources().getColor(R.color.disabled_color));
            btnClearAll.setEnabled(false);
            btnClearAll.setTextColor(getResources().getColor(R.color.disabled_color));
            btnDeleteProduct.setEnabled(false);
            btnDeleteProduct.setTextColor(getResources().getColor(R.color.disabled_color));
        } else {
            btnCheckAll.setEnabled(true);
//            btnCheckAll.setTextColor(getResources().getColor(android.R.color.black));
            btnClearAll.setEnabled(true);
//            btnClearAll.setTextColor(getResources().getColor(android.R.color.black));
            btnDeleteProduct.setEnabled(true);
//            btnDeleteProduct.setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    private void refreshActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


}