package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.Driver.Ticket;
import com.example.bus_uni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Bus_Schedule extends AppCompatActivity {

    private boolean searchFlag=false;
    String keyId="";


    private RecyclerView mRecyclerView;
    private TicketAdpter mTicketAdpter;
    private TextView mErrorMessageDisplay;

    private ArrayList<Ticket> tickets = new ArrayList<>();
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

        busLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Position",busLineSpinner.getSelectedItemPosition()+"");
//                Toast.makeText(Bus_Schedule.this,busLineSpinner.getSelectedItemPosition()+"",Toast.LENGTH_LONG).show();

                searchFlag=false;
                    tickets.clear();
               // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }// end onCreate

    private void showRecyclerView(String bus_line) {

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Bus_Schedule.this, LinearLayoutManager.VERTICAL, false);

                            mRecyclerView.setLayoutManager(linearLayoutManager);
                            mRecyclerView.setHasFixedSize(true);


                            mTicketAdpter = new TicketAdpter(tickets, Bus_Schedule.this);
                            mRecyclerView.setAdapter(mTicketAdpter);

                            // here how we want to display the list of tickets
                            showTickets();

//

      }


    private void showTickets() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }



    //here we click on the search image to find the buses ....
    public void searchOnCLick(View view) {
      //  Toast.makeText(this,searchFlag+"",Toast.LENGTH_LONG).show();
        // here get what chosen in spinner and found all dates same in database realtime
        if(!searchFlag) {
            String bus_line = busLineSpinner.getSelectedItem().toString();


            mTicketDatabaseReference = FirebaseDatabase.getInstance().getReference("Ticket");
            mTicketDatabaseReference.child(bus_line).
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                //TODO: This is for what?
                                 keyId = childSnapshot.getKey();


                                // declare this function out of this scope
                                String name = childSnapshot.child("name").getValue().toString();
                                String line = childSnapshot.child("busLine").getValue().toString();
                                String price = childSnapshot.child("price").getValue().toString();
                                String time = childSnapshot.child("leavingTime").getValue().toString();
                                //TODO: What do you mean by seatNum here?!
                                String seat = childSnapshot.child("seatNum").getValue().toString();
                                String company = childSnapshot.child("company").getValue().toString();
                                String phone = childSnapshot.child("driverPhone").getValue().toString();
                                String latitude = childSnapshot.child("latitude").getValue().toString();
                                String longitude = childSnapshot.child("longitude").getValue().toString();
                                String busNum = childSnapshot.child("busNum").getValue().toString();
                                String driverId = childSnapshot.child("driverId").getValue().toString();
                                String duration = childSnapshot.child("expectedTime").getValue().toString();


                                tickets.add(new Ticket(driverId, name, line, price, time, seat, company, phone, latitude, longitude, keyId, busNum, duration));
//                                    Ticket ticket = childSnapshot.getValue(Ticket.class);
//                                    tickets.add(ticket);

                                showRecyclerView(line);

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            showRecyclerView(bus_line);

        }
        searchFlag=true;

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

