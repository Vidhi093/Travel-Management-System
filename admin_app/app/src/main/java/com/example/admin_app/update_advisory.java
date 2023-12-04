package com.example.admin_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LongSparseArray;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class update_advisory extends AppCompatActivity {

    FloatingActionButton fab;
    GridView gridView;
    ArrayList<advisoryData> datalist;
    advisoryAdapter adapter;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Advisory");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_advisory);

        fab = findViewById(R.id.fab);
        gridView = findViewById(R.id.gridview);

        datalist = new ArrayList<>();
        adapter = new advisoryAdapter(datalist, this);
        gridView.setAdapter(adapter);

        ActionBar actionBar2 = getSupportActionBar();
        if (actionBar2 != null) {
            actionBar2.setTitle("Advisory");
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                advisoryData data = (advisoryData) parent.getItemAtPosition(position);

                Intent intent = new Intent(update_advisory.this, last_advisory.class);
                intent.putExtra("name",data.getPlace());
                intent.putExtra("state",data.getState());
                intent.putExtra("description",data.getDescription());
                intent.putExtra("wtt",data.getWtt());
                intent.putExtra("ttd",data.getTtd());
                intent.putExtra("resId",data.getImage());
                startActivity(intent);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData advisorydata = dataSnapshot.getValue(advisoryData.class);
                    datalist.add(advisorydata);
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
                Intent intent = new Intent(getApplicationContext(),add_advisory.class);
                startActivity(intent);
            }
        });



    }
}