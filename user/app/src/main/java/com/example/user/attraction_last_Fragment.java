package com.example.user;

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
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class attraction_last_Fragment extends Fragment {

    ImageView image, likebtn;
    TextView title, place, state;
    boolean flag = false;
    ReadMoreTextView desription;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_attraction_last_, container, false);

        assert getArguments() != null;
        String name3 = getArguments().getString("title");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            checkisLike(name3);
        }

        image = view.findViewById(R.id.img_p);
        title = view.findViewById(R.id.place_p);
        desription = view.findViewById(R.id.desc_p);
        place = view.findViewById(R.id.name);
        state = view.findViewById(R.id.state);
        likebtn = view.findViewById(R.id.likebtn);

        assert getArguments() != null;
        String name1 = getArguments().getString("title");
        title.setText(name1);
        String desc1 = getArguments().getString("description");
        desription.setText(desc1);
        String place1 = getArguments().getString("place");
        place.setText(place1);
        String state1 = getArguments().getString("state");
        state.setText(state1);
        String img = getArguments().getString("image");
        Picasso.get().load(img).into(image);

        likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    unlike();
                } else {
                    like(String.valueOf(img));
                }
            }
        });

        return view;
    }

    private void checkisLike(String name) {

        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference.child("/"+currentuser).child("/Like").child("Attraction").child("/"+name).addValueEventListener(new ValueEventListener() {
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

        DatabaseReference ref = databaseReference.child(currentuser).child("/Like").child("Attraction").child(place3);
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

        String atitle = title.getText().toString();
        String astate = state.getText().toString();
        String aplace = place.getText().toString();
        String adesc = desription.getText().toString();

        AttractionData attractData = new AttractionData(atitle ,imageurl , adesc,  uniquekey, astate, aplace);
        databaseReference.child(currentuser).child("/Like").child("Attraction").child(atitle).setValue(attractData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                likebtn.setImageResource(R.drawable.likebtn);
            }
        });
    }

}