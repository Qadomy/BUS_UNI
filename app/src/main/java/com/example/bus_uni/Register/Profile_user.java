package com.example.bus_uni.Register;

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

public class Profile_user extends AppCompatActivity {


    ImageView mProfileEdit, mProfileImage;
    TextView mProfileName, mProfileEmail, mProfileRfid_number, mProfileMobile_number, mProfileCity;

    // firebase database
    private DatabaseReference mUserDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mProfileEdit = (ImageView) findViewById(R.id.profile_edit);
        mProfileImage = (ImageView) findViewById(R.id.profile_image);


        mProfileName = (TextView) findViewById(R.id.profile_displayName);
        mProfileEmail = (TextView) findViewById(R.id.emailValue_profile);
        mProfileRfid_number = (TextView) findViewById(R.id.rfidValue_profile);
        mProfileMobile_number = (TextView) findViewById(R.id.mobileValue_profile);
        mProfileCity = (TextView) findViewById(R.id.cityValue_profile);


        // here for get the id of current user and save in the string
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // init firebase database
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");


        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // here using Picasso for get the image url and set in ImageView
                String imageUrl = dataSnapshot.child("profile_pic").getValue().toString();
                Picasso.with(Profile_user.this).load(imageUrl).into(mProfileImage);



                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String mobile = dataSnapshot.child("mobile").getValue().toString();
                String rfid = dataSnapshot.child("refid").getValue().toString();
                String city = dataSnapshot.child("city").getValue().toString();

                mProfileName.setText(name);
                mProfileEmail.setText(email);
                mProfileMobile_number.setText(mobile);
                mProfileRfid_number.setText(rfid);
                mProfileCity.setText(city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // when press on edit icon
        mProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(Profile_user.this, ProfileEdit_user.class);
                startActivity(editProfile);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
