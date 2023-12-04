package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class last_popular extends AppCompatActivity {

    ImageView img;
    TextView title, description, attractionbtn, state;
    Button update, delete;
    GridView gridView;
    ArrayList<AttractionData> datalist1;
    attractionAdapter adapter;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("State");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_popular);

        img = findViewById(R.id.img_p);
        title = findViewById(R.id.place_p);
        description = findViewById(R.id.desc_p);
        attractionbtn = findViewById(R.id.pop_btn);
        gridView = findViewById(R. id.pop_gridview);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        datalist1 = new ArrayList<>();
        adapter = new attractionAdapter(datalist1, this);
        gridView.setAdapter(adapter);

        Intent intent = getIntent();
        String name = intent.getStringExtra("title");
        title.setText(name);

        String des = intent.getStringExtra("description");
        description.setText(des);

        String img1 = intent.getStringExtra("resId");
        Picasso.get().load(img1).into(img);

        String state1 = intent.getStringExtra("nams");

        attractionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(last_popular.this,update_attraction.class);
                intent2.putExtra("popname",name);
                intent2.putExtra("staten",state1);
                startActivity(intent2);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AttractionData data = (AttractionData) parent.getItemAtPosition(position);

                String name1 = title.getText().toString();
                Intent intent = new Intent(last_popular.this, last_attraction.class);
                intent.putExtra("title",data.getTitle());
                intent.putExtra("description",data.getDescription());
                intent.putExtra("resId",data.getImage());
                intent.putExtra("popname",name1);
                intent.putExtra("State",state1);
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Attraction");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String title1 = title.getText().toString();
                    String data = dataSnapshot.child("/popular_place").getValue(String.class);
                    assert data != null;
                    if (data.equals(title1)) {
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String na = title.getText().toString();
                String de = description.getText().toString();

                Intent intent1 = new Intent(last_popular.this,update_info_popular.class);
                intent1.putExtra("name",na);
                intent1.putExtra("des",de);
                intent1.putExtra("image",img1);
                intent1.putExtra("st",state1);
                startActivity(intent1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(last_popular.this);
                alert.setTitle("Confirm Delete");
                alert.setMessage("Are you sure, you want to delete?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cont = title.getText().toString();
                        DatabaseReference ref = databaseReference.child("/"+state1).child("/Popular Place").child("/"+cont);

                        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(last_popular.this, "Deleted Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(last_popular.this, update_destination.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(last_popular.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(last_popular.this, "Delete Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();


                }

        });

    }
}