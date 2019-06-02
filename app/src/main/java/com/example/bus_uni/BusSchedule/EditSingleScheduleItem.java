package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bus_uni.Driver.EditBusSchedule;
import com.example.bus_uni.Driver.Ticket;
import com.example.bus_uni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditSingleScheduleItem extends AppCompatActivity {


    TextView textView_seatNumbers;
    Button addButton, minusButton, saveButton;
    TimePicker changeTime_TimePicker;


    String seat = "";
    String line, time;


    // firebase database
    private DatabaseReference mEditTicketDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_schedule_item);


        textView_seatNumbers = (TextView) findViewById(R.id.textView_seatNumbers);
        addButton = (Button) findViewById(R.id.addOneButton);
        minusButton = (Button) findViewById(R.id.minusOneButton);
        saveButton = (Button) findViewById(R.id.saveEditScheduleItems);
        changeTime_TimePicker = (TimePicker) findViewById(R.id.changeLeavingTimeSpinner);


        // we receive the data from the ticket from choose locations
        Intent getTicketInfo = getIntent();

        line = getTicketInfo.getExtras().getString("busLine");
        final String company = getTicketInfo.getExtras().getString("companyName");
        final String name = getTicketInfo.getExtras().getString("driverName");
        final String phone = getTicketInfo.getExtras().getString("driverPhone");
        seat = getTicketInfo.getExtras().getString("seatNum");
        time = getTicketInfo.getExtras().getString("leavingTime");
        final String latitude = getTicketInfo.getExtras().getString("latitude");
        final String longitude = getTicketInfo.getExtras().getString("longitude");
        final String price = getTicketInfo.getExtras().getString("ticketPrice");
        final String keyId = getTicketInfo.getExtras().getString("keyId");
        final String driverId = getTicketInfo.getExtras().getString("driverId");
        final String busNum = getTicketInfo.getExtras().getString("busNum");


        // here assign the number of seat in textView
        textView_seatNumbers.setText(seat);


        // when click on add button "+"
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int addNum = Integer.parseInt(seat);
                addNum = addNum + 1;
                seat = addNum + "";

                //addSeat[0] = Integer.toString(newNumber);
                textView_seatNumbers.setText(seat);


            }
        });


        // when click on minus button "-"
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int minNum = Integer.parseInt(seat);
                minNum = minNum - 1;

                //TODO: here to make stop if arrive to zero
                seat = minNum + "";
                textView_seatNumbers.setText(seat);
            }
        });


        final Ticket ticket = new Ticket(driverId, name, line, price, time, seat, company, phone, longitude, latitude, keyId, busNum);

        /*
         *
         * when click on save button
         *
         * */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    hour = changeTime_TimePicker.getHour();
                    minute = changeTime_TimePicker.getMinute();
                } else {

                    hour = changeTime_TimePicker.getCurrentHour();
                    minute = changeTime_TimePicker.getCurrentMinute();
                }

                if (hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                } else {
                    am_pm = "AM";
                }


                time = hour + ":" + minute + " " + am_pm;


                /*
                 *
                 *        hoblaaaaaaaaa
                 * */

//                Log.d("KEY", "time picker now is :" + time);


                // we updated the new seat and time in the Ticket class
                ticket.setSeatNum(seat);
                ticket.setLeavingTime(time);

                mEditTicketDatabaseReference = FirebaseDatabase.getInstance().getReference("Ticket");
                mEditTicketDatabaseReference.child(line).child(keyId)
                        .setValue(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(EditSingleScheduleItem.this, "Your data saved successfully", Toast.LENGTH_SHORT).show();

                            Intent editTicket = new Intent(EditSingleScheduleItem.this, EditBusSchedule.class);
                            startActivity(editTicket);
                        }
                    }
                });


//                Log.d("KEY", "seat number now is :" + seat);

            }
        });


    }// end of onCreate


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
