package com.example.bus_uni.Driver;

import android.app.ActionBar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.bus_uni.Booking.Book;
import com.example.bus_uni.Booking.BookedTicket_Adapter;
import com.example.bus_uni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailedBookings extends AppCompatActivity {
    DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private BookedTicket_Adapter bookedTicket_adapter;
    private ArrayList<Book> booked = new ArrayList<>();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String current_uid="";
        String leavingTime="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_bookings);
        current_uid=firebaseAuth.getCurrentUser().getUid();
        leavingTime=getIntent().getStringExtra("Leaving Time");
        mRecyclerView =  findViewById(R.id.bookings_recyclerview);
        showBookedTicketList();

    }

    private void showBookedTicketList() {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Booking");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        if((childSnapshot.child("driverID").getValue()).equals(current_uid) &&
                                (childSnapshot.child("leavingTime").getValue()).equals(leavingTime)) {
                            Book books = childSnapshot.getValue(Book.class);
                            booked.add(books);
                            showRecycleView();
                        }



                }

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

        bookedTicket_adapter = new BookedTicket_Adapter(booked, DetailedBookings.this);
        mRecyclerView.setAdapter(bookedTicket_adapter);
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
