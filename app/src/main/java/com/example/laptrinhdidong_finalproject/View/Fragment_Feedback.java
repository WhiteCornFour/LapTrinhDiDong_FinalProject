package com.example.laptrinhdidong_finalproject.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerFeedbackHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Model.CustomerFeedbacks;
import com.example.laptrinhdidong_finalproject.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Feedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Feedback extends Fragment {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    ListView lv_feedback_list;
    Button btn_add_feedback_customer;

    CustomAdapter_ListView_FeedBack_Customer customAdapter_listView_feedBack_customer;
    CustomerFeedbackHandler customerFeedbackHandler;
    CustomerHandler customerHandler;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Feedback() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Feedback.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Feedback newInstance(String param1, String param2) {
        Fragment_Feedback fragment = new Fragment_Feedback();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__feedback, container, false);


        btn_add_feedback_customer = view.findViewById(R.id.btn_add_feedback_customer);
        //---------------
        btn_add_feedback_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Add_Feedback_Customer fragment_add_feedback_customer = new Fragment_Add_Feedback_Customer();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_feedback,fragment_add_feedback_customer);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

}
