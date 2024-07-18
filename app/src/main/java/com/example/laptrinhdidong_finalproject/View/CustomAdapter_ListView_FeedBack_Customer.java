package com.example.laptrinhdidong_finalproject.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerFeedbackHandler;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.CustomerFeedbacks;
import com.example.laptrinhdidong_finalproject.R;

import java.util.List;
import java.util.Map;

public class CustomAdapter_ListView_FeedBack_Customer extends BaseAdapter {

    Context context;
    List<CustomerFeedbacks> feedbackList;
    List<Customer> CustomerList;
    LayoutInflater layoutInflater;
    Map<String, Customer> customerInfoMap;
    CustomerFeedbackHandler customerFeedbackHandler;

    public CustomAdapter_ListView_FeedBack_Customer(Context context, List<CustomerFeedbacks> feedbackList, List<Customer> customerList, LayoutInflater layoutInflater) {
        this.context = context;
        this.feedbackList = feedbackList;
        this.CustomerList = customerList;
        this.layoutInflater = layoutInflater;
    }


    @Override
    public int getCount() {
        return (feedbackList != null) ? feedbackList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (feedbackList != null && position < feedbackList.size()) ? feedbackList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater
                    .inflate(R.layout.layout_custom_adapter_listview_feedback
                            ,parent, false);
        }

        if (feedbackList != null && position < feedbackList.size()) {
            CustomerFeedbacks feedback = feedbackList.get(position);

            TextView tvFeedbackName = convertView.findViewById(R.id.tv_customer_name);


            TextView tvFeedbackTime = convertView.findViewById(R.id.tv_feedback_time);
            tvFeedbackTime.setText(feedback.getFeedbackTime());

            TextView tvFeedbackContent = convertView.findViewById(R.id.tv_feedback_content);
            tvFeedbackContent.setText(feedback.getFeedbackContent());

            // Tìm kiếm thông tin của khách hàng tương ứng với feedback này
            String customerID = feedback.getCustomerID();
            String customerName = "";
            if (customerInfoMap != null && customerInfoMap.containsKey(customerID)) {
                customerName = customerInfoMap.get(customerID).getNameCustomer();
            }
            tvFeedbackName.setText(customerName);
        }

        return convertView;
    }
}
