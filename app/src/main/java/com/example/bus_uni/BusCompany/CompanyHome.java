package com.example.bus_uni.BusCompany;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bus_uni.LoginUserActivity;
import com.example.bus_uni.R;
import com.example.bus_uni.Street_Information;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CompanyHome extends AppCompatActivity {


    Button registerNewDriver, trckingBuses, addPost, aboutCompany;
    ImageView companyPic;
    TextView companyName;


    // firebase auth
    private FirebaseAuth firebaseAuth;


    // firebase database reference
    private DatabaseReference mUserDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);

        registerNewDriver = (Button) findViewById(R.id.rejisterNewDriverButton);
        trckingBuses = (Button) findViewById(R.id.trackingBusesButton);
        addPost = (Button) findViewById(R.id.companyAddPostButton);
        aboutCompany = (Button) findViewById(R.id.companyInfoButton);

        companyName = (TextView) findViewById(R.id.company_name);


        ///////
        companyPic = (ImageView) findViewById(R.id.companyProfilePhoto);


        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // here for get the id of current user and save in the string
        String currentuser = firebaseAuth.getCurrentUser().getUid();


        // init firebase database
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");


        mUserDatabaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // here using Picasso for get the image url and set in ImageView
                String imageUrl = dataSnapshot.child("profile_pic").getValue().toString();
                Picasso.with(CompanyHome.this).load(imageUrl).into(companyPic);

                String name = dataSnapshot.child("name").getValue().toString();

                // here we get the name from name in data base and set in textView
                companyName.setText(name);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        registerNewDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerNewDriver = new Intent(CompanyHome.this, RegisterNewDriver.class);
                startActivity(registerNewDriver);
            }
        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPost = new Intent(CompanyHome.this, Street_Information.class);
                startActivity(addPost);
            }
        });

        aboutCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutCompany = new Intent(CompanyHome.this, AboutCompany_for_company.class);
                startActivity(aboutCompany);
            }
        });

    }


    // Here for shown the menu on the XML activity
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.company_menu, menu);
        return true;
    }


    // here for menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.company_logout_menu:

                // here when we press on Sign Out button in menu
                firebaseAuth.signOut();

                Intent signOut = new Intent(CompanyHome.this, LoginUserActivity.class);

                // here for when sign out of the account and when we make a back we can`t retrieve information
                // again of the user until we Login again

                signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signOut);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
