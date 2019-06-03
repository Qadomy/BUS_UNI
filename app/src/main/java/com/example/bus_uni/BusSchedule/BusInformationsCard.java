package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.Booking.Book;
import com.example.bus_uni.Booking.BookingSeat;
import com.example.bus_uni.BusLocation;
import com.example.bus_uni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusInformationsCard extends AppCompatActivity {

    Button bookingTicket, currentLocation;
    TextView busRuteLine, busCompanyName, driverName, driverPhone, busSeatNumbers, busTime;


    // for get the current user
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String busSeatNumbersData = "";
    // firebase database
    private DatabaseReference mUserDatabaseReference, mBookingAnTicket, mUpdateTicketDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_informations_card);

        bookingTicket = (Button) findViewById(R.id.bookingTicketButton);
        currentLocation = (Button) findViewById(R.id.currentLocationButton);


        busRuteLine = (TextView) findViewById(R.id.busRuteLine);
        busCompanyName = (TextView) findViewById(R.id.busCompany);
        driverName = (TextView) findViewById(R.id.busDriverName);
        driverPhone = (TextView) findViewById(R.id.busDriverPhone);
        busSeatNumbers = (TextView) findViewById(R.id.busSeat);
        busTime = (TextView) findViewById(R.id.busLeavingTime_BusInfoCard);


        // we receive the data from the ticket from TicketAdapter
        Intent getTicketInfo = getIntent();

        final String busRuteLineData = getTicketInfo.getExtras().getString("busLine");
        final String busCompanyNameData = getTicketInfo.getExtras().getString("companyName");
        final String driverNameData = getTicketInfo.getExtras().getString("driverName");
        final String driverPhoneData = getTicketInfo.getExtras().getString("driverPhone");
        busSeatNumbersData = getTicketInfo.getExtras().getString("seatNum");
        final String busTimeData = getTicketInfo.getExtras().getString("leavingTime");
        final String latitude = getTicketInfo.getExtras().getString("latitude");
        final String longitude = getTicketInfo.getExtras().getString("longitude");
        final String driverID = getTicketInfo.getExtras().getString("driverId");
        final String keyId = getTicketInfo.getExtras().getString("keyId");
        final String busNum = getTicketInfo.getExtras().getString("busNum");



        busRuteLine.setText(busRuteLineData);
        busCompanyName.setText(busCompanyNameData);
        driverName.setText(driverNameData);
        driverPhone.setText(driverPhoneData);
        busSeatNumbers.setText(busSeatNumbersData);
        busTime.setText(busTimeData);


        // we clicked on booking ticket
        bookingTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        BusInformationsCard.this);

                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want booking this ticket?");
                alertDialog.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {



                                /*
                                 * if we click yes
                                 *
                                 * here to send the data in info card to booking seat(tickets)
                                 *
                                 * */

//                                Intent sendData = new Intent(BusInformationsCard.this, BookingSeat.class);
//                                //sendData.putExtra("busCityData", busCityData);
//                                sendData.putExtra("busRuteLineData", busRuteLineData);
//                                sendData.putExtra("busCompanyNameData", busCompanyNameData);
//                                sendData.putExtra("driverNameData", driverNameData);
//                                sendData.putExtra("driverPhoneData", driverPhoneData);
//                                sendData.putExtra("busLeavingTime", busTimeData);
//                                sendData.putExtra("latitude", latitude);
//                                sendData.putExtra("longitude", longitude);
//
//
//                                startActivity(sendData);


                                // and we send the driver id to BusLocation activity until
                                Intent sendDriverId = new Intent(BusInformationsCard.this, BusLocation.class);
                                sendDriverId.putExtra("driverId", driverID);
                                //startActivity(sendDriverId);


                                /*
                                 *
                                 *
                                 * */

                                /*
                                 * here I decrease the seat number by one
                                 * every time user click on yes to booking an ticket
                                 *
                                 * send the new number to user ticket to know his seat number
                                 * and I will update the seat number on the Ticket class */

                                int minNum = Integer.parseInt(busSeatNumbersData);
                                minNum = minNum - 1;

                                busSeatNumbersData = minNum + "";


                                mUpdateTicketDataBase = FirebaseDatabase.getInstance().getReference().child("Ticket");
                                mUpdateTicketDataBase.child(busRuteLineData).child(keyId).child("seatNum").setValue(busSeatNumbersData);


                                /*
                                 *
                                 *
                                 * */

                                mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                                mUserDatabaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String userName = dataSnapshot.child("name").getValue().toString();
                                        String rfidNumber = dataSnapshot.child("refid").getValue().toString();
                                        String city = dataSnapshot.child("city").getValue().toString();
                                        String userPhone = dataSnapshot.child("mobile").getValue().toString();
                                        String userEmail = dataSnapshot.child("email").getValue().toString();



                                        Book book = new Book(currentUser, userName, driverID, driverNameData, driverPhoneData,
                                                busRuteLineData, busTimeData, latitude, longitude, busSeatNumbersData,
                                                rfidNumber, busCompanyNameData, city, busNum, userPhone, userEmail);


                                        // here we create an a new class name a Book and uploaded it in firebase
                                        // * it contain all information of the ticket and the current user*/
                                        mBookingAnTicket = FirebaseDatabase.getInstance().getReference().child("Booking");
                                        mBookingAnTicket.child(currentUser).setValue(book).addOnCompleteListener(
                                                new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {

                                                            //showMessageDialog(getString(R.string.successfully), getString(R.string.Your_ticket_booked) + busSeatNumbersData, R.drawable.ic_check_circle_30dp);
                                                            Toast.makeText(BusInformationsCard.this, "Your ticket booked, your number: " + busSeatNumbersData, Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(BusInformationsCard.this, BookingSeat.class);
                                                            startActivity(intent);

                                                        }
                                                    }
                                                });

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                /*
                                 *
                                 * here we create an a new class name a Book and uploaded it in firebase
                                 * it contain all information of the ticket and the user*/


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

                /*
                 *
                 * here we send the latitude and longitude to currentLocations to tracking the bus
                 * */
                Intent currentLocation = new Intent(BusInformationsCard.this, CurrentLocation.class);
                currentLocation.putExtra("latitude", latitude);
                currentLocation.putExtra("longitude", longitude);
                currentLocation.putExtra("driverId", driverID);
                currentLocation.putExtra("busLine", busRuteLineData);
                startActivity(currentLocation);
            }
        });

    }// end onCreate


    // here method for make a message dialog on android
    private void showMessageDialog(String title, String message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(BusInformationsCard.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(messageIcon);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do stuff
                    }
                });
        alertDialog.show();
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
