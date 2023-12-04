package com.example.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tourguide_last_Fragment extends Fragment {

    TextView name, contact, state, place, price;
    CircleImageView image;
    Button reserve;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tour Guide");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tourguide_last_, container, false);

        name = view.findViewById(R.id.name);
        contact = view.findViewById(R.id.contact);
        state = view.findViewById(R.id.state);
        place = view.findViewById(R.id.place);
        price = view.findViewById(R.id.price);
        image = view.findViewById(R.id.image);
        reserve = view.findViewById(R.id.reserve);

        assert getArguments() != null;
        String name1 = getArguments().getString("name");
        String con = getArguments().getString("contact");
        String image1 = getArguments().getString("image");
        String st = getArguments().getString("state");
        String ct = getArguments().getString("city");
        String pr = getArguments().getString("price");


        name.setText(name1);
        contact.setText(con);
        state.setText(st);
        place.setText(ct);
        price.setText(pr);
        Picasso.get().load(image1).into(image);


        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle intent = new Bundle();
                intent.putString("name",name1);
                intent.putString("contact",con);
                intent.putString("state",st);
                intent.putString("city",ct);
                intent.putString("price",pr);
                intent.putString("image",image1);

                Fragment to = new reserve_tourguide_Fragment();
                to.setArguments(intent);
                getParentFragmentManager().beginTransaction().replace(R.id.tourlast, to)
                        .addToBackStack(String.valueOf(Tourguide_last_Fragment.class)).commit();
            }
        });




        return view;
    }
}