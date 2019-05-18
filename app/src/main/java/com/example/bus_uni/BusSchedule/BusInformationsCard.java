package com.example.bus_uni.BusSchedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bus_uni.BookingSeat;
import com.example.bus_uni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusInformationsCard extends AppCompatActivity {

    Button bookingSeat, currentLocation;
    TextView busCity, busRuteLine, busCompanyName, driverName, driverPhone, busSeatNumbers, busTime;


    // firebase database
    private DatabaseReference mUserDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_informations_card);

        bookingSeat = (Button) findViewById(R.id.bookingSeatButton);
        currentLocation = (Button) findViewById(R.id.currentLocationButton);


        busCity = (TextView) findViewById(R.id.busCity);
        busRuteLine = (TextView) findViewById(R.id.busRuteLine);
        busCompanyName = (TextView) findViewById(R.id.busCompany);
        driverName = (TextView) findViewById(R.id.busDriverName);
        driverPhone = (TextView) findViewById(R.id.busDriverPhone);
        busSeatNumbers = (TextView) findViewById(R.id.busSeat);
        busTime = (TextView) findViewById(R.id.busLeavingTime);


        // TODO: here we must make a query to get the data of driver of the specific driver from firebase
        // here for get the id of current user and save in the string
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // init firebase database
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");


        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //TODO: here we change the strings in child s
                String busCityData = dataSnapshot.child("name").getValue().toString();
                String busRuteLineData = dataSnapshot.child("email").getValue().toString();
                String busCompanyNameData = dataSnapshot.child("mobile").getValue().toString();
                String driverNameData = dataSnapshot.child("refid").getValue().toString();
                String driverPhoneData = dataSnapshot.child("city").getValue().toString();
                String busSeatNumbersData = dataSnapshot.child("city").getValue().toString();
                String busTimeData = dataSnapshot.child("city").getValue().toString();


                busCity.setText(busCityData);
                busRuteLine.setText(busRuteLineData);
                busCompanyName.setText(busCompanyNameData);
                driverName.setText(driverNameData);
                driverPhone.setText(driverPhoneData);
                busSeatNumbers.setText(busSeatNumbersData);
                busTime.setText(busTimeData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bookingSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookingSeat = new Intent(BusInformationsCard.this, BookingSeat.class);
                startActivity(bookingSeat);
            }
        });

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent currentLocation = new Intent(BusInformationsCard.this, CurrentLocation.class);
                startActivity(currentLocation);
            }
        });
    }
}
