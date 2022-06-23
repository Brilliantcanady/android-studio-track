package com.example.tae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;

public class Driver_Page extends AppCompatActivity {
    TextInputLayout bus_no, ucode;
    FusedLocationProviderClient client;
    int flag = 0;
    Location newlocation;
    private Handler mhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_page);
        bus_no = findViewById(R.id.bus_no);
        ucode = findViewById(R.id.regcodeNo);

    }

    public void drivercheck(View view) {
        locupd();
mtoastruning.run();
    }

    public void stoplocationupdate(View view) {
      mhandler.removeCallbacks(mtoastruning);
        Toast.makeText(Driver_Page.this, " location updation Stopped", Toast.LENGTH_SHORT).show();
    }

    private Boolean validateUsername(TextInputLayout bus_no){
        String val = this.bus_no.getEditText().getText().toString();
        String noWhiteSpace="(?=\\s+$)";
        if (val.isEmpty()) {
            this.bus_no.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15){
            this.bus_no.setError("Username too long");
            return false;
        }
        else if(val.matches(noWhiteSpace)){
            this.bus_no.setError("White Spaces not allowed");
            return false;
        }
        else{
            this.bus_no.setError(null);
            this.bus_no.setErrorEnabled(false);
            return true;
        }
    }

    private void locupd() {
        String enteredbusno = bus_no.getEditText().getText().toString();
        String entereducode = ucode.getEditText().getText().toString();
        if (!validateUsername(bus_no) | !validateUsername(ucode)) {
            return ;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bus").child(entereducode);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    ucode.setError(null);
                    ucode.setErrorEnabled(false);
try {
    int k = 0;
    for (DataSnapshot snapshot : datasnapshot.getChildren()) {

        String busnofromDB = snapshot.child("busno").getValue(String.class);
        // Toast.makeText(Driver_Page.this, busnofromDB+"  "+enteredbusno, Toast.LENGTH_SHORT).show();


        if (busnofromDB.equals(enteredbusno)) {
            flag = 1;
            bus_no.setError(null);
            bus_no.setErrorEnabled(false);
            k = 1;
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            checkperm();

            Task<Location> task = client.getLastLocation();

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    Double curlat = location.getLatitude();
                    Double curlong = location.getLongitude();

                    HashMap hashMap = new HashMap();
                    hashMap.put("lat", curlat.toString());
                    hashMap.put("lng", curlong.toString());
                    reference.child(busnofromDB).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(Driver_Page.this, " location updating", Toast.LENGTH_SHORT).show();


                        }
                    });
                    //Toast.makeText(Admin_LoginPage.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(Login.this, UserProfile.class);


                }
            });
        }

    }

    if (k == 0) {
        bus_no.setError("invalid busno");
        bus_no.requestFocus();

    }
} catch (Exception e) {
    Toast.makeText(Driver_Page.this, e.toString(), Toast.LENGTH_SHORT).show();
}

                } else {
                    ucode.setError("invalid code");
                    ucode.requestFocus();

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void checkperm() {
        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getApplicationContext();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }

                }).check();


    }

private Runnable mtoastruning=new Runnable() {
    @Override
    public void run() {
        locupd();
        mhandler.postDelayed(this,3000);
    }
};



}