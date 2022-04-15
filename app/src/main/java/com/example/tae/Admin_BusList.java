package com.example.tae;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Admin_BusList extends Fragment {

    private RecyclerView recyclerView;
    personAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
AdapterView.OnItemClickListener     itemClickListener;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycleitems, container, false);
        mbase = FirebaseDatabase.getInstance().getReference().child("bus");

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
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
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