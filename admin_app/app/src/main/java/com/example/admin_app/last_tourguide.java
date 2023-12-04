package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class last_tourguide extends AppCompatActivity {

    TextView name, contact, state, place, price;
    CircleImageView image;
    Button update, delete;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tour Guide");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_tourguide);

        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        state = findViewById(R.id.state);
        place = findViewById(R.id.place);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        Intent intent = getIntent();
        String tname = intent.getStringExtra("name");
        name.setText(tname);
        String tcontact = intent.getStringExtra("contact");
        contact.setText(tcontact);
        String tstate = intent.getStringExtra("state");
        state.setText(tstate);
        String tcity = intent.getStringExtra("city");
        place.setText(tcity);
        String tprice = intent.getStringExtra("price");
        price.setText(tprice);
        String timage = intent.getStringExtra("image");
        Picasso.get().load(timage).into(image);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(last_tourguide.this);
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
                        Toast.makeText(last_tourguide.this, "Delete Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(last_tourguide.this, update_tourguide.class);
                intent1.putExtra("name",tname);
                intent1.putExtra("contact",tcontact);
                intent1.putExtra("state",tstate);
                intent1.putExtra("place",tcity);
                intent1.putExtra("price",tprice);
                intent1.putExtra("image",timage);
                startActivity(intent1);
            }
        });

    }

    private void deleteData() {

        String cont = contact.getText().toString();
        DatabaseReference ref = databaseReference.child("/"+cont);

        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(last_tourguide.this, "Deleted Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(last_tourguide.this, tour_guide.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(last_tourguide.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}