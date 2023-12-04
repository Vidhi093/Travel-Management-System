package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class special_package extends AppCompatActivity {

    TextView summer, winter, monsoon;
    GridView summerg, winterg, monsoong;
    LinearLayout summerl, winterl, monsoonl;
    FloatingActionButton fab;

    ArrayList<advisoryData> slist, wlist, mlist;
    advisoryAdapter adapter;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Advisory");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_package);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Special Package");
        }

        summer = findViewById(R.id.summer);
        winter = findViewById(R.id.winter);
        monsoon = findViewById(R.id.monsoon);

        summerg = findViewById(R.id.summerg);
        winterg = findViewById(R.id.winterg);
        monsoong = findViewById(R.id.monsoong);

        summerl = findViewById(R.id.summerl);
        winterl = findViewById(R.id.winterl);
        monsoonl = findViewById(R.id.monsoonl);
        fab = findViewById(R.id.fab);

        summerg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                advisoryData data = (advisoryData) parent.getItemAtPosition(position);

                Intent intent = new Intent(special_package.this, last_special.class);
                intent.putExtra("name",data.getPlace());
                intent.putExtra("state",data.getState());
                intent.putExtra("wtt",data.getWtt());
                intent.putExtra("resId",data.getImage());
                intent.putExtra("ttd",data.getTtd());
                intent.putExtra("description",data.getDescription());
                startActivity(intent);

            }
        });

        winterg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                advisoryData data = (advisoryData) parent.getItemAtPosition(position);

                Intent intent = new Intent(special_package.this, last_special.class);
                intent.putExtra("name",data.getPlace());
                intent.putExtra("state",data.getState());
                intent.putExtra("wtt",data.getWtt());
                intent.putExtra("resId",data.getImage());
                intent.putExtra("ttd",data.getTtd());
                intent.putExtra("description",data.getDescription());
                startActivity(intent);

            }
        });

        monsoong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                advisoryData data = (advisoryData) parent.getItemAtPosition(position);

                Intent intent = new Intent(special_package.this, last_special.class);
                intent.putExtra("name",data.getPlace());
                intent.putExtra("state",data.getState());
                intent.putExtra("wtt",data.getWtt());
                intent.putExtra("resId",data.getImage());
                intent.putExtra("ttd",data.getTtd());
                intent.putExtra("description",data.getDescription());
                startActivity(intent);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(special_package.this, add_advisory.class);
                startActivity(intent);
            }
        });

        summerl();
        winterl();
        monsoonl();

    }

    private void monsoonl() {
        mlist = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    assert data != null;
                    if (data.getWtt().equals("Monsoon") || data.getWtt().equals("All season available")) {
                        mlist.add(data);

                    }
                    adapter = new advisoryAdapter(mlist,special_package.this);
                    monsoong.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void winterl() {

        wlist = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    assert data != null;
                    if (data.getWtt().equals("Winter") || data.getWtt().equals("All season available")) {
                        wlist.add(data);

                    }
                    adapter = new advisoryAdapter(wlist,special_package.this);
                    winterg.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void summerl() {

        slist = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    assert data != null;
                    if (data.getWtt().equals("Summer") || data.getWtt().equals("All season available")) {
                        slist.add(data);

                    }
                    adapter = new advisoryAdapter(slist,special_package.this);
                    summerg.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}