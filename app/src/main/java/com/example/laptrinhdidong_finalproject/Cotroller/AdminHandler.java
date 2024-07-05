package com.example.laptrinhdidong_finalproject.Cotroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class AdminHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Admin";
    private static final String adminID = "AdminID";
    private static final String adminName = "AdminName";
    private static final String loginAccount = "LoginAccount";
    private static final String loginPassword = "LoginPassword";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";


    public AdminHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    public boolean validateLoginAdmin(String username, String password) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + loginAccount + " = '" + username + "' AND " + loginPassword + " = '" + password + "'";
        Cursor cursor = db.rawQuery(sql, null);

        boolean isValid = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isValid = (count > 0);
        }
        Log.d("SQL_COUNT_RESULT", sql);
        cursor.close();
        db.close();
        return isValid;
    }

    @SuppressLint("Range")
    public String getADName(String us, String pass)
    {
        String result = "";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT AdminName FROM " + TABLE_NAME + " WHERE " + loginAccount + " = '" + us + "' AND " + loginPassword + " = '" + pass + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(adminName));
        }

        cursor.close();
        sqLiteDatabase.close();

        //Log.d("Name admin", result);
        return result;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
