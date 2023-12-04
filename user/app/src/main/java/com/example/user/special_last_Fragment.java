package com.example.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class special_last_Fragment extends Fragment {

    ImageView img, likebtn;
    GridView hotelg;
    boolean flag = false;
    TextView place, state, wtt, hotels;
    ReadMoreTextView desc, ttd;
    LinearLayout hotell;
    StorageReference storageReference;
    ArrayList<hotelData> list;
    platinumAdapter adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");

    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Advisory");
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Hotel");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_special_last_, container, false);

        assert getArguments() != null;
        String name3 = getArguments().getString("name");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            checkisLike(name3);
        }

        img = view.findViewById(R.id.img);
        place = view.findViewById(R.id.name);
        state = view.findViewById(R.id.state_n);
        desc = view.findViewById(R.id.desc_n);
        wtt = view.findViewById(R.id.wtt_n);
        ttd = view.findViewById(R.id.ttd2);
        hotels = view.findViewById(R.id.hotel);
        hotelg = view.findViewById(R.id.hotelg);
        hotell = view.findViewById(R.id.hotell);
        likebtn = view.findViewById(R.id.likebtn);

        list = new ArrayList<>();
        adapter = new platinumAdapter(list,getActivity());
        hotelg.setAdapter(adapter);

        assert getArguments() != null;
        String name1 = getArguments().getString("name");
        String desc1 = getArguments().getString("description");
        String image = getArguments().getString("resId");
        String state1 = getArguments().getString("state");
        String wtt1 = getArguments().getString("wtt");
        String tt1 = getArguments().getString("ttd");

        place.setText(name1);
        desc.setText(desc1);
        state.setText(state1);
        wtt.setText(wtt1);
        ttd.setText(tt1);
        Picasso.get().load(image).into(img);

        hotelg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                getParentFragmentManager().beginTransaction().replace(R.id.special, tofragment)
                        .addToBackStack(String.valueOf(special_last_Fragment.class)).commit();
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.child("/city").getValue(String.class);
                    assert data != null;
                    if (data.equals(name1)) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        list.add(data1);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    unlike();
                } else {
                    like(image);
                }
            }
        });

        return view;
    }

    private void checkisLike(String name) {

        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference.child("/"+currentuser).child("/Like").child("Special").child("/"+name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                flag = snapshot.exists();
                if (flag) {
                    likebtn.setImageResource(R.drawable.likebtn);
                } else {
                    likebtn.setImageResource(R.drawable.nolike);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void unlike() {

        String place3 = place.getText().toString();
        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DatabaseReference ref = databaseReference.child(currentuser).child("/Like").child("Special").child(place3);
        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(getActivity(), "Removed Successful", Toast.LENGTH_SHORT).show();
                likebtn.setImageResource(R.drawable.nolike);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void like(String imageurl) {
        final String uniquekey = databaseReference.push().getKey();

        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        String astate = state.getText().toString();
        String aplace = place.getText().toString();
        String awtt = wtt.getText().toString();
        String attd = ttd.getText().toString();
        String adesc = desc.getText().toString();

        advisoryData advisorydata = new advisoryData(astate ,aplace, awtt, attd,imageurl, uniquekey, adesc);
        databaseReference.child(currentuser).child("/Like").child("Special").child(aplace).setValue(advisorydata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                likebtn.setImageResource(R.drawable.likebtn);
            }
        });
    }


}