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

public class ltourguide_all_Fragment extends Fragment {

    ArrayList<tourguideData> list, tourlist;
    GridView gridview;
    tourguideAdapter tourguideAdapter;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tour Guide");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ltourguide_all_, container, false);

        gridview = view.findViewById(R.id.gridview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tourguideData data = (tourguideData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getName());
                intent.putString("contact",data.getContact());
                intent.putString("state",data.getState());
                intent.putString("city",data.getCity());
                intent.putString("price",data.getPrice());
                intent.putString("image",data.getImage());

                Fragment tofragment = new Tourguide_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.tourfrag, tofragment)
                        .addToBackStack(String.valueOf(ltourguide_all_Fragment.class)).commit();
            }
        });

        list = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    tourguideData data = dataSnapshot.getValue(tourguideData.class);
                    list.add(data);
                }
                tourguideAdapter = new tourguideAdapter(list, getActivity());
                gridview.setAdapter(tourguideAdapter);
                tourguideAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}