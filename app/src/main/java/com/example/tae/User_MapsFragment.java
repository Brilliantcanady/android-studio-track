package com.example.tae;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.tae.User_Login_Page.Uniquecode;
import static com.example.tae.User_Login_Page.filename;
import static com.example.tae.User_Login_Page.reguserbus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_MapsFragment extends Fragment implements LocationListener {
    SharedPreferences sharedPreferences;
    DatabaseReference reference;
    private LocationManager manager;
    Marker mymarker;
private final int MIN_TIME=1000;
private final int MIN_DISTANCE=1;
    int latitude=-34,longitude=151;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //LatLng sydney = new LatLng(-34, 151);


            LatLng sydney1 = new LatLng(15,80);
           mymarker= googleMap.addMarker(new MarkerOptions().position(sydney1).title("Marker in Bus"));
           //googleMap.setMinZoomPreference(50);
           googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney1));

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user__maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager=(LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
            try {
                getLocationUpdate();
                readChanges();
            }catch (Exception e){
                Toast.makeText(getContext(), " exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(filename, getActivity().getApplicationContext().MODE_PRIVATE);
            if(sharedPreferences.contains(Uniquecode) && sharedPreferences.contains(reguserbus)) {
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("bus")
                        .child(sharedPreferences.getString(Uniquecode, "")).child(sharedPreferences.getString(reguserbus, ""));
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            try{
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    latitude = (int) Float.parseFloat(dataSnapshot.child("latitude").getValue().toString());
                                    longitude = (int) Float.parseFloat(dataSnapshot.child("longitude").getValue().toString());
                                    Toast.makeText(getContext(), latitude+"  "+longitude, Toast.LENGTH_SHORT).show();
                                    mymarker.setPosition(new LatLng(latitude, longitude));
                                }


                            }catch (Exception e){
                                Toast.makeText(getContext(), " exception 2"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }



        }
    }

    private void readChanges() {
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(filename, getActivity().getApplicationContext().MODE_PRIVATE);
        if(sharedPreferences.contains(Uniquecode) && sharedPreferences.contains(reguserbus)) {
            reference = FirebaseDatabase.getInstance().getReference()
                    .child("bus")
                    .child(sharedPreferences.getString(Uniquecode, "")).child(sharedPreferences.getString(reguserbus, ""));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        try{
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        latitude = Integer.parseInt(dataSnapshot.child("latitude").getValue().toString());
                        longitude = Integer.parseInt(dataSnapshot.child("longitude").getValue().toString());
                        Toast.makeText(getContext(), latitude+"  "+longitude, Toast.LENGTH_SHORT).show();
                        mymarker.setPosition(new LatLng(latitude, longitude));
                    }


                    }catch (Exception e){
                            Toast.makeText(getContext(), " exception 1"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    private void getLocationUpdate() {
        if(manager!=null) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, (LocationListener) getContext());
                } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, (LocationListener) getContext());
                } else {
                    Toast.makeText(getContext(), " NoProvider", Toast.LENGTH_SHORT).show();

                }
            }
            else{
                ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLocationUpdate();
            }
            else {
                Toast.makeText(getContext(), " Permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(location!=null){
            saveLocation(location);
        }
        else {
            Toast.makeText(getContext(), "No Location", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLocation(Location location) {
    }
}