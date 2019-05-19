package com.example.bus_uni.Driver;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bus_uni.ProfileEdit_user;
import com.example.bus_uni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditBusSchedule extends AppCompatActivity {

    String busLine;
    private Calendar calendar;
    // firebase database
    private DatabaseReference mUserDatabaseReference, mTicketDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bus_schedule);

        calendar = Calendar.getInstance();

        // here for get the id of current user and save in the string
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // init firebase database
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mTicketDatabaseReference = FirebaseDatabase.getInstance().getReference("Ticket");


        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                busLine = dataSnapshot.child("bus_line").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //
    //
    // here when driver click on add new date
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

        final EditText ticketPrice = (EditText) promptsView
                .findViewById(R.id.addTicketPrice);

        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int min = c.get(Calendar.MINUTE);

        // save the hour and minute in one string
        final String time = hour + ":" + min;

        //TODO: here why text not saved in new String >>> ??
        final String newTicketPrice = ticketPrice.getText().toString();


        // class Ticket to send data to save it in database reference
        final Ticket ticket = new Ticket(busLine, newTicketPrice, time);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get time input and set it to result
                                // edit text

                                //Toast.makeText(EditBusSchedule.this, time, Toast.LENGTH_SHORT).show();

                                mTicketDatabaseReference = FirebaseDatabase.getInstance().getReference("Ticket")
                                        .child(busLine);

                                mTicketDatabaseReference.setValue(ticket)
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
