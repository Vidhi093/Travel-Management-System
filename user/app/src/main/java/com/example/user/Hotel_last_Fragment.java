package com.example.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class Hotel_last_Fragment extends Fragment {

    ImageView image, likebtn;
    Button reserve;
    boolean flag = false;
    TextView hname, hprice, hadd, hcon, hlocation, hcity, hemail, hcategory;
    ReadMoreTextView hdesc, hpoly;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel_last_, container, false);

        String contact1 = getArguments().getString("contact");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            checkisLike(contact1);
        }

        image = view.findViewById(R.id.imageview);
        hname = view.findViewById(R.id.name);
        hprice = view.findViewById(R.id.price);
        hadd = view.findViewById(R.id.address);
        hdesc = view.findViewById(R.id.descr);
        hcon = view.findViewById(R.id.contact);
        hpoly = view.findViewById(R.id.policy);
        hlocation = view.findViewById(R.id.location);
        hcity = view.findViewById(R.id.city);
        hemail = view.findViewById(R.id.email);
        hcategory = view.findViewById(R.id.category);
        likebtn = view.findViewById(R.id.likebtn);

        reserve = view.findViewById(R.id.reserve);

        assert getArguments() != null;
        String name = getArguments().getString("name");
        hname.setText(name);

        String add = getArguments().getString("add");
        hadd.setText(add) ;

        String desc = getArguments().getString("desc");
        hdesc.setText(desc);

        String contact = getArguments().getString("contact");
        hcon.setText(contact);

        String price = getArguments().getString("price");
        hprice.setText(price);

        String policy = getArguments().getString("policy");
        hpoly.setText(policy);

        String loca = getArguments().getString("location");
        hlocation.setText(loca);

        String city = getArguments().getString("city");
        hcity.setText(city);

        String email = getArguments().getString("email");
        hemail.setText(email);

        String cat = getArguments().getString("category");
        hcategory.setText(cat);

        String img1 = getArguments().getString("resId");
        Picasso.get().load(img1).into(image);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), payment_page.class);
                //startActivity(intent);

                Bundle intent = new Bundle();
                intent.putString("name",name);
                intent.putString("add",add);
                intent.putString("desc",desc);
                intent.putString("contact",contact);
                intent.putString("price",price);
                intent.putString("policy",policy);
                intent.putString("location",loca);
                intent.putString("city",city);
                intent.putString("email",email);
                intent.putString("category",cat);
                intent.putString("resId",img1);
                Fragment to = new reservation_Fragment();
                to.setArguments(intent);
                
                getParentFragmentManager().beginTransaction().replace(R.id.hotle_last, to)
                        .addToBackStack(String.valueOf(Hotel_last_Fragment.class)).commit();

            }
        });

        likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    unlike();
                } else {
                    like(img1);
                }
            }
        });

        return view;
    }

    private void checkisLike(String name) {

        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference.child("/"+currentuser).child("Like").child("/Hotel").child("/"+name).addValueEventListener(new ValueEventListener() {
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

        String place3 = hcon.getText().toString();
        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DatabaseReference ref = databaseReference.child(currentuser).child("Like").child("/Hotel").child(place3);
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

        //final String uniquekey = reference.push().getKey();

        String hname1 = hname.getText().toString();
        String hadd1 = hadd.getText().toString();
        String hdesc1 = hdesc.getText().toString();
        String hcontact1 = hcon.getText().toString();
        String hprice1 = hprice.getText().toString();
        String hpolicy1 = hpoly.getText().toString();
        String hcity1 = hcity.getText().toString();
        String hlocations1 = hlocation.getText().toString();
        String hemail1 = hemail.getText().toString();
        String cat = hcategory.getText().toString();


        hotelData hotelData = new hotelData(hname1 ,hadd1, hdesc1, hcontact1, hprice1, hpolicy1, imageurl , uniquekey, cat, hlocations1, hcity1, hemail1);
        databaseReference.child(currentuser).child("Like").child("Hotel").child(hcontact1).setValue(hotelData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                likebtn.setImageResource(R.drawable.likebtn);
            }
        });
    }
}