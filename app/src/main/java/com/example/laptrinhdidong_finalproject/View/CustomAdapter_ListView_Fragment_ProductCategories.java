package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class CustomAdapter_ListView_Fragment_ProductCategories extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<ProductCategories> arrayList;

    public CustomAdapter_ListView_Fragment_ProductCategories(Context context, int layoutItem, ArrayList<ProductCategories> arrayList) {
        super(context, layoutItem, arrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductCategories category = arrayList.get(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        }

        ImageView imgCategory = convertView.findViewById(R.id.img_category);
        if (category.getImageCategory() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(category.getImageCategory(), 0, category.getImageCategory().length);
            imgCategory.setImageBitmap(bitmap);
        }
        else {
            imgCategory.setImageResource(R.drawable.empty_img); // set a placeholder image
        }

        TextView tvCategoryID = convertView.findViewById(R.id.tv_category_id);
        tvCategoryID.setText("ID: " +category.getIdCategory());

        TextView tvCategoryName = convertView.findViewById(R.id.tv_category_name);
        tvCategoryName.setText(category.getNameCategory());

        TextView tvCategoryDescription = convertView.findViewById(R.id.tv_category_description);
        tvCategoryDescription.setText( category.getDescriptionCategory());
        return convertView;
    }
}