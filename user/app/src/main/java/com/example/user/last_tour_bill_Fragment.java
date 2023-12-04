package com.example.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class last_tour_bill_Fragment extends Fragment {

    TextView checkin, checkout, adults, childs, days, total, name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last_tour_bill_, container, false);

        adults = view.findViewById(R.id.adult);
        childs = view.findViewById(R.id.child);
        checkin = view.findViewById(R.id.checkindate);
        checkout = view.findViewById(R.id.checkoutdate);
        name = view.findViewById(R.id.name);
        days = view.findViewById(R.id.days);
        total = view.findViewById(R.id.total);

        assert getArguments() != null;
        String n = getArguments().getString("name");
        name.setText(n);
        String d = getArguments().getString("days");
        days.setText(d);
        String s = getArguments().getString("start");
        checkin.setText(s);
        String e = getArguments().getString("end");
        checkout.setText(e);
        String a = getArguments().getString("adults");
        adults.setText(a);
        String c = getArguments().getString("childs");
        childs.setText(c);
        String t = getArguments().getString("total");
        total.setText(t);


        return view;
    }
}