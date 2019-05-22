package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.BookingSeat;
import com.example.bus_uni.R;
import com.google.firebase.database.DatabaseReference;

public class BusInformationsCard extends AppCompatActivity {

    Button bookingTicket, currentLocation;
    TextView busRuteLine, busCompanyName, driverName, driverPhone, busSeatNumbers, busTime;


    // firebase database
    //private DatabaseReference mUserDatabaseReference;


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


        //TODO: here the data dosen`t passing from TicketAdapter

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
                alertDialog.setIcon(R.drawable.call_icon);
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //TODO: here we send all the data
                                Toast.makeText(getApplicationContext(),
                                        "You clicked on YES", Toast.LENGTH_SHORT)
                                        .show();


                                /*
                                 * if we click yes
                                 *
                                 * here to send the data in info card to booking seat(tickets)
                                 *
                                 * */

                                Intent sendData = new Intent(BusInformationsCard.this, BookingSeat.class);
                                //sendData.putExtra("busCityData", busCityData);
                                sendData.putExtra("busRuteLineData", busRuteLineData);
                                sendData.putExtra("busCompanyNameData", busCompanyNameData);
                                sendData.putExtra("driverNameData", driverNameData);
                                sendData.putExtra("driverPhoneData", driverPhoneData);
                                sendData.putExtra("busLeavingTime", busTimeData);
                                sendData.putExtra("latitude", latitude);
                                sendData.putExtra("longitude", longitude);


                                startActivity(sendData);

                                /*
                                 *
                                 *
                                 * */

//                                Intent bookingSeat = new Intent(BusInformationsCard.this, BookingSeat.class);
//                                startActivity(bookingSeat);
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

                // TODO: here we send the current data to check where is the bus
                Intent currentLocation = new Intent(BusInformationsCard.this, CurrentLocation.class);
                startActivity(currentLocation);
            }
        });
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
