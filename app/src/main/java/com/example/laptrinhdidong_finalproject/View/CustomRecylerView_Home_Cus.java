package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

public class CustomRecylerView_Home_Cus extends RecyclerView.Adapter<CustomRecylerView_Home_Cus.ViewHolder> {

    private Context context;
    private ArrayList<ProductCategories> categoriesArrayList;
    private OnItemClickListener onItemClickListener;

    public CustomRecylerView_Home_Cus(Context context, ArrayList<ProductCategories> categoriesArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoriesArrayList = categoriesArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_recyler_view_home_cus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCategories productCategories = categoriesArrayList.get(position);
        holder.tvRecyler.setText(productCategories.getNameCategory());
        if (productCategories.getImageCategory() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productCategories.getImageCategory(),
                    0, productCategories.getImageCategory().length);
            holder.imgRecyler.setImageBitmap(bitmap);
        } else {
            holder.imgRecyler.setImageResource(R.drawable.empty_img); // set a placeholder image
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(productCategories);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRecyler;
        public ImageView imgRecyler;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRecyler = itemView.findViewById(R.id.tvRecyler);
            imgRecyler = itemView.findViewById(R.id.imgRecyler);
        }
    }
}
