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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Popular_dest extends AppCompatActivity {

    private final int REQ =1;
    Bitmap bitmap;
    EditText State_n, pop_title, pop_desc;
    CardView select_pop;
    ImageView imgset_pop;
    Button pop_upload;
    DatabaseReference reference;
    StorageReference storageReference;
    String downloadurl = "";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_dest);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Popular Place");
        }

        pd = new ProgressDialog(this);
        State_n = findViewById(R.id.state_name);
        pop_title = findViewById(R.id.popular_title);
        pop_desc = findViewById(R.id.pop_dsp);
        select_pop = findViewById(R.id.upload_pop_destination);
        imgset_pop = findViewById(R.id.pop_image);
        pop_upload = findViewById(R.id.upload_popularbtn);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        Intent state = getIntent();
        String str = state.getStringExtra("state name");
        State_n.setText(str);

        Intent state1 = getIntent();
        String str1 = state1.getStringExtra("state");
        State_n.setText(str1);

         pop_upload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(pop_title.getText().toString().isEmpty()) {
                     pop_title.setError("Place Title Empty");
                     pop_title.requestFocus();
                 } else if (pop_desc.getText().toString().isEmpty()) {
                     pop_desc.setError("Place Description Empty");
                     pop_desc.requestFocus();
                 } else if (State_n.getText().toString().isEmpty()) {
                     State_n.setError("State Name Empty");
                     State_n.requestFocus();
                 } else if (bitmap == null) {
                     uploadData();
                 } else {
                     uploadImage();
                 }
             }
         });

        select_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryforpopular();
            }
        });

    }

    private void uploadImage() {
        String state = State_n.getText().toString();
            pd.setMessage("Uploading...");
            pd.show();
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,boas);
            byte[] finalimg = boas.toByteArray();
            final StorageReference filepath;
            filepath = storageReference.child("/State").child(finalimg+"jpg"); //image stored in storage
            final UploadTask uploadTask = filepath.putBytes(finalimg);
            uploadTask.addOnCompleteListener(Popular_dest.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                        Toast.makeText(Popular_dest.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void uploadData() {
        //reference = reference.child("State"); //data will be stored in firebase
        final String uniquekey = reference.push().getKey();

        String staten = State_n.getText().toString();
        String ptitle = pop_title.getText().toString();
        String pdesc = pop_desc.getText().toString();

        PopulardestData popData = new PopulardestData(ptitle ,downloadurl , pdesc,  uniquekey);
        reference.child("/State").child("/"+staten).child("Popular Place").child(ptitle).setValue(popData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Popular_dest.this, "Place Information Uploaded", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Popular_dest.this, update_popular.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Popular_dest.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void openGalleryforpopular() {
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
            imgset_pop.setImageBitmap(bitmap);

        }
    }


}