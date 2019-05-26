package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bus_uni.Driver.Ticket;
import com.example.bus_uni.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Bus_Schedule extends AppCompatActivity  {


    private RecyclerView mRecyclerView;
    //private BusAdapter mBusAdapter;
    private TicketAdpter mTicketAdpter;
    private TextView mErrorMessageDisplay;
    //private ArrayList<Bus> buses = new ArrayList<>();
    private ArrayList<Ticket> tickets = new ArrayList<>();


    /////
    private ImageView searchBuses;
    private Spinner busLineSpinner;
    private ProgressBar mLoadingIndicator;

    // database realtime
    private DatabaseReference mTicketDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus__schedule);


        mRecyclerView = (RecyclerView) findViewById(R.id.buses_rv);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicatorSchedule);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_displaySchedule);


        searchBuses = (ImageView) findViewById(R.id.searchBuses);

        // init here a spinner
        busLineSpinner = (Spinner) findViewById(R.id.busLineNameSearch_spinner);

        // here for init the spinner and get her the data from string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bus_line_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        busLineSpinner.setAdapter(adapter);


        // here we click on the search image to find the buses ....
        searchBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // here get what chosen in spinner and found all dates same in database realtime
                final String bus_line = busLineSpinner.getSelectedItem().toString();


                mTicketDatabaseReference = FirebaseDatabase.getInstance().getReference("Ticket");

                // so here we get the data ordered by leaving time to show it in bus schedule
                Query myMostViewedPostsQuery = mTicketDatabaseReference.child(bus_line)
                        .orderByChild("leavingTime");

                myMostViewedPostsQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                        /*
                         *
                         *
                         *
                         * */

                        // Todo: here we create  a loop for return the all data in the database in firebase

                        String name = dataSnapshot.child("name").getValue().toString();
                        String line = dataSnapshot.child("busLine").getValue().toString();
                        String price = dataSnapshot.child("price").getValue().toString();
                        String time = dataSnapshot.child("leavingTime").getValue().toString();
                        String seat = dataSnapshot.child("seatNum").getValue().toString();
                        String company = dataSnapshot.child("company").getValue().toString();
                        String phone = dataSnapshot.child("driverPhone").getValue().toString();
                        String latitude = dataSnapshot.child("latitude").getValue().toString();
                        String longitude = dataSnapshot.child("longitude").getValue().toString();



                        Ticket ticket = new Ticket(name,line,price,time,seat,company,phone,latitude,longitude,dataSnapshot.getKey());
                        tickets.add(ticket);
                        showRecyclerView();

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


            }
        });

    }// end onCreate


    private void showRecyclerView() {

        // here how we want to display the list of tickets
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        mTicketAdpter = new TicketAdpter(tickets, Bus_Schedule.this);
        mRecyclerView.setAdapter(mTicketAdpter);
        //mTicketAdpter.setBusesData(buses);
        showTickets();
    }


    private void showTickets() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }



    // for back
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

