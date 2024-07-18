package com.example.laptrinhdidong_finalproject.Cotroller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.CartItems;
import com.example.laptrinhdidong_finalproject.Model.OrderDetails;
import com.example.laptrinhdidong_finalproject.Model.Orders;

import java.util.ArrayList;

public class OrderDetailsHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "OrderDetails";
    private static final String orderID = "OrderID";
    private static final String productID = "ProductID";
    private static final String cartID = "CartID";
    private static final String size = "Size";
    private static final String quantity = "Quantity";
    private static final String unitPrice = "UnitPrice";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public OrderDetailsHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public ArrayList<OrderDetails> loadAllDataOfOrderDetails() {
        ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        do {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setOrderID(cursor.getString(0));
            orderDetail.setProductID(cursor.getString(1));
            orderDetail.setCartID(cursor.getString(2));
            orderDetail.setSize(cursor.getString(3));
            orderDetail.setQuantity(cursor.getInt(4));
            orderDetail.setUnitPrice(cursor.getDouble(5));
            orderDetailsArrayList.add(orderDetail);
        } while (cursor.moveToNext());
        cursor.close();
        sqLiteDatabase.close();
        return orderDetailsArrayList;
    }

    public void insertNewOrderDetails(String idOrder, ArrayList<CartItems> cartItemofOrder) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        for (int i = 0; i < cartItemofOrder.size(); i++) {
            CartItems item = cartItemofOrder.get(i);
            String insertSQL = "INSERT INTO " + TABLE_NAME + " (" + orderID + "," + productID + "," + cartID + "," + size + "," + quantity + "," + unitPrice + ") VALUES (?, ?, ?, ?, ?, ?)";
            sqLiteDatabase.execSQL(insertSQL, new Object[]{idOrder, item.getProductId(), item.getCartId(), item.getProductSize(), item.getProductQuantity(), item.getCartUnitPrice()});
        }
        sqLiteDatabase.close();
    }

    public void deleteOrderDetail(String orderID, String productID) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE OrderID= '" + orderID + "' AND ProductID= '" + productID + "'";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public boolean updateOrderDetail(OrderDetails orderDetail) {
        boolean updated = false;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

            ContentValues contentValues = new ContentValues();
            contentValues.put(cartID, orderDetail.getCartID());
            contentValues.put(size, orderDetail.getSize());
            contentValues.put(quantity, orderDetail.getQuantity());
            contentValues.put(unitPrice, orderDetail.getUnitPrice());

            int kq = sqLiteDatabase.update(TABLE_NAME, contentValues, orderID + " = ? AND " + productID + " = ?", new String[]{orderDetail.getOrderID(), orderDetail.getProductID()});
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

