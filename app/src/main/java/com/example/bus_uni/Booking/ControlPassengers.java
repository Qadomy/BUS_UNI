package com.example.bus_uni.Booking;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bus_uni.R;

public class ControlPassengers extends AppCompatActivity {


    Button addBlackList;
    TextView name, phone, email, seatLabel, city, rfid, paymentMethod, calling;


    String seatNum, userPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_passengers);

        addBlackList = (Button)findViewById(R.id.addBlackListButton);

        name = (TextView)findViewById(R.id.passengerName);
        phone = (TextView)findViewById(R.id.passengerPhone);
        email = (TextView)findViewById(R.id.passengerEmail);
        seatLabel = (TextView)findViewById(R.id.passengerSeatLabel);
        city = (TextView)findViewById(R.id.passengerCity);
        rfid = (TextView)findViewById(R.id.passengerRFID);
        paymentMethod = (TextView)findViewById(R.id.passengerPaymentMethod);
        calling = (TextView)findViewById(R.id.callingPassenger);


        // now receive data from BookedTicketAdapter
        Intent getBookedInfo = getIntent();

        final String userName = getBookedInfo.getExtras().getString("userName");
        userPhone = getBookedInfo.getExtras().getString("userPhone");
        final String userEmail = getBookedInfo.getExtras().getString("userEmail");
        seatNum = getBookedInfo.getExtras().getString("seatNum");
        final String userCity = getBookedInfo.getExtras().getString("city");
        final String userRfid = getBookedInfo.getExtras().getString("rfid");

        final String userId = getBookedInfo.getExtras().getString("userId");
        final String driverId = getBookedInfo.getExtras().getString("driverId");


        name.setText(userName);
        phone.setText(userPhone);
        email.setText(userEmail);
        seatLabel.setText(seatNum);
        city.setText(userCity);
        rfid.setText(userRfid);





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

            }
        });
    }
}
