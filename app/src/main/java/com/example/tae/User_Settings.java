package com.example.tae;

import static androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

import io.reactivex.rxjava3.core.Notification;

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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.getRoot();
        sessionLogout(view);
        return view;
    }

    private void sessionLogout(View container) {
        buslogout = container.findViewById(R.id.Bus_logout);

        regbus = container.findViewById(R.id.reguserbus);
        busnumupd = container.findViewById(R.id.busupdatebutton);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(filename, getActivity().getApplicationContext().MODE_PRIVATE);
        regbus.getEditText().setText(sharedPreferences.getString(reguserbus, ""));
        busnumupd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("bus").child(sharedPreferences.getString(Uniquecode, ""));
                String businfouser = regbus.getEditText().getText().toString();
                Query checkbus = reference.orderByChild("busno").equalTo(businfouser);
                checkbus.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(reguserbus, businfouser);
                            editor.commit();
                            Toast.makeText(getContext(), "bus number updated", Toast.LENGTH_SHORT).show();
                            regbus.setError(null);
                            regbus.setErrorEnabled(false);
                        } else {
                            regbus.setError("Bus number is invalid");
                            regbus.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
        settime = container.findViewById(R.id.timeselect);
        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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


    private void shoeTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();
        picker.show(getActivity().getSupportFragmentManager(), "foxandroid");


    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "foxandroidremaimderchannel", importance);
            mChannel.setDescription("channel for bus alarm");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(mChannel);
        }


    }
}