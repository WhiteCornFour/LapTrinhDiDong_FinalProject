package com.example.laptrinhdidong_finalproject.Cotroller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.Model.Revenue;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class RevenueHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "MonthlyRevenue";
    private static final String revenueID = "RevenueID";
    private static final String year = "Year";
    private static final String month = "Month";
    private static final String totalRevenue = "TotalRevenue";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public RevenueHandler(@Nullable Context context, @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    public ArrayList<Revenue> loadAllDataOfRevenue()
    {
        ArrayList<Revenue> revenueArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        do {
            Revenue revenue = new Revenue();
            revenue.setRevenueID(cursor.getString(0));
            revenue.setYear(cursor.getInt(1));
            revenue.setMonth(cursor.getInt(2));
            revenue.setTotalRevenue(cursor.getDouble(3));
            revenueArrayList.add(revenue);
        }while (cursor.moveToNext());
        cursor.close();
        sqLiteDatabase.close();
        return revenueArrayList;
    }
    Calendar calendar = Calendar.getInstance();
    public int monthNow = calendar.get(Calendar.MONTH) + 1;
    public ArrayList<Revenue> loadAllDataOfRevenueMonth()
    {
        ArrayList<Revenue> revenueArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + month + " = + '"+ 5 +"'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                    Revenue revenue = new Revenue();
                    revenue.setRevenueID(cursor.getString(0));
                    revenue.setYear(cursor.getInt(1));
                    revenue.setMonth(cursor.getInt(2));
                    revenue.setTotalRevenue(cursor.getDouble(3));
                    revenueArrayList.add(revenue);
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        Log.d("revenueArrayList" , String.valueOf(revenueArrayList));
        return revenueArrayList;
    }
    public ArrayList<Revenue> returnResultForSearch(String m, String y)
    {
        ArrayList<Revenue> revenueArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME
                +" WHERE Month = + '"+ m+ "' AND Year = + '"+ y + "'", null);
        cursor.moveToFirst();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Revenue revenue = new Revenue();
                    revenue.setRevenueID(cursor.getString(0));
                    revenue.setYear(cursor.getInt(1));
                    revenue.setMonth(cursor.getInt(2));
                    revenue.setTotalRevenue(cursor.getDouble(3));
                    revenueArrayList.add(revenue);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return revenueArrayList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
