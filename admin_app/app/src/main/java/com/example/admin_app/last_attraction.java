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
import com.squareup.picasso.Picasso;

public class last_attraction extends AppCompatActivity {

    ImageView image;
    TextView place, desription;
    Button update, delete;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("State");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_attraction);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        image = findViewById(R.id.img_p);
        place = findViewById(R.id.place_p);
        desription = findViewById(R.id.desc_p);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        Intent intent = getIntent();
        String name = intent.getStringExtra("title");
        place.setText(name);

        String des = intent.getStringExtra("description");
        desription.setText(des);

        String img1 = intent.getStringExtra("resId");
        Picasso.get().load(img1).into(image);

        String state = intent.getStringExtra("State");
        String pop = intent.getStringExtra("popname");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(last_attraction.this);
                alert.setTitle("Confirm Delete");
                alert.setMessage("Are you sure, you want to delete?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String cont = place.getText().toString();
                        DatabaseReference ref = databaseReference.child("/"+state).child("/Popular Place").child("/"+pop).child("/Attraction").child("/"+cont);
                        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(last_attraction.this, "Deleted Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(last_attraction.this, update_destination.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(last_attraction.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(last_attraction.this, "Delete Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1 = place.getText().toString();
                String desc = desription.getText().toString();

                Intent intent1 = new Intent(last_attraction.this, update_info_attraction.class);
                intent1.putExtra("name",name1);
                intent1.putExtra("desc",desc);
                intent1.putExtra("image",img1);
                intent1.putExtra("sta",state);
                intent1.putExtra("pop",pop);
                startActivity(intent1);
            }
        });

    }
}