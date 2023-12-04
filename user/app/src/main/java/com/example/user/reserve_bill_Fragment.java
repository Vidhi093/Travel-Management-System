package com.example.user;

import static android.content.ContentValues.TAG;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class reserve_bill_Fragment extends Fragment {
    Button reserve;
    ProgressDialog pd;
    TextView checkin, checkout, rooms, adults, childs, intime, outtimel, days, total;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private AtomicInteger msgId = new AtomicInteger(0);
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_bill_, container, false);

        FirebaseApp.initializeApp(requireActivity());
        rooms = view.findViewById(R.id.room);
        adults = view.findViewById(R.id.adult);
        childs = view.findViewById(R.id.child);
        checkin = view.findViewById(R.id.checkindate);
        checkout = view.findViewById(R.id.checkoutdate);
        reserve = view.findViewById(R.id.reserve);
        pd = new ProgressDialog(getActivity());

        intime = view.findViewById(R.id.intime);
        outtimel = view.findViewById(R.id.outtime);
        days = view.findViewById(R.id.days);
        total = view.findViewById(R.id.total);

        assert getArguments() != null;
        String name = getArguments().getString("name");
        String add = getArguments().getString("add");
        String desc = getArguments().getString("desc");
        String contact = getArguments().getString("contact");
        String price = getArguments().getString("price");
        String policy = getArguments().getString("policy");
        String loca = getArguments().getString("location");
        String city = getArguments().getString("city");
        String email = getArguments().getString("email");
        String cat = getArguments().getString("category");
        String img1 = getArguments().getString("resId");

        String r = getArguments().getString("rooms");
        rooms.setText(r);

        String a = getArguments().getString("adults");
        adults.setText(a);

        String c = getArguments().getString("childs");
        childs.setText(c);

        String in = getArguments().getString("checkin");
        checkin.setText(in);

        String out = getArguments().getString("checkout");
        checkout.setText(out);

        String it = getArguments().getString("cin");
        intime.setText(it);

        String ot = getArguments().getString("cout");
        outtimel.setText(ot);

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
                Integer.parseInt(price) * Integer.parseInt(rooms.getText().toString());

        String l = Integer.toString(pr);
        total.setText("₹ "+l);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid = user.getUid();

                DatabaseReference reference = ref.child(uid);
                   // reference = ref.child("State"); //data will be stored in firebase
                    final String uniquekey = reference.push().getKey();

                    String d = days.getText().toString();
                    String t = total.getText().toString();
                    String hname = name;
                    String hadd = add;
                    String hdesc = desc;
                    String hcontact = contact;
                    String hprice = price;
                    String hpolicy = policy;
                    String hcity = city;
                    String hemail = email;

                    //hotelData hotelData = new hotelData(hname ,hadd, hdesc, hcontact, hprice, hpolicy, img1 , uniquekey, cat, loca, hcity, hemail);
                    hotelBillData bill = new hotelBillData(in,out, it,ot, r,a,c,t, name,city,cat,price,add,d, img1) ;
                    reference.child("History").child("Hotel").child(name).setValue(bill).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void sendnotification() {

        /*Map<String ,String> data = new HashMap<>();
        data.put("message", "Booking process");
        data.put("title","new notification");
        data.put("description","This is a test booking process");


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String receiverToken = task.getResult();
                            Log.d(TAG,"FCM registration token:" + receiverToken);

                            FirebaseMessaging.getInstance().send(new RemoteMessage.Builder(receiverToken)
                                    .setData(data)
                                    .build());

                        } else {
                            Log.e(TAG, "Failed to get registration token" ,task.getException());
                        }
                    }
                });*/



    }
}