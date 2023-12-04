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

public class attraction_all2_Fragment extends Fragment {

    GridView gridView;
    ArrayList<AttractionData> datalist1;
    attractionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attraction_all2_, container, false);

        gridView = view.findViewById(R.id.gridview4);

        datalist1 = new ArrayList<>();
        adapter = new attractionAdapter(datalist1, getActivity());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttractionData data = (AttractionData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("title",data.getTitle());
                intent.putString("state",data.getState());
                intent.putString("description",data.getDescription());
                intent.putString("place",data.getPopular_place());
                intent.putString("image",data.getImage());

                Fragment tofragment = new attraction_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.attract, tofragment)
                        .addToBackStack(String.valueOf(popular_last_Fragment.class)).commit();
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Attraction");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AttractionData attractionData = dataSnapshot.getValue(AttractionData.class);
                    datalist1.add(attractionData);
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