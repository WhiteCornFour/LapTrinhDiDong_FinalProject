package com.example.laptrinhdidong_finalproject.Cotroller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.ProductCategory;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class ProductCategoryHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "ProductCategories";
    private static final String categoryID = "CategoryID";
    private static final String categoryName = "CategoryName";
    private static final String categoryDescription = "CategoryDescription";
    private static final String categoryImage = "CategoryImage";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public ProductCategoryHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                categoryID + " TEXT NOT NULL UNIQUE, " +
                categoryName + " TEXT NOT NULL," +
                categoryDescription + " TEXT NOT NULL," +
                categoryImage + " BLOB NOT NULL," +
                " PRIMARY KEY(" + categoryID + ")" +
                ")";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public boolean updateProductCategory(ProductCategory category) {
        boolean updated = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

            ContentValues contentValues = new ContentValues();
            contentValues.put(categoryName, category.getCategoryName());
            contentValues.put(categoryDescription, category.getCategoryDescription());
            contentValues.put(categoryImage, category.getCategoryImage());

            int kq = sqLiteDatabase.update(TABLE_NAME, contentValues, categoryID + " = ?", new String[]{category.getCategoryID()});
            updated = kq > 0;

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return updated;
    }



    public void insertRecordIntoProductCategoryTable(ProductCategory category) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql1 = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + categoryID + ", " + categoryName + ", " + categoryDescription + ", " + categoryImage + ") " +
                "VALUES "
                + "('" + category.getCategoryID() + "','" + category.getCategoryName() + "','" + category.getCategoryDescription() + "','" + category.getCategoryImage() + "')";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.close();
    }

    public ArrayList<ProductCategory> loadAllDataOfProductCategory() {
        ArrayList<ProductCategory> categoryArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        do {
            ProductCategory category = new ProductCategory();
            category.setCategoryID(cursor.getString(0));
            category.setCategoryName(cursor.getString(1));
            category.setCategoryDescription(cursor.getString(2));
            category.setCategoryImage(cursor.getBlob(3));
            categoryArrayList.add(category);
        } while (cursor.moveToNext());
        sqLiteDatabase.close();
        return categoryArrayList;
    }


}
