package com.example.bus_uni.Driver;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.bus_uni.Booking.Book;
import com.example.bus_uni.Booking.BookedTicket_Adapter;
import com.example.bus_uni.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckBookings extends AppCompatActivity {


    DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private BookedTicket_Adapter bookedTicket_adapter;
    private ArrayList<Book> booked = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bookings);


        mRecyclerView = (RecyclerView) findViewById(R.id.bookedTicketsRecycleView);
        showBookedTicketList();

    }

    private void showBookedTicketList() {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Booking");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())  {

//
//                    String name = childSnapshot.child("userName").getValue().toString();
//                    String userID = childSnapshot.child("userID").getValue().toString();
//                    String driverID = childSnapshot.child("driverID").getValue().toString();
//                    String driverName = childSnapshot.child("driverName").getValue().toString();
//                    String driverPhone = childSnapshot.child("driverPhone").getValue().toString();
//                    String busLine = childSnapshot.child("busLine").getValue().toString();
//                    String leavingTime = childSnapshot.child("leavingTime").getValue().toString();
//                    String latitude = childSnapshot.child("latitude").getValue().toString();
//                    String longitude = childSnapshot.child("longitude").getValue().toString();
//                    String seatNumber = childSnapshot.child("seatNumber").getValue().toString();
//                    String rfidNumber = childSnapshot.child("rfidNumber").getValue().toString();
//                    String company = childSnapshot.child("company").getValue().toString();
//                    String city = childSnapshot.child("city").getValue().toString();
//                    String busNum = childSnapshot.child("busNum").getValue().toString();
//
//                    booked.add(new Book(userID, name, driverID, driverName, driverPhone, busLine,
//                            leavingTime, latitude, longitude, seatNumber, rfidNumber, company,
//                            city, busNum));

                    //todo: here why not all data display on the recycle view
                    Book book =  childSnapshot.getValue(Book.class);
                    booked.add(book);

                    showRecycleView();

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

        bookedTicket_adapter = new BookedTicket_Adapter(booked, CheckBookings.this);
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
