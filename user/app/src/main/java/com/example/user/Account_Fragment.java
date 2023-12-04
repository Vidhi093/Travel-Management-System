package com.example.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Account_Fragment extends Fragment {

    LinearLayout logout, contactus, like, history;
    TextView name, number;
    Button edit;
    FirebaseAuth user = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_, container, false);

        logout = view.findViewById(R.id.logout);
        contactus = view.findViewById(R.id.contactus);
        like = view.findViewById(R.id.like);
        name = view.findViewById(R.id.name);
        number = view.findViewById(R.id.number);
        edit = view.findViewById(R.id.edit);
        history = view.findViewById(R.id.history);

        String uid = Objects.requireNonNull(user.getCurrentUser()).getUid();

        DatabaseReference ref = reference.child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n = snapshot.child("/number").getValue().toString();
                String u = snapshot.child("/username").getValue().toString();
                String e = snapshot.child("/email").getValue().toString();

                name.setText(u);
                number.setText(n);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment to = new edit_profile_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.account, to)
                        .addToBackStack(String.valueOf(Account_Fragment.class)).commit();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment to = new history_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.account, to)
                        .addToBackStack(String.valueOf(Account_Fragment.class)).commit();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment to = new Like_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.account, to)
                        .addToBackStack(String.valueOf(Account_Fragment.class)).commit();
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment to = new contactus_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.account, to)
                        .addToBackStack(String.valueOf(Account_Fragment.class)).commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Confirm Logout");
                alert.setMessage("Are you sure, you want to logout?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Logout Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        return view;
    }

}