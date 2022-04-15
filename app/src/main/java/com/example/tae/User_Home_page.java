package com.example.tae;

import static com.example.tae.User_Login_Page.filename;
import static com.example.tae.User_Login_Page.username;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class User_Home_page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

        BottomNavigationView bottomNavigationView;
        String user_name="";
        SharedPreferences sharedPreferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mamainactivity);
            Intent i=getIntent();
            Toast.makeText(this, user_name, Toast.LENGTH_SHORT).show();
            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            bottomNavigationView.setSelectedItemId(R.id.person);
            sharedPreferences=getSharedPreferences(filename, Context.MODE_PRIVATE);
            if(sharedPreferences.contains(username)){
                user_name=sharedPreferences.getString(username,"");
                //Toast.makeText(this, "preference", Toast.LENGTH_SHORT).show();
            }


        }
        Admin_BusList Bus_list=new Admin_BusList();
        User_Profile_Update secondFragment = new User_Profile_Update();
        User_Settings thirdFragment = new User_Settings();



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.person:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, Bus_list).commit();
                    return true;

                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                    //secondFragment.setUserName(user_name);
                    return true;

                case R.id.settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                    return true;
            }
            return false;
        }
    }
