package com.example.bus_uni.Booking;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.Home;
import com.example.bus_uni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BookingSeat extends AppCompatActivity {


    TextView passengerName, orignLocation, destinationLocation, leavingTime, arrivalTime, busNumber, gateNumber, seatNumber, driverName, driverPhone, companyName, rfidNumber;

    DatabaseReference mDatabaseReference, mDeleteFromDatabase;

    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_seat);

        if (currentUser == null) {
            Toast.makeText(this, "You have no booking ticket yet", Toast.LENGTH_SHORT).show();
        }


        passengerName = (TextView) findViewById(R.id.textViewPassengerName);
        orignLocation = (TextView) findViewById(R.id.textViewOriginLocation);
        destinationLocation = (TextView) findViewById(R.id.textViewDestinationLocation);
        leavingTime = (TextView) findViewById(R.id.textViewLeavingTime);
        arrivalTime = (TextView) findViewById(R.id.textViewArrivalTime);
        busNumber = (TextView) findViewById(R.id.textViewBusNumber);
        gateNumber = (TextView) findViewById(R.id.textViewGate);
        seatNumber = (TextView) findViewById(R.id.textViewSeat);
//        driverName = (TextView) findViewById(R.id.driverName_bookingSeat);
//        driverPhone = (TextView) findViewById(R.id.driverPhone_bookingSeat);
//        companyName = (TextView) findViewById(R.id.companyName_bookingSeat);
//        rfidNumber = (TextView) findViewById(R.id.rfidNumber_bookingSeat);


        // init databaseReference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Booking");
        mDatabaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // here we check if there ticket booking for current user in database or no
                if (dataSnapshot.exists()) {

                    String name = dataSnapshot.child("userName").getValue().toString();
                    String driver_Name = dataSnapshot.child("driverName").getValue().toString();
                    String driver_Phone = dataSnapshot.child("driverPhone").getValue().toString();
                    String busLine = dataSnapshot.child("busLine").getValue().toString();
                    String time = dataSnapshot.child("leavingTime").getValue().toString();
                    String seatNum = dataSnapshot.child("seatNumber").getValue().toString();
                    String company = dataSnapshot.child("company").getValue().toString();
                    String city = dataSnapshot.child("city").getValue().toString();
                    String busNum = dataSnapshot.child("busNum").getValue().toString();


                    passengerName.setText(name);
                    orignLocation.setText(busLine);
                    destinationLocation.setText(city);
                    leavingTime.setText(time);
                    busNumber.setText(busNum);
                    seatNumber.setText(seatNum);
//                driverName.setText(driver_Name);
//                driverPhone.setText(driver_Phone);
//                companyName.setText(company);
//                rfidNumber.setText(rfid_Number);

                } else {

                    Toast.makeText(BookingSeat.this, "You have no booking tickets yet", Toast.LENGTH_SHORT).show();
                    Intent backHome = new Intent(BookingSeat.this, Home.class);
                    startActivity(backHome);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }// end onCreate


    //
    ////
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancelBooking(View view) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                BookingSeat.this);

        alertDialog.setTitle("Confirm cancelling...");
        alertDialog.setMessage("Are you sure you want to cancel this ticket?");
        alertDialog.setIcon(R.drawable.ic_warning_yellow_30dp);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // if we click yes to cancel ticket

                        /*
                         *
                         * here we delete user from Booking database from firebase*/

                        mDeleteFromDatabase = FirebaseDatabase.getInstance().getReference().child("Booking");
                        Query query = mDeleteFromDatabase.child(currentUser);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                dataSnapshot.getRef().removeValue();

                                Intent intent = new Intent(BookingSeat.this, Home.class);
                                startActivity(intent);
                                Toast.makeText(BookingSeat.this, "Your ticket canceled", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });

        // here when we clicked No in message dialog
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }
}
