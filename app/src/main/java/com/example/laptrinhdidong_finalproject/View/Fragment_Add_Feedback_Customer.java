package com.example.laptrinhdidong_finalproject.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerFeedbackHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.CustomerFeedbacks;
import com.example.laptrinhdidong_finalproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Add_Feedback_Customer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Add_Feedback_Customer extends Fragment {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    TextView edt_feedback;
    Button btn_submit_feedback;
    CustomerHandler customerHandler;

    CustomerFeedbackHandler customerFeedbackHandler;

    String customerName;
    String customerID = Fragment_Home.getIdCus();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Add_Feedback_Customer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Add_Feedback_Customer.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Add_Feedback_Customer newInstance(String param1, String param2) {
        Fragment_Add_Feedback_Customer fragment = new Fragment_Add_Feedback_Customer();
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
        //--------
        if (getArguments() != null) {
            customerID = getArguments().getString("customerID", String.valueOf(-1));
            customerName = getArguments().getString("customerName", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__add__feedback__customer, container, false);

        edt_feedback = view.findViewById(R.id.edt_feedback);
        btn_submit_feedback = view.findViewById(R.id.btn_submit_feedback);

        customerFeedbackHandler = new CustomerFeedbackHandler(getActivity(), DB_NAME, null, DB_VERSION);
        customerHandler = new CustomerHandler(getActivity(), DB_NAME, null, DB_VERSION);
        btn_submit_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        return view;
    }

    private void submitFeedback() {
        String feedbackContent = edt_feedback.getText().toString();
        if (feedbackContent.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập nhận xét của bạn", Toast.LENGTH_SHORT).show();
            return;
        }

        //lấy được thời gian hiện tại hiển thị lên listview
        String feedbackTime = getCurrentTimestamp();

        //lưu xún cơ sở dữ liệu
        CustomerFeedbacks feedback = new CustomerFeedbacks(customerID, feedbackContent, feedbackTime);
        customerFeedbackHandler.insertFeedback(feedback);

//        chuyển về trang feedback chính
        Fragment_List_Feedback_Customer fragmentListFeedback = new Fragment_List_Feedback_Customer();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_feedback, fragmentListFeedback);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


//        // Thêm phản hồi vào cơ sở dữ liệu và nhận feedbackID trả về
//        long feedbackID = customerFeedbackHandler.insertFeedback(feedback);
//
//        if (feedbackID != -1) {
//            Toast.makeText(requireContext(), "Gửi phản hồi thành công", Toast.LENGTH_SHORT).show();
//            //
//            Bundle result = new Bundle();
//            result.putString("customerID", customerID);
//            result.putString("customerName", customerName);
//            getParentFragmentManager().setFragmentResult("feedbackResult", result);
//
//            //chuyển về trang feedback chính
//            Fragment_List_Feedback_Customer fragmentListFeedback = new Fragment_List_Feedback_Customer();
//            FragmentManager fragmentManager = getParentFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//            fragmentTransaction.replace(R.id.fragment_feedback, fragmentListFeedback);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        } else {
//            Toast.makeText(requireContext(), "Đã xảy ra lỗi khi gửi phản hồi", Toast.LENGTH_SHORT).show();
//        }

    }


    //hàm nhận thời gian hiện tại của máy ảo
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}