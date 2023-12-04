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

public class hotel_package_Fragment extends Fragment {

    GridView gridView, gold, silver, bronze, searchhotel, searchtour, tourg;

    ArrayList<hotelData> datalist, list2, list3, list4, hotellist;
    platinumAdapter adapter;
    ArrayList<tourguideData> list, tourlist;
    tourguideAdapter tourguideAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hotel");
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tour Guide");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel_package_, container, false);

        gridView = view.findViewById(R.id.platniumg);
        gold = view.findViewById(R.id.goldg);
        silver = view.findViewById(R.id.silverg);
        bronze = view.findViewById(R.id.bronzeg);
        tourg = view.findViewById(R.id.tourg);

        assert getArguments() != null;
        String name1 = getArguments().getString("name");

        if (name1.equals("Platinum Package")) {
            platniu();
        } else if (name1.equals("Gold Package")) {
            gold();
        } else if (name1.equals("Silver Package")) {
            silver();
        } else if (name1.equals("Bronze Package")) {
            bronze();
        } else if (name1.equals("Tour Guide")) {
            tourguide();
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getName());
                intent.putString("add",data.getAddress());
                intent.putString("desc",data.getDesc());
                intent.putString("contact",data.getContact());
                intent.putString("price",data.getPrice());
                intent.putString("policy",data.getPolicy());
                intent.putString("location",data.getLocation());
                intent.putString("city",data.getCity());
                intent.putString("email",data.getEmail());
                intent.putString("category",data.getCategory());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new Hotel_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        gold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getName());
                intent.putString("add",data.getAddress());
                intent.putString("desc",data.getDesc());
                intent.putString("contact",data.getContact());
                intent.putString("price",data.getPrice());
                intent.putString("policy",data.getPolicy());
                intent.putString("location",data.getLocation());
                intent.putString("city",data.getCity());
                intent.putString("email",data.getEmail());
                intent.putString("category",data.getCategory());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new Hotel_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        silver.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getName());
                intent.putString("add",data.getAddress());
                intent.putString("desc",data.getDesc());
                intent.putString("contact",data.getContact());
                intent.putString("price",data.getPrice());
                intent.putString("policy",data.getPolicy());
                intent.putString("location",data.getLocation());
                intent.putString("city",data.getCity());
                intent.putString("email",data.getEmail());
                intent.putString("category",data.getCategory());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new Hotel_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        bronze.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getName());
                intent.putString("add",data.getAddress());
                intent.putString("desc",data.getDesc());
                intent.putString("contact",data.getContact());
                intent.putString("price",data.getPrice());
                intent.putString("policy",data.getPolicy());
                intent.putString("location",data.getLocation());
                intent.putString("city",data.getCity());
                intent.putString("email",data.getEmail());
                intent.putString("category",data.getCategory());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new Hotel_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        tourg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        return view;
    }

    private void tourguide() {
        list = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    tourguideData data = dataSnapshot.getValue(tourguideData.class);
                    list.add(data);
                }
                tourguideAdapter = new tourguideAdapter(list, getActivity());
                tourg.setAdapter(tourguideAdapter);
                tourguideAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void bronze() {
        DatabaseReference ref = databaseReference.child("/Bronze Package");
        list4 = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String data = dataSnapshot.child("/category").getValue(String.class);
                    assert data != null;
                    if (data.equals("Bronze Package")) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        list4.add(data1);
                    }
                }
                adapter = new platinumAdapter(list4, getActivity());
                bronze.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void silver() {
        DatabaseReference ref = databaseReference.child("/Silver Package");
        list3 = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String data = dataSnapshot.child("/category").getValue(String.class);
                    assert data != null;
                    if (data.equals("Silver Package")) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        list3.add(data1);
                    }
                }
                adapter = new platinumAdapter(list3, getActivity());
                silver.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void gold() {

        list2 = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String data = dataSnapshot.child("/category").getValue(String.class);
                    assert data != null;
                    if (data.equals("Gold Package")) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        list2.add(data1);
                    }
                }
                adapter = new platinumAdapter(list2, getActivity());
                gold.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void platniu()  {

        datalist = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String data = dataSnapshot.child("/category").getValue(String.class);
                    assert data != null;
                    if (data.equals("Platinum Package")) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        datalist.add(data1);
                    }
                }
                adapter = new platinumAdapter(datalist, getActivity());
                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}