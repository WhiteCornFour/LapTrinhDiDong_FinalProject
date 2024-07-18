package com.example.laptrinhdidong_finalproject.Cotroller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.CustomerFeedbacks;
import com.example.laptrinhdidong_finalproject.Model.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerFeedbackHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "CustomerFeedbacks";
    private static final String idFeedback = "FeedbackID";
    private static final String idCustomer = "CustomerID";
    private static final String feedbackContent = "FeedbackContent";
    private static final String feedbackTime = "FeedbackTime";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public CustomerFeedbackHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
//                "(" +
//                idFeedback + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                idCustomer + " INTEGER," +
//                feedbackContent + " TEXT NOT NULL," +
//                feedbackTime + " TEXT NOT NULL" +
//                ")";
//        sqLiteDatabase.execSQL(sql);
//        sqLiteDatabase.close();
    }

    public long insertFeedback(CustomerFeedbacks feedback) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        String sql = "INSERT INTO " + TABLE_NAME + " ("
                + idCustomer + ", " + feedbackContent + ", " + feedbackTime + ") VALUES ("
                + "'" + feedback.getCustomerID() + "', '" + feedback.getFeedbackContent() + "', '" + feedback.getFeedbackTime() + "')";

        sqLiteDatabase.execSQL(sql);

        // Lấy feedbackID của phản hồi vừa chèn
        String selectLastRowID = "SELECT last_insert_rowid()";
        Cursor cursor = sqLiteDatabase.rawQuery(selectLastRowID, null);
        long feedbackID = -1;
        if (cursor.moveToFirst()) {
            feedbackID = cursor.getLong(0);
        }
        cursor.close();

        sqLiteDatabase.close();

        return feedbackID;
    }
    public ArrayList<CustomerFeedbacks> loadAllFeedbacks() {
        ArrayList<CustomerFeedbacks> feedbacksList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                CustomerFeedbacks feedback = new CustomerFeedbacks(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                );
                feedbacksList.add(feedback);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return feedbacksList;
    }
    public void deleteFeedback(String feedbackID) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + idFeedback + " = '" + feedbackID + "'";
        Log.d("sql delete cmt", sql);
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }
    public void updateFeedbacks(CustomerFeedbacks feedbacks) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);


        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + idFeedback + " = ?";
        sqLiteDatabase.execSQL(deleteSQL, new Object[]{feedbacks.getFeedbackID()});


        String insertSQL = "INSERT INTO " + TABLE_NAME + " (" +
                idCustomer + ", " + feedbackTime + ", " + idFeedback + ") VALUES (?, ?, ?)";
        sqLiteDatabase.execSQL(insertSQL, new Object[]{feedbacks.getCustomerID(), feedbacks.getFeedbackTime(), feedbacks.getFeedbackID()});


        sqLiteDatabase.close();
    }
    public static Map<String, Customer> getCustomerInfoMap() {
        Map<String, Customer> cusInfoMap = new HashMap<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT CustomerName FROM Customers";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String nameCus = cursor.getString(cursor.getColumnIndexOrThrow("CustomerName"));
                Customer customer = new Customer(nameCus);
                cusInfoMap.put(nameCus, customer);
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return cusInfoMap;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}