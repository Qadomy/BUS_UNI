package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.Driver.Ticket;
import com.example.bus_uni.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Bus_Schedule extends AppCompatActivity implements BusAdapter.BusAdapterOnClickHandler {


    private static final String Tag = "BusSchedule";

    private RecyclerView mRecyclerView;
    private BusAdapter mBusAdapter;
    private TextView mErrorMessageDisplay;
    private TextView origin, destination;
    private ArrayList<Bus> buses = new ArrayList<>();

    private ProgressBar mLoadingIndicator;
    // private GridLayoutManager gridLayoutManager;


    // databasereference
    private DatabaseReference mTicketDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus__schedule);


        Log.d(Tag, "onCreate: started.");


        mRecyclerView = findViewById(R.id.buses_rv);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicatorSchedule);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_displaySchedule);

        origin = findViewById(R.id.origin);
        destination = findViewById(R.id.destination);


        mTicketDatabaseReference = FirebaseDatabase.getInstance().getReference("Ticket");


        // so here we get the data ordered by leaving time to show it in bus schedule
        // TODO: AAUJ-Tulkarm ---> here we must change it to what user bus line choose it from app
        Query myMostViewedPostsQuery = mTicketDatabaseReference.child("AAUJ-Tulkarm")
                .orderByChild("leavingTime");

        myMostViewedPostsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                /*
                *
                * TODO: ok we get the data from database, but now how we can to know which each data
                *
                * */

                String name = dataSnapshot.child("busLine").getValue().toString();
                Toast.makeText(Bus_Schedule.this, name, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Bus bus1 = new Bus();
        Bus bus2 = new Bus();
        Bus bus3 = new Bus();

        buses.add(bus1);
        buses.add(bus2);
        buses.add(bus3);

        showRecyclerView();

    }// end onCreate

    private void showRecyclerView() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setHasFixedSize(true);
        mBusAdapter = new BusAdapter(Bus_Schedule.this, this);
        mRecyclerView.setAdapter(mBusAdapter);
        mBusAdapter.setBusesData(buses);
        showBuses();
    }

    private void showBuses() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Bus bus) {
        // Intent intent = new Intent(this, DetailsActivity.class);
        //intent.putExtra("Recipe", recipe);
        // startActivity(intent);
    }



    //
    //
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
            origin.setText(data.getStringExtra(Choose_Locations.CITY_EXTRA));
        else if (requestCode == 2 && resultCode == RESULT_OK)
            destination.setText(data.getStringExtra(Choose_Locations.CITY_EXTRA));

    }


    public void chooseSource(View view) {
        Intent intent = new Intent(this, Choose_Locations.class);
        startActivityForResult(intent, 1);

    }

    public void chooseDestination(View view) {
        Intent intent = new Intent(this, Choose_Locations.class);
        startActivityForResult(intent, 2);
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

