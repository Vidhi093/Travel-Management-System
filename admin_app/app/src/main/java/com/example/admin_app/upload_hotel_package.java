 package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

 public class upload_hotel_package extends AppCompatActivity {

    ImageView img;
    Button btn;
    CardView select;
    EditText name, address, desc, contact, price, policy,email ;
    Spinner spinner, location;
    AutoCompleteTextView city;
     private final int REQ =1;
     Bitmap bitmap;
     DatabaseReference reference;
     StorageReference storageReference;
     String downloadurl = "";
     ProgressDialog pd;
     String category, locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_hotel_package);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Upload Hotel Package");
        }

        pd = new ProgressDialog(this);
        img = findViewById(R.id.img_h);
        btn = findViewById(R.id.upload);
        select = findViewById(R.id.hotel_package);
        name = findViewById(R.id.hotel_name);
        address = findViewById(R.id.address);
        desc = findViewById(R.id.hotel_desc);
        contact = findViewById(R.id.hotel_contact);
        price = findViewById(R.id.price);
        policy = findViewById(R.id.policy);
        spinner = findViewById(R.id.hotel_spinner);
        location = findViewById(R.id.location);
        city = findViewById(R.id.city);
        email = findViewById(R.id.email);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        final ArrayAdapter<String> autocity = new ArrayAdapter<>(this,R.layout.hotel_item);

        String[] items = new String[] {"Select Category","Platinum Package","Gold Package","Silver Package","Bronze Package" };
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.hotel_item, items));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] states = new String[] {"Select State","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chattisgarh "
                ,"Goa ","Gujarat","Haryana ","Himachal Pradesh ","Jammu and Kashmir ","Jharkhand ","Karnataka "
                ,"Kerala","Madhya Pradesh ","Maharashtra ","Manipur ","Meghalaya ",
                "Mizoram ","Nagaland","Odisha ","Punjab","Rajasthan "
                ,"Sikkim","Tamil Nadu ","Telangana ","Tripura","Uttar Pradesh ", "Uttarakhand ","West Bengal " };

        location.setAdapter(new ArrayAdapter<String>(this, R.layout.hotel_item, states));

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locations = location.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()) {
                    name.setError("Hotel Name Empty");
                    name.requestFocus();
                } else if (address.getText().toString().isEmpty()) {
                    address.setError("Address Empty");
                    address.requestFocus();
                } else if (locations.equals("Select State")) {
                    Toast.makeText(upload_hotel_package.this, "Please provide Hotel State", Toast.LENGTH_SHORT).show();
                } else if (desc.getText().toString().isEmpty()) {
                    desc.setError("Description Empty");
                    desc.requestFocus();
                } else if (contact.getText().toString().isEmpty()) {
                    contact.setError("Contact Empty");
                    contact.requestFocus();
                } else if (price.getText().toString().isEmpty()) {
                    price.setError("Price Empty");
                    price.requestFocus();
                } else if (category.equals("Select Category")) {
                    Toast.makeText(upload_hotel_package.this, "Please provide Package Category", Toast.LENGTH_SHORT).show();
                } else if (bitmap == null) {
                    uploadData();
                } else {
                    uploadImage();
                }

            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


    }

     private void uploadData() {
         //reference = reference.child("State"); //data will be stored in firebase
         final String uniquekey = reference.push().getKey();

         String hname = name.getText().toString();
         String hadd = address.getText().toString();
         String hdesc = desc.getText().toString();
         String hcontact = contact.getText().toString();
         String hprice = price.getText().toString();
         String hpolicy = policy.getText().toString();
         String hcity = city.getText().toString();
         String hemail = email.getText().toString();

         hotelData hotelData = new hotelData(hname ,hadd, hdesc, hcontact, hprice, hpolicy, downloadurl , uniquekey, category, locations, hcity, hemail);
         reference.child("Hotel").child(hcontact).setValue(hotelData).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void unused) {
                 pd.dismiss();
                 Toast.makeText(upload_hotel_package.this, "Hotel Uploaded", Toast.LENGTH_SHORT).show();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 pd.dismiss();
                 Toast.makeText(upload_hotel_package.this,"Something went wrong",Toast.LENGTH_SHORT).show();
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
         uploadTask.addOnCompleteListener(upload_hotel_package.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                     Toast.makeText(upload_hotel_package.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                 }
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
             img.setImageBitmap(bitmap);
         }
     }
 }