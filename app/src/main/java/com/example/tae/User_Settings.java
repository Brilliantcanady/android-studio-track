package com.example.tae;

import static com.example.tae.User_Login_Page.filename;
import static com.example.tae.User_Login_Page.password;
import static com.example.tae.User_Login_Page.username;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class User_Settings extends Fragment {
Button buslogout;
FirebaseAuth mauth;
SharedPreferences sharedPreferences;
    public User_Settings(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // mauth=FirebaseAuth.getInstance();
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
        Intent i=new Intent(getActivity().getApplicationContext(),MainActivity.class);
        
        i.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent. FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        getActivity().finish();
    
    }




}