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

import com.example.laptrinhdidong_finalproject.Cotroller.ProductCategoriesHandler;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class CustomAdapterLV_Product_Home extends ArrayAdapter {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "ProductCategories";
    private static final String idCategory = "CategoryID";
    private static final String nameCategory = "CategoryName";
    private static final String descriptionCategory = "CategoryDescription";
    private static final String imageCategory = "CategoryImage";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    ProductCategoriesHandler productCategoriesHandler;
    Context context;
    int layoutItem;
    ArrayList<Products> productsArrayList = new ArrayList<>();

    public CustomAdapterLV_Product_Home(@NonNull Context context, int layoutItem, @NonNull ArrayList<Products> productsArrayList) {
        super(context, layoutItem, productsArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.productsArrayList = productsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        productCategoriesHandler = new ProductCategoriesHandler(getContext(),
                DB_NAME, null, DB_VERSION);
        ImageView imgHome = convertView.findViewById(R.id.imgHome);
        Products product = productsArrayList.get(position);
        if (product.getImageProduct() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImageProduct(),
                    0, product.getImageProduct().length);

            imgHome.setImageBitmap(bitmap);
        }
        else {
            imgHome.setImageResource(R.drawable.empty_img); // set a placeholder image
        }
        TextView tvNameHome = convertView.findViewById(R.id.tvNameHome);
        tvNameHome.setText(product.getNameProduct());
        TextView tvCateHome = convertView.findViewById(R.id.tvCateHome);
        String kq = productCategoriesHandler.returnCategoryName(product.getIdCategory());
        tvCateHome.setText(kq);
        TextView tvPriceHome = convertView.findViewById(R.id.tvPriceHome);
        tvPriceHome.setText("Price: "+String.valueOf(product.getInitialPrice()));
        return convertView;
    }
}
