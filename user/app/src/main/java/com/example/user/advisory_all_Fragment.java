package com.example.user;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class advisory_all_Fragment extends Fragment {
    
    GridView gridView;
    ArrayList<advisoryData> datalist1;
    advisoryAdapter adapter;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Advisory");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advisory_all_, container, false);

        gridView = view.findViewById(R.id.gridview1);

        datalist1 = new ArrayList<>();
        adapter = new advisoryAdapter(datalist1, getActivity());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                advisoryData data = (advisoryData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getPlace());
                intent.putString("state",data.getState());
                intent.putString("description",data.getDescription());
                intent.putString("wtt",data.getWtt());
                intent.putString("ttd",data.getTtd());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new advisory_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, tofragment)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    datalist1.add(data);
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