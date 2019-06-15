package com.example.bus_uni;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.bus_uni.Booking.BookingSeat;
import com.example.bus_uni.BusSchedule.Bus_Schedule;
import com.example.bus_uni.Register.Profile_user;
import com.example.bus_uni.Register.SignupForFree;
import com.example.bus_uni.StreetsInformation.StreetInformation;
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

//
//    protected void onDestroy() {
//
//        super.onDestroy();
//    }

    // Here for shown the menu on the MainActivity
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_after_login, menu);
        return true;
    }


    // here for menu
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.settings_menu:
                startActivity(new Intent(Home.this, Setting.class));
                return true;

            case R.id.help_menu:
                startActivity(new Intent(Home.this, HelpActivity.class));
                return true;

            case R.id.logout_menu:
                // here when we press on Sign Out button in menu
                firebaseAuth.signOut();

                Intent signOut = new Intent(Home.this, SignupForFree.class);

                // here for when sign out of the account and when we make a back we can`t retrieve information
                // again of the user until we Login again
                signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signOut);
                finish();
                return true;

            case R.id.connect_menu:
                startActivity(new Intent(Home.this, ConnectWithUs.class));
                return true;

            case R.id.about_menu:
                startActivity(new Intent(Home.this, About.class));
                return true;

            //TODO:
            case R.id.exit_menu:
                finish();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                }
                else
                    moveTaskToBack(true);
                //TODO: This may be not necessary
                System.exit(0);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void bookinSeatClick(View view) {
        Intent booking = new Intent(Home.this, BookingSeat.class);
        startActivity(booking);
    }

    public void streetNewsClick(View view) {
        Intent street = new Intent(Home.this, StreetInformation.class);
        startActivity(street);
    }

    public void busScheduleClick(View view) {
        Intent busSchedule = new Intent(Home.this, Bus_Schedule.class);
        startActivity(busSchedule);
    }

    public void myLocationClick(View view) {
        Intent myLocation = new Intent(Home.this, MyLocation.class);
        startActivity(myLocation);
    }


    public void passengerProfile(View view) {
        Intent profile = new Intent(Home.this, Profile_user.class);
        startActivity(profile);
    }
}
