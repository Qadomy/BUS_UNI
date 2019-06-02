package com.example.bus_uni.Driver;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bus_uni.BusSchedule.EditItemSchedule_Adapter;
import com.example.bus_uni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditBusSchedule extends AppCompatActivity {

    //
    private String keyId, busLine, driverName, seatNumber, busCompany, driverPhone, latitude, longitude, busNum;


    // here for get the id of current user and save in the string
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    //
    // firebase database
    private DatabaseReference mUserDatabaseReference, mTicketDatabaseReference, mEditTicketDatabaseReference;


    // for recycle view and TicketAdapter
    private RecyclerView mRecyclerView;
    private EditItemSchedule_Adapter mEditItemAdapter;
    private ArrayList<Ticket> tickets = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bus_schedule);




        // init firebase database
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                busLine = dataSnapshot.child("bus_line").getValue().toString();
                driverName = dataSnapshot.child("name").getValue().toString();
                seatNumber = dataSnapshot.child("bus_seat").getValue().toString();
                busCompany = dataSnapshot.child("bus_company").getValue().toString();
                driverPhone = dataSnapshot.child("mobile").getValue().toString();
                latitude = dataSnapshot.child("latitude").getValue().toString();
                longitude = dataSnapshot.child("longitude").getValue().toString();
                busNum = dataSnapshot.child("bus_num").getValue().toString();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*
         *
         *   init recycle view
         *
         *   */

        mRecyclerView = (RecyclerView) findViewById(R.id.editSchudeleDate_RecycleView);
        showTicketsInRecycleView();


    }// end of onCreate


    // method for display the list of tickets >>>>>>> uuuuuuh yaaaa man -,-
    private void showTicketsInRecycleView() {

        mEditTicketDatabaseReference = FirebaseDatabase.getInstance().getReference("Ticket");


        mEditTicketDatabaseReference.child("AAUJ-Tulkarm").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                            keyId = childSnapshot.getKey();


                            String driverName = childSnapshot.child("name").getValue().toString();

                            // declare this function out of this scope
                            String driverId = childSnapshot.child("driverId").getValue().toString();
                            String line = childSnapshot.child("busLine").getValue().toString();
                            String price = childSnapshot.child("price").getValue().toString();
                            String time = childSnapshot.child("leavingTime").getValue().toString();
                            String seat = childSnapshot.child("seatNum").getValue().toString();
                            String company = childSnapshot.child("company").getValue().toString();
                            String phone = childSnapshot.child("driverPhone").getValue().toString();
                            String latitude = childSnapshot.child("latitude").getValue().toString();
                            String longitude = childSnapshot.child("longitude").getValue().toString();
                            String busNum= childSnapshot.child("busNum").getValue().toString();

                            tickets.add(new Ticket(driverId, driverName, line, price, time, seat, company, phone, latitude, longitude, keyId, busNum));
                            showRecyclerView();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    private void showRecyclerView() {

        // here how we want to display the list of tickets
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mEditItemAdapter = new EditItemSchedule_Adapter(tickets, EditBusSchedule.this);
        mRecyclerView.setAdapter(mEditItemAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    //
    //
    // here when driver click on add new date button to pop form for input a new date (ticket)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addNewDate(View view) {

        // get activity_add_new_bus_date.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.activity_add_new_bus_date, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set activity_add_new_bus_date.xml to alert dialog builder
        alertDialogBuilder.setView(promptsView);

        final TimePicker timeInput = (TimePicker) promptsView
                .findViewById(R.id.timePicker_addNewDate);

        final EditText ticketPrice = (EditText) promptsView.findViewById(R.id.addTicketPrice);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {



                                /*
                                 *
                                 *
                                 * ooooooh finally I get it ha ha ha ha
                                 *
                                 *
                                 * here we get the current time from the spinner (time picker)*/

                                int hour, minute;
                                String am_pm;
                                if (Build.VERSION.SDK_INT >= 23) {
                                    hour = timeInput.getHour();
                                    minute = timeInput.getMinute();
                                } else {

                                    hour = timeInput.getCurrentHour();
                                    minute = timeInput.getCurrentMinute();
                                }

                                if (hour > 12) {
                                    am_pm = "PM";
                                    hour = hour - 12;
                                } else {
                                    am_pm = "AM";
                                }


                                String time = hour + ":" + minute + " " + am_pm;


                                /*
                                 *
                                 *        hoblaaaaaaaaa
                                 * */

                                String newTicketPrice = ticketPrice.getText().toString();

                                // To send data to save it in database reference
                                final Ticket ticket = new Ticket(currentuser, driverName, busLine, newTicketPrice, time, seatNumber, busCompany, driverPhone, latitude, longitude, "", busNum);


                                mTicketDatabaseReference = FirebaseDatabase.getInstance().getReference("Ticket")
                                        .child(busLine);

                                // here we using push() to added without updated the previous,
                                // give to every data an ID
                                mTicketDatabaseReference.push().setValue(ticket)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                showMessageDialog(getString(R.string.yourNewDateAded),
                                                        getString(R.string.successfully), R.drawable.ic_check_circle_30dp);


                                            }
                                        });


                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    // here method for make a message dialog on android
    private void showMessageDialog(String title, String message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(EditBusSchedule.this).create();
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


    //
    ///// for back
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
