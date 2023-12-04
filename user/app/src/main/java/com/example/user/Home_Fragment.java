package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Home_Fragment extends Fragment {

    GridView destg, advg, specialg, attractg;
    TextView destt, advt, specialt, attractt;
    LinearLayout destl, advl, speciall, attractl;
    FrameLayout container_home;
    ArrayList<advisoryData> list2;
    advisoryAdapter adapter2;
    ArrayList<advisoryData> list4;
    advisoryAdapter adapter4;
    ArrayList<DestnData> list1;
    DestAdapter adapter1;
    Handler handler = new Handler();
    ArrayList<AttractionData> list3;
    attractionAdapter adapter3;
    SwipeRefreshLayout swipeRefreshLayout;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_, container, false);

        container_home = view.findViewById(R.id.container_home);

        destg = view.findViewById(R.id.grid_dest);
        destt = view.findViewById(R.id.destt);
        destl = view.findViewById(R.id.destl);

        advg = view.findViewById(R.id.grid_advisory);
        advt = view.findViewById(R.id.advt);
        advl = view.findViewById(R.id.advl);

        specialg = view.findViewById(R.id.grid_special);
        speciall = view.findViewById(R.id.speciall);
        specialt = view.findViewById(R.id.specialt);

        attractg = view.findViewById(R.id.grid_att);
        attractl = view.findViewById(R.id.attractl);
        attractt = view.findViewById(R.id.attractt);

        swipeRefreshLayout = view.findViewById(R.id.swipe);


        boolean connection = isNetworkAvailable();
        if(connection){

        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Internet not connected", Toast.LENGTH_SHORT).show();
                }
            }, 700000);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                boolean connection = isNetworkAvailable();
                if(connection){
                    Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
                    Fragment to = new Home_Fragment();

                    getParentFragmentManager().beginTransaction().replace(R.id.container_home, to)
                            .addToBackStack(String.valueOf(Home_Fragment.class)).commit();


                } else {
                    Toast.makeText(getActivity(), "Internet not connected", Toast.LENGTH_SHORT).show();
                }
            }
        });


        destt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment to = new Dest_all_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, to)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });

        specialt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment to = new special_all_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, to)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });

        attractt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment to = new attraction_all2_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, to)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });

        advt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment to = new advisory_all_Fragment();

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, to)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });


        destg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DestnData data = (DestnData) parent.getItemAtPosition(position);

                Bundle args = new Bundle();
                args.putString("name", data.getTitle());
                args.putString("description", data.getDescription());
                args.putString("image", data.getImage());

                Fragment tofragment = new Home_last_Fragment();
                tofragment.setArguments(args);

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, tofragment)
                .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });

        advg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, tofragment)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });


        specialg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, tofragment)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });

        attractg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                getParentFragmentManager().beginTransaction().replace(R.id.container_home, tofragment)
                        .addToBackStack(String.valueOf(Home_Fragment.class)).commit();
            }
        });


        advl();
        destl();
        speciall();
        attractl();

        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager= (ConnectivityManager) this.requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;

    }

    private void attractl() {
        list3 = new ArrayList<>();

        DatabaseReference reference = databaseReference.child("Attraction");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    AttractionData data = dataSnapshot.getValue(AttractionData.class);
                    list3.add(data);
                }
                adapter3 = new attractionAdapter(list3, getActivity());
                attractg.setAdapter(adapter3);
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void speciall() {
        list4 = new ArrayList<>();

        DatabaseReference reference = databaseReference.child("Advisory");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    list4.add(0,data);
                }
                adapter4 = new advisoryAdapter(list4, getActivity());
                specialg.setAdapter(adapter4);
                adapter4.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void destl() {
        list1 = new ArrayList<>();

        DatabaseReference reference = databaseReference.child("State");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    DestnData data = dataSnapshot.getValue(DestnData.class);
                    list1.add(data);
                }
                adapter1 = new DestAdapter(list1, getActivity());
                destg.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void advl() {

        list2 = new ArrayList<>();

        DatabaseReference reference = databaseReference.child("Advisory");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    advisoryData data = dataSnapshot.getValue(advisoryData.class);
                    list2.add(data);
                }
                adapter2 = new advisoryAdapter(list2, getActivity());
                advg.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}