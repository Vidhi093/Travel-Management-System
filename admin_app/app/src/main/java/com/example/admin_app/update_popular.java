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

public class update_popular extends AppCompatActivity {

    FloatingActionButton fab;
    GridView gridView;
    ArrayList<PopulardestData> datalist1;
    PopularAdapter adapter;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("State");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_popular);

        gridView =findViewById(R.id.gridview);
        fab = findViewById(R.id.fab);

        datalist1 = new ArrayList<>();
        adapter = new PopularAdapter(datalist1, this);
        gridView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Popular");
        }

        Intent intent = getIntent();
        String state = intent.getStringExtra("name");

        Intent intent3 = new Intent(update_popular.this,last_popular.class);
        intent3.putExtra("nams",state);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PopulardestData data = (PopulardestData) parent.getItemAtPosition(position);
                Intent intent = new Intent(update_popular.this, last_popular.class);
                intent.putExtra("title",data.getTitle());
                intent.putExtra("description",data.getDescription());
                intent.putExtra("resId",data.getImage());
                startActivity(intent);
            }
        });

        DatabaseReference reference = databaseReference.child("/"+state).child("/Popular Place");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PopulardestData populardestData = dataSnapshot.getValue(PopulardestData.class);
                    datalist1.add(populardestData);
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
                Intent intent = new Intent(update_popular.this, Popular_dest.class);
                intent.putExtra("state", state);
                startActivity(intent);
            }
        });
    }
}