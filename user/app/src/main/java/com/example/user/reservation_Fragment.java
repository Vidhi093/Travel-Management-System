package com.example.user;

import static com.google.api.services.gmail.GmailScopes.GMAIL_SEND;

import android.app.DatePickerDialog;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class reservation_Fragment extends Fragment{

    EditText rooms, adults, childs;
    Spinner intime, outtimel;
    TextView checkin, checkout;
    LinearLayout in, out;
    DatePickerDialog date;
    Button reserve;

    String cin, cout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation_, container, false);

        rooms = view.findViewById(R.id.room);
        adults = view.findViewById(R.id.adult);
        childs = view.findViewById(R.id.child);
        checkin = view.findViewById(R.id.checkindate);
        checkout = view.findViewById(R.id.checkoutdate);
        in = view.findViewById(R.id.checkin);
        out = view.findViewById(R.id.checkout);
        reserve = view.findViewById(R.id.reserve);
        
        intime = view.findViewById(R.id.intime);
        outtimel = view.findViewById(R.id.outtime);

        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

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


        String[] items = new String[] {"In Time","Morning","Afternoon","Evening"};
        intime.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.items, items));

        intime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cin = intime.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] items1 = new String[] {"Out Time","Morning","Afternoon","Evening"};

        outtimel.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.items, items1));

        outtimel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cout = outtimel.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (checkin.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Enter Check-in Date", Toast.LENGTH_SHORT).show();
                } else if (checkout.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Check-out Date", Toast.LENGTH_SHORT).show();
                } else if (cin.equals("In Time")) {
                    Toast.makeText(getActivity(), "Enter Check-In Time", Toast.LENGTH_SHORT).show();
                } else if (cout.equals("Out Time")) {
                    Toast.makeText(getActivity(), "Enter Check-Out Time", Toast.LENGTH_SHORT).show();
                } else if (rooms.getText().toString().isEmpty() || rooms.getText().toString().equals("0")) {
                    rooms.setError("Please Enter number of rooms");
                    rooms.requestFocus();
                } else if (adults.getText().toString().isEmpty() || adults.getText().toString().equals("0")) {
                    adults.setError("Please Enter number of adults");
                    adults.requestFocus();
                } else if (childs.getText().toString().isEmpty()) {
                    childs.setError("Please Enter number of childrens");
                    Toast.makeText(getActivity(), "If no children then enter 0", Toast.LENGTH_SHORT).show();
                    childs.requestFocus();
                } else {

                    Bundle intent = new Bundle();
                    intent.putString("checkin",checkin.getText().toString());
                    intent.putString("checkout",checkout.getText().toString());
                    intent.putString("cin",cin);
                    intent.putString("cout",cout);
                    intent.putString("rooms",rooms.getText().toString());
                    intent.putString("adults",adults.getText().toString());
                    intent.putString("childs",childs.getText().toString());

                    intent.putString("name",name);
                    intent.putString("add",add);
                    intent.putString("desc",desc);
                    intent.putString("contact",contact);
                    intent.putString("price",price);
                    intent.putString("policy",policy);
                    intent.putString("location",loca);
                    intent.putString("city",city);
                    intent.putString("email",email);
                    intent.putString("category",cat);
                    intent.putString("resId",img1);

                    Fragment to = new reserve_bill_Fragment();
                    to.setArguments(intent);

                    getParentFragmentManager().beginTransaction().replace(R.id.reservation, to)
                            .addToBackStack(String.valueOf(reservation_Fragment.class)).commit();

                }
            }
        });




        return view;
    }

}