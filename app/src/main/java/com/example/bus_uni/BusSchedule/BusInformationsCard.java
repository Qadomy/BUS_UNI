package com.example.bus_uni.BusSchedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


        final String[] busCityData = new String[1];
        final String[] busRuteLineData = new String[1];
        final String[] busCompanyNameData = new String[1];
        final String[] driverNameData = new String[1];
        final String[] driverPhoneData = new String[1];
        final String[] busTimeData = new String[1];
        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //TODO: here we change the strings in child s
                busCityData[0] = dataSnapshot.child("name").getValue().toString();
                busRuteLineData[0] = dataSnapshot.child("email").getValue().toString();
                busCompanyNameData[0] = dataSnapshot.child("mobile").getValue().toString();
                driverNameData[0] = dataSnapshot.child("refid").getValue().toString();
                driverPhoneData[0] = dataSnapshot.child("city").getValue().toString();
                String busSeatNumbersData = dataSnapshot.child("city").getValue().toString();
                busTimeData[0] = dataSnapshot.child("city").getValue().toString();


                busCity.setText(busCityData[0]);
                busRuteLine.setText(busRuteLineData[0]);
                busCompanyName.setText(busCompanyNameData[0]);
                driverName.setText(driverNameData[0]);
                driverPhone.setText(driverPhoneData[0]);
                busSeatNumbers.setText(busSeatNumbersData);
                busTime.setText(busTimeData[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bookingSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        BusInformationsCard.this);

                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want booking this ticket?");
                alertDialog.setIcon(R.drawable.call_icon);
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog

                                //TODO: here we send all the data
                                Toast.makeText(getApplicationContext(),
                                        "You clicked on YES", Toast.LENGTH_SHORT)
                                        .show();


                                /*
                                 *
                                 *
                                 * here to send the data in info card to booking seat(tickets)
                                 *
                                 * */

                                Intent sendData = new Intent(BusInformationsCard.this, BookingSeat.class);
                                //TODO: here if there a error will be the [0] in the strings
                                //sendData.putExtra("busCityData", busCityData);
                                sendData.putExtra("busRuteLineData", busRuteLineData);
                                sendData.putExtra("busCompanyNameData", busCompanyNameData);
                                sendData.putExtra("driverNameData", driverNameData);
                                sendData.putExtra("driverPhoneData", driverPhoneData);
                                sendData.putExtra("busTimeData", busTimeData);
                                startActivity(sendData);

                                /*
                                 *
                                 *
                                 * */

                                Intent bookingSeat = new Intent(BusInformationsCard.this, BookingSeat.class);
                                startActivity(bookingSeat);
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
