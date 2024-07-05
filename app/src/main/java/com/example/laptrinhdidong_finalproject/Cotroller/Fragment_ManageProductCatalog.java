package com.example.laptrinhdidong_finalproject.Cotroller;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.R;
import com.example.laptrinhdidong_finalproject.View.CustomAdapterListViewFragment_Product;
import com.example.laptrinhdidong_finalproject.View.CustomListViewCategories;

import java.util.ArrayList;

public class Fragment_ManageProductCatalog extends Fragment {

    static final String DB_NAME = "drinkingmanager";
    static final int DB_VERSION = 1;
    static final String TABLE_NAME = "ProductCategories";
    static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";
    CategoryHandler categoryHandler;
    ListView lvCategories;
    Button btnAddCategory, btnConfirmAddCategory, btnCancelAdding;
    ImageButton btnUploadCategoryImage;
    EditText edtCategoryID, edtCategoryName;
    ImageView imgAddedCategory;
    ArrayList<ProductCategories> categoriesArrayList = new ArrayList<>();
    CustomListViewCategories customListViewCategories;

    SQLiteDatabase sqLiteDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_funtion_product_catalog, container, false);
        addControl(view);
        categoryHandler = new CategoryHandler(getContext(), DB_NAME, null, DB_VERSION);
        loadDBCategoryData();
        addEvent();
        return view;
    }

    void addControl(View view) {
        lvCategories = (ListView) view.findViewById(R.id.lvCategories);
        btnAddCategory = (Button) view.findViewById(R.id.btnAddCategory);
    }
    void addEvent()
    {
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    void loadDBCategoryData()
    {
        categoriesArrayList = categoryHandler.loadData();
        customListViewCategories = new CustomListViewCategories(getContext(),
                R.layout.layout_gridview_categorymanager, categoriesArrayList);
        lvCategories.setAdapter(customListViewCategories);
    }
    void showDialog()
    {
        Dialog addCategoryDialog = new Dialog(getContext()); // Tạo dialog thêm category

        // Sử dụng custom layout cho dialog
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.layout_addcategory_dialog, null);
        addCategoryDialog.setContentView(customView);

        // Lấy kích thước màn hình
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;

        addCategoryDialog.getWindow().setLayout(width, height); //Thiết lập kích thước hiển thị trên màn hình cho dialog
        addCategoryDialog.show();

        // Tham chiếu đến button trong dialog thông qua customView
        btnConfirmAddCategory = customView.findViewById(R.id.btnConfirmAddCategory);
        btnCancelAdding = customView.findViewById(R.id.btnCancelAdding);
        btnUploadCategoryImage = customView.findViewById(R.id.btnUploadCategoryImage);
        edtCategoryID = customView.findViewById(R.id.edtCategoryID);
        edtCategoryName = customView.findViewById(R.id.edtCategoryName);
        imgAddedCategory = customView.findViewById(R.id.imgAddedCategory);

//        btnUploadCategoryImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        btnConfirmAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryID = edtCategoryID.getText().toString();
                String categoryName = edtCategoryName.getText().toString();

//                Bitmap categoryImage = Utils.getBitMapFromDrawable(customView.getContext(), R.drawable.empty_img);
                ProductCategories category = new ProductCategories(categoryID, categoryName, null);
                categoryHandler.insertNewData(category);

                //Load lại data và set lại adapter
                categoriesArrayList = categoryHandler.loadData();
                customListViewCategories = new CustomListViewCategories(getContext(),
                        R.layout.layout_gridview_categorymanager, categoriesArrayList);
                lvCategories.setAdapter(customListViewCategories);
                addCategoryDialog.dismiss();
            }
        });
        btnCancelAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategoryDialog.dismiss();
            }
        });
    }
}
