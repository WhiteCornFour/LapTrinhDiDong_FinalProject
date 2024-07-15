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

import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter_ListView_Fragment_Product extends ArrayAdapter {

    Context context;
    int layoutItem;
    ArrayList<Products> productArrayList = new ArrayList<>();

    public CustomAdapter_ListView_Fragment_Product(@NonNull Context context, int layoutItem, @NonNull ArrayList<Products> productArrayList) {
        super(context, layoutItem, productArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Products product = productArrayList.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);

        ImageView imgDetailDeleteP = (ImageView) convertView.findViewById(R.id.imgCTProduct);
        byte[] imageBytes = product.getImageProduct();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgDetailDeleteP.setImageBitmap(bitmap);
        } else {
            imgDetailDeleteP.setImageResource(R.drawable.background);
        }
        TextView name = (TextView) convertView.findViewById(R.id.tvCTNameProduct);
        name.setText(product.getNameProduct());
        TextView id = (TextView) convertView.findViewById(R.id.tvCTIdProduct);
        id.setText("ID: " + product.getIdProduct());
        TextView price = (TextView) convertView.findViewById(R.id.tvCTInitialPrice);
        price.setText("Price: "+ String.valueOf(product.getInitialPrice()));

        return convertView;
    }
}
