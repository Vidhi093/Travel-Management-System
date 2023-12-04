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

public class update_attraction extends AppCompatActivity {

    FloatingActionButton fab;
    GridView gridView;
    ArrayList<AttractionData> datalist1;
    attractionAdapter adapter;
    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attraction");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_attraction);

        fab = findViewById(R.id.fab);
        gridView = findViewById(R.id.gridviewa);

        datalist1 = new ArrayList<>();
        adapter = new attractionAdapter(datalist1, this);
        gridView.setAdapter(adapter);

        Intent intent = getIntent();
        String name = intent.getStringExtra("popname");
        String state1 = intent.getStringExtra("staten");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Attraction");
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AttractionData data = (AttractionData) parent.getItemAtPosition(position);
                Intent intent = new Intent(update_attraction.this, last_attraction.class);
                intent.putExtra("title",data.getTitle());
                intent.putExtra("description",data.getDescription());
                intent.putExtra("resId",data.getImage());
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_attraction.this, Attraction.class);
                intent.putExtra("pname", name);
                intent.putExtra("pstate",state1);
                startActivity(intent);
            }
        });

        //DatabaseReference reference = databaseReference.child("Attraction");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.child("/popular_place").getValue(String.class);
                    assert data != null;
                    if (data.equals(name)) {
                        AttractionData attractionData = dataSnapshot.getValue(AttractionData.class);
                        datalist1.add(attractionData);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}