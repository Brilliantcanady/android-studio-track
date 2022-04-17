package com.example.tae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_Sign_Up extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword,regcodeno;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);


        regName = findViewById(R.id.regName);
        regUsername = findViewById(R.id.regUsername);
        regEmail = findViewById(R.id.regEmail);
        regPhoneNo = findViewById(R.id.regPhoneNo);
        regPassword = findViewById(R.id.regPassword);
        regBtn = findViewById(R.id.regBtn);
        regToLoginBtn=findViewById(R.id.regToLoginBtn);
        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(User_Sign_Up.this, User_Login_Page.class);
                startActivity(i);
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateName() | !validatePassword() | !validatePhoneNumber() | !validateEmail() | !validateUsername())
                {
                    return;
                }

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                //Get all the values
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                User_Schema helperClass = new User_Schema(name, username, email, phoneNo, password);
                reference.child(username).setValue(helperClass);
                Toast.makeText(User_Sign_Up.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(User_Sign_Up.this, User_Login_Page.class);
                startActivity(i);
            }
        });
    }//onCreate Method End

    private Boolean validateName(){
        String val =regName.getEditText().getText().toString();
         if(val.isEmpty())
         {
             regName.setError("Field cannot be empty");
             return false;
         }
         else{
             regName.setError(null);
             regName.setErrorEnabled(false);
             return true;
         }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace="(?=\\s+$)";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15){
            regUsername.setError("Username too long");
        return false;
    }
        else if(val.matches(noWhiteSpace)){
            regUsername.setError("White Spaces not allowed");
            return false;
        }
        else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val =regEmail.getEditText().getText().toString();
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty())
        {
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;

        }
        else{
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNumber(){
        String val =regPhoneNo.getEditText().getText().toString();

        if(val.isEmpty())
        {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        }
        else{
            regPhoneNo.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val =regPassword.getEditText().getText().toString();
        String passwordVal="^"+
                "(?=.*[0-9])"+
                "(?=.*[a-z])"+
                "(?=.*[A-Z])"+
                "(?=.*[a-zA-Z])"+
                "(?=.*[@#$%^&+=])"+
                "(?=\\S+$)"+
                ".{4,}"+
                "$";
        if(val.isEmpty())
        {
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(passwordVal)){
            regPassword.setError("Password is too weak");
            return false;
        }
        else{
            regPassword.setError(null);
            return true;
        }
    }



}