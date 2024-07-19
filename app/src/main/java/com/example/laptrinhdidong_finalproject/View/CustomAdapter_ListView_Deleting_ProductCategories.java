package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class CustomAdapter_ListView_Deleting_ProductCategories extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<ProductCategories> productCategoriesArrayList = new ArrayList<>();



    public CustomAdapter_ListView_Deleting_ProductCategories(@NonNull Context context, int layoutItem, @NonNull ArrayList<ProductCategories> productCategoriesArrayList) {
        super(context, layoutItem, productCategoriesArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.productCategoriesArrayList = productCategoriesArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductCategories pc = productCategoriesArrayList.get(position);
        if(convertView==null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        ImageView imgPicCate = (ImageView) convertView.findViewById(R.id.imgProductCate);
        byte[] imageBytes = pc.getImageCategory();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgPicCate.setImageBitmap(bitmap);
        } else {
            imgPicCate.setImageResource(R.drawable.background);
        }

        TextView tvIdProductCate = (TextView)  convertView.findViewById(R.id.tvIdProductCate);
        tvIdProductCate.setText(pc.getIdCategory());

        TextView tvNameProductCate = (TextView) convertView.findViewById(R.id.tvNameProductCate);
        tvNameProductCate.setText(pc.getNameCategory());

        return convertView;
    }

}
