package com.example.admin_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class last_advisory extends AppCompatActivity {

    ImageView img;
    TextView place, state, desc, wtt, ttd;
    StorageReference storageReference;
    Button delete, update;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Advisory");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_advisory);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        img = findViewById(R.id.img);
        place = findViewById(R.id.name);
        state = findViewById(R.id.state_n);
        desc = findViewById(R.id.desc_n);
        wtt = findViewById(R.id.wtt_n);
        ttd = findViewById(R.id.ttd2);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        storageReference = FirebaseStorage.getInstance().getReference();


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        place.setText(name);
        String state1 = intent.getStringExtra("state");
        state.setText(state1);
        String des = intent.getStringExtra("description");
        desc.setText(des);
        String wtt1 = intent.getStringExtra("wtt");
        wtt.setText(wtt1);
        String ttd1 = intent.getStringExtra("ttd");
        ttd.setText(ttd1);
        String img1 = intent.getStringExtra("resId");
        Picasso.get().load(img1).into(img);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(last_advisory.this, update_info_advisory.class);
                intent1.putExtra("place",name);
                intent1.putExtra("state",state1);
                intent1.putExtra("desc",des);
                intent1.putExtra("wtt",wtt1);
                intent1.putExtra("ttd",ttd1);
                intent1.putExtra("image",img1);
                startActivity(intent1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(last_advisory.this);
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
                        Toast.makeText(last_advisory.this, "Delete Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

    }

    private void deleteData() {

        String cont = place.getText().toString();
        DatabaseReference ref = databaseReference.child("/"+cont);

        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(last_advisory.this, "Deleted Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(last_advisory.this, update_advisory.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(last_advisory.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}