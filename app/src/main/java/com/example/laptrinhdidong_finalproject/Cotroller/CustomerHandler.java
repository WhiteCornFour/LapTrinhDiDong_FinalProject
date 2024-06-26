package com.example.laptrinhdidong_finalproject.Cotroller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.View.Activity_Register;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class CustomerHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Customer";
    private static final String idCustomer = "idCustomer";
    private static final String nameCustomer = "nameCustomer";
    private static final String emailCustomer = "emailCustomer";
    private static final String phoneCustomer = "phoneCustomer";
    private static final String accountCustomer = "accountCustomer";
    private static final String passwordCustomer = "passwordCustomer";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public CustomerHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                idCustomer + " TEXT NOT NULL UNIQUE, " +
                nameCustomer + " TEXT NOT NULL," +
                emailCustomer + " TEXT NOT NULL," +
                phoneCustomer + " INTEGER NOT NULL UNIQUE," +
                accountCustomer + " TEXT NOT NULL," +
                passwordCustomer + " TEXT NOT NULL," +
                " PRIMARY KEY(" + idCustomer + ")" +
                ")";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void insertRecordIntoCustomerTable(Customer c)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql1 = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + idCustomer + ", " + nameCustomer + ", "+ emailCustomer +", "+ phoneCustomer +", "+ accountCustomer +", "+ passwordCustomer +") " +
                "Values "
                + "('" + c.getIdCustomer() + "','" + c.getNameCustomer() + "', '"+ c.getEmailCustomer() +"','"+ c.getPhoneCustomer() +"', '"+ c.getAccountCustomer() +"', '"+ c.getPasswordCustomer() +"')";
        //Log.d("SQL_INSERT_STATEMENT", sql1);
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.close();
    }
    public ArrayList<Customer> loadAllDataOfCustomer()
    {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        do {
            Customer c = new Customer();
            c.setIdCustomer(cursor.getString(0));
            c.setNameCustomer(cursor.getString(1));
            c.setEmailCustomer(cursor.getString(2));
            c.setPhoneCustomer(cursor.getString(3));
            c.setAccountCustomer(cursor.getString(4));
            c.setPasswordCustomer(cursor.getString(5));
            customerArrayList.add(c);
        }while (cursor.moveToNext());
        sqLiteDatabase.close();
        return customerArrayList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
