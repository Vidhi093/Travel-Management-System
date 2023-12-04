package com.example.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.Objects;

public class advisory_last_Fragment extends Fragment {

    ImageView img, likebtn;
    boolean flag = false;
    TextView place, state, wtt;
    ReadMoreTextView desc, ttd;
    StorageReference storageReference;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_advisory_last_, container, false);

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

        likebtn = view.findViewById(R.id.likebtn);

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
        databaseReference.child("/"+currentuser).child("/Like").child("Advisory").child("/"+name).addValueEventListener(new ValueEventListener() {
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

        DatabaseReference ref = databaseReference.child(currentuser).child("/Like").child("Advisory").child(place3);
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
        databaseReference.child(currentuser).child("/Like").child("Advisory").child(aplace).setValue(advisorydata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                likebtn.setImageResource(R.drawable.likebtn);
            }
        });
    }
}