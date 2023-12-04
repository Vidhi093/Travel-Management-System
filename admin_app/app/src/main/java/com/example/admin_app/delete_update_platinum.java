package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class delete_update_platinum extends AppCompatActivity {

    ImageView img;
    Button update, delete;
    EditText name, address, desc, contact, price, policy, email;
    AutoCompleteTextView city;
    Spinner location;
    String locations, packagecat;
    AutoCompleteTextView categories;
    StorageReference storageReference;
    Bitmap bitmap;
    String downloadurl = "";
    ProgressDialog pd;
    private final int REQ = 1;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hotel");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_update_platinum);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        img = findViewById(R.id.imageview);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        name = findViewById(R.id.hotel_name);
        address = findViewById(R.id.address);
        desc = findViewById(R.id.hotel_desc);
        contact = findViewById(R.id.hotel_contact);
        price = findViewById(R.id.price);
        policy = findViewById(R.id.policy);
        location = findViewById(R.id.location);
        city = findViewById(R.id.city);
        email = findViewById(R.id.email);
        categories = findViewById(R.id.category);
        pd = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        final ArrayAdapter<String> autocity = new ArrayAdapter<>(this,R.layout.hotel_item);

        String[] states = new String[] {"Select State","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh"
                ,"Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka"
                ,"Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan"
                ,"Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","West Bengal" };

        String[] packagec = {"Platinum Package","Gold Package","Silver Package","Bronze Package"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.hotel_item,packagec);
        categories.setThreshold(1);
        categories.setAdapter(adapter);

        Intent intent2 = getIntent();
        String loct = intent2.getStringExtra("loc");
        String[] loca = new String[] {loct};

        if (loct.equals("")) {
            location.setAdapter(new ArrayAdapter<String>(this, R.layout.hotel_item, states));
        }
        else {
            location.setAdapter(new ArrayAdapter<String>(this, R.layout.hotel_item, loca));
        }
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locations = location.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Intent intent = getIntent();
        String hname = intent.getStringExtra("name");
        name.setText(hname);

        String add = intent.getStringExtra("add");
        address.setText(add);

        String hdesc = intent.getStringExtra("desc");
        desc.setText(hdesc);

        String contact1 = intent.getStringExtra("contact");
        contact.setText(contact1);

        String price1 = intent.getStringExtra("price");
        price.setText(price1);

        String policy1 = intent.getStringExtra("policy");
        policy.setText(policy1);

        String city1 = intent.getStringExtra("city");
        city.setText(city1);

        String email1 = intent.getStringExtra("email");
        email.setText(email1);

        String category = intent.getStringExtra("cat");
        categories.setText(category);

        String img1 = intent.getStringExtra("resId");
        Picasso.get().load(img1).into(img);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Advisory");

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

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().isEmpty()) {
                    name.setError("Hotel Name Empty");
                    name.requestFocus();
                } else if (address.getText().toString().isEmpty()) {
                    address.setError("Address Empty");
                    address.requestFocus();
                } else if (city.getText().toString().isEmpty()) {
                    city.setError("City Empty");
                    city.requestFocus();
                } else if (locations.equals("Select State")) {
                    Toast.makeText(delete_update_platinum.this, "Please provide State Location", Toast.LENGTH_SHORT).show();
                } else if (desc.getText().toString().isEmpty()) {
                    desc.setError("Description Empty");
                    desc.requestFocus();
                } else if (contact.getText().toString().isEmpty()) {
                    contact.setError("Contact Empty");
                    contact.requestFocus();
                } else if (email.getText().toString().isEmpty()) {
                    email.setError("Email Empty");
                    email.requestFocus();
                } else if (price.getText().toString().isEmpty()) {
                    price.setError("Price Empty");
                    price.requestFocus();
                } else if (bitmap == null) {
                    updateData(img1);
                } else {
                    uploadImage();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(delete_update_platinum.this);
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
                        Toast.makeText(delete_update_platinum.this, "Delete Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
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
        uploadTask.addOnCompleteListener(delete_update_platinum.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(downloadurl);
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(delete_update_platinum.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {

        String con = contact.getText().toString();

        HashMap hp = new HashMap();
        hp.put("name",name.getText().toString());
        hp.put("address",address.getText().toString());
        hp.put("location",locations);
        hp.put("desc",desc.getText().toString());
        hp.put("contact",contact.getText().toString());
        hp.put("price",price.getText().toString());
        hp.put("policy",policy.getText().toString());
        hp.put("city",city.getText().toString());
        hp.put("email",email.getText().toString());
        hp.put("category",categories.getText().toString());
        hp.put("image",s);

        DatabaseReference ref = databaseReference.child("/"+con);

        ref.updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(delete_update_platinum.this, "Update Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(delete_update_platinum.this, update_hotel_package.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
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
            img.setImageBitmap(bitmap);
        }
    }

    private void deleteData() {

        String cont = contact.getText().toString();
        DatabaseReference ref = databaseReference.child("/"+cont);

        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(delete_update_platinum.this, "Deleted Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(delete_update_platinum.this, update_hotel_package.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(delete_update_platinum.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}