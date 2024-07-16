package com.example.laptrinhdidong_finalproject.Cotroller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Carts;
import com.example.laptrinhdidong_finalproject.Model.Customer;

public class CartsHandler  extends SQLiteOpenHelper {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Carts";
    private static final String idCart = "CartID";
    private static final String idCustomer = "CustomerID";
    private static final String idProduct = "ProductID";
    private static final String sizeProduct = "ProductSize";
    private static final String quantityProduct = "ProductQuantity";
    private static final String cartUnitPrice = "CartUnitPrice";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    public CartsHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    public void insertRecordIntoCartsTable(Carts cr)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql1 = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + idCart + ", " + idCustomer + ", "+ idProduct+", "+ sizeProduct +", "+ quantityProduct+", "+ cartUnitPrice+") " +
                "Values "
                + "('" + cr.getCartId() + "','" + cr.getCustomerId() + "', '"+ cr.getProductId()+"','"+ cr.getProductSize() +"', '" + cr.getCartUnitPrice() + "', '"+ cr.getCartUnitPrice() +"')";
//        Log.d("SQL_CARTS_INSERT", sql1);
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
