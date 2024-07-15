package com.example.laptrinhdidong_finalproject.View;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.ProductCategoriesHandler;
import com.example.laptrinhdidong_finalproject.Model.ProductCategories;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Home extends Fragment implements OnItemClickListener {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    EditText edtSearchHome;
    Button btnSearchHome;
    RecyclerView recyclerViewHome;
    ListView lvProductHomeCus;

    CustomRecylerView_Home_Cus customRecylerView_home_cus;
    ArrayList<ProductCategories> productCategories = new ArrayList<>();

    ProductCategoriesHandler productCategoriesHandler;
    SQLiteDatabase sqLiteDatabase;

    ArrayList<Products> productArrayList = new ArrayList<>();
    ArrayList<Products> productsForSearch = new ArrayList<>();

    ArrayList<Products> productForRecylerView = new ArrayList<>();
    CustomAdapterLV_Product_Home customAdapter;

    com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler productHandler;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Fragment_Home() {
        // Required empty public constructor
    }

    public static Fragment_Home newInstance(String param1, String param2) {
        Fragment_Home fragment = new Fragment_Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreateState(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__home, container, false);
        addControl(view);

        productCategoriesHandler = new ProductCategoriesHandler(getContext(), DB_NAME,
                null, DB_VERSION);
        loadDataOfRecylerView();

        productHandler = new com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler(getContext(),
                DB_NAME, null, DB_VERSION);
        loadDataOfListView(productArrayList);

        addEvent();
        return view;
    }

    void loadDataOfRecylerView() {
        productCategories = productCategoriesHandler.loadAllDataOfProductCategories();
        customRecylerView_home_cus = new CustomRecylerView_Home_Cus(getActivity(), productCategories, this);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, true);

        recyclerViewHome.setLayoutManager(layoutManager);
        recyclerViewHome.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHome.setAdapter(customRecylerView_home_cus);
    }

    void loadDataOfListView(ArrayList<Products> products) {
        products = productHandler.loadAllDataOfProducts();
        customAdapter = new CustomAdapterLV_Product_Home(getContext(), R.layout.layout_custom_lv_product_home, products);
        lvProductHomeCus.setAdapter(customAdapter);
    }

    void addControl(View view) {
        recyclerViewHome = view.findViewById(R.id.recyclerViewHome);
        lvProductHomeCus = view.findViewById(R.id.lvProductHomeCus);
        edtSearchHome = view.findViewById(R.id.edtSearchHome);
        btnSearchHome = view.findViewById(R.id.btnSearchHome);
    }

    void addEvent() {
        btnSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st = edtSearchHome.getText().toString();
                if (st.trim().isEmpty()) {
                    loadDataOfListView(productArrayList);
                } else {
                    productsForSearch = productHandler.returnResultForSearchHome(st);
                    if (productsForSearch.size() == 0) {
                        Toast.makeText(getActivity(), "Sorry, no results were returned for " + st, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        customAdapter = new CustomAdapterLV_Product_Home(getContext(),
                                R.layout.layout_custom_lv_product_home, productsForSearch);
                        lvProductHomeCus.setAdapter(customAdapter);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(ProductCategories productCategories) {
        Toast.makeText(getActivity(), "Selected Category: " + productCategories.getNameCategory(), Toast.LENGTH_SHORT).show();
        String kq = productCategories.getIdCategory();
        productForRecylerView = productHandler.returnResultForRecylerView(kq);
        customAdapter = new CustomAdapterLV_Product_Home(getContext(),
                R.layout.layout_custom_lv_product_home, productForRecylerView);
        lvProductHomeCus.setAdapter(customAdapter);
    }
}
