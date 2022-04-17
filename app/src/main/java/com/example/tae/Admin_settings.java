package com.example.tae;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Admin_settings extends Fragment {

    Button buslogout;
    FirebaseAuth mauth;

    public Admin_settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mauth=FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.usersettings, container, false);
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