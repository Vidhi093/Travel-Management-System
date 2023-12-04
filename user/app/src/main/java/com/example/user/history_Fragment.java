package com.example.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class history_Fragment extends Fragment {

    GridView hotel, tour;
    ArrayList<hotelBillData> advlist;
    hotelbittadapter advAdapter;
    ArrayList<tourbillData> attractionlist;
    tourbillAdapter attadapter;
    TextView hot;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history_, container, false);

        hotel = view.findViewById(R.id.hotel);
        tour = view.findViewById(R.id.tour);
        hot = view.findViewById(R.id.ho);

        advisory();
        advisory1();

        tour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tourbillData data = (tourbillData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();

                intent.putString("name", data.getName());
                intent.putString("days",data.getDays());
                intent.putString("start", data.getStart());
                intent.putString("end",data.getEnd());
                intent.putString("adults",data.getAdults());
                intent.putString("childs",data.getChilds());
                intent.putString("total",data.getTotal());

                Fragment tofragment = new last_tour_bill_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.hiz, tofragment)
                        .addToBackStack(String.valueOf(history_Fragment.class)).commit();
            }
        });


        hotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotelBillData data = (hotelBillData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getHotel_name());
                intent.putString("price",data.getHotel_price());
                intent.putString("city",data.getHotel_city());
                intent.putString("category",data.getCategory());
                intent.putString("indate",data.getCheckin_date());
                intent.putString("outdate",data.getCheckout_date());
                intent.putString("intime",data.getCheckin_time());
                intent.putString("outtime",data.getCheckout_time());
                intent.putString("days",data.getDays());
                intent.putString("rooms",data.getRooms());
                intent.putString("adults",data.getAdults());
                intent.putString("childs",data.getChilds());
                intent.putString("total",data.getTotal());

                Fragment tofragment = new last_history_hotel_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.hiz, tofragment)
                        .addToBackStack(String.valueOf(history_Fragment.class)).commit();


            }
        });


        return view;
    }

    private void advisory1() {
        assert user != null;
        String uid = user.getUid();
        attractionlist = new ArrayList<>();
        DatabaseReference ref = reference.child(uid).child("History").child("Tourguide");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    tourbillData data = dataSnapshot.getValue(tourbillData.class);
                    attractionlist.add(data);
                }
                attadapter = new tourbillAdapter(attractionlist, getActivity());
                tour.setAdapter(attadapter);
                attadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void advisory() {
        assert user != null;
        String uid = user.getUid();
        advlist = new ArrayList<>();
        DatabaseReference ref = reference.child(uid).child("History").child("Hotel");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    hotelBillData data = dataSnapshot.getValue(hotelBillData.class);
                    advlist.add(data);
                }
                advAdapter = new hotelbittadapter(advlist, getActivity());
                hotel.setAdapter(advAdapter);
                advAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}