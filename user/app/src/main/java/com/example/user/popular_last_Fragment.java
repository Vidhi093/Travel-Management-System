package com.example.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class popular_last_Fragment extends Fragment {

    ImageView img, likebtn;
    TextView title, attractionbtn, state;
    ReadMoreTextView description;
    GridView gridView;
    ArrayList<AttractionData> datalist1;
    attractionAdapter adapter;
    boolean flag = false;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("State");

    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_popular_last_, container, false);


        img = view.findViewById(R.id.img_p);
        title = view.findViewById(R.id.place_p);
        description = view.findViewById(R.id.desc_p);
        attractionbtn = view.findViewById(R.id.pop_btn);
        gridView = view.findViewById(R. id.pop_gridview);

        likebtn = view.findViewById(R.id.likebtn);

        datalist1 = new ArrayList<>();
        adapter = new attractionAdapter(datalist1, getActivity());
        gridView.setAdapter(adapter);

        assert getArguments() != null;
        String title1 = getArguments().getString("title");
        String description1 = getArguments().getString("description");
        String image = getArguments().getString("resId");
        String name1 = getArguments().getString("nams");

        title.setText(title1);
        description.setText(description1);
        Picasso.get().load(image).into(img);


        attractionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment to = new attraction_all_Fragment();

                Bundle intent = new Bundle();
                intent.putString("title",title.getText().toString());

                to.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.popl, to)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttractionData data = (AttractionData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("title",data.getTitle());
                intent.putString("state",data.getState());
                intent.putString("description",data.getDescription());
                intent.putString("place",data.getPopular_place());
                intent.putString("image",data.getImage());

                Fragment tofragment = new attraction_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.popl, tofragment)
                        .addToBackStack(String.valueOf(popular_last_Fragment.class)).commit();
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Attraction");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String title1 = title.getText().toString();
                    String data = dataSnapshot.child("/popular_place").getValue(String.class);
                    assert data != null;
                    if (data.equals(title1)) {
                        AttractionData attractionData = dataSnapshot.getValue(AttractionData.class);
                        datalist1.add(attractionData);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}