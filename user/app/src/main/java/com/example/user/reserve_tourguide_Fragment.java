package com.example.user;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class reserve_tourguide_Fragment extends Fragment {

    EditText adults, childs;
    TextView checkin, checkout;
    LinearLayout in, out;
    DatePickerDialog date;
    Button reserve;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_tourguide_, container, false);

        adults = view.findViewById(R.id.adult);
        childs = view.findViewById(R.id.child);
        checkin = view.findViewById(R.id.checkindate);
        checkout = view.findViewById(R.id.checkoutdate);
        in = view.findViewById(R.id.checkin);
        out = view.findViewById(R.id.checkout);
        reserve = view.findViewById(R.id.reserve);

        assert getArguments() != null;
        String name1 = getArguments().getString("name");
        String con = getArguments().getString("contact");
        String image1 = getArguments().getString("image");
        String st = getArguments().getString("state");
        String ct = getArguments().getString("city");
        String pr = getArguments().getString("price");

        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        date = new DatePickerDialog(getActivity());

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        checkin.setText(dayOfMonth+ "/"+(month+1)+"/"+year);
                    }
                }, year, month, day);
                date.getDatePicker().setMinDate(calendar.getTimeInMillis());
                date.show();
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        checkout.setText(dayOfMonth+ "/"+(month+1)+"/"+year);
                    }
                }, year, month, day);
                date.getDatePicker().setMinDate(calendar.getTimeInMillis());
                date.show();
            }
        });

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        checkin.setText(dayOfMonth+ "/"+(month+1)+"/"+year);
                    }
                }, year, month, day);
                date.getDatePicker().setMinDate(calendar.getTimeInMillis());
                date.show();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        checkout.setText(dayOfMonth+ "/"+(month+1)+"/"+year);
                    }
                }, year, month, day);
                date.getDatePicker().setMinDate(calendar.getTimeInMillis()-1);
                date.show();
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkin.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Enter Check-in Date", Toast.LENGTH_SHORT).show();
                } else if (checkout.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Check-out Date", Toast.LENGTH_SHORT).show();
                } else if (adults.getText().toString().isEmpty() || adults.getText().toString().equals("0")) {
                    adults.setError("Please Enter number of adults");
                    adults.requestFocus();
                } else if (childs.getText().toString().isEmpty()) {
                    childs.setError("Please Enter number of childrens");
                    Toast.makeText(getActivity(), "If no children then enter 0", Toast.LENGTH_SHORT).show();
                    childs.requestFocus();
                } else {
                    Bundle intent = new Bundle();
                    intent.putString("checkin", checkin.getText().toString());
                    intent.putString("checkout", checkout.getText().toString());
                    intent.putString("adults", adults.getText().toString());
                    intent.putString("childs", childs.getText().toString());

                    intent.putString("price", pr);
                    intent.putString("name",name1);
                    intent.putString("contact",con);
                    intent.putString("state",st);
                    intent.putString("city",ct);
                    intent.putString("image",image1);

                    Fragment to = new tour_bill_Fragment();
                    to.setArguments(intent);

                    getParentFragmentManager().beginTransaction().replace(R.id.reservetour, to)
                            .addToBackStack(String.valueOf(reserve_tourguide_Fragment.class)).commit();
                }
            }
        });

        return view;
    }
}