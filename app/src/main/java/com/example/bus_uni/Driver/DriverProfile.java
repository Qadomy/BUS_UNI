package com.example.bus_uni.Driver;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bus_uni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DriverProfile extends AppCompatActivity {


    ImageView driverImage, editProfile;
    TextView driverName, driverEmail, driverPhone, driverBusCompany,
            driverBusNumber, driverSeatNum, driverLineName, driverLongitude, driverLatitude;


    // here for get the id of current user and save in the string
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


    // firebase database
    private DatabaseReference mUserDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_for_driver);


        driverImage = (ImageView) findViewById(R.id.driverProfile_ProfilePhoto);
        editProfile = (ImageView) findViewById(R.id.driverProfile_EditProfile);
        driverName = (TextView) findViewById(R.id.driverProfile_name);
        driverEmail = (TextView) findViewById(R.id.driverProfile_Email);
        driverPhone = (TextView) findViewById(R.id.driverProfile_Phone);
        driverBusCompany = (TextView) findViewById(R.id.driverProfile_busCompany);
        driverBusNumber = (TextView) findViewById(R.id.driverProfile_BusNumber);
        driverLineName = (TextView) findViewById(R.id.driverProfile_LineName);
        driverSeatNum = (TextView) findViewById(R.id.driverProfile_seatNum);

        driverLongitude = (TextView) findViewById(R.id.driverProfile_longitude);
        driverLatitude = (TextView) findViewById(R.id.driverProfile_latitude);


        // init firebase database
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");


        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // here using Picasso for get the image url and set in ImageView
                String imageUrl = dataSnapshot.child("profile_pic").getValue().toString();
                Picasso.with(DriverProfile.this).load(imageUrl).into(driverImage);


                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String mobile = dataSnapshot.child("mobile").getValue().toString();
                String busCompany = dataSnapshot.child("bus_company").getValue().toString();
                String busNum = dataSnapshot.child("bus_num").getValue().toString();
                String lineName = dataSnapshot.child("bus_line").getValue().toString();
                String seatNum = dataSnapshot.child("bus_seat").getValue().toString();

                String longitude = dataSnapshot.child("longitude").getValue().toString();
                String latitude = dataSnapshot.child("latitude").getValue().toString();


                //
                driverName.setText(name);
                driverEmail.setText(email);
                driverPhone.setText(mobile);
                driverBusCompany.setText(busCompany);
                driverBusNumber.setText(busNum);
                driverLineName.setText(lineName);
                driverSeatNum.setText(seatNum);

                driverLongitude.setText(longitude);
                driverLatitude.setText(latitude);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(DriverProfile.this, EditDriverProfile.class);
                startActivity(editProfile);
            }
        });

    } // end of onCreate


    // here for back symbol in action bar
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
