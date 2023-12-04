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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class update_info_advisory extends AppCompatActivity {

    EditText placea, statea, desca , ttda;
    ImageView img;
    AutoCompleteTextView wtta;
    Button update;
    private final int REQ =1;
    Bitmap bitmap;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Advisory");
    StorageReference storageReference;
    String downloadurl = "";
    ProgressDialog pd;
    String[] season = {"Summer","Winter","Monsoon"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info_advisory);

        placea = findViewById(R.id.place);
        statea = findViewById(R.id.state);
        desca = findViewById(R.id.desc);
        wtta = findViewById(R.id.wtt);
        ttda = findViewById(R.id.ttd);
        img = findViewById(R.id.img_adv);
        update = findViewById(R.id.update);

        pd = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.hotel_item,season);
        wtta.setThreshold(1);
        wtta.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Update Advisory Information");
        }

        storageReference = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        String hname = intent.getStringExtra("place");
        placea.setText(hname);
        String state = intent.getStringExtra("state");
        statea.setText(state);
        String desc = intent.getStringExtra("desc");
        desca.setText(desc);
        String wtt1 = intent.getStringExtra("wtt");
        wtta.setText(wtt1);
        String ttd1 = intent.getStringExtra("ttd");
        ttda.setText(ttd1);
        String imga = intent.getStringExtra("image");
        Picasso.get().load(imga).into(img);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(placea.getText().toString().isEmpty()) {
                    placea.setError("Place Name Empty");
                    placea.requestFocus();
                } else if (statea.getText().toString().isEmpty()) {
                    statea.setError("State Empty");
                    statea.requestFocus();
                }  else if (desca.getText().toString().isEmpty()) {
                    desca.setError("Description Empty");
                    desca.requestFocus();
                } else if (wtta.getText().toString().isEmpty()) {
                    wtta.setError("When to visit Empty");
                    wtta.requestFocus();
                } else if (ttda.getText().toString().isEmpty()) {
                    ttda.setError("Things To Do Empty");
                    ttda.requestFocus();
                } else if (bitmap == null) {
                    updateData(imga);
                } else {
                    uploadImage();
                }
            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
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
        uploadTask.addOnCompleteListener(update_info_advisory.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(update_info_advisory.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {

        String con = placea.getText().toString();


        HashMap hp = new HashMap();
        hp.put("place",placea.getText().toString());
        hp.put("state",statea.getText().toString());
        hp.put("description",desca.getText().toString());
        hp.put("wtt",wtta.getText().toString());
        hp.put("ttd",ttda.getText().toString());
        hp.put("image",s);

        DatabaseReference ref = reference.child("/"+con);

        ref.updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(update_info_advisory.this, "Update Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(update_info_advisory.this, update_advisory.class);
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

}