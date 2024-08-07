package com.example.laptrinhdidong_finalproject.Cotroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Customers";
    private static final String idCustomer = "CustomerID";
    private static final String nameCustomer = "CustomerName";
    private static final String emailCustomer = "CustomerEmail";
    private static final String phoneCustomer = "PhoneNumber";
    private static final String passwordCustomer = "LoginPassword";

    private static final String customerImage = "CustomerImage";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";
    
    public CustomerHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
//                "(" +
//                idCustomer + " TEXT NOT NULL UNIQUE, " +
//                nameCustomer + " TEXT NOT NULL," +
//                emailCustomer + " TEXT NOT NULL," +
//                phoneCustomer + " INTEGER NOT NULL UNIQUE," +
//                accountCustomer + " TEXT NOT NULL," +
//                passwordCustomer + " TEXT NOT NULL," +
//                " PRIMARY KEY(" + idCustomer + ")" +
//                ")";
//        sqLiteDatabase.execSQL(sql);
//        sqLiteDatabase.close();
    }

    public void insertRecordIntoCustomerTable(Customer c)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql1 = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + idCustomer + ", " + nameCustomer + ", "+ emailCustomer +", "+ phoneCustomer +", "+ passwordCustomer +", "+ customerImage + ") " +
                "Values "
                + "('" + c.getIdCustomer() + "','" + c.getNameCustomer() + "', '"+ c.getEmailCustomer() +"','"+ c.getPhoneCustomer() +"', '"+ c.getPasswordCustomer() +"', '"+ c.getCustomerImage() + "')";
        //Log.d("SQL_INSERT_STATEMENT", sql1);
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.close();
    }

    public static Map<String, Customer> getCustomerInfoMap() {
        Map<String, Customer> customerInfoMap = new HashMap<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT CustomerID, CustomerName, PhoneNumber FROM Customers";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String customerID = cursor.getString(cursor.getColumnIndexOrThrow("CustomerID"));
                String customerName = cursor.getString(cursor.getColumnIndexOrThrow("CustomerName"));
                String customerPhone = cursor.getString(cursor.getColumnIndexOrThrow("PhoneNumber"));
                Customer customerInfo = new Customer(customerName, customerPhone);
                customerInfoMap.put(customerID, customerInfo);
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return customerInfoMap;
    }

    public boolean validateLogin(String phone, String password) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + phoneCustomer + " = '" + phone + "' AND " + passwordCustomer + " = '" + password + "'";
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

    public ArrayList<Customer> loadAllDataOfCustomer()
    {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Customer c = new Customer();
                c.setIdCustomer(cursor.getString(0));
                c.setNameCustomer(cursor.getString(1));
                c.setEmailCustomer(cursor.getString(2));
                c.setPhoneCustomer(cursor.getString(3));
                c.setPasswordCustomer(cursor.getString(4));
                customerArrayList.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return customerArrayList;
    }

    public boolean verifyPhoneNumberAndSendOTP(String phoneNumber)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + phoneCustomer + " = '" + phoneNumber + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        boolean phoneNumberExists = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            phoneNumberExists = (count > 0);
        }
        cursor.close();
        sqLiteDatabase.close();

        return phoneNumberExists;
    }

    @SuppressLint("Range")
    public String returnPassWord(String phoneNumber)
    {
        String resultPass = "The results will be displayed here";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT LoginPassword FROM " + TABLE_NAME + " WHERE " + phoneCustomer + " = '" + phoneNumber + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            resultPass = cursor.getString(cursor.getColumnIndex(passwordCustomer));
        }

        cursor.close();
        sqLiteDatabase.close();

        //Log.d("Pass", resultPass);
        return resultPass;
    }
    @SuppressLint("Range")
    public String getIdCustomer(String phone, String pass)
    {
        String result = "";
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT CustomerID FROM " + TABLE_NAME + " WHERE " + phoneCustomer + " = '" + phone + "' AND " + passwordCustomer + " = '" + pass + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(idCustomer));
        }
        cursor.close();
        sqLiteDatabase.close();
        return result;
    }

    public Customer loadInfOfOnePerson(String id)
    {
        Customer customer = null;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + idCustomer + " = '" + id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                customer = new Customer();
                customer.setIdCustomer(cursor.getString(0));
                customer.setNameCustomer(cursor.getString(1));
                customer.setEmailCustomer(cursor.getString(2));
                customer.setPhoneCustomer(cursor.getString(3));
                customer.setPasswordCustomer(cursor.getString(4));
                customer.setCustomerImage(cursor.getBlob(5));
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return  customer;
    }
    public void updateRecord(Customer p) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        // Delete existing record based on ProductID
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + idCustomer + " = ?";
        sqLiteDatabase.execSQL(deleteSQL, new Object[]{p.getIdCustomer()});

        // Insert new record
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (" +
                idCustomer + ", " + nameCustomer + ", " + emailCustomer + ", " +
                phoneCustomer + ", " + passwordCustomer + ", " + customerImage + ") VALUES (?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(insertSQL, new Object[]{p.getIdCustomer(), p.getNameCustomer(), p.getEmailCustomer(),
                p.getPhoneCustomer(), p.getPasswordCustomer(), p.getCustomerImage()});
        sqLiteDatabase.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
