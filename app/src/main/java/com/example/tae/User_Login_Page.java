package com.example.tae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class User_Login_Page extends AppCompatActivity {
    public static final String filename = "login";
    public static final String username = "username";
    public static final String password = "password";
    public static final String reguserbus = "reguserbus";
    public static final String Uniquecode = "Uniquecode";
    Button callSignup, loginbutton, admin_screen;
    TextInputLayout loginuser, loginpass;
    SharedPreferences sharedPreferences;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        callSignup = findViewById(R.id.signup_screen);
        loginuser = findViewById(R.id.username);
        loginpass = findViewById(R.id.password);
        admin_screen = findViewById(R.id.admin_screen);
        sharedPreferences = this.getSharedPreferences(filename, getApplicationContext().MODE_PRIVATE);


        admin_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentadmin = new Intent(User_Login_Page.this, Admin_LoginPage.class);
                startActivity(intentadmin);
            }
        });
        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Login_Page.this, User_Sign_Up.class);
                startActivity(intent);
            }
        });
        loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected(User_Login_Page.this)) {
                    showCustomDialog();
                } else {
                    if (!validatePassword() | !validateUsername()) {
                        return;
                    } else {
                        isUser();
                    }
                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
        if (sharedPreferences.contains(username)) {
            //Toast.makeText(this, "shared", Toast.LENGTH_SHORT).show();
            Intent inte = new Intent(User_Login_Page.this, User_Home_page.class);
            startActivity(inte);
        }
    }

    private Boolean isConnected(User_Login_Page user_login_page) {
        ConnectivityManager connectivityManager = (ConnectivityManager) user_login_page.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wificonn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {
            return false;
        }

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Login_Page.this);
        builder.setMessage("Please connect to internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        //Toast.makeText(this, "dflkjfdk", Toast.LENGTH_SHORT).show();
        AlertDialog alert = builder.create();
        alert.show();
    }

    //end of oncreate
    private Boolean validateUsername() {
        String val = loginuser.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)";
        if (val.isEmpty()) {
            loginuser.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            loginuser.setError("Username too long");
            return false;
        } else if (val.matches(noWhiteSpace)) {
            loginuser.setError("White Spaces not allowed");
            return false;
        } else {
            loginuser.setError(null);
            loginuser.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = loginpass.getEditText().getText().toString();
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
            loginpass.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            loginpass.setError("Password is wrong");
            return false;
        } else {
            loginpass.setError(null);
            return true;
        }
    }


    private void isUser() {
        String enteredusername = loginuser.getEditText().getText().toString();
        String enteredpassword = loginpass.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");


        Query checkuser = reference.orderByChild("username").equalTo(enteredusername);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loginuser.setError(null);
                    loginuser.setErrorEnabled(false);

                    String passwordfromDB = snapshot.child(enteredusername).child("password").getValue(String.class);

                    if (passwordfromDB.equals(enteredpassword)) {
                        loginuser.setError(null);
                        loginuser.setErrorEnabled(false);

                        Toast.makeText(User_Login_Page.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(username, enteredusername);
                        editor.putString(password, enteredpassword);
                        editor.commit();

                        // Intent i = new Intent(Login.this, UserProfile.class);
                        Intent inte = new Intent(User_Login_Page.this, User_Home_page.class);
                        inte.putExtra("username", enteredusername);
                        startActivity(inte);


                    } else {
                        loginpass.setError("incorrect password");
                        loginuser.getEditText().setText("");

                        loginpass.getEditText().setText("");
                        loginpass.requestFocus();

                    }
                } else {
                    loginuser.setError("invalid user");
                    loginuser.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void driverpage(View view) {
        Intent i=new Intent(getApplicationContext(),Driver_Login.class);
        startActivity(i);
    }
}
