package com.example.laptrinhdidong_finalproject.Cotroller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Carts;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.View.Fragment_Home;

public class CartsHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";
    private static final String TABLE_NAME = "Carts";
    private static final String cartID = "CartID";
    private static final String customerID = "CustomerID";

    public CartsHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    //Hàm static lấy cartID
    @SuppressLint("Range")
    public static String getCustomerCartID() {
        String result = null;

        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String cusID = Fragment_Home.getIdCus();
        // Truy vấn SQL sử dụng tham số để tránh SQL Injection
        String sql = "SELECT CartID FROM " + TABLE_NAME + " WHERE " + customerID + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{cusID});
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(cartID));
            cursor.close();
        }
        sqLiteDatabase.close();

        return result;
    }

    public void insertNewCart(Carts c)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

        ContentValues contentValues = new ContentValues();
        contentValues.put("CartID", c.getCartID()); // Automatically generated CartID
        contentValues.put("CustomerID", c.getCustomerID());

        // Using insertWithOnConflict method to handle ON CONFLICT DO NOTHING
        sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
