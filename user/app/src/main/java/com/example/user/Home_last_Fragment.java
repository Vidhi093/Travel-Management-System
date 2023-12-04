package com.example.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
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

import java.util.ArrayList;
import java.util.Objects;



public class Home_last_Fragment extends Fragment {

    ImageView img, likebtn;
    boolean flag = false;
    TextView state, pop_btn, hotel;
    GridView gridView, hotelg;
    ReadMoreTextView more;
    FrameLayout last;
    ArrayList<PopulardestData> list;
    PopularAdapter adapter;
    ArrayList<hotelData> list1;
    platinumAdapter adapter1;

    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User");
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("State");
    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Hotel");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_last_, container, false);

        assert getArguments() != null;
        String name3 = getArguments().getString("name");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            checkisLike(name3);
        }

        img = view.findViewById(R.id.img);
        state = view.findViewById(R.id.state);
        pop_btn = view.findViewById(R.id.pop_btn);
        more = view.findViewById(R.id.readmore);

        hotel = view.findViewById(R.id.hotel);
        hotelg = view.findViewById(R.id.hotelg);

        last = view.findViewById(R.id.last);

        likebtn = view.findViewById(R.id.likebtn);

        gridView = view.findViewById(R.id.pop_gridview);

        list = new ArrayList<>();
        adapter = new PopularAdapter(list, getActivity());
        gridView.setAdapter(adapter);

        list1 = new ArrayList<>();
        adapter1 = new platinumAdapter(list1,getActivity());
        hotelg.setAdapter(adapter1);

        assert getArguments() != null;
        String name1 = getArguments().getString("name");
        String desc1 = getArguments().getString("description");
        String image = getArguments().getString("image");

        state.setText(name1);
        Picasso.get().load(image).into(img);
        more.setText(desc1);


        pop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment to = new popular_all_Fragment();

                Bundle args = new Bundle();
                args.putString("name", name1);
                to.setArguments(args);

                getParentFragmentManager().beginTransaction().replace(R.id.last, to)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopulardestData data = (PopulardestData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("title",data.getTitle());
                intent.putString("description",data.getDescription());
                intent.putString("resId",data.getImage());
                intent.putString("nams",name1);

                Fragment tofragment = new popular_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.last, tofragment)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();

            }
        });

        DatabaseReference reference = databaseReference.child("/"+name1).child("/Popular Place");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    PopulardestData data = dataSnapshot.getValue(PopulardestData.class);
                    list.add(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

                getParentFragmentManager().beginTransaction().replace(R.id.last, tofragment)
                        .addToBackStack(String.valueOf(Home_last_Fragment.class)).commit();
            }
        });

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.child("/location").getValue(String.class);
                    assert data != null;
                    if (data.equals(name1)) {
                        hotelData data2 = dataSnapshot.getValue(hotelData.class);
                        list1.add(data2);
                    }
                    adapter1.notifyDataSetChanged();
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
        databaseReference1.child("/"+currentuser).child("/Like").child("Destination").child("/"+name).addValueEventListener(new ValueEventListener() {
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

        String place3 = state.getText().toString();
        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DatabaseReference ref = databaseReference1.child(currentuser).child("/Like").child("Destination").child(place3);
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
        final String uniquekey = databaseReference1.push().getKey();

        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        String astate = state.getText().toString();
        String adesc = more.getText().toString();


        DestnData destnData = new DestnData(astate ,imageurl , adesc,  uniquekey);
        databaseReference1.child(currentuser).child("/Like").child("Destination").child(astate).setValue(destnData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                likebtn.setImageResource(R.drawable.likebtn);
            }
        });
    }
}