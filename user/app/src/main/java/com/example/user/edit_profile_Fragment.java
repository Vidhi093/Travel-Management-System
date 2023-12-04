package com.example.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class edit_profile_Fragment extends Fragment {

    EditText name, number;
    TextView email;
    Button update;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile_, container, false);

        name = view.findViewById(R.id.name);
        number = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        update = view.findViewById(R.id.update);

        String uid = user.getUid();

        DatabaseReference ref = reference.child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n = snapshot.child("/number").getValue().toString();
                String u = snapshot.child("/username").getValue().toString();
                String e = snapshot.child("/email").getValue().toString();

                name.setText(u);
                number.setText(n);
                email.setText(e);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()){
                    name.setError("Enter Name");
                } else if (number.getText().toString().isEmpty()) {
                    number.setError("Please enter number");
                } else {
                    update();
                }

            }
        });

        return view;
    }

    private void update() {

        HashMap hp = new HashMap();
        hp.put("username",name.getText().toString());
        hp.put("number",number.getText().toString());

        String uid = user.getUid();

        DatabaseReference ref = reference.child(uid);

        ref.updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getActivity(), "Update Successful", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}