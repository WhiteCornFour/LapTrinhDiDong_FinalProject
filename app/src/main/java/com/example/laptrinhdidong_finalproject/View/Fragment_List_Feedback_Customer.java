package com.example.laptrinhdidong_finalproject.View;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerFeedbackHandler;
import com.example.laptrinhdidong_finalproject.Model.CustomerFeedbacks;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_List_Feedback_Customer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_List_Feedback_Customer extends Fragment {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    ListView lv_feedback_list;
    CustomerFeedbackHandler customerFeedbackHandler;
    CustomAdapter_ListView_FeedBack_Customer customerFeedbacksArrayAdapter;
    ArrayList<CustomerFeedbacks> feedbacksList = new ArrayList<>();

    String customerID;
    String customerName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

            getParentFragmentManager().setFragmentResultListener("feedbackResult", this,
                    new FragmentResultListener() {
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
        feedbacksList = customerFeedbackHandler.loadAllFeedbacks();
        customerFeedbacksArrayAdapter = new CustomAdapter_ListView_FeedBack_Customer(getActivity(),
                R.layout.layout_custom_adapter_listview_feedback, feedbacksList);
        lv_feedback_list.setAdapter(customerFeedbacksArrayAdapter);

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
                CustomerFeedbacks fb = feedbacksList.get(i);  // Sử dụng feedbacksList thay vì feedbacksArrayList
                AlertDialog alertDialog = createAlertDialogDeleteProducts(fb.getFeedbackID());
                Log.d("Feedback id: ", String.valueOf(fb.getFeedbackID()));  // Sử dụng String.valueOf để in giá trị feedbackID
                alertDialog.show();
                return true;  // Trả về true để biểu thị rằng sự kiện đã được xử lý
            }
        });
    }

    private androidx.appcompat.app.AlertDialog createAlertDialogDeleteProducts(String id) {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Feedback");
        builder.setMessage("Are you sure to delete the selected feedback?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                customerFeedbackHandler.deleteFeedback(id);
                Toast.makeText(getActivity(), "Feedback deleted", Toast.LENGTH_SHORT).show();
                feedbacksList = customerFeedbackHandler.loadAllFeedbacks();
                customerFeedbacksArrayAdapter = new CustomAdapter_ListView_FeedBack_Customer(getActivity(),
                        R.layout.layout_custom_adapter_listview_feedback, feedbacksList);
                lv_feedback_list.setAdapter(customerFeedbacksArrayAdapter);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }
}
