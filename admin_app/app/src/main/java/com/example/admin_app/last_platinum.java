package com.example.admin_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class last_platinum extends AppCompatActivity {

    ImageView image;
    TextView hname, hprice, hadd, hdesc, hcon, hpoly, hlocation, hcity, hemail;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_platinum);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        image = findViewById(R.id.imageview);
        hname = findViewById(R.id.name);
        hprice = findViewById(R.id.price);
        hadd = findViewById(R.id.address);
        hdesc = findViewById(R.id.descr);
        hcon = findViewById(R.id.contact);
        hpoly = findViewById(R.id.policy);
        update = findViewById(R.id.update);
        hlocation = findViewById(R.id.location);
        hcity = findViewById(R.id.city);
        hemail = findViewById(R.id.email);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        hname.setText(name);

        String add = intent.getStringExtra("add");
        hadd.setText(add) ;

        String desc = intent.getStringExtra("desc");
        hdesc.setText(desc);

        String contact = intent.getStringExtra("contact");
        hcon.setText(contact);

        String price = intent.getStringExtra("price");
        hprice.setText(price);

        String policy = intent.getStringExtra("policy");
        hpoly.setText(policy);

        String loca = intent.getStringExtra("location");
        hlocation.setText(loca);

        String city = intent.getStringExtra("city");
        hcity.setText(city);

        String email = intent.getStringExtra("email");
        hemail.setText(email);

        String cat = intent.getStringExtra("category");

        String img1 = intent.getStringExtra("resId");
        Picasso.get().load(img1).into(image);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(last_platinum.this, delete_update_platinum.class);
                intent1.putExtra("name",name);
                intent1.putExtra("add",add);
                intent1.putExtra("desc",desc);
                intent1.putExtra("contact",contact);
                intent1.putExtra("price",price);
                intent1.putExtra("policy",policy);
                intent1.putExtra("resId", img1);
                intent1.putExtra("cat",cat);
                intent1.putExtra("loc",loca);
                intent1.putExtra("city",city);
                intent1.putExtra("email",email);
                startActivity(intent1);
            }
        });

    }
}