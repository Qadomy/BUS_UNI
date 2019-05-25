package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bus_uni.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditSingleScheduleItem extends AppCompatActivity {


    TextView textView_seatNumbers;
    Button addButton, minusButton, saveButton;
    TimePicker changeTime_TimePicker;
    String busSeatNumbersData = "";

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

        final String busRuteLineData = getTicketInfo.getExtras().getString("busLine");
        final String busCompanyNameData = getTicketInfo.getExtras().getString("companyName");
        final String driverNameData = getTicketInfo.getExtras().getString("driverName");
        final String driverPhoneData = getTicketInfo.getExtras().getString("driverPhone");
        busSeatNumbersData = getTicketInfo.getExtras().getString("seatNum");
        final String busTimeData = getTicketInfo.getExtras().getString("leavingTime");
        final String latitude = getTicketInfo.getExtras().getString("latitude");
        final String longitude = getTicketInfo.getExtras().getString("longitude");


        // here assign the number of seat in textView
        textView_seatNumbers.setText(busSeatNumbersData);

        Toast.makeText(this, busSeatNumbersData, Toast.LENGTH_SHORT).show();

        //TODO: make sure if this work or no
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


                int addNum = Integer.parseInt(busSeatNumbersData);
                addNum = addNum + 1;
                busSeatNumbersData = addNum + "";

                //addSeat[0] = Integer.toString(newNumber);
                textView_seatNumbers.setText(busSeatNumbersData);


            }
        });


        // when click on minus button "-"
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int minNum = Integer.parseInt(busSeatNumbersData);
                minNum = minNum - 1;

                //TODO: here to make stop if arrive to zero
                busSeatNumbersData = minNum + "";
                textView_seatNumbers.setText(busSeatNumbersData);
            }
        });



        /*
         *
         * when click on save button
         *
         * */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // here we call the firebase to update the new data in database reference
                // if updated successfully we make an intent to return to previuos activity
                //

                



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
