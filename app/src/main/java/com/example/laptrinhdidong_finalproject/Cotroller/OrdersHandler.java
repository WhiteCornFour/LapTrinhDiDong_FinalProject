package com.example.laptrinhdidong_finalproject.Cotroller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Orders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class OrdersHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Orders";
    private static final String orderID = "OrderID";
    private static final String cartID = "CartID";
    private static final String orderDate = "OrderDate";
    private static final String shipAddress = "ShipAddress";
    private static final String orderTotal = "OrderTotal";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public OrdersHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public ArrayList<Orders> loadAllDataOfOrders() {
        ArrayList<Orders> ordersArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        do {
            Orders order = new Orders();
            order.setOrderID(cursor.getString(0));
            order.setCartID(cursor.getString(1));
            order.setOrderDate(cursor.getString(2));
            order.setShipAddress(cursor.getString(3));
            order.setOrderTotal(cursor.getDouble(4));
            ordersArrayList.add(order);
        } while (cursor.moveToNext());
        cursor.close();
        sqLiteDatabase.close();
        return ordersArrayList;
    }

    private String generateOrderID() {
        // Lấy ngày và giờ hiện tại
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"); // Định dạng ngày giờ
        String formattedDateTime = now.format(formatter);

        // Tạo số ngẫu nhiên từ 1000 đến 9999
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        return "Ord" + formattedDateTime + randomNumber;
    }

    public String insertNewOrder(Orders order) {
        String generatedID = generateOrderID();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (" + orderID + "," + cartID + "," + orderDate + "," + shipAddress + "," + orderTotal + ") VALUES (?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(insertSQL, new Object[]{generatedID, order.getCartID(), order.getOrderDate(), order.getShipAddress(), order.getOrderTotal()});
        sqLiteDatabase.close();

        return generatedID;
    }

    public void deleteOrder(String orderID) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE OrderID= '" + orderID + "'";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public boolean updateOrder(Orders order) {
        boolean updated = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);

            ContentValues contentValues = new ContentValues();
            contentValues.put(cartID, order.getCartID());
            contentValues.put(orderDate, order.getOrderDate());
            contentValues.put(shipAddress, order.getShipAddress());
            contentValues.put(orderTotal, order.getOrderTotal());

            int kq = sqLiteDatabase.update(TABLE_NAME, contentValues, orderID + " = ?", new String[]{order.getOrderID()});
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
