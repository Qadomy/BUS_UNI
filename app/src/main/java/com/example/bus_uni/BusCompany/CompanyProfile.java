package com.example.bus_uni.BusCompany;

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
import com.example.bus_uni.Register.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CompanyProfile extends AppCompatActivity {


    ImageView companyPhoto, editCompanyProfile;
    TextView companyName, companyEmail, companyPhone, companyBusesNumbers, companyLineName;


    // here for get the id of current user and save in the string
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


    // firebase database
    private DatabaseReference mUserDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);


        companyPhoto = (ImageView) findViewById(R.id.aboutCompany_ProfilePhoto);
        editCompanyProfile = (ImageView) findViewById(R.id.aboutCompany_EditProfile);


        companyName = (TextView) findViewById(R.id.companyName);
        companyEmail = (TextView) findViewById(R.id.companyEmail);
        companyPhone = (TextView) findViewById(R.id.companyPhone);
        companyBusesNumbers = (TextView) findViewById(R.id.companyBusesNumbers);
        companyLineName = (TextView) findViewById(R.id.companyLineName);




        // init firebase database
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // here using Picasso for get the image url and set in ImageView
                String imageUrl = dataSnapshot.child("profile_pic").getValue().toString();
                Picasso.with(CompanyProfile.this).load(imageUrl).into(companyPhoto);


                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String phone = dataSnapshot.child("mobile").getValue().toString();
                String busesNumbers = dataSnapshot.child("buses_numbers").getValue().toString();
                String lineName = dataSnapshot.child("bus_line").getValue().toString();

                User user = dataSnapshot.getValue(User.class);


                // here we get the data from
                companyName.setText(user.getName());
                companyEmail.setText(user.getEmail());
                companyPhone.setText(user.getMobile());
                companyBusesNumbers.setText(user.getBuses_numbers());
                companyLineName.setText(user.getBus_line());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editCompanyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editCompanyProfile = new Intent(CompanyProfile.this, EditCompanyProfile.class);
                startActivity(editCompanyProfile);
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
