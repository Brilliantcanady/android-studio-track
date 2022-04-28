package com.example.tae;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;


public class Admin_map_page extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_admin_map_page, container, false);
        checkMyPermission();
        return view;
    }
    private void checkMyPermission() {
        Dexter.withContext(getContext().getApplicationContext()).withPermission (Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                   //Toast.makeText(getContext().getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }

         @Override
                 public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                   Intent intent= new Intent();
                     intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
             Uri uri = Uri.fromParts(  "package",getContext().getPackageName(),"");
                 intent.setData(uri);
             startActivity (intent);
        }
                         @Override
        public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {
         permissionToken.continuePermissionRequest();
          }
        }
        ).check();
    }
}