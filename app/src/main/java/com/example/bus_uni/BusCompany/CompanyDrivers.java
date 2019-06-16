package com.example.bus_uni.BusCompany;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.bus_uni.R;
import com.example.bus_uni.Register.User;
import com.example.bus_uni.StreetsInformation.PostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompanyDrivers extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private DisplayDrivers_Adapter mDisplayDrivers_adapter;


    private ArrayList<User> drivers = new ArrayList<>();


    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference mUserDatabaseReference, mDriverDatabaseReference;


    String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_drivers);


        mRecyclerView = (RecyclerView) findViewById(R.id.allDrivers_RecycleView);


        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mUserDatabaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                companyName = dataSnapshot.child("name").getValue().toString();





                mDriverDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
                final Query query = mDriverDatabaseReference.orderByChild("bus_company").equalTo(companyName);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){

                            User driver = childSnapshot.getValue(User.class);
                            drivers.add(driver);
                        }

                        showRecycleView();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    private void showRecycleView() {

        // here how we want to display the list of tickets
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        //To show them in descending order
        mDisplayDrivers_adapter = new DisplayDrivers_Adapter(drivers, CompanyDrivers.this);
        mRecyclerView.setAdapter(mDisplayDrivers_adapter);
        mRecyclerView.setVisibility(View.VISIBLE);

    }
}
