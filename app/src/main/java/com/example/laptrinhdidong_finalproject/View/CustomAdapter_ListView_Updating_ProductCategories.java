package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class CustomAdapter_ListView_Updating_ProductCategories extends BaseAdapter {

     ImageView img_category;
     Context   context;
     ArrayList<ProductCategories> categories;

    public CustomAdapter_ListView_Updating_ProductCategories(Context context, ArrayList<ProductCategories> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_product_category, null);
        }

        TextView tvCategoryId = view.findViewById(R.id.tv_category_id);
        TextView tvCategoryName = view.findViewById(R.id.tv_category_name);
        TextView tvCategoryDescription = view.findViewById(R.id.tv_category_description);
        ImageView img_category = view.findViewById(R.id.img_category);

        ProductCategories category = categories.get(position);
        tvCategoryId.setText("ID: " + category.getIdCategory());
        tvCategoryName.setText("Name: " + category.getNameCategory());
        tvCategoryDescription.setText("Description: " + category.getDescriptionCategory());

        byte[] imageBlob = category.getImageCategory();
        if (imageBlob != null && imageBlob.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            img_category.setImageBitmap(bitmap);
        } else {
            img_category.setImageResource(R.drawable.hinh_anh_dang_nhap);
        }

        return view;
    }
}
