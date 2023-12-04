package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class update_gold extends AppCompatActivity {

    FloatingActionButton fab;
    GridView gridView;
    ArrayList<hotelData> datalist;
    platinumAdapter adapter;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hotel");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gold);

        fab = findViewById(R.id.fab);
        gridView = findViewById(R.id.gridview);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Gold Package");
        }

        DatabaseReference ref = databaseReference.child("/Gold Package");

        datalist = new ArrayList<>();
        adapter = new platinumAdapter(datalist, this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Intent intent = new Intent(update_gold.this, last_platinum.class);
                intent.putExtra("name",data.getName());
                intent.putExtra("add",data.getAddress());
                intent.putExtra("desc",data.getDesc());
                intent.putExtra("contact",data.getContact());
                intent.putExtra("price",data.getPrice());
                intent.putExtra("policy",data.getPolicy());
                intent.putExtra("resId",data.getImage());
                intent.putExtra("location",data.getLocation());
                intent.putExtra("city",data.getCity());
                intent.putExtra("email",data.getEmail());
                intent.putExtra("category",data.getCategory());
                startActivity(intent);
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String data = dataSnapshot.child("/category").getValue(String.class);
                    assert data != null;
                    if (data.equals("Gold Package")) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        datalist.add(data1);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),upload_hotel_package.class);
                startActivity(intent);
            }
        });
    }
}