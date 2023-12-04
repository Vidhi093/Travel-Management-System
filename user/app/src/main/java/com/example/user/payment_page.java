package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class payment_page extends AppCompatActivity implements PaymentResultListener {
    TextView amount;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        Checkout.preload(getApplicationContext());
        amount = findViewById(R.id.amount);
        pay = findViewById(R.id.pay);

        Intent intent = getIntent();
        String in = intent.getStringExtra("total");
        amount.setText(in);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //methodpay("0");

                String s = amount.getText().toString();
                startPayment(s);
            }
        });


    }

    public void startPayment(String s) {

        Checkout checkout = new Checkout();

        /* Set your logo here */
        //checkout.setImage(R.drawable.appicon);

        /*Reference to current activity */
        final Activity activity = this;
        int a = 10000;

        double finalAmount = Float.parseFloat(String.valueOf(s))*100;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Travel");
            options.put("description", "Payment for service");
            options.put("theme.color", "#94E5F7");
            options.put("currency", "INR");
            options.put("amount", finalAmount+"0000000000");
            options.put("prefill.email", "vidhip996@gmail.com");
            options.put("prefill.contact","8999325568");

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 10);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Toast.makeText(activity, "Error in payment"+ e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this,"Payment Success! "+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment Error! "+s, Toast.LENGTH_SHORT).show();

    }



    private void methodpay(String amount) {
        final Activity activity = this;

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_wnbQLa3jb7OO1y");
        checkout.setImage(R.drawable.appicon);

        double finalAmount = Float.parseFloat(amount)*100;

        try {
            JSONObject options = new JSONObject();
            options.put("name","Vidhi");
            options.put("description","Reference No. #123456");
            options.put("theme.color","#3399cc");
            options.put("currency","INR");
            options.put("image",R.drawable.appicon);
            options.put("amount",finalAmount+"");
            options.put("prefill.email","vp9230@gmail.com");
            options.put("prefill.password","8999325568");



        } catch (JSONException e) {
            Log.e("TAG", "Error in starting Razoray Checkout");
        }

    }


/*
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment UnSuccessful", Toast.LENGTH_SHORT).show();
    }*/
}