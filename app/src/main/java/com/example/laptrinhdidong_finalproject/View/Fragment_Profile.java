package com.example.laptrinhdidong_finalproject.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.Cotroller.CustomerHandler;
import com.example.laptrinhdidong_finalproject.Model.Customer;
import com.example.laptrinhdidong_finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Profile extends Fragment {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;
    ImageView imgProfile;
    TextView tvNameProfile, tvAccProfile, tvEdit;
    Button btnLogOutProfile;

    String idCus = "";

    Customer customer;
    CustomerHandler customerHandler;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Profile newInstance(String param1, String param2) {
        Fragment_Profile fragment = new Fragment_Profile();
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
        View view = inflater.inflate(R.layout.fragment__profile, container, false);
        addControl(view);
        customerHandler = new CustomerHandler(getActivity(), DB_NAME, null, DB_VERSION);
        idCus = Fragment_Home.getIdCus();
        customer = customerHandler.loadInfOfOnePerson(idCus);
        tvNameProfile.setText(customer.getNameCustomer());
        tvAccProfile.setText(customer.getEmailCustomer());
        Bitmap bitmap = BitmapFactory.decodeByteArray(customer.getCustomerImage(),
                0, customer.getCustomerImage().length);
        if (bitmap == null)
        {
            imgProfile.setImageResource(R.drawable.avtadmin);
        }
        else {
            imgProfile.setImageBitmap(bitmap);
        }
        addEvent();
        return view;
    }

    void addControl(View view)
    {
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        tvNameProfile = (TextView) view.findViewById(R.id.tvNameProfile);
        tvAccProfile = (TextView) view.findViewById(R.id.tvAccProfile);
        btnLogOutProfile = (Button) view.findViewById(R.id.btnLogOutProfile);
        tvEdit = (TextView) view.findViewById(R.id.tvEdit);
    }

    void addEvent()
    {
        btnLogOutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Activity_Login_Customer.class);
                startActivity(intent);
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fragment_Profile", "Edit button clicked");
                Intent intent = new Intent(getActivity(), Activity_Detail_Profile_Cus.class);
                intent.putExtra("customer", customer);
                startActivity(intent);
            }
        });
    }
}