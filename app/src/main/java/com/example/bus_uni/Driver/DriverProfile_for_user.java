package com.example.bus_uni.Driver;

import android.app.ActionBar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

public class DriverProfile_for_user extends AppCompatActivity {


    ImageView driverImage;
    TextView driverName, driverEmail, driverPhone, driverBusCompany, driverBusNumber, driverLineName;


    // firebase database
    private DatabaseReference mUserDatabaseReference;

    //
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_for_user);


        driverImage = (ImageView) findViewById(R.id.driverProfile_ProfilePhoto);
        driverName = (TextView) findViewById(R.id.driverProfile_name);
        driverEmail = (TextView) findViewById(R.id.driverProfile_Email);
        driverPhone = (TextView) findViewById(R.id.driverProfile_Phone);
        driverBusCompany = (TextView) findViewById(R.id.driverProfile_busCompany);
        driverBusNumber = (TextView) findViewById(R.id.driverProfile_BusNumber);
        driverLineName = (TextView) findViewById(R.id.driverProfile_LineName);



        //TODO: here for get the driver id not current user
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // init firebase database
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");


        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // here using Picasso for get the image url and set in ImageView
                String imageUrl = dataSnapshot.child("profile_pic").getValue().toString();
                Picasso.with(DriverProfile_for_user.this).load(imageUrl).into(driverImage);



                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String mobile = dataSnapshot.child("mobile").getValue().toString();
                String busCompany = dataSnapshot.child("bus_company").getValue().toString();
                String busNum = dataSnapshot.child("bus_num").getValue().toString();
                String lineName = dataSnapshot.child("bus_line").getValue().toString();

                driverName.setText(name);
                driverEmail.setText(email);
                driverPhone.setText(mobile);
                driverBusCompany.setText(busCompany);
                driverBusNumber.setText(busNum);
                driverLineName.setText(lineName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }// end of onCreate



    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}// end of Class
