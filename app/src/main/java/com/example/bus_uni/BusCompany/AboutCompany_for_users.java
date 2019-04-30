package com.example.bus_uni.BusCompany;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class AboutCompany_for_users extends AppCompatActivity {


    ImageView companyPhoto;
    Button driversInformations;
    TextView companyName, companyEmail, companyPhone, companyBusesNumbers, companyLineName;


    // firebase database
    private DatabaseReference mUserDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_company_for_users);


        companyPhoto = (ImageView) findViewById(R.id.aboutCompany_ProfilePhoto);

        driversInformations = (Button) findViewById(R.id.companyDriversButton);

        companyName = (TextView) findViewById(R.id.aboutCompany_name);
        companyEmail = (TextView) findViewById(R.id.aboutCompany_Email);
        companyPhone = (TextView) findViewById(R.id.aboutCompany_Phone);
        companyBusesNumbers = (TextView) findViewById(R.id.aboutCompany_BusesNumbers);
        companyLineName = (TextView) findViewById(R.id.aboutCompany_LineName);


        ///
        ///
        ///
        //TODO: here for get the id of company id not current user id
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // init firebase database
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // here using Picasso for get the image url and set in ImageView
                String imageUrl = dataSnapshot.child("profile_pic").getValue().toString();
                Picasso.with(AboutCompany_for_users.this).load(imageUrl).into(companyPhoto);


                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String phone = dataSnapshot.child("mobile").getValue().toString();
                String busesNumbers = dataSnapshot.child("buses_numbers").getValue().toString();
                String lineName = dataSnapshot.child("bus_line").getValue().toString();


                // here we get the data from
                companyName.setText(name);
                companyEmail.setText(email);
                companyPhone.setText(phone);
                companyBusesNumbers.setText(busesNumbers);
                companyLineName.setText(lineName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        driversInformations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driversInfo = new Intent(AboutCompany_for_users.this, CompanyDrivers.class);
                startActivity(driversInfo);
            }
        });
    }
}
