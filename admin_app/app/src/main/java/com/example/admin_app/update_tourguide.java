package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class update_tourguide extends AppCompatActivity {

    EditText name, contact, price;
    AutoCompleteTextView city, state;
    CircleImageView image;
    Button upload;

    Bitmap bitmap;
    DatabaseReference reference;
    StorageReference storageReference;
    String downloadurl = "";
    ProgressDialog pd;
    private final int REQ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tourguide);

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

        String[] packagec = {"Select State","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh"
                ,"Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka"
                ,"Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan"
                ,"Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","West Bengal" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.hotel_item,packagec);
        state.setThreshold(1);
        state.setAdapter(adapter);

        Intent intent = getIntent();
        String tname = intent.getStringExtra("name");
        name.setText(tname);
        String tcontact = intent.getStringExtra("contact");
        contact.setText(tcontact);
        String tstate = intent.getStringExtra("state");
        state.setText(tstate);
        String tcity = intent.getStringExtra("place");
        city.setText(tcity);
        String tprice = intent.getStringExtra("price");
        price.setText(tprice);
        String timage = intent.getStringExtra("image");
        Picasso.get().load(timage).into(image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Enter Name");
                    name.requestFocus();
                } else if (contact.getText().toString().isEmpty()) {
                    contact.setError("Enter Contact");
                    contact.requestFocus();
                }else if (state.getText().toString().isEmpty()) {
                    Toast.makeText(update_tourguide.this, "Select State", Toast.LENGTH_SHORT).show();
                }else if (city.getText().toString().isEmpty()) {
                    city.setError("Enter Contact");
                    city.requestFocus();
                }else if (price.getText().toString().isEmpty()) {
                    price.setError("Enter Contact");
                    price.requestFocus();
                } else if (bitmap == null) {
                    updateData(timage);
                } else {
                    uploadImage();
                }
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
        uploadTask.addOnCompleteListener(update_tourguide.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(update_tourguide.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {
        String con = contact.getText().toString();

        HashMap hp = new HashMap();
        hp.put("name",name.getText().toString());
        hp.put("contact",contact.getText().toString());
        hp.put("price",price.getText().toString());
        hp.put("city",city.getText().toString());
        hp.put("state",state.getText().toString());
        hp.put("image",s);

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Tour Guide");
        DatabaseReference ref = reference1.child("/"+con);

        ref.updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(update_tourguide.this, "Update Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(update_tourguide.this, tour_guide.class);
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
            image.setImageBitmap(bitmap);
        }
    }

}