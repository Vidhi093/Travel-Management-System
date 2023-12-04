package com.example.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class last_history_hotel_Fragment extends Fragment {

    TextView checkin, checkout, rooms, adults, childs, intime, outtimel, days, total, name, hprice, state, price, city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_last_history_hotel_, container, false);

        rooms = view.findViewById(R.id.room);
        adults = view.findViewById(R.id.adult);
        childs = view.findViewById(R.id.child);
        checkin = view.findViewById(R.id.checkindate);
        checkout = view.findViewById(R.id.checkoutdate);
        intime = view.findViewById(R.id.intime);
        outtimel = view.findViewById(R.id.outtime);
        days = view.findViewById(R.id.days);
        total = view.findViewById(R.id.total);

        name = view.findViewById(R.id.hname);
        hprice = view.findViewById(R.id.hprice);
        state = view.findViewById(R.id.state);
        city = view.findViewById(R.id.place);


       assert getArguments() != null;
        String name1 = getArguments().getString("name");
        name.setText(name1);

        String price1 = getArguments().getString("price");
        hprice.setText(price1);

        String city1 = getArguments().getString("city");
        city.setText(city1);

        String cat = getArguments().getString("category");
        state.setText(cat);

        String r = getArguments().getString("rooms");
        rooms.setText(r);

        String ad = getArguments().getString("adults");
        adults.setText(ad);

        String ch = getArguments().getString("childs");
        childs.setText(ch);

        String to = getArguments().getString("total");
        total.setText(to);

        String in = getArguments().getString("indate");
        checkin.setText(in);

        String out = getArguments().getString("outdate");
        checkout.setText(out);

        String cin = getArguments().getString("intime");
        intime.setText(cin);

        String cout = getArguments().getString("outtime");
        outtimel.setText(cout);

        String da = getArguments().getString("days");
        days.setText(da);



        return view;
    }
}