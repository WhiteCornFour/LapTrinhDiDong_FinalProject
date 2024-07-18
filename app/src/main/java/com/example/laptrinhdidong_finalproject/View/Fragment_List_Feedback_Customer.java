package com.example.laptrinhdidong_finalproject.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerFeedbackHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.CustomerFeedbacks;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_List_Feedback_Customer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_List_Feedback_Customer extends Fragment {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView lv_feedback_list;
    CustomerFeedbackHandler customerFeedbackHandler;
    CustomerHandler customerHandler;
    ArrayList<CustomerFeedbacks> feedbacksList = new ArrayList<>();
    ArrayList<Customer> customerList = new ArrayList<>();
    String customerID;
    String customerName;
    int feedbackID = 0;
    Map<String, Customer> customerInfoMap;


    public Fragment_List_Feedback_Customer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_List_Feedback_Customer.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_List_Feedback_Customer newInstance(String param1, String param2) {
        Fragment_List_Feedback_Customer fragment = new Fragment_List_Feedback_Customer();
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

            ////-------
            getParentFragmentManager().setFragmentResultListener("feedbackResult", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    if (requestKey.equals("feedbackResult")) {
                        customerID = result.getString("customerID", String.valueOf(-1));
                        customerName = result.getString("customerName", "");
                    }
                }
            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__list__feedback__customer, container, false);

        addControl(view);
        customerFeedbackHandler = new CustomerFeedbackHandler(getActivity(),
                DB_NAME, null, DB_VERSION);
        customerHandler = new CustomerHandler(getActivity(),
                DB_NAME, null, DB_VERSION);

        feedbacksList = customerFeedbackHandler.loadAllFeedbacks();
        customerList = customerHandler.loadAllDataOfCustomer();
        customerInfoMap = customerHandler.getCustomerInfoMap();
        if (customerInfoMap == null) {
            customerInfoMap = new HashMap<>();
        }

        CustomAdapter_ListView_FeedBack_Customer adapter = new CustomAdapter_ListView_FeedBack_Customer(
                getActivity(), feedbacksList, customerList, LayoutInflater.from(getActivity())
        );

        lv_feedback_list.setAdapter(adapter);

        lv_feedback_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                CustomerFeedbacks customerFeedbacks = feedbacksList.get(i);
                int idFeedBack = customerFeedbacks.getFeedbackID();
                Log.d("idFeedBack", String.valueOf(idFeedBack));
                showDeleteFeedbackDialog(customerFeedbacks, idFeedBack);
                return false;
            }
        });
        lv_feedback_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        addEvent();
        return view;
    }
    void addControl(View view){
        lv_feedback_list = view.findViewById(R.id.lv_feedback_list);
    }

    void addEvent(){
      lv_feedback_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
              CustomerFeedbacks customerFeedbacks = feedbacksList.get(i);
              Log.d("idfeedback khi longclicklistener", String.valueOf(customerFeedbacks.getCustomerID()));
              return false;
          }
      });
    }
    private void showDeleteFeedbackDialog(CustomerFeedbacks customerFeedbacks, int position) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Xóa phản hồi")
                .setMessage("Bạn có chắc chắn muốn xóa phản hồi này?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customerFeedbackHandler.deleteFeedback(i);
                        feedbacksList.remove(position);
                        ((CustomAdapter_ListView_FeedBack_Customer) lv_feedback_list.getAdapter()).notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Phản hồi đã được xóa", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }
}


