package com.example.laptrinhdidong_finalproject.Cotroller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.Model.Products;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductCategoriesHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "ProductCategories";
    private static final String idCategory = "CategoryID";
    private static final String nameCategory = "CategoryName";
    private static final String descriptionCategory = "CategoryDescription";
    private static final String imageCategory = "CategoryImage";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public ProductCategoriesHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public ArrayList<ProductCategories> loadAllDataOfProductCategories()
    {
        ArrayList<ProductCategories> productCategoriesArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        do {
            ProductCategories pc = new ProductCategories();
            pc.setIdCategory(cursor.getString(0));
            pc.setNameCategory(cursor.getString(1));
            pc.setDescriptionCategory(cursor.getString(2));
            pc.setImageCategory(cursor.getBlob(3));
            productCategoriesArrayList.add(pc);
        }while (cursor.moveToNext());
        cursor.close();
        sqLiteDatabase.close();
        return productCategoriesArrayList;
    }

    public ArrayList<String> importSpinnerData(ArrayList<ProductCategories> categories) {
        ArrayList<String> spinnerData = new ArrayList<>();
        for (ProductCategories category : categories) {
            spinnerData.add(category.getIdCategory() + " - " + category.getNameCategory());
        }
        return spinnerData;
    }

    public boolean checkDuplicateCategoryData(String idCategory, String nameCategory, ArrayList<ProductCategories> categoryData)
    {
        for (int i = 0; i < categoryData.size(); i++) {
            ProductCategories category = categoryData.get(i);
            if(category.getIdCategory().equals(idCategory) || category.getNameCategory().equals(nameCategory)){
                return false;
            }
        }
        return true;
    }
    public void insertNewData(ProductCategories category){
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
//        String insertSQL = "INSERT INTO ProductCategories VALUES " + "(" + "'" + category.getIdCategory() + "','" + category.getNameCategory() + "'," + category.getImageCategory() +")";
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (" +  idCategory + "," + nameCategory + "," + descriptionCategory + "," + imageCategory + ") VALUES (?, ?, ?, ?)";
        sqLiteDatabase.execSQL(insertSQL, new Object[]{category.getIdCategory(), category.getNameCategory(), category.getDescriptionCategory(), category.getImageCategory()});
        sqLiteDatabase.close();
    }

    public void deleteProductCarte(String idCategory) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE CategoryID= '" + idCategory + "'";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public boolean updateProductCategor(ProductCategories category) {
        boolean updated = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

            ContentValues contentValues = new ContentValues();
            contentValues.put(nameCategory, category.getNameCategory());
            contentValues.put(descriptionCategory, category.getDescriptionCategory());
            contentValues.put(imageCategory, category.getImageCategory());

            int kq = sqLiteDatabase.update(TABLE_NAME, contentValues, idCategory + " = ?", new String[]{category.getIdCategory()});
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

    @SuppressLint("Range")
    public ProductCategories returnResultForSearchCategory(String productCateID) {
        ProductCategories productCategories = null;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + idCategory + " = '" + productCateID + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                productCategories = new ProductCategories();
                productCategories.setIdCategory(cursor.getString(cursor.getColumnIndex(idCategory)));
                productCategories.setNameCategory(cursor.getString(cursor.getColumnIndex(nameCategory)));
                productCategories.setDescriptionCategory(cursor.getString(cursor.getColumnIndex(descriptionCategory)));
                productCategories.setImageCategory(cursor.getBlob(cursor.getColumnIndex(imageCategory)));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return productCategories;
    }

    public String returnCategoryName(String id)
    {
        String result = "";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT CategoryName FROM " + TABLE_NAME + " WHERE " + idCategory + " = '" + id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(nameCategory));
        }

        cursor.close();
        sqLiteDatabase.close();

        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
