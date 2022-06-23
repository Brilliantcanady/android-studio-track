package com.example.tae;

import static com.example.tae.Admin_LoginPage.adminUniquecode;
import static com.example.tae.Admin_LoginPage.adminfilename;
import static com.example.tae.Admin_LoginPage.adminreguserbus;
import static com.example.tae.User_Login_Page.Uniquecode;
import static com.example.tae.User_Login_Page.filename;
import static com.example.tae.User_Login_Page.reguserbus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Admin_settings extends Fragment {

    Button buslogout, settime, busnumupd;
    TextInputLayout regbus;
    FirebaseAuth mauth;
    SharedPreferences sharedPreferences;
    public Admin_settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mauth=FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_admin_settings, container, false);
        sessionLogout(view);
        return view;
    }
    private void sessionLogout(View container) {



        buslogout=container.findViewById(R.id.Bus_logout);
        buslogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FirebaseAuth.getInstance().signOut();
                mauth.signOut();
                signOutUser();
            }
        });
    }

    private void signOutUser() {
        Toast.makeText(getActivity().getApplicationContext(), "logout", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getActivity().getApplicationContext(),MainActivity.class);

        i.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent. FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        getActivity().finish();

    }


}