package com.example.bus_uni.Booking;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bus_uni.Driver.CheckBookings;
import com.example.bus_uni.Driver.EditBusSchedule;
import com.example.bus_uni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ControlPassengers extends AppCompatActivity {

    //TODO: This Activity for what!
    Button addBlackList;
    TextView name, phone, email, seatLabel, city, paymentMethod, calling;


    String seatNum, userPhone, userName;

    // get the id for current user
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    // firebase database
    DatabaseReference mBlacklistDataBase, mDeleteFromDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_passengers);

        addBlackList = (Button) findViewById(R.id.addBlackListButton);

        name = (TextView) findViewById(R.id.passengerName);
        phone = (TextView) findViewById(R.id.passengerPhone);
        email = (TextView) findViewById(R.id.passengerEmail);
        seatLabel = (TextView) findViewById(R.id.passengerSeatLabel);
        city = (TextView) findViewById(R.id.passengerCity);
        paymentMethod = (TextView) findViewById(R.id.passengerPaymentMethod);
        calling = (TextView) findViewById(R.id.callingPassenger);


        // now receive data from BookedTicketAdapter
        Intent getBookedInfo = getIntent();

        userName = getBookedInfo.getExtras().getString("userName");
        userPhone = getBookedInfo.getExtras().getString("userPhone");
        final String userEmail = getBookedInfo.getExtras().getString("userEmail");
        seatNum = getBookedInfo.getExtras().getString("seatNum");
        final String userCity = getBookedInfo.getExtras().getString("city");

        final String userId = getBookedInfo.getExtras().getString("userId");
        final String driverId = getBookedInfo.getExtras().getString("driverId");


        name.setText(userName);
        phone.setText(userPhone);
        email.setText(userEmail);
        seatLabel.setText(seatNum);
        city.setText(userCity);


        // here when click on calling we open the phone and calling passenger number
        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts("tel", userPhone, null));
                startActivity(intent);
            }
        });


        // when click on add to blacklist button
        addBlackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                 *
                 * here must remove this user from booking class from database in firebase
                 *
                 * */


                // message dialog confirmation to add user to blacklist or no
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        ControlPassengers.this);

                alertDialog.setTitle("Confirmations...");
                alertDialog.setMessage("Are you sure want to add (" + userName + ") to blacklist?");
                alertDialog.setIcon(R.drawable.ic_warning_yellow_30dp);

                // here when we clicked Yes in message dialog
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                /*
                                *
                                * here we delete user from Booking database from firebase*/

                                mDeleteFromDatabase = FirebaseDatabase.getInstance().getReference().child("Booking");
                                Query query = mDeleteFromDatabase.child(userId);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        dataSnapshot.getRef().removeValue();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                /*
                                *
                                * first thing create an new table name Blacklist in firebase to save the
                                * list of users who added to blacklist
                                * */

                                BlackList blackList = new BlackList(userId, userName, userEmail, userPhone, userCity, driverId);
                                mBlacklistDataBase = FirebaseDatabase.getInstance().getReference().child("BlackList");
                                mBlacklistDataBase.push().setValue(blackList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){

                                            showMessageDialog(getString(R.string.User_added_to_blacklist),
                                                    getString(R.string.successfully), R.drawable.ic_check_circle_30dp);

                                            Intent intent = new Intent(ControlPassengers.this, CheckBookings.class);
                                            startActivity(intent);
                                        }
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
        });

    }// end onCreate

    // here method for make a message dialog on android
    private void showMessageDialog(String title, String message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(ControlPassengers.this).create();
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
