package com.example.tae;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

public class Testing extends Fragment {

    TextInputLayout name,to,from,mob,details,busno;
    TextInputLayout drivername,to_city,from_city,mob_no,bus_no;
    Button button;

    DatabaseReference driverDbRef;
    public Testing() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_testing, container, false);
        update_bus_info(view);
        return view;
    }

    public void update_bus_info(View container) {

    }
    }