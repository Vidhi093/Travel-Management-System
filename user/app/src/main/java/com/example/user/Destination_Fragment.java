package com.example.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Destination_Fragment extends Fragment {

    AutoCompleteTextView search;
    Button searchbtn;
    TextView summer, winter, monsoon;
    List<advisoryData> list = new ArrayList<>();
    GridView summerg, winterg, monsoong, searchg, searchsg, searchpop, searchattract;
    LinearLayout summerl, winterl, monsoonl;
    ArrayList<advisoryData> slist, wlist, mlist, searchlist;
    advisoryAdapter adapter;
    ArrayList<DestnData> destlist;
    DestAdapter destAdapter;
    PopularAdapter popularAdapter;
    ArrayList<PopulardestData> poplist;
    attractionAdapter attadapter;
    ArrayList<AttractionData> attractlist;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Advisory");
    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("State");
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("State").child("Popular Place");
    DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Attraction");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination_, container, false);

        summer = view.findViewById(R.id.summer);
        winter = view.findViewById(R.id.winter);
        monsoon = view.findViewById(R.id.monsoon);

        summerg = view.findViewById(R.id.summerg);
        winterg = view.findViewById(R.id.winterg);
        monsoong = view.findViewById(R.id.monsoong);

        summerl = view.findViewById(R.id.summerl);
        winterl = view.findViewById(R.id.winterl);
        monsoonl = view.findViewById(R.id.monsoonl);

        search = view.findViewById(R.id.search);
        searchg = view.findViewById(R.id.searchg);
        searchbtn = view.findViewById(R.id.searchbtn1);
        searchsg = view.findViewById(R.id.searchstate);
        searchpop = view.findViewById(R.id.searchpopular);
        searchattract = view.findViewById(R.id.searchattract);

        final ArrayAdapter<String> autosearch = new ArrayAdapter<>(getActivity(), R.layout.items);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("place").getValue(String.class);
                    autosearch.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setThreshold(1);
        search.setAdapter(autosearch);

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("title").getValue(String.class);
                    autosearch.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setThreshold(1);
        search.setAdapter(autosearch);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("title").getValue(String.class);
                    autosearch.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setThreshold(1);
        search.setAdapter(autosearch);

        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("title").getValue(String.class);
                    autosearch.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setThreshold(1);
        search.setAdapter(autosearch);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    searchlist = new ArrayList<>();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                advisoryData data = dataSnapshot.getValue(advisoryData.class);
                                if (search.getText().toString().isEmpty()) {
                                    Toast.makeText(getActivity(), "Please Enter the text", Toast.LENGTH_SHORT).show();
                                }
                                String se = search.getText().toString();
                                assert data != null;
                                if (data.getPlace().equals(se)) {
                                    searchlist.add(data);

                                }
                                adapter = new advisoryAdapter(searchlist, getActivity());
                                searchg.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                destlist = new ArrayList<>();
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DestnData data = dataSnapshot.getValue(DestnData.class);
                            if (search.getText().toString().isEmpty()) {
                                Toast.makeText(getActivity(), "Please Enter the text", Toast.LENGTH_SHORT).show();
                            }
                            String se = search.getText().toString();
                            assert data != null;
                            if (data.getTitle().equals(se)) {
                                destlist.add(data);

                            }
                            destAdapter = new DestAdapter(destlist, getActivity());
                            searchsg.setAdapter(destAdapter);
                            destAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                poplist = new ArrayList<>();
                reference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PopulardestData data = dataSnapshot.getValue(PopulardestData.class);
                            if (search.getText().toString().isEmpty()) {
                                Toast.makeText(getActivity(), "Please Enter the text", Toast.LENGTH_SHORT).show();
                            }
                            String se = search.getText().toString();
                            assert data != null;
                            if (data.getTitle().equals(se)) {
                                poplist.add(data);

                            }
                            popularAdapter = new PopularAdapter(poplist, getActivity());
                            searchpop.setAdapter(popularAdapter);
                            popularAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                attractlist = new ArrayList<>();
                reference3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            AttractionData data = dataSnapshot.getValue(AttractionData.class);
                            if (search.getText().toString().isEmpty()) {
                                Toast.makeText(getActivity(), "Please Enter the text", Toast.LENGTH_SHORT).show();
                            }
                            String se = search.getText().toString();
                            assert data != null;
                            if (data.getTitle().equals(se)) {
                                attractlist.add(data);

                            }
                            attadapter = new attractionAdapter(attractlist, getActivity());
                            searchattract.setAdapter(attadapter);
                            attadapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        searchg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                advisoryData data = (advisoryData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getPlace());
                intent.putString("state",data.getState());
                intent.putString("description",data.getDescription());
                intent.putString("wtt",data.getWtt());
                intent.putString("ttd",data.getTtd());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new advisory_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });

        searchsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DestnData data = (DestnData) parent.getItemAtPosition(position);

                Bundle args = new Bundle();
                args.putString("name", data.getTitle());
                args.putString("description", data.getDescription());
                args.putString("image", data.getImage());

                Fragment tofragment = new Home_last_Fragment();
                tofragment.setArguments(args);

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });

        searchpop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopulardestData data = (PopulardestData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("title",data.getTitle());
                intent.putString("description",data.getDescription());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new popular_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });

        searchattract.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });

        winterg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                advisoryData data = (advisoryData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getPlace());
                intent.putString("state",data.getState());
                intent.putString("description",data.getDescription());
                intent.putString("wtt",data.getWtt());
                intent.putString("ttd",data.getTtd());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new special_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });

        summerg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                advisoryData data = (advisoryData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getPlace());
                intent.putString("state",data.getState());
                intent.putString("description",data.getDescription());
                intent.putString("wtt",data.getWtt());
                intent.putString("ttd",data.getTtd());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new special_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });

        monsoong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                advisoryData data = (advisoryData) parent.getItemAtPosition(position);

                Bundle intent = new Bundle();
                intent.putString("name",data.getPlace());
                intent.putString("state",data.getState());
                intent.putString("description",data.getDescription());
                intent.putString("wtt",data.getWtt());
                intent.putString("ttd",data.getTtd());
                intent.putString("resId",data.getImage());

                Fragment tofragment = new special_last_Fragment();
                tofragment.setArguments(intent);

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });

        summer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment tofragment = new Summer_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });

        winter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment tofragment = new Winter_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });

        monsoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment tofragment = new Monsoon_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.destfrag, tofragment)
                        .addToBackStack(String.valueOf(Destination_Fragment.class)).commit();
            }
        });


        summerl();
        winterl();
        monsoonl();

        return view;
    }
    private void monsoonl() {
        mlist = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    assert data != null;
                    if (data.getWtt().equals("Monsoon") || data.getWtt().equals("All season available")) {
                        mlist.add(data);

                    }
                    adapter = new advisoryAdapter(mlist,getActivity());
                    monsoong.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void winterl() {

        wlist = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    assert data != null;
                    if (data.getWtt().equals("Winter") || data.getWtt().equals("All season available")) {
                        wlist.add(data);

                    }
                    adapter = new advisoryAdapter(wlist,getActivity());
                    winterg.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void summerl() {

        slist = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    assert data != null;
                    if (data.getWtt().equals("Summer") || data.getWtt().equals("All season available")) {
                        slist.add(data);

                    }
                    adapter = new advisoryAdapter(slist,getActivity());
                    summerg.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}