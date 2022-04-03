package com.example.tae;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Admin_profile extends Fragment {


    TextInputLayout regName, regEmail, regPhoneNo, regPassword;
    Button update;

    DatabaseReference reference;
    String namefromDB, emailfromDB, phonenofromDB, passwordfromDB, pusername;

    public Admin_profile() {
        // Required empty public constructor
    }

      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view = inflater.inflate(R.layout.activity_user_profile, container, false);
          userprofile(view);
          return view;
    }
    public void userprofile(View container) {
        regName = container.findViewById(R.id.regName);
        regEmail = container.findViewById(R.id.regEmail);
        regPhoneNo = container.findViewById(R.id.regPhoneNo);
        regPassword = container.findViewById(R.id.regPassword);
        update = container.findViewById(R.id.updateBtn);

        showalldata();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ispasswordchange() | isnamechange() | isemailchange() | isphonenochange()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Data is updated", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Data is same or invalid data", Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private boolean isnamechange() {
        if (!namefromDB.equals(regName.getEditText().getText().toString()) && validateName()) {

            reference.child(pusername).child("name").setValue(regName.getEditText().getText().toString());
            namefromDB = regName.getEditText().getText().toString();

            return true;
        } else {
            return false;
        }

    }
    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;

        } else {
            regEmail.setError(null);
            return true;
        }
    }
    private boolean isemailchange() {
        if (!emailfromDB.equals(regEmail.getEditText().getText().toString()) && validateEmail()) {

            reference.child(pusername).child("email").setValue(regEmail.getEditText().getText().toString());
            emailfromDB = regEmail.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private Boolean validatePhoneNumber() {
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            return true;
        }
    }
    private boolean isphonenochange() {
        if (!phonenofromDB.equals(regPhoneNo.getEditText().getText().toString()) && validatePhoneNumber()) {

            reference.child(pusername).child("phoneNo").setValue(regPhoneNo.getEditText().getText().toString());
            phonenofromDB = regPhoneNo.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }
    private boolean ispasswordchange() {
        if (!passwordfromDB.equals(regPassword.getEditText().getText().toString()) && validatePassword()) {

            reference.child(pusername).child("password").setValue(regPassword.getEditText().getText().toString());
            passwordfromDB = regPassword.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }
    public void setUserName(String userName){
        pusername = userName;

    }
    private void showalldata() {

        reference = FirebaseDatabase.getInstance().getReference("admins");

        Query checkuser = reference.orderByChild("username").equalTo(pusername);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                namefromDB = snapshot.child(pusername).child("name").getValue(String.class);
                emailfromDB = snapshot.child(pusername).child("email").getValue(String.class);
                phonenofromDB = snapshot.child(pusername).child("phoneNo").getValue(String.class);
                passwordfromDB = snapshot.child(pusername).child("password").getValue(String.class);

                regName.getEditText().setText(namefromDB);
                regEmail.getEditText().setText(emailfromDB);
                regPhoneNo.getEditText().setText(phonenofromDB);
                regPassword.getEditText().setText(passwordfromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}