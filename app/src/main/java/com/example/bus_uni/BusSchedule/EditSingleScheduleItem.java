package com.example.bus_uni.BusSchedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.bus_uni.R;

public class EditSingleScheduleItem extends AppCompatActivity {


    TextView textView_seatNumbers;
    Button addButton, minusButton, saveButton;
    TimePicker changeTime_TimePicker;

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
        final String busSeatNumbersData = getTicketInfo.getExtras().getString("seatNum");
        final String busTimeData = getTicketInfo.getExtras().getString("leavingTime");
        final String latitude = getTicketInfo.getExtras().getString("latitued");
        final String longitude = getTicketInfo.getExtras().getString("longitude");


        textView_seatNumbers.setText(busSeatNumbersData);

        // when click on add button "+"
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // when click on minus button "-"
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });


    }
}
