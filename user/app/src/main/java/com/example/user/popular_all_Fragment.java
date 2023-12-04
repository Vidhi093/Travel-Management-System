package com.example.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class popular_all_Fragment extends Fragment {

    GridView gridView;
    FrameLayout popular;
    ArrayList<PopulardestData> list;
    PopularAdapter adapter;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("State");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular_all_, container, false);
        gridView = view.findViewById(R.id.gridview1);
        popular = view.findViewById(R.id.popular);

        list = new ArrayList<>();
        adapter = new PopularAdapter(list, getActivity());
        gridView.setAdapter(adapter);

        assert getArguments() != null;
        String name1 = getArguments().getString("name");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopulardestData data = (PopulardestData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("title",data.getTitle());
                intent.putString("description",data.getDescription());
                intent.putString("resId",data.getImage());
                intent.putString("nams",name1);

                Fragment tofragment = new popular_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.last, tofragment)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();

            }
        });

        DatabaseReference reference = databaseReference.child("/"+name1).child("/Popular Place");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    PopulardestData data = dataSnapshot.getValue(PopulardestData.class);
                    list.add(data);
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