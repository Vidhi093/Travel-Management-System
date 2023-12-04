package com.example.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

public class Summer_Fragment extends Fragment {

    GridView gridView;
    ArrayList<advisoryData> slist;
    advisoryAdapter adapter;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Advisory");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summer_, container, false);

        gridView = view.findViewById(R.id.gridview1);

            slist = new ArrayList<>();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        advisoryData data = dataSnapshot.getValue(advisoryData.class);
                        assert data != null;
                        if (data.getWtt().equals("Summer") || data.getWtt().equals("All season available")) {
                            slist.add(data);

                        }
                        adapter = new advisoryAdapter(slist,getActivity());
                        gridView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

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

                Fragment tofragment = new special_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.summerfrag, tofragment)
                        .addToBackStack(String.valueOf(Summer_Fragment.class)).commit();
            }
        });


        return view;
    }
}