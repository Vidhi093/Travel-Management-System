package com.example.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search_Fragment extends Fragment {

    AutoCompleteTextView search;
    Button searchbtn;
    GridView gridView, gold, silver, bronze, searchhotel, searchtour, tourg;
    LinearLayout lplatinum, lgold, lsilver, lbronze, ltour;
    TextView tplatinum, tgold, tsilver, tbronze, tourguide;
    ArrayList<hotelData> datalist, list2, list3, list4, hotellist;
    platinumAdapter adapter;
    ArrayList<tourguideData> list, tourlist;
    tourguideAdapter tourguideAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hotel");
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tour Guide");
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Advisory");
    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("State");
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("State").child("Popular Place");
    DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Attraction");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_, container, false);

        lplatinum = view.findViewById(R.id.platniuml);
        lgold = view.findViewById(R.id.goldl);
        lsilver = view.findViewById(R.id.silverl);
        lbronze = view.findViewById(R.id.bronzel);

        tplatinum = view.findViewById(R.id.platnium);
        tgold = view.findViewById(R.id.gold);
        tsilver = view.findViewById(R.id.silver);
        tbronze = view.findViewById(R.id.bronze);

        gridView = view.findViewById(R.id.platniumg);
        gold = view.findViewById(R.id.goldg);
        silver = view.findViewById(R.id.silverg);
        bronze = view.findViewById(R.id.bronzeg);

        search = view.findViewById(R.id.search);
        searchbtn = view.findViewById(R.id.searchbtn);
        searchhotel = view.findViewById(R.id.searchhotel);
        searchtour = view.findViewById(R.id.searchtour);

        ltour = view.findViewById(R.id.tourl);
        tourguide = view.findViewById(R.id.tour);
        tourg = view.findViewById(R.id.tourg);

        final ArrayAdapter<String> autosearch = new ArrayAdapter<>(getActivity(), R.layout.items);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("place").getValue(String.class);
                    autosearch.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setThreshold(1);
        search.setAdapter(autosearch);

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("title").getValue(String.class);
                    autosearch.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setThreshold(1);
        search.setAdapter(autosearch);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("title").getValue(String.class);
                    autosearch.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setThreshold(1);
        search.setAdapter(autosearch);

        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("title").getValue(String.class);
                    autosearch.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setThreshold(1);
        search.setAdapter(autosearch);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("name").getValue(String.class);
                    autosearch.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setThreshold(1);
        search.setAdapter(autosearch);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (search.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter the text", Toast.LENGTH_SHORT).show();
                }

                hotellist = new ArrayList<>();

                databaseReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                            String data = dataSnapshot.child("/location").getValue(String.class);
                            String data12 = dataSnapshot.child("/city").getValue(String.class);

                            assert data != null;
                            assert data12 != null;

                            if (data.equals(search.getText().toString()) || data12.equals(search.getText().toString())) {
                                hotelData data1 = dataSnapshot.getValue(hotelData.class);
                                hotellist.add(data1);
                            }
                        }
                        adapter = new platinumAdapter(hotellist, getActivity());
                        searchhotel.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                tourlist = new ArrayList<>();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            String data = dataSnapshot.child("/state").getValue(String.class);
                            String data12 = dataSnapshot.child("/city").getValue(String.class);
                            assert data != null;
                            assert data12 != null;

                            if (data.equals(search.getText().toString()) || data12.equals(search.getText().toString())) {
                                tourguideData data1 = dataSnapshot.getValue(tourguideData.class);
                                tourlist.add(data1);
                            }
                        }
                        tourguideAdapter = new tourguideAdapter(tourlist, getActivity());
                        searchtour.setAdapter(tourguideAdapter);
                        tourguideAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        searchhotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        searchtour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        tplatinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle intent = new Bundle();
                intent.putString("name",tplatinum.getText().toString());
                Fragment tofragment = new hotel_package_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        tgold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle intent = new Bundle();
                intent.putString("name",tgold.getText().toString());
                Fragment tofragment = new hotel_package_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        tsilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle intent = new Bundle();
                intent.putString("name",tsilver.getText().toString());
                Fragment tofragment = new hotel_package_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        tbronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle intent = new Bundle();
                intent.putString("name",tbronze.getText().toString());
                Fragment tofragment = new hotel_package_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        tourguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle intent = new Bundle();
                intent.putString("name",tourguide.getText().toString());
                Fragment tofragment = new hotel_package_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.searchfrag, tofragment)
                        .addToBackStack(String.valueOf(Search_Fragment.class)).commit();
            }
        });

        lplatinum();
        lgold();
        lsilver();
        lbronze();
        ltour();

        return view;
    }

    private void ltour() {
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

    private void lbronze() {
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

    private void lsilver() {
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

    private void lgold() {

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

    private void lplatinum() {

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