package com.example.admin_app;

import androidx.annotation.NonNull;
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

public class last_destination extends AppCompatActivity {

    ImageView img;
    TextView state, desc, pop_btn;
    GridView gridView;
    Button update, delete;
    ArrayList<PopulardestData> datalist1;
    PopularAdapter adapter;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("State");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_destination);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        img = findViewById(R.id.img);
        state = findViewById(R.id.state);
        desc = findViewById(R.id.desc_);
        pop_btn = findViewById(R.id.pop_btn);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);

        gridView =findViewById(R.id.pop_gridview);

        datalist1 = new ArrayList<>();
        adapter = new PopularAdapter(datalist1, this);
        gridView.setAdapter(adapter);

        Intent intent = getIntent();
        String stat = intent.getStringExtra("name");
        state.setText(stat);

        String des = intent.getStringExtra("description");
        desc.setText(des);

        String img1 = intent.getStringExtra("resId");
        Picasso.get().load(img1).into(img);


        pop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(last_destination.this, update_popular.class);
                intent.putExtra("name",stat);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(last_destination.this);
                alert.setTitle("Confirm Delete");
                alert.setMessage("Are you sure, you want to delete?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteData();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(last_destination.this, "Delete Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(last_destination.this, update_info_destination.class);
                intent1.putExtra("title", stat);
                intent1.putExtra("desc",des);
                intent1.putExtra("image",img1);
                startActivity(intent1);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PopulardestData data = (PopulardestData) parent.getItemAtPosition(position);

                Intent intent = new Intent(last_destination.this, last_popular.class);
                intent.putExtra("title",data.getTitle());
                intent.putExtra("description",data.getDescription());
                intent.putExtra("resId",data.getImage());
                intent.putExtra("nams",stat);
                startActivity(intent);
            }
        });

        DatabaseReference reference = databaseReference.child("/"+stat).child("/Popular Place");

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


    }

    private void deleteData() {
        String cont = state.getText().toString();
        DatabaseReference ref = databaseReference.child("/"+cont);

        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(last_destination.this, "Deleted Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(last_destination.this, update_destination.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(last_destination.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}