package com.example.admin_app;

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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class add_advisory extends AppCompatActivity {

    ImageView img_adv;
    EditText place, state, ttd, desc;
    AutoCompleteTextView wtt;
    Button upload;
    private final int REQ =1;
    Bitmap bitmap;
    DatabaseReference reference;
    StorageReference storageReference;
    String downloadurl = "";
    ProgressDialog pd;

    String[] season = {"Summer","Winter","Monsoon", "All season available"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advisory);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Upload Advisory");
        }

        pd = new ProgressDialog(this);
        img_adv = findViewById(R.id.img_adv);
        place = findViewById(R.id.place);
        state = findViewById(R.id.state);
        wtt = findViewById(R.id.wtt);
        ttd = findViewById(R.id.ttd);
        desc = findViewById(R.id.desc);
        upload = findViewById(R.id.up_adv);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.hotel_item,season);
        wtt.setThreshold(1);
        wtt.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(place.getText().toString().isEmpty()) {
                    place.setError("State Name Empty");
                    place.requestFocus();
                } else if (state.getText().toString().isEmpty()) {
                    state.setError("State Description Empty");
                    state.requestFocus();
                } else if (desc.getText().toString().isEmpty()) {
                    desc.setError("Description is Empty");
                    desc.requestFocus();
                } else if (wtt.getText().toString().isEmpty()) {
                    wtt.setError("Best time to visit is empty");
                }else if (ttd.getText().toString().isEmpty()) {
                    ttd.setError("Things to Do is Empty");
                } else if (bitmap == null) {
                    uploadData();
                } else {
                    uploadImage();
                }

            }
        });

        img_adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void uploadData() {

        final String uniquekey = reference.push().getKey();

        String astate = state.getText().toString();
        String aplace = place.getText().toString();
        String awtt = wtt.getText().toString();
        String attd = ttd.getText().toString();
        String adesc = desc.getText().toString();

        advisoryData advisorydata = new advisoryData(astate ,aplace, awtt, attd,downloadurl, uniquekey, adesc);
        reference.child("Advisory").child(aplace).setValue(advisorydata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(add_advisory.this, "Advisory Information Uploaded", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(add_advisory.this, update_advisory.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(add_advisory.this,"Something went wrong",Toast.LENGTH_SHORT).show();
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
        filepath = storageReference.child("Advisory").child(finalimg+"jpg"); //image stored in storage
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(add_advisory.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(add_advisory.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
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
            img_adv.setImageBitmap(bitmap);
        }
    }
}
