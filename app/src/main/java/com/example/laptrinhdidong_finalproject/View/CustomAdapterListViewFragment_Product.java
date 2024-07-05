package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.Model.Product;
import com.example.laptrinhdidong_finalproject.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapterListViewFragment_Product extends ArrayAdapter {

    Context context;
    int layoutItem;
    ArrayList<Product> productArrayList = new ArrayList<>();

    public CustomAdapterListViewFragment_Product(@NonNull Context context, int layoutItem, @NonNull ArrayList<Product> productArrayList) {
        super(context, layoutItem, productArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = productArrayList.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);

        ImageView img = (ImageView) convertView.findViewById(R.id.imgCTProduct);
        img.setImageBitmap(product.getProductImage());
        TextView name = (TextView) convertView.findViewById(R.id.tvCTNameProduct);
        name.setText(product.getProductName());
        TextView id = (TextView) convertView.findViewById(R.id.tvCTIdProduct);
        id.setText(product.getProductID());
        TextView price = (TextView) convertView.findViewById(R.id.tvCTInitialPrice);
        price.setText(String.valueOf(product.getInitialPrice()));

        return convertView;
    }
}
