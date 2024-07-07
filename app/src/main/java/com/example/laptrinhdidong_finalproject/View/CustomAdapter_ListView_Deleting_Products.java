package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class CustomAdapter_ListView_Deleting_Products extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<Products> productsArrayList = new ArrayList<>();

    boolean[] checkedStates;

    public CustomAdapter_ListView_Deleting_Products(@NonNull Context context, int layoutItem, @NonNull ArrayList<Products> productsArrayList, boolean[] checkedStates) {
        super(context, layoutItem, productsArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.productsArrayList = productsArrayList;
        this.checkedStates = checkedStates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Products p = productsArrayList.get(position);
        if(convertView==null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        ImageView imgDetailDeleteP = (ImageView) convertView.findViewById(R.id.imgDetailDeleteP);
        byte[] imageBytes = p.getImageProduct();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgDetailDeleteP.setImageBitmap(bitmap);
        } else {
            imgDetailDeleteP.setImageResource(R.drawable.background);
        }

        TextView tvDetailProductName = (TextView)  convertView.findViewById(R.id.tvDetailProductName);
        tvDetailProductName.setText(p.getNameProduct());

        TextView tvDetailProductID = (TextView) convertView.findViewById(R.id.tvDetailProductID);
        tvDetailProductID.setText(p.getIdProduct());

        TextView tvDetailProductPrice = (TextView) convertView.findViewById(R.id.tvDetailProductPrice);
        tvDetailProductPrice.setText(String.valueOf(p.getInitialPrice()));

        CheckBox cbDeleteProduct = convertView.findViewById(R.id.cbDeleteProduct);

        cbDeleteProduct.setChecked(checkedStates[position]);
        cbDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedStates[position] = ((CheckBox) v).isChecked();
            }
        });

        return convertView;
    }
}
