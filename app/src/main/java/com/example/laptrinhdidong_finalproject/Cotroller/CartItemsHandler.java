package com.example.laptrinhdidong_finalproject.Cotroller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.CartItems;
import com.example.laptrinhdidong_finalproject.Model.Products;

import java.util.ArrayList;

public class CartItemsHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "CartItems";
    private static final String idCart = "CartID";
    private static final String idProduct = "ProductID";
    private static final String sizeProduct = "ProductSize";
    private static final String quantityProduct = "ProductQuantity";
    private static final String cartUnitPrice = "CartUnitPrice";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";
    String cartID = CartsHandler.getCustomerCartID();
    public CartItemsHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public ArrayList<CartItems> loadCartItemsData()
    {
        ArrayList<CartItems> cartItems = new ArrayList<>();
        if (cartID == null)
        {
            return cartItems;
        }
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + idCart + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{cartID});
        if (cursor != null)
        {
            if(cursor.moveToFirst())
            {
                do{
                    CartItems item = new CartItems();
                    item.setCartId(cursor.getString(1));
                    item.setProductId(cursor.getString(2));
                    item.setProductSize(cursor.getString(3));
                    item.setProductQuantity(cursor.getInt(4));
                    item.setCartUnitPrice(cursor.getDouble(5));
                    cartItems.add(item);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return cartItems;
    }

    public void deleteCartItems()
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE CartID = '" + cartID + "'";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void insertRecordIntoCartsTable(CartItems cr)
    {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
        String sql1 = "INSERT OR IGNORE INTO " + TABLE_NAME + " ("
                + idCart + ", "+ idProduct+", "+ sizeProduct +", "+ quantityProduct+", "+ cartUnitPrice+") " +
                "Values "
                + "('" + cr.getCartId() + "','" + cr.getProductId()+"','"+ cr.getProductSize() +"', '" + cr.getProductQuantity() + "', '"+ cr.getCartUnitPrice() +"')";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.close();
    }
    public Double sumTotalForCarts()
    {
        String cartID = CartsHandler.getCustomerCartID();
        if (cartID == null)
        {
            return 0.0;
        }
        Double totalSum = 0.0;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT SUM(" + cartUnitPrice + ") FROM " + TABLE_NAME + " WHERE " + idCart + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{cartID});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                totalSum = cursor.getDouble(0);
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return totalSum;
    }

    public int getGetItemCount() {
        return loadCartItemsData().size();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
