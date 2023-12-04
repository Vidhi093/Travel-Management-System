package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class special_hotels extends AppCompatActivity {

    GridView gridview;
    ArrayList<hotelData> list;
    platinumAdapter adapter;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Hotel");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_hotels);
        gridview = findViewById(R.id.gridview);

        list = new ArrayList<>();


        Intent intent = getIntent();
        String name = intent.getStringExtra("placen");

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotelData data = (hotelData) parent.getItemAtPosition(position);

                Intent intent1 = new Intent(special_hotels.this,last_platinum.class);
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

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.child("/city").getValue(String.class);
                    assert data != null;
                    if (data.equals(name)) {
                        hotelData data1 = dataSnapshot.getValue(hotelData.class);
                        list.add(data1);
                    }
                    adapter = new platinumAdapter(list,special_hotels.this);
                    gridview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}