package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.CartItems;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;
import java.util.Map;

public class CustomAdapter_ListView_Payment extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<CartItems> itemsArrayList = new ArrayList<>();
    private Map<String, Products> productInfoMap = ProductsHandler.getProductInfoMap();

    public CustomAdapter_ListView_Payment(@NonNull Context context, int resource, @NonNull ArrayList<CartItems> itemsArrayList) {
        super(context, resource, itemsArrayList);
        this.context = context;
        this.layoutItem = resource;
        this.itemsArrayList = itemsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CartItems items = itemsArrayList.get(position);
        Products productInfo = productInfoMap.get(items.getProductId());
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        }
        TextView tvName = convertView.findViewById(R.id.tvPayProductName);
        TextView tvSize = convertView.findViewById(R.id.tvPayProductSize);
        TextView tvQuantity = convertView.findViewById(R.id.tvPayQuantity);
        TextView tvUnitPrice = convertView.findViewById(R.id.tvPayUnitPrice);

        if(productInfo != null){
            tvName.setText(productInfo.getNameProduct());
        }else {
            tvName.setText("Unknown Product");
        }
        tvSize.setText(items.getProductSize());
        tvQuantity.setText(String.valueOf(items.getProductQuantity()));
        tvUnitPrice.setText(String.valueOf(items.getCartUnitPrice()));

        return convertView;
    }
}
