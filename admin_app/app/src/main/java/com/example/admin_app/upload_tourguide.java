package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class upload_tourguide extends AppCompatActivity {

    EditText name, contact, price;
    AutoCompleteTextView city;
    Spinner state;
    String locations;
    CircleImageView image;
    Button upload;
    private final int REQ =1;
    Bitmap bitmap;
    DatabaseReference reference;
    StorageReference storageReference;
    String downloadurl = "";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_tourguide);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Tour Guide");
        }

        pd = new ProgressDialog(this);

        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        price = findViewById(R.id.price);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        image = findViewById(R.id.image);
        upload = findViewById(R.id.upload);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] states = new String[] {"Select State","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh"
                ,"Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka"
                ,"Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan"
                ,"Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","West Bengal" };

        state.setAdapter(new ArrayAdapter<String>(this, R.layout.hotel_item, states));

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locations = state.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayAdapter<String> autocity = new ArrayAdapter<>(this,R.layout.hotel_item);

        DatabaseReference ref = reference.child("Advisory");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("place").getValue(String.class);
                    autocity.add(suggestion);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        city.setThreshold(1);
        city.setAdapter(autocity);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Enter Name");
                    name.requestFocus();
                } else if (contact.getText().toString().isEmpty()) {
                    contact.setError("Enter Contact");
                    contact.requestFocus();
                }else if (locations.equals("Select State")) {
                    Toast.makeText(upload_tourguide.this, "Select State", Toast.LENGTH_SHORT).show();
                }else if (city.getText().toString().isEmpty()) {
                    city.setError("Enter Contact");
                    city.requestFocus();
                }else if (price.getText().toString().isEmpty()) {
                    price.setError("Enter Contact");
                    price.requestFocus();
                } else if (bitmap == null) {
                    uploadData();
                } else {
                    uploadImage();
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent pickimg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimg,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK){
            assert data != null;
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            image.setImageBitmap(bitmap);
        }
    }

    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,boas);
        byte[] finalimg = boas.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("State").child(finalimg+"jpg"); //image stored in storage
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(upload_tourguide.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(upload_tourguide.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        //reference = reference.child("State"); //data will be stored in firebase

        String tname = name.getText().toString();
        String tstate = locations;
        String tcontact = contact.getText().toString();
        String tcity = city.getText().toString();
        String tprice = price.getText().toString();

        tourguideData data = new tourguideData(tname, tstate, tcontact, tcity, tprice, downloadurl);
        reference.child("Tour Guide").child(tcontact).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(upload_tourguide.this, "Tour Guide Uploaded", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(upload_tourguide.this, tour_guide.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(upload_tourguide.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}