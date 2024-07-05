package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class CustomListViewCategories extends BaseAdapter {
    Context context;

    int layoutItem;
    ArrayList<ProductCategories> arrayList;

    public CustomListViewCategories(Context context, int layoutItem, ArrayList<ProductCategories> arrayList) {
        this.context = context;
        this.layoutItem = layoutItem;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
            holder = new ViewHolder();
            holder.imgCategory = convertView.findViewById(R.id.imgCategory);
            holder.tvCategoryID = convertView.findViewById(R.id.tvCategoryID);
            holder.tvCategoryName = convertView.findViewById(R.id.tvCategoryName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductCategories category = arrayList.get(position);
        holder.tvCategoryID.setText(category.getCategoryID());
        holder.tvCategoryName.setText(category.getCategoryName());

        if (category.getCategoryImage() != null) {
            holder.imgCategory.setImageBitmap(category.getCategoryImage());
        } else {
            holder.imgCategory.setImageResource(R.drawable.empty_img); // set a placeholder image
        }

        return convertView;
    }

    static class ViewHolder{
        ImageView imgCategory;
        TextView tvCategoryID, tvCategoryName;
    }
}