package com.example.laptrinhdidong_finalproject.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laptrinhdidong_finalproject.Cotroller.RevenueHandler;
import com.example.laptrinhdidong_finalproject.Model.Revenue;
import com.example.laptrinhdidong_finalproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Fragment_Revenue extends Fragment {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    RevenueHandler revenueHandler;
    Spinner spinnerMonth, spinnerYear;
    Button btnFilterYear;

    ImageView imgRF;
    ListView lvRevenueByYear;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> adapter;
    ArrayList<String> stringArrayListMonth = new ArrayList<>();
    ArrayList<String> stringArrayListYear = new ArrayList<>();
    ArrayList<String> dataForSearch = new ArrayList<>();
    ArrayList<String> dataLV = new ArrayList<>();
    String itemMonth = "";
    String itemYear = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_revenue, container, false);
        addControl(view);
        revenueHandler = new RevenueHandler(getActivity(), DB_NAME, null, DB_VERSION);
        loadDataSpinnerMonth();
        loadDataSpinnerYear();
        loadDataListView();
        addEvent();
        return view;
    }

    void addControl(View view) {
        lvRevenueByYear = view.findViewById(R.id.lvRevenueByYear);
        spinnerMonth = view.findViewById(R.id.spinnerMonth);
        spinnerYear = view.findViewById(R.id.spinnerYear);
        btnFilterYear = view.findViewById(R.id.btnFilterYear);
        imgRF = view.findViewById(R.id.imgRF);
    }

    void addEvent() {
        imgRF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDataListView();
            }
        });
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!stringArrayListMonth.isEmpty()) {
                    String kq = stringArrayListMonth.get(i);
                    if (kq.isEmpty()) {
                        Calendar calendar = Calendar.getInstance();
                        int monthNow = calendar.get(Calendar.MONTH) + 1;
                        itemMonth = String.valueOf(monthNow);
                    } else {
                        itemMonth = kq;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!stringArrayListYear.isEmpty()) {
                    String kq = stringArrayListYear.get(i);
                    if (kq.isEmpty()) {
                        Calendar calendar = Calendar.getInstance();
                        int yearNow = calendar.get(Calendar.YEAR);
                        itemYear = String.valueOf(yearNow);
                    } else {
                        itemYear = kq;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
        btnFilterYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataForSearch = getDataListView(revenueHandler.returnResultForSearch(itemMonth, itemYear));
                arrayAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dataForSearch);
                lvRevenueByYear.setAdapter(arrayAdapter);
            }
        });
    }

    void loadDataSpinnerMonth() {
        stringArrayListMonth = getDataMonth(revenueHandler.loadAllDataOfRevenue());
        arrayAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, stringArrayListMonth);
        spinnerMonth.setAdapter(arrayAdapter);
    }

    void loadDataSpinnerYear() {
        stringArrayListYear = getDataYear(revenueHandler.loadAllDataOfRevenue());
        arrayAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, stringArrayListYear);
        spinnerYear.setAdapter(arrayAdapter);
    }

    void loadDataListView() {
        dataLV = getDataListView(revenueHandler.loadAllDataOfRevenue());
        Log.d("DatalV", String.valueOf(dataLV));
        adapter = new ArrayAdapter<>(getActivity(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, dataLV);
        lvRevenueByYear.setAdapter(adapter);
    }

    ArrayList<String> getDataMonth(ArrayList<Revenue> revenueArrayList) {
        Set<String> uniqueMonths = new HashSet<>();
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Revenue r : revenueArrayList) {
            String month = String.valueOf(r.getMonth());
            if (!uniqueMonths.contains(month)) {
                uniqueMonths.add(month);
                stringArrayList.add(month);
            }
        }
        return stringArrayList;
    }

    ArrayList<String> getDataYear(ArrayList<Revenue> revenueArrayList) {
        Set<String> uniqueYears = new HashSet<>();
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Revenue r : revenueArrayList) {
            String year = String.valueOf(r.getYear());
            if (!uniqueYears.contains(year)) {
                uniqueYears.add(year);
                stringArrayList.add(year);
            }
        }
        return stringArrayList;
    }

    ArrayList<String> getDataListView(ArrayList<Revenue> revenueArrayList) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Revenue r : revenueArrayList) {
            String kq = r.getMonth() + "/" + r.getYear() + " - Total: " + r.getTotalRevenue();
            stringArrayList.add(kq);
        }
        return stringArrayList;
    }
}
