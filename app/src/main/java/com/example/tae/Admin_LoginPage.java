package com.example.tae;

import static com.example.tae.User_Login_Page.filename;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Admin_LoginPage extends AppCompatActivity {
    Button loginbutton,user_screen;
    TextInputLayout loginuser,loginpass;
    SharedPreferences sharedPreferences;
    public static final String adminfilename="adminlogin";
    public static final String adminusername="username";
    public static final String adminpassword="password";
    public static final String adminUniquecode="Uniquecode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_adminlogin);
        sharedPreferences=getApplicationContext().getSharedPreferences(adminfilename, getApplicationContext().MODE_PRIVATE);
        loginuser=findViewById(R.id.username);
        loginpass=findViewById(R.id.password);
        loginbutton=findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected(Admin_LoginPage.this)) {
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
    }
    //end of oncreate
    private Boolean validateUsername(){
        String val = loginuser.getEditText().getText().toString();
        String noWhiteSpace="(?=\\s+$)";
        if (val.isEmpty()) {
            loginuser.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15){
            loginuser.setError("Username too long");
            return false;
        }
        else if(val.matches(noWhiteSpace)){
            loginuser.setError("White Spaces not allowed");
            return false;
        }
        else{
            loginuser.setError(null);
            loginuser.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val =loginpass.getEditText().getText().toString();
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
            loginpass.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(passwordVal)){
            loginpass.setError("Password is wrong");
            return false;
        }
        else{
            loginpass.setError(null);
            return true;
        }
    }


    private void isUser() {
        String enteredusername =loginuser.getEditText().getText().toString();
        String enteredpassword= loginpass.getEditText().getText().toString();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("admins");

        Query checkuser=reference.orderByChild("username").equalTo(enteredusername);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    loginuser.setError(null);
                    loginuser.setErrorEnabled(false);

                    String passwordfromDB = snapshot.child(enteredusername).child("password").getValue(String.class);

                    if (passwordfromDB.equals(enteredpassword)) {
                        loginuser.setError(null);
                        loginuser.setErrorEnabled(false);

                        //Toast.makeText(Admin_LoginPage.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                        //Intent i = new Intent(Login.this, UserProfile.class);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(adminusername,enteredusername);
                        editor.putString(adminpassword,enteredpassword);
                        editor.commit();
                        Intent inte=new Intent(Admin_LoginPage.this, Admin_Home.class);
                        inte.putExtra("username",enteredusername);
                        startActivity(inte);


                    } else {
                        loginpass.setError("incorrect password");
                        loginpass.requestFocus();
                    }
                }
                else{
                    loginuser.setError("invalid user");
                    loginuser.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private Boolean isConnected(Admin_LoginPage user_login_page){
        ConnectivityManager connectivityManager= (ConnectivityManager) user_login_page.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wificonn=connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn=connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if((wificonn!=null && wificonn.isConnected()) || (mobileconn!=null && mobileconn.isConnected()))
        {
            return true;
        }
        else{
            return false;
        }

    }
    private void showCustomDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Admin_LoginPage.this);
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
        AlertDialog alert=builder.create();
        alert.show();
    }
}
