package com.example.bus_uni;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class BookingSeat extends AppCompatActivity {


    TextView passengerName, orignLocation, destinationLocation, leavingTime, arrivalTime, busNumber, gateNumber, seatNumber, driverName, driverPhone, companyName, rfidNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_seat);


        passengerName = (TextView) findViewById(R.id.textViewPassengerName);
        orignLocation = (TextView) findViewById(R.id.textViewOriginLocation);
        destinationLocation = (TextView) findViewById(R.id.textViewDestinationLocation);
        leavingTime = (TextView) findViewById(R.id.textViewLeavingTime);
        arrivalTime = (TextView) findViewById(R.id.textViewArrivalTime);
        busNumber = (TextView) findViewById(R.id.textViewBusNumber);
        gateNumber = (TextView) findViewById(R.id.textViewGate);
        seatNumber = (TextView) findViewById(R.id.textViewSeat);
        driverName = (TextView) findViewById(R.id.driverName_bookingSeat);
        driverPhone = (TextView) findViewById(R.id.driverPhone_bookingSeat);
        companyName = (TextView) findViewById(R.id.companyName_bookingSeat);
        rfidNumber = (TextView) findViewById(R.id.rfidNumber_bookingSeat);


        Intent getData = getIntent();
        //String busCityData = getData.getStringExtra("busCityData");
        String busRuteLineData = getData.getStringExtra("busRuteLineData");
        String busCompanyNameData = getData.getStringExtra("busCompanyNameData");
        String driverNameData = getData.getStringExtra("driverNameData");
        String driverPhoneData = getData.getStringExtra("driverPhoneData");
        String busTimeData = getData.getStringExtra("busLeavingTime");


        orignLocation.setText(busRuteLineData);
        leavingTime.setText(busTimeData);
        companyName.setText(busCompanyNameData);
        driverName.setText(driverNameData);
        driverPhone.setText(driverPhoneData);

    }



    //
    ////
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
