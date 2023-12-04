package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class update_hotel_package extends AppCompatActivity {

    FloatingActionButton fab;
    GridView gridView, gold, silver, bronze;
    LinearLayout lplatinum, lgold, lsilver, lbronze;
    TextView tplatinum, tgold, tsilver, tbronze;
    ArrayList<hotelData> datalist, list2, list3, list4;
    platinumAdapter adapter;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hotel");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hotel_package);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Hotel Package");
        }

        fab = findViewById(R.id.fab);
        gridView = findViewById(R.id.platinum_grid);
        gold = findViewById(R.id.gold_grid);
        silver = findViewById(R.id.silver_grid);
        bronze = findViewById(R.id.bronze_grid);

        lbronze = findViewById(R.id.lbronze);
        lplatinum = findViewById(R.id.lplatinum);
        lgold = findViewById(R.id.lgold);
        lsilver = findViewById(R.id.lsilver);

        tplatinum = findViewById(R.id.platinum);
        tgold = findViewById(R.id.gold);
        tsilver = findViewById(R.id.silver);
        tbronze = findViewById(R.id.bronze);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Intent intent = new Intent(update_hotel_package.this, last_platinum.class);
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

        gold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Intent intent = new Intent(update_hotel_package.this, last_platinum.class);
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

        silver.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Intent intent = new Intent(update_hotel_package.this, last_platinum.class);
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

        bronze.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Intent intent = new Intent(update_hotel_package.this, last_platinum.class);
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

        lplatinum();
        lgold();
        lsilver();
        lbronze();

        tplatinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_hotel_package.this, update_platinum.class);
                startActivity(intent);
            }
        });

        tgold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_hotel_package.this, update_gold.class);
                startActivity(intent);
            }
        });

        tsilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_hotel_package.this, update_silver.class);
                startActivity(intent);
            }
        });

        tbronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_hotel_package.this, update_bronze.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_hotel_package.this, upload_hotel_package.class);
                startActivity(intent);
            }
        });
    }

    private void lbronze() {
        DatabaseReference ref = databaseReference.child("/Bronze Package");
        list4 = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String data = dataSnapshot.child("/category").getValue(String.class);
                    assert data != null;
                    if (data.equals("Bronze Package")) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        list4.add(data1);
                    }
                }
                adapter = new platinumAdapter(list4, update_hotel_package.this);
                bronze.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void lsilver() {
        DatabaseReference ref = databaseReference.child("/Silver Package");
        list3 = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String data = dataSnapshot.child("/category").getValue(String.class);
                    assert data != null;
                    if (data.equals("Silver Package")) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        list3.add(data1);
                    }
                }
                adapter = new platinumAdapter(list3, update_hotel_package.this);
                silver.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void lgold() {

            list2 = new ArrayList<>();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        String data = dataSnapshot.child("/category").getValue(String.class);
                        assert data != null;
                        if (data.equals("Gold Package")) {
                            hotelData data1 = dataSnapshot.getValue(hotelData.class);
                            list2.add(data1);
                        }
                    }
                    adapter = new platinumAdapter(list2, update_hotel_package.this);
                    gold.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    private void lplatinum() {

        datalist = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String data = dataSnapshot.child("/category").getValue(String.class);
                    assert data != null;
                    if (data.equals("Platinum Package")) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        datalist.add(data1);
                    }
                }
                adapter = new platinumAdapter(datalist, update_hotel_package.this);
                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}