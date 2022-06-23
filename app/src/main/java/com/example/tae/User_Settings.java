package com.example.tae;

import static com.example.tae.User_Login_Page.Uniquecode;
import static com.example.tae.User_Login_Page.filename;
import static com.example.tae.User_Login_Page.password;
import static com.example.tae.User_Login_Page.reguserbus;
import static com.example.tae.User_Login_Page.username;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tae.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class User_Settings extends Fragment {
    public MaterialTimePicker picker;
    public String CHANNEL_ID = "busnotificationchannel";
    Button buslogout, settime, busnumupd;
    TextInputLayout regbus;
    FirebaseAuth mauth;
    SharedPreferences sharedPreferences;
    private ActivityMainBinding binding;

    public User_Settings() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // mauth=FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.usersettings, container, false);
        // binding = ActivityMainBinding.inflate(getLayoutInflater());
        //binding.getRoot();
        sessionLogout(view);
        return view;
    }

    private void sessionLogout(View container) {
        buslogout = container.findViewById(R.id.Bus_logout);


        buslogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FirebaseAuth.getInstance().signOut();
                //mauth.signOut();
                //signOutUser();

                sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(filename, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(username);
                editor.remove(password);
                editor.commit();
                getActivity().finish();
            }
        });
    }

    private void signOutUser() {
        Toast.makeText(getActivity().getApplicationContext(), "logout", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        getActivity().finish();

    }



    }
