package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class update_destination extends AppCompatActivity {

    FloatingActionButton fab1;
    SwipeRefreshLayout swipeRefreshLayout;
    GridView gridView;
    ArrayList<DestnData> datalist1;
    DestAdapter adapter;
    Handler handler = new Handler();
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("State");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_destination);
        fab1 = findViewById(R.id.fab);
        gridView = findViewById(R.id.gridview1);
        swipeRefreshLayout = findViewById(R.id.swipe);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        datalist1 = new ArrayList<>();
        adapter = new DestAdapter(datalist1, this);
        gridView.setAdapter(adapter);

        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) {
            actionBar1.setTitle("Destinations");
        }

        boolean connection = isNetworkAvailable();
        if(connection){

        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(update_destination.this, "Internet not connected", Toast.LENGTH_SHORT).show();
                }
            }, 700000);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                boolean connection = isNetworkAvailable();
                if(connection){
                    Toast.makeText(update_destination.this, "Refreshed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), update_destination.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(update_destination.this, "Internet not connected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DestnData data = (DestnData) parent.getItemAtPosition(position);

                Intent intent = new Intent(update_destination.this, last_destination.class);
                intent.putExtra("name",data.getTitle());
                intent.putExtra("description",data.getDescription());
                intent.putExtra("resId",data.getImage());
                startActivity(intent);
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    DestnData destnData = dataSnapshot.getValue(DestnData.class);
                    datalist1.add(destnData);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Upload_Destination.class);
                startActivity(intent);
            }
        });


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager= (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}