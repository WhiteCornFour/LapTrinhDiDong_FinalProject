package com.example.laptrinhdidong_finalproject.Cotroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.Product;

import java.util.ArrayList;

public class ProductHandler extends SQLiteOpenHelper {

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

    public ProductHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    public ArrayList<Product> loadAllDataOfProducts() {
        ArrayList<Product> productArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Product p = new Product();
                p.setProductID(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_ID)));
                p.setCategoryID(cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY_ID)));
                p.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_NAME)));
                p.setProductDescription(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_DESCRIPTION)));

                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(PRODUCT_IMAGE));
                Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                p.setProductImage(image);

                p.setInitialPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(INITIAL_PRICE)));
                productArrayList.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return productArrayList;
    }

    @SuppressLint("Range")
    public Product returnResultForRearch(String productID) {
        Product product = null;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + PRODUCT_ID + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{productID});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                product = new Product();
                product.setProductID(cursor.getString(0));
                product.setCategoryID(cursor.getString(1));
                product.setProductName(cursor.getString(2));
                product.setProductDescription(cursor.getString(3));

                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(PRODUCT_IMAGE));
                Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                product.setProductImage(image);

                product.setInitialPrice(cursor.getDouble(5));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return product;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
