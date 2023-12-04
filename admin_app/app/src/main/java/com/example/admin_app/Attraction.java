package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class Attraction extends AppCompatActivity {
    EditText pop_name,attract_title, attract_desc, staten;
    CardView selectimg;
    ImageView setimg;
    Button upload_attbtn;
    private final int REQ =1;
    Bitmap bitmap;
    DatabaseReference reference;
    StorageReference storageReference;
    String downloadurl = "";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Attraction");
        }

        pd = new ProgressDialog(this);
        pop_name = findViewById(R.id.pop_name);
        staten = findViewById(R.id.staten);
        attract_title = findViewById(R.id.attract_title);
        attract_desc = findViewById(R.id.attraction_dsp);
        selectimg = findViewById(R.id.upload_attract);
        setimg = findViewById(R.id.imgAttract);
        upload_attbtn = findViewById(R.id.upload_attractbtn);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        Intent popular = getIntent();
        String str = popular.getStringExtra("popular name");
        pop_name.setText(str);

        Intent state = getIntent();
        String strstate = state.getStringExtra("staten");
        staten.setText(strstate);

        Intent pop = getIntent();
        String pname = pop.getStringExtra("pname");
        pop_name.setText(pname);
        String pstate = pop.getStringExtra("pstate");
        staten.setText(pstate);


        upload_attbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attract_title.getText().toString().isEmpty()) {
                    attract_title.setError("Attraction Title Empty");
                    attract_title.requestFocus();
                } else if (attract_desc.getText().toString().isEmpty()) {
                    attract_desc.setError("Attraction Description Empty");
                    attract_desc.requestFocus();
                } else if (pop_name.getText().toString().isEmpty()) {
                    pop_name.setError("Place Name Empty");
                    pop_name.requestFocus();
                } else if (staten.getText().toString().isEmpty()) {
                    staten.setError("State Name Empty");
                }else if (bitmap == null) {
                    uploadData();
                } else {
                    uploadImage();
                }
            }
        });

        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        
    }

    private void opengallery() {
        Intent pickimgp = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimgp,REQ);
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
            setimg.setImageBitmap(bitmap);

        }
    }

    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,boas);
        byte[] finalimg = boas.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("/State").child(finalimg+"jpg"); //image stored in storage
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(Attraction.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(Attraction.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadData() {
        //reference = reference.child("State"); //data will be stored in firebase
        final String uniquekey = reference.push().getKey();

        String state = staten.getText().toString();
        String popn = pop_name.getText().toString();
        String atitle = attract_title.getText().toString();
        String adesc = attract_desc.getText().toString();

        AttractionData attractData = new AttractionData(atitle ,downloadurl , adesc,  uniquekey, state, popn);
        reference.child("Attraction").child(atitle).setValue(attractData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Attraction.this, "Attraction Uploaded", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Attraction.this, update_attraction.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Attraction.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }
}