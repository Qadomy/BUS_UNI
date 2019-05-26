package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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


        // here assign the number of seat in textView
        textView_seatNumbers.setText(seat);


        //TODO: get the corrcet time from time picker
        SimpleDateFormat sdf = new SimpleDateFormat("hh:ss");
        Date date = null;
        try {
            date = sdf.parse("07:00");
        } catch (ParseException e) {
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        //TimePicker picker = new TimePicker(getApplicationContext());
        changeTime_TimePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
        changeTime_TimePicker.setCurrentMinute(c.get(Calendar.MINUTE));


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


        final Ticket ticket = new Ticket(name, line, price, time, seat, company, phone, longitude, latitude, keyId);

        /*
         *
         * when click on save button
         *
         * */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // here we call the firebase to update the new data in database reference
                // if updated successfully we make an intent to return to previous activity
                //


                // we updated the new seat in the Ticket class
                ticket.setSeatNum(seat);

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
