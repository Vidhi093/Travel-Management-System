package com.example.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Like_Fragment extends Fragment {

    ArrayList<advisoryData> advlist, speciallist;
    advisoryAdapter advAdapter, specialadapter;
    ArrayList<AttractionData> attractionlist;
    attractionAdapter attadapter;
    ArrayList<DestnData> destnlist;
    DestAdapter destAdapter;
    ArrayList<hotelData> hotelist;
    platinumAdapter hoteladapter;
    GridView advg, attractg, stateg, specialg, hotelg;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like_, container, false);

        hotelg = view.findViewById(R.id.hotelg);
        advg = view.findViewById(R.id.advgrid);
        attractg = view.findViewById(R.id.attractg);
        stateg = view.findViewById(R.id.stateg);
        specialg = view.findViewById(R.id.specialg);

        hotel();

        advisory();

        specialpackage();

        destination();

        attraction();

        attractg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                getParentFragmentManager().beginTransaction().replace(R.id.likefrag, tofragment)
                        .addToBackStack(String.valueOf(Like_Fragment.class)).commit();
            }
        });

        specialg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                getParentFragmentManager().beginTransaction().replace(R.id.likefrag, tofragment)
                        .addToBackStack(String.valueOf(Like_Fragment.class)).commit();
            }
        });

        stateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DestnData data = (DestnData) parent.getItemAtPosition(position);

                Bundle args = new Bundle();
                args.putString("name", data.getTitle());
                args.putString("description", data.getDescription());
                args.putString("image", data.getImage());

                Fragment tofragment = new Home_last_Fragment();
                tofragment.setArguments(args);

                getParentFragmentManager().beginTransaction().replace(R.id.likefrag, tofragment)
                        .addToBackStack(String.valueOf(Like_Fragment.class)).commit();
            }
        });

        advg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                getParentFragmentManager().beginTransaction().replace(R.id.likefrag, tofragment)
                        .addToBackStack(String.valueOf(Like_Fragment.class)).commit();
            }
        });


        return view;
    }

    private void hotel() {
        assert user != null;
        String uid = user.getUid();

        destnlist = new ArrayList<>();
        DatabaseReference ref1 = reference.child(uid).child("Like").child("Destination");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    DestnData data = dataSnapshot.getValue(DestnData.class);
                    destnlist.add(data);
                }
                destAdapter = new DestAdapter(destnlist, getActivity());
                stateg.setAdapter(destAdapter);
                destAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void destination() {
        assert user != null;
        String uid = user.getUid();

        hotelist = new ArrayList<>();
        DatabaseReference ref1 = reference.child(uid).child("Like").child("Hotel");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    hotelData data = dataSnapshot.getValue(hotelData.class);
                    hotelist.add(data);
                }
                hoteladapter = new platinumAdapter(hotelist, getActivity());
                hotelg.setAdapter(hoteladapter);
                hoteladapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void attraction() {
        assert user != null;
        String uid = user.getUid();

        attractionlist = new ArrayList<>();
        DatabaseReference ref2 = reference.child(uid).child("Like").child("Attraction");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    AttractionData data = dataSnapshot.getValue(AttractionData.class);
                    attractionlist.add(data);
                }
                attadapter = new attractionAdapter(attractionlist, getActivity());
                attractg.setAdapter(attadapter);
                attadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

   private void specialpackage() {
        assert user != null;
        String uid = user.getUid();
        speciallist = new ArrayList<>();
        DatabaseReference ref5 = reference.child(uid).child("Like").child("Special");
        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    speciallist.add(data);
                }
                specialadapter = new advisoryAdapter(speciallist, getActivity());
                specialg.setAdapter(specialadapter);
                specialadapter.notifyDataSetChanged();
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
        DatabaseReference ref = reference.child(uid).child("Like").child("Advisory");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    advlist.add(data);
                }
                advAdapter = new advisoryAdapter(advlist, getActivity());
                advg.setAdapter(advAdapter);
                advAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}