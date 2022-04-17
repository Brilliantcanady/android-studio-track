package com.example.tae;

import static com.example.tae.Admin_LoginPage.adminUniquecode;
import static com.example.tae.Admin_LoginPage.adminfilename;
import static com.example.tae.User_Login_Page.Uniquecode;
import static com.example.tae.User_Login_Page.filename;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Admin_Bus_Registration extends Fragment {

    TextInputLayout name,to,from,mob,details,busno,codeno;
    TextInputLayout drivername,to_city,from_city,mob_no,bus_no;
    String unicodeno;
    Button button;
    SharedPreferences sharedPreferences;
    DatabaseReference driverDbRef;
public Admin_Bus_Registration(){

}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_admin__bus__registration, container, false);
            update_bus_info(view);


            return view;
        }

    private void update_bus_info(View viewarg) {
        drivername=viewarg.findViewById(R.id.drivername);
        to_city=viewarg.findViewById(R.id.to_city);
        from_city=viewarg.findViewById(R.id.from_city);
        mob_no=viewarg.findViewById(R.id.mob_no);
        bus_no=viewarg.findViewById(R.id.bus_no);
        codeno=viewarg.findViewById(R.id.regcodeNo);
        button=viewarg.findViewById(R.id.Bus_data_Btn);
        sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(adminfilename, getActivity().getApplicationContext().MODE_PRIVATE);
        if(sharedPreferences.contains(adminUniquecode)){

           Toast.makeText(getActivity().getApplicationContext(),sharedPreferences.getString(adminUniquecode,"") , Toast.LENGTH_SHORT).show();
        }
        unicodeno= sharedPreferences.getString(adminUniquecode,"");
        codeno.getEditText().setText(unicodeno);
        driverDbRef= FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "running", Toast.LENGTH_SHORT).show();
                insertDriverData();
            }
        });
    }
    private void insertDriverData(){
        String name=drivername.getEditText().getText().toString();
        String mob=mob_no.getEditText().getText().toString();
        String busno=bus_no.getEditText().getText().toString();
        String from=from_city.getEditText().getText().toString();
        String to=to_city.getEditText().getText().toString();
        String unco=codeno.getEditText().getText().toString();
        String id=driverDbRef.push().getKey();

        BusSchema drivers=new BusSchema(name,mob,busno,from,to,unicodeno);
        driverDbRef.child("bus").child(unicodeno).child(busno).setValue(drivers).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}