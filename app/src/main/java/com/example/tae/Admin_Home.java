package com.example.tae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigation;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        Intent i=getIntent();
        user_name=i.getStringExtra("username");
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.maps);
    }

    Admin_map_page mappage=new Admin_map_page();
    Admin_BusList Bus_list=new Admin_BusList();
    Admin_Bus_Registration Bus_regist=new Admin_Bus_Registration();
    Admin_profile Bus_profile = new Admin_profile();
    Admin_settings Admin_settings=new Admin_settings();
    Testing test=new Testing();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.maps:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, mappage).commit();
                return true;
            case R.id.Buslist:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, Bus_list).commit();
                return true;

            case R.id.Addbus:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,Bus_regist).commit();
                return true;

            case R.id.Adminprofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, Bus_profile).commit();
                Bus_profile.setUserName(user_name);
                return true;

            case R.id.Admin_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, Admin_settings).commit();
                return true;

        }
        return false;
    }
}