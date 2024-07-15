package com.example.laptrinhdidong_finalproject.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductCategoriesHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.Utils;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.R;
import com.example.laptrinhdidong_finalproject.View.Activity_Deleting_ProductCategories;
import com.example.laptrinhdidong_finalproject.View.Activity_Deleting_Products;
import com.example.laptrinhdidong_finalproject.View.CustomAdapter_ListView_Fragment_Product;
import com.example.laptrinhdidong_finalproject.View.CustomAdapter_ListView_Fragment_ProductCategories;
import com.example.laptrinhdidong_finalproject.View.CustomAdapter_ListView_Fragment_ProductCategories;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class Fragment_ManageProductCatalog extends Fragment {

    static final String DB_NAME = "drinkingmanager";
    static final int DB_VERSION = 1;
    static final String TABLE_NAME = "ProductCategories";
    static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";
    ProductCategoriesHandler categoryHandler;
    ListView lvCategories;
    Button btnAddCategory, btnConfirmAddCategory, btnCancelAdding, btnDeleteCategory, btnUpdateCategory;
    ImageButton btnUploadCategoryImage;
    EditText edtCategoryID, edtCategoryName, edtCategoryDescription;
    ImageView imgAddedCategory;
    ArrayList<ProductCategories> categoriesArrayList = new ArrayList<>();
    CustomAdapter_ListView_Fragment_ProductCategories customListViewCategories;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri selectedImage = result.getData().getData();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(requireActivity().getContentResolver().openInputStream(selectedImage));
                    imgAddedCategory.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

//                    byte[] imageBytes = byteArrayOutputStream.toByteArray(); // Sử dụng imageBytes nếu cần lưu trữ trong cơ sở dữ liệu

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Unable to load image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_funtion_product_catalog, container, false);
        addControl(view);
        categoryHandler = new ProductCategoriesHandler(getContext(), DB_NAME, null, DB_VERSION);
        loadDBCategoryData();
        addEvent();
        return view;
    }

    void addControl(View view) {
        lvCategories = view.findViewById(R.id.lvCategories);
        btnAddCategory = view.findViewById(R.id.btnAddCategory);
        btnDeleteCategory = view.findViewById(R.id.btnDeleteCategory);
        btnUpdateCategory = view.findViewById(R.id.btnUpdateCategory);
    }
    void addEvent()
    {
        btnAddCategory.setOnClickListener(v -> showDialog());

        btnDeleteCategory.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Activity_Deleting_ProductCategories.class);
            startActivity(intent);
        });
        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Activity_Updating_ProductCategories.class);
                startActivity(intent);
            }
        });
    }

    void loadDBCategoryData()
    {
        categoriesArrayList = categoryHandler.loadAllDataOfProductCategories();
        customListViewCategories = new CustomAdapter_ListView_Fragment_ProductCategories(getContext(),
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
        addDialogEvent(addCategoryDialog, customView);
    }
    void addDialogEvent(Dialog addCategoryDialog, View customView)
    {
        btnConfirmAddCategory = customView.findViewById(R.id.btnConfirmAddCategory);
        btnCancelAdding = customView.findViewById(R.id.btnCancelAdding);
        btnUploadCategoryImage = customView.findViewById(R.id.btnUploadCategoryImage);
        edtCategoryID = customView.findViewById(R.id.edtCategoryID);
        edtCategoryName = customView.findViewById(R.id.edtCategoryName);
        edtCategoryDescription = customView.findViewById(R.id.edtCategoryDescription);
        imgAddedCategory = customView.findViewById(R.id.imgAddedCategory);

        btnUploadCategoryImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncher.launch(intent);
        });

        btnConfirmAddCategory.setOnClickListener(v -> {
            String categoryID = edtCategoryID.getText().toString();
            String categoryName = edtCategoryName.getText().toString();
            String categoryDescription = edtCategoryDescription.getText().toString();
            Bitmap insertImage = Utils.getBitmapFromImageView(imgAddedCategory);

            if(!categoryID.isEmpty() || !categoryName.isEmpty())
            {
                if (!categoryHandler.checkDuplicateCategoryData(categoryID, categoryName, categoriesArrayList)) {
                    Toast.makeText(getActivity(), "The category ID or Name has existed!", Toast.LENGTH_SHORT).show();
                }
                else {
                    ProductCategories category = new ProductCategories(categoryID, categoryName, categoryDescription, Utils.getBytesFromBitmap(insertImage));
                    categoryHandler.insertNewData(category);
                    loadDBCategoryData();
                    addCategoryDialog.dismiss();
                }
            }
            else {
                Toast.makeText(getActivity(), "Null information!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelAdding.setOnClickListener(v -> addCategoryDialog.dismiss());
    }
}
