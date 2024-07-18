package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Cotroller.CartItemsHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.CartItems;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;
import java.util.Map;

public class CustomAdapter_ListView_Cart extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<CartItems> itemsArrayList = new ArrayList<>();
    private final Map<String, Products> productInfoMap = ProductsHandler.getProductInfoMap();

    public CustomAdapter_ListView_Cart(@NonNull Context context, int resource, @NonNull ArrayList<CartItems> cartItems) {
        super(context, resource, cartItems);
        this.context = context;
        this.layoutItem = resource;
        this.itemsArrayList = cartItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CartItems items = itemsArrayList.get(position);
        Products productInfo = productInfoMap.get(items.getProductId());
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        }

        ImageView imgItem = convertView.findViewById(R.id.imgCartProduct);
        TextView tvItemName = convertView.findViewById(R.id.tvCartProductName);
        TextView tvItemPrice = convertView.findViewById(R.id.tvCartProductPrice);
        TextView tvItemSize = convertView.findViewById(R.id.tvCartProductSize);
        TextView tvItemQuantity = convertView.findViewById(R.id.tvCartProductQuantity);
        TextView btnCartDelete = convertView.findViewById(R.id.btnCartDelete);

        if (productInfo != null) {
            // Gán hình ảnh sản phẩm nếu có, nếu không gán hình ảnh mặc định
            if (productInfo.getImageProduct() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(productInfo.getImageProduct(), 0, productInfo.getImageProduct().length);
                imgItem.setImageBitmap(bitmap);
            } else {
                imgItem.setImageResource(R.drawable.empty_img); // hình ảnh mặc định
            }
            // Gán tên sản phẩm
            tvItemName.setText(productInfo.getNameProduct());
        } else {
            // Nếu không có thông tin sản phẩm trong Map
            imgItem.setImageResource(R.drawable.empty_img); // hình ảnh mặc định
            tvItemName.setText("Unknown Product");
        }

        tvItemPrice.setText(String.valueOf(items.getCartUnitPrice()));
        tvItemSize.setText(items.getProductSize());
        tvItemQuantity.setText(String.valueOf(items.getProductQuantity()));

        btnCartDelete.setOnClickListener(v -> {
            CartItemsHandler cartItemsHandler = new CartItemsHandler(context);
            if (productInfo != null) {
                cartItemsHandler.deleteOneSingleCartItem(productInfo.getIdProduct(), items.getProductSize());
                itemsArrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
