package com.example.bus_uni.Driver;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bus_uni.Booking.Book;
import com.example.bus_uni.Booking.BookedTicket_Adapter;
import com.example.bus_uni.Booking.TicketDatesAdapter;
import com.example.bus_uni.BusSchedule.TicketAdpter;
import com.example.bus_uni.R;
import com.example.bus_uni.StreetsInformation.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckBookings extends AppCompatActivity {


    DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private TicketDatesAdapter ticketDatesAdapter;
    private ArrayList<String> ticketDates = new ArrayList<>();
    private String busLine="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bookings);


        mRecyclerView =  findViewById(R.id.ticketDatesRecyclerView);
        showTicketDatesList();

    }

    private void showTicketDatesList() {


        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String current_uid=firebaseAuth.getCurrentUser().getUid();
        //Toast.makeText(this,current_uid,Toast.LENGTH_LONG).show();
        DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("Users");
        userDatabase.child(current_uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bus_line=dataSnapshot.child("bus_line").getValue().toString();
                //Toast.makeText(CheckBookings.this,bus_line,Toast.LENGTH_LONG).show();
                setBusLine(bus_line);
                mDatabaseReference = FirebaseDatabase.getInstance().getReference("Ticket");
              //  Toast.makeText(CheckBookings.this,busLine,Toast.LENGTH_LONG).show();
                mDatabaseReference.child(busLine).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String time="";
                        for (DataSnapshot childSnap : snapshot.getChildren()) {
                            for(DataSnapshot ch: childSnap.getChildren()) {
                                if(ch.getKey().equals("leavingTime")) {
                                    time = ch.getValue().toString();
                                    break;
                                }

                               }
                            ticketDates.add(time);
                            //Ticket ticket = childSnapshot.getValue(Ticket.class);
                            //tickets.add(ticket);
                            showRecycleView();



                        }

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

    void setBusLine(String bus_line){

        busLine=bus_line;

    }
    private void showRecycleView() {

        // here how we want to display the list of tickets
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        ticketDatesAdapter = new TicketDatesAdapter(ticketDates, CheckBookings.this);
        mRecyclerView.setAdapter(ticketDatesAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);

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
