package com.example.laptrinhdidong_finalproject.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.Cotroller.CartItemsHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CartsHandler;
import com.example.laptrinhdidong_finalproject.Model.CartItems;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Cart extends Fragment {
    ListView lvCartProduct;
    TextView tvTotalCart;
    Button btnOrderCart;
    ArrayList<CartItems> itemsArrayList = new ArrayList<>();
    CustomAdapter_ListView_Cart cartAdapter;
    CartItemsHandler cartItemsHandler;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Cart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Cart.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Cart newInstance(String param1, String param2) {
        Fragment_Cart fragment = new Fragment_Cart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        addControl(view);
        setDisplayCart();
        return view;
    }
    void addControl(View view)
    {
        lvCartProduct = view.findViewById(R.id.lvCartProduct);
        tvTotalCart = view.findViewById(R.id.tvTotalCart);
        btnOrderCart = view.findViewById(R.id.btnOrderCart);
    }
    public void setDisplayCart()
    {
        cartItemsHandler = new CartItemsHandler(getActivity());
        tvTotalCart.setText(String.valueOf(cartItemsHandler.sumTotalForCarts()));
        itemsArrayList = cartItemsHandler.loadCartItemsData();
        if (itemsArrayList.size() == 0)
        {
            btnOrderCart.setText("No item available in cart!");
            btnOrderCart.setEnabled(false);
            return;
        }
        cartAdapter = new CustomAdapter_ListView_Cart(getActivity(), R.layout.layout_custom_listview_cart, itemsArrayList);
        lvCartProduct.setAdapter(cartAdapter);
    }

}
