package com.example.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tour_bill_Fragment extends Fragment {

    Button reserve;
    ProgressDialog pd;
    TextView checkin, checkout, adults, childs, days, total;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tour_bill_, container, false);


        adults = view.findViewById(R.id.adult);
        childs = view.findViewById(R.id.child);
        checkin = view.findViewById(R.id.checkindate);
        checkout = view.findViewById(R.id.checkoutdate);
        reserve = view.findViewById(R.id.reserve);

        days = view.findViewById(R.id.days);
        total = view.findViewById(R.id.total);
        pd = new ProgressDialog(getActivity());

        assert getArguments() != null;
        String name = getArguments().getString("name");
        String p = getArguments().getString("price");
        String co = getArguments().getString("contact");
        String st = getArguments().getString("state");
        String ct = getArguments().getString("city");
        String image = getArguments().getString("image");

        String a = getArguments().getString("adults");
        adults.setText(a);

        String c = getArguments().getString("childs");
        childs.setText(c);

        String in = getArguments().getString("checkin");
        checkin.setText(in);

        String out = getArguments().getString("checkout");
        checkout.setText(out);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date checkin1 = simpleDateFormat.parse(in);
            Date checkout2 = simpleDateFormat.parse(out);

            assert checkin1 != null;
            assert checkout2 != null;
            long difference = Math.abs(checkin1.getTime() - checkout2.getTime());
            long diiferencedates = difference / (24 * 60 * 60 * 1000);
            String daydifference = Long.toString(diiferencedates);
            //String fin = daydifference + 1;
            days.setText(daydifference);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        int pr = Integer.parseInt(days.getText().toString()) *
                Integer.parseInt(p);

        String l = Integer.toString(pr);
        total.setText("₹ "+l);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = user.getUid();

                DatabaseReference reference = ref.child(uid);
                String d = days.getText().toString();
                String t = total.getText().toString();

                tourbillData bill = new tourbillData(in, out,a, c,d,t,name,p,co, st, ct, image);
                reference.child("History").child("Tourguide").child(name).setValue(bill).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Booking in process", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), payment_page.class);
                        intent.putExtra("total",l);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return view;
    }
}