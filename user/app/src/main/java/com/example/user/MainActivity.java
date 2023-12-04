package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button login;
    TextView register;
    EditText emailin,passwordin;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ProgressDialog pd;


    @Override
    public void onStart() {
        super.onStart();
        //check if the user is signed in (non-null) and update ui accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Intent intent = new Intent(this, home_page.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login_btn);
        emailin = findViewById(R.id.email);
        passwordin = findViewById(R.id.inpassword);
        register = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference();

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginaccountadmin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, register_page.class);
                startActivity(intent);
            }
        });
    }

    private void loginaccountadmin() {
        pd.setMessage("Wait a moment...");
        pd.show();
        String email,password;
        email = String.valueOf(emailin.getText());
        password = String.valueOf(passwordin.getText());

        if(TextUtils.isEmpty(email)) {
            pd.dismiss();
            Toast.makeText(MainActivity.this,"Enter Your Email", Toast.LENGTH_SHORT).show();
            emailin.setError("Email is required");
            emailin.requestFocus();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            pd.dismiss();
            Toast.makeText(MainActivity.this,"Re-Enter Your Email", Toast.LENGTH_SHORT).show();
            emailin.setError("Valid Email is Required");
            emailin.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            pd.dismiss();
            Toast.makeText(MainActivity.this,"Enter Password", Toast.LENGTH_SHORT).show();

        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            boolean connection = isNetworkAvailable();
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                pd.dismiss();
                                Intent intent = new Intent(getApplicationContext(),home_page.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                pd.dismiss();
                                if (connection){

                                } else {
                                    Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }

                    });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager= (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }
}