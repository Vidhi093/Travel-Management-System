package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class register_page extends AppCompatActivity {

    EditText name, email1, number, password1;
    Button register;
    ProgressDialog pd;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        register = findViewById(R.id.register);
        pd = new ProgressDialog(this);
        name = findViewById(R.id.name);
        email1 = findViewById(R.id.email);
        password1 = findViewById(R.id.inpassword);
        number = findViewById(R.id.phone);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Wait a moment...");
                pd.show();
                if (name.getText().toString().isEmpty()) {
                    pd.dismiss();
                    name.setError("Enter Name");
                    name.requestFocus();
                } else if (number.getText().toString().isEmpty()) {
                    pd.dismiss();
                    number.setError("Enter Phone Number");
                    number.requestFocus();
                }else if (email1.getText().toString().isEmpty()) {
                    pd.dismiss();
                    email1.setError("Enter Phone Number");
                    email1.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email1.getText().toString()).matches()) {
                    pd.dismiss();
                    Toast.makeText(register_page.this,"Re-Enter Your Email", Toast.LENGTH_SHORT).show();
                    email1.setError("Valid Email is Required");
                    email1.requestFocus();
                }else if (password1.getText().toString().isEmpty()) {
                    pd.dismiss();
                    password1.setError("Enter Phone Number");
                    password1.requestFocus();
                } else {
                    createuser();
                }
            }
        });

    }

    private void createuser() {

        String uname = name.getText().toString();
        String email = email1.getText().toString();
        String password = password1.getText().toString();
        String num = number.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(register_page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        boolean connection = isNetworkAvailable();
                        if (task.isSuccessful()){
                            pd.dismiss();
                            String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                            userData data = new userData(uname, num, email);
                            ref.child(currentuser).setValue(data);
                            Toast.makeText(register_page.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(register_page.this, home_page.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            pd.dismiss();
                            if (connection){

                            } else {
                                Toast.makeText(register_page.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(register_page.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    private void saveData() {
        String name1 = name.getText().toString();
        String email3 = email1.getText().toString();
        String numb = number.getText().toString();

        userData data = new userData(name1, numb, email3);
        ref.child("/"+email3).setValue(data);

        pd.dismiss();
        Toast.makeText(register_page.this, "Registration Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(register_page.this, home_page.class);
        startActivity(intent);
        finish();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager= (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

}