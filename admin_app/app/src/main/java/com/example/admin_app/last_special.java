package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class last_special extends AppCompatActivity {

    ImageView img;
    GridView hotelg;
    TextView place, state, desc, wtt, ttd, hotels;
    LinearLayout hotell;
    StorageReference storageReference;
    ArrayList<hotelData> list;
    platinumAdapter adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Advisory");
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Hotel");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_special);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        img = findViewById(R.id.img);
        place = findViewById(R.id.name);
        state = findViewById(R.id.state_n);
        desc = findViewById(R.id.desc_n);
        wtt = findViewById(R.id.wtt_n);
        ttd = findViewById(R.id.ttd2);
        hotels = findViewById(R.id.hotel);
        hotelg = findViewById(R.id.hotelg);
        hotell = findViewById(R.id.hotell);

        storageReference = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        place.setText(name);
        String state1 = intent.getStringExtra("state");
        state.setText(state1);
        String des = intent.getStringExtra("description");
        desc.setText(des);
        String wtt1 = intent.getStringExtra("wtt");
        wtt.setText(wtt1);
        String ttd1 = intent.getStringExtra("ttd");
        ttd.setText(ttd1);
        String img1 = intent.getStringExtra("resId");
        Picasso.get().load(img1).into(img);

        hotell();

        hotelg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Intent intent1 = new Intent(last_special.this,last_platinum.class);
                intent1.putExtra("name",data.getName());
                intent1.putExtra("add",data.getAddress());
                intent1.putExtra("desc",data.getDesc());
                intent1.putExtra("contact",data.getContact());
                intent1.putExtra("price",data.getPrice());
                intent1.putExtra("policy",data.getPolicy());
                intent1.putExtra("resId",data.getImage());
                intent1.putExtra("location",data.getLocation());
                intent1.putExtra("city",data.getCity());
                intent1.putExtra("email",data.getEmail());
                intent1.putExtra("category",data.getCategory());
                startActivity(intent1);
            }
        });

        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(last_special.this,special_hotels.class);
                intent1.putExtra("placen",name);
                startActivity(intent1);
            }
        });

    }

    private void hotell() {
        String name1 = place.getText().toString();
        list = new ArrayList<>();

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
                    adapter = new platinumAdapter(list,last_special.this);
                    hotelg.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}