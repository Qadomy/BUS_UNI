package com.example.bus_uni.Driver;

import android.content.Intent;
import android.os.Bundle;
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

public class DriverHome extends AppCompatActivity {


    ImageView driverImage;
    TextView driverName;
    Button checkBookings, editBusSchedule, addPost, profile;

    //
    // firebase auth
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);


        driverImage = (ImageView) findViewById(R.id.driverImagePhoto);
        driverName = (TextView) findViewById(R.id.driverHome_name);
        checkBookings = (Button) findViewById(R.id.checkBookingsButton);
        editBusSchedule = (Button) findViewById(R.id.editBusSchedule);
        addPost = (Button) findViewById(R.id.driverAddPostButton);
        profile = (Button) findViewById(R.id.driverProfileButton);


        //
        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();



        //
        ///
        ////
        ///
        //
        ///


        checkBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkBookings = new Intent(DriverHome.this, CheckBookings.class);
                startActivity(checkBookings);
            }
        });

        editBusSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editBusSchedule = new Intent(DriverHome.this, EditBusSchedule.class);
                startActivity(editBusSchedule);
            }
        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPost = new Intent(DriverHome.this, Street_Information.class);
                startActivity(addPost);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driverProfile = new Intent(DriverHome.this, DriverProfile_for_driver.class);
                startActivity(driverProfile);
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

                Intent signOut = new Intent(DriverHome.this, LoginUserActivity.class);

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
