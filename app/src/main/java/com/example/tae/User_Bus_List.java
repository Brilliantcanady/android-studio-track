package com.example.tae;

import static com.example.tae.User_Login_Page.Uniquecode;
import static com.example.tae.User_Login_Page.filename;
import static com.example.tae.User_Login_Page.reguserbus;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class User_Bus_List extends Fragment {

    private RecyclerView recyclerView;
    TextInputLayout regbus;
    Button buslogout, busnumupd;
    personAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    SharedPreferences sharedPreferences;
    String unicodeno;
    AdapterView.OnItemClickListener     itemClickListener;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycleitems, container, false);
        sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(filename, getActivity().getApplicationContext().MODE_PRIVATE);
        if(sharedPreferences.contains(Uniquecode)){

            //Toast.makeText(getActivity().getApplicationContext(),sharedPreferences.getString(Uniquecode,"") , Toast.LENGTH_SHORT).show();
        }
        mbase = FirebaseDatabase.getInstance().getReference().child("bus").child(sharedPreferences.getString(Uniquecode,""));

        recyclerView = view.findViewById(R.id.recycler1);
        itemClickListener =new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"Position : || Value : ",Toast.LENGTH_SHORT).show();
                Intent iy=new Intent(getContext(),BusTrackMenu.class);
                startActivity(iy);
            }
        };
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<person> options =
                new FirebaseRecyclerOptions.Builder<person>()
                        .setQuery(mbase, person.class)
                        .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new personAdapter(options,itemClickListener);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);


        regbus = view.findViewById(R.id.reguserbus);
        busnumupd = view.findViewById(R.id.busupdatebutton);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(filename, getActivity().getApplicationContext().MODE_PRIVATE);
        regbus.getEditText().setText(sharedPreferences.getString(reguserbus, ""));
        regbus.setError(null);
        regbus.setErrorEnabled(false);
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




        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        regbus.setError(null);
        regbus.setErrorEnabled(false);
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void bustrackmenu() {
        Intent i=new Intent(getContext(),BusTrackMenu.class);
        startActivity(i);
    }
}