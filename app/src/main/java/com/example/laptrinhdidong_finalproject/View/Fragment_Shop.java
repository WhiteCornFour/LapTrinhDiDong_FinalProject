package com.example.laptrinhdidong_finalproject.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.laptrinhdidong_finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Shop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Shop extends Fragment {

    Button btnShop;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Shop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Shop.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Shop newInstance(String param1, String param2) {
        Fragment_Shop fragment = new Fragment_Shop();
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
// Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__shop,
                container, false);
        btnShop=(Button) view.findViewById(R.id.btnShop);
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("key","hello");
                FragmentManager fm=getParentFragmentManager();
                fm.setFragmentResult("keyM",bundle);
            }
        });
        return view;
    }
}