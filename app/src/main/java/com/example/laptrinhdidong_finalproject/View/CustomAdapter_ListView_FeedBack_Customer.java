package com.example.laptrinhdidong_finalproject.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerFeedbackHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.ProductsHandler;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.Model.CustomerFeedbacks;
import com.example.laptrinhdidong_finalproject.Model.Products;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomAdapter_ListView_FeedBack_Customer extends ArrayAdapter {

    Context context;
    int layoutItem;
    ArrayList<CustomerFeedbacks> customerFeedbacks = new ArrayList<>();
    private Map<String, Customer> cusInFor = CustomerHandler.getCustomerInfoMap();
    public CustomAdapter_ListView_FeedBack_Customer(@NonNull Context context, int layoutItem, @NonNull ArrayList<CustomerFeedbacks> customerFeedbacks) {
        super(context, layoutItem, customerFeedbacks);
        this.context = context;
        this.layoutItem = layoutItem;
        this.customerFeedbacks = customerFeedbacks;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CustomerFeedbacks ct = customerFeedbacks.get(position);
        Customer cusInfor = cusInFor.get(ct.getCustomerID());
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        }

        TextView tv_customer_name = convertView.findViewById(R.id.tv_customer_name);
        if (cusInfor != null)
        {
            tv_customer_name.setText(cusInfor.getNameCustomer());
        }
        TextView tv_feedback_time = convertView.findViewById(R.id.tv_feedback_time);
        tv_feedback_time.setText(ct.getFeedbackTime());
        TextView tv_feedback_content = convertView.findViewById(R.id.tv_feedback_content);
        tv_feedback_content.setText(ct.getFeedbackContent());

        return convertView;
    }
}
