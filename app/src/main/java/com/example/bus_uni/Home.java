package com.example.bus_uni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.bus_uni.BusSchedule.Bus_Schedule;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    // firebase auth for sign out
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        firebaseAuth = FirebaseAuth.getInstance();

    }



    // Here for shown the menu on the MainAvtivity
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_after_login, menu);
        return true;
    }




    // here for menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profile_menu:
                startActivity(new Intent(Home.this, Profile_user.class));
                return true;

//            case R.id.settings_menu:
//                startActivity(new Intent(this,. class));
//                return true;

            case R.id.about_menu:
                startActivity(new Intent(Home.this, About.class));
                return true;

//            case R.id.help_menu:
//                startActivity(new Intent(Home.this, HelpActivity.class));

            case R.id.logout_menu:
                // here when we press on Sign Out button in menu
                firebaseAuth.signOut();

                Intent signOut = new Intent(Home.this, SignupForFree.class);

                // here for when sign out of the account and when we make a back we can`t retrieve information
                // again of the user until we Login again
                signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signOut);
                return true;

            case R.id.connect_menu:
                startActivity(new Intent(Home.this, ConnectWithUs.class));
                return true;

            case R.id.exit_menu:
                System.exit(0);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // here methods when we click on the images and text in After Login Activity after we log in in applications

    public void bookinSeatClick(View view) {
        Intent booking = new Intent(Home.this, BookingSeat.class);
        startActivity(booking);
    }

    public void streetNewsClick(View view) {
        Intent street = new Intent(Home.this, Street_Information.class);
        startActivity(street);
    }

    public void busScheduleClick(View view) {
        Intent busSchedule = new Intent(Home.this, Bus_Schedule.class);
        startActivity(busSchedule);
    }

    public void busLocationClick(View view) {
        Intent busLocation = new Intent(Home.this, BusLocation.class);
        startActivity(busLocation);
    }




}
