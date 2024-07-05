package com.example.laptrinhdidong_finalproject.Cotroller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.Model.Products;

import java.util.ArrayList;

public class ProductsHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Products";
    private static final String idProduct = "ProductID";
    private static final String idCategory = "CategoryID";
    private static final String nameProduct = "ProductName";
    private static final String descriptionProduct = "ProductDescription";
    private static final Float priceProduct = 0.0f;
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

    public void deleteProducts(Products pc) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ProductID = '" + pc.getIdProduct() + "'";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
