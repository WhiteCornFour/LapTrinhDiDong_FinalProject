package com.example.laptrinhdidong_finalproject.Cotroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Products";
    private static final String idProduct = "ProductID";
    private static final String idCategory = "CategoryID";
    private static final String nameProduct = "ProductName";
    private static final String descriptionProduct = "ProductDescription";
    private static final String productImage = "ProductImage";
    private static final String priceProduct = "InitialPrice";

    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public ProductsHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public ArrayList<Products> loadAllDataOfProducts()
    {
        ArrayList<Products> productsArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Products p = new Products();
                    p.setIdProduct(cursor.getString(0));
                    p.setIdCategory(cursor.getString(1));
                    p.setNameProduct(cursor.getString(2));
                    p.setDescriptionProduct(cursor.getString(3));
                    p.setImageProduct(cursor.getBlob(4));
                    p.setInitialPrice(cursor.getFloat(5));
                    productsArrayList.add(p);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return productsArrayList;
    }
    public static Map<String, Products> getProductInfoMap() {
        Map<String, Products> productInfoMap = new HashMap<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT ProductID, ProductName, ProductImage FROM Products";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String productID = cursor.getString(cursor.getColumnIndexOrThrow("ProductID"));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("ProductName"));
                byte[] productImage = cursor.getBlob(cursor.getColumnIndexOrThrow("ProductImage"));
                Products productInfo = new Products(productName, productImage);
                productInfoMap.put(productID, productInfo);
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return productInfoMap;
    }

    public void updateRecord(Products p) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        // Delete existing record based on ProductID
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + idProduct + " = ?";
        sqLiteDatabase.execSQL(deleteSQL, new Object[]{p.getIdProduct()});

        // Insert new record
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (" +
                idProduct + ", " + idCategory + ", " + nameProduct + ", " +
                descriptionProduct + ", " + productImage + ", " + priceProduct + ") VALUES (?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(insertSQL, new Object[]{p.getIdProduct(), p.getIdCategory(), p.getNameProduct(),
                p.getDescriptionProduct(), p.getImageProduct(), p.getInitialPrice()});
        sqLiteDatabase.close();
    }

    public ArrayList<Products> loadProductsByCategory(String categoryId) {
        ArrayList<Products> productsArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("  SELECT *  FROM " + TABLE_NAME + " WHERE CategoryID= '" + categoryId + "'", null);
        cursor.moveToFirst();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Products p = new Products();
                    p.setIdProduct(cursor.getString(0));
                    p.setIdCategory(cursor.getString(1));
                    p.setNameProduct(cursor.getString(2));
                    p.setDescriptionProduct(cursor.getString(3));
                    p.setImageProduct(cursor.getBlob(4));
                    p.setInitialPrice(cursor.getFloat(5));
                    productsArrayList.add(p);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return productsArrayList;
    }


    public void insertProducts(Products p) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (" +
                idProduct + ", " + idCategory + ", " + nameProduct + ", " +
                descriptionProduct + ", " + productImage + ", " + priceProduct + ") VALUES (?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(insertSQL, new Object[]{p.getIdProduct(), p.getIdCategory(), p.getNameProduct(),
                p.getDescriptionProduct(), p.getImageProduct(), p.getInitialPrice()});
        // Thêm log để kiểm tra câu lệnh SQL
        Log.d("SQL_INSERT", "Executing SQL: " + insertSQL);

//        sqLiteDatabase.execSQL(insertSQL);
        sqLiteDatabase.close();
    }



    public void deleteProducts(Products pc) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ProductID = '" + pc.getIdProduct() + "'";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public Products returnResultForSearch(String productID) {
        Products product = null;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + idProduct + " = + '"+ productID +"'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                product = new Products();
                product.setIdProduct(cursor.getString(0));
                product.setIdCategory(cursor.getString(1));
                product.setNameProduct(cursor.getString(2));
                product.setDescriptionProduct(cursor.getString(3));
                product.setImageProduct(cursor.getBlob(4));
                product.setInitialPrice(cursor.getFloat(5));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return product;
    }

    @SuppressLint("Range")
    public ArrayList<Products> returnResultForSearchHome(String keySearch) {
        ArrayList<Products> products = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + idProduct + " LIKE '%" + keySearch +
                "%' OR " + nameProduct + " LIKE '%" + keySearch + "%'" +
                " OR " + idCategory + " LIKE '%" + keySearch + "%'" +
                " OR " + priceProduct + " >= ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[] { keySearch });

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Products product = new Products();
                product.setIdProduct(cursor.getString(0));
                product.setIdCategory(cursor.getString(1));
                product.setNameProduct(cursor.getString(2));
                product.setDescriptionProduct(cursor.getString(3));
                product.setImageProduct(cursor.getBlob(4));
                product.setInitialPrice(cursor.getFloat(5));
                products.add(product);
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return products;
    }

    @SuppressLint("Range")
    public ArrayList<Products> returnResultForRecylerView(String cateID) {
        ArrayList<Products> products = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + idCategory + " = + '"+ cateID +"'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Products product = new Products();
                product.setIdProduct(cursor.getString(0));
                product.setIdCategory(cursor.getString(1));
                product.setNameProduct(cursor.getString(2));
                product.setDescriptionProduct(cursor.getString(3));
                product.setImageProduct(cursor.getBlob(4));
                product.setInitialPrice(cursor.getFloat(5));
                products.add(product);
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return products;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
