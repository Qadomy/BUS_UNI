package com.example.bus_uni.BusSchedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bus_uni.BookingSeat;
import com.example.bus_uni.R;

public class BusInformationsCard extends AppCompatActivity {

    Button bookingSeat, currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_informations_card);

        bookingSeat = (Button)findViewById(R.id.bookingSeatButton);
        currentLocation = (Button)findViewById(R.id.currentLocationButton);



        bookingSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookingSeat = new Intent(BusInformationsCard.this, BookingSeat.class);
                startActivity(bookingSeat);
            }
        });

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent currentLocation = new Intent(BusInformationsCard.this, CurrentLocation.class);
                startActivity(currentLocation);
            }
        });
    }
}
