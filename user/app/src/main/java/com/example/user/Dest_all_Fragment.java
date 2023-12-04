package com.example.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dest_all_Fragment extends Fragment {

    GridView gridView;
    ArrayList<DestnData> datalist1;
    DestAdapter adapter;
    Handler handler = new Handler();
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("State");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dest_all_, container, false);

        gridView = view.findViewById(R.id.gridview1);

        datalist1 = new ArrayList<>();
        adapter = new DestAdapter(datalist1, getActivity());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DestnData data = (DestnData) parent.getItemAtPosition(position);

                Bundle args = new Bundle();
                args.putString("name", data.getTitle());
                args.putString("description", data.getDescription());
                args.putString("image", data.getImage());

                Fragment tofragment = new Home_last_Fragment();
                tofragment.setArguments(args);

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, tofragment)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    DestnData destnData = dataSnapshot.getValue(DestnData.class);
                    datalist1.add(destnData);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}