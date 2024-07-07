package com.example.laptrinhdidong_finalproject.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laptrinhdidong_finalproject.R;

public class Fragment_Revenue extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EditText e = (EditText) container.findViewById(R.id.edtToYear);
        return inflater.inflate(R.layout.activity_revenue, container, false);
    }
}
