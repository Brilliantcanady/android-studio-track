package com.example.tae;

import static android.content.Context.LOCATION_SERVICE;

import static com.example.tae.Admin_LoginPage.adminUniquecode;
import static com.example.tae.Admin_LoginPage.adminfilename;
import static com.example.tae.Admin_LoginPage.adminreguserbus;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Admin_MapsFragment extends Fragment {
    SupportMapFragment smf;
    FusedLocationProviderClient client;
    SharedPreferences sharedPreferences;
    GoogleMap googleMap1;
    DatabaseReference reference;
    Marker mymarker;
    Double latitude = -34.0900, longitude = 151.000;
    private LocationManager manager;
    private final int MIN_TIME=1000;
    private final int MIN_DISTANCE=1;
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
            LatLng[] sydney1 = {new LatLng(15, 80)};
            googleMap1 = googleMap;


            mymarker = googleMap.addMarker(new MarkerOptions().position(sydney1[0]).title("Bus location")
                    .snippet("Current bus location"));
            //googleMap.setMinZoomPreference(50);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney1[0]));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.fragment_admin__maps, container, false);
        View view = inflater.inflate(R.layout.fragment_admin__maps, container, false);
        client = LocationServices.getFusedLocationProviderClient(getContext());
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }

                }).check();


        return view;
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
                getmylocation();
               readChanges();
            }catch (Exception e){
                Toast.makeText(getContext(), " exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }



        }
    }

    private void readChanges() {
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(adminfilename, getActivity().getApplicationContext().MODE_PRIVATE);
        if(sharedPreferences.contains(adminUniquecode) && sharedPreferences.contains(adminreguserbus)) {
            //Toast.makeText(getContext(), sharedPreferences.getString(adminreguserbus, ""), Toast.LENGTH_SHORT).show();
            reference = FirebaseDatabase.getInstance().getReference()
                    .child("bus")
                    .child(sharedPreferences.getString(adminUniquecode, "")).child(sharedPreferences.getString(adminreguserbus, ""));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        try{
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                latitude = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                                longitude =Double.parseDouble(dataSnapshot.child("lng").getValue().toString());
                               // Toast.makeText(getContext(), latitude+"  "+longitude, Toast.LENGTH_SHORT).show();
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
    private void getmylocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng sydney1 = new LatLng(location.getLatitude(), location.getLongitude());
                //mymarker.setPosition(sydney1);
                MarkerOptions mk = new MarkerOptions()
                        .position(sydney1)
                        .title("you are here")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                googleMap1.addMarker(mk);
                //googleMap.setMinZoomPreference(50);
                googleMap1.getUiSettings().setZoomControlsEnabled(true);
                googleMap1.getUiSettings().setAllGesturesEnabled(true);
                googleMap1.moveCamera(CameraUpdateFactory.newLatLng(sydney1));

            }
        });

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