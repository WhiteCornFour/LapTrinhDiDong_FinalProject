package com.example.laptrinhdidong_finalproject.Cotroller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.ProductCategories;

import java.util.ArrayList;

public class CategoryHandler extends SQLiteOpenHelper {
    static final String DB_NAME = "drinkingmanager";
    static final int DB_VERSION = 1;
    static final String TABLE_NAME = "ProductCategories";

    private static final String CATEGORY_IMAGE = "CategoryImage";
    static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public CategoryHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public ArrayList<ProductCategories> loadData(){
        ArrayList<ProductCategories> categoryList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                ProductCategories category = new ProductCategories();
                category.setCategoryID(cursor.getString(0));
                category.setCategoryName(cursor.getString(1));

                // Đọc dữ liệu ảnh BLOB từ SQLite
                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(CATEGORY_IMAGE));
                Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                category.setCategoryImage(image);

                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return categoryList;
    }


    public void insertNewData(ProductCategories category){
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String insertSQL = "INSERT INTO ProductCategories VALUES (" + "'" + category.getCategoryID() + "','" + category.getCategoryName() + "'," + category.getCategoryImage() +")";
        sqLiteDatabase.execSQL(insertSQL);
        sqLiteDatabase.close();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}