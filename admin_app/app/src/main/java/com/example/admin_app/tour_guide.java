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

public class tour_guide extends AppCompatActivity {

    FloatingActionButton fab;
    GridView gridview;
    ArrayList<tourguideData> datalist;
    tourguideAdapter adapter;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tour Guide");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide);

        fab = findViewById(R.id.fab);
        gridview = findViewById(R.id.gridview);

        datalist = new ArrayList<>();
        adapter = new tourguideAdapter(datalist, this);
        gridview.setAdapter(adapter);

        ActionBar actionBar2 = getSupportActionBar();
        if (actionBar2 != null) {
            actionBar2.setTitle("Tour Guide");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tour_guide.this, upload_tourguide.class);
                startActivity(intent);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    tourguideData data = dataSnapshot.getValue(tourguideData.class);
                    datalist.add(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tourguideData data = (tourguideData) parent.getItemAtPosition(position);

                Intent intent = new Intent(tour_guide.this, last_tourguide.class);
                intent.putExtra("name",data.getName());
                intent.putExtra("contact",data.getContact());
                intent.putExtra("state",data.getState());
                intent.putExtra("city",data.getCity());
                intent.putExtra("price",data.getPrice());
                intent.putExtra("image",data.getImage());
                startActivity(intent);
            }
        });
    }
}