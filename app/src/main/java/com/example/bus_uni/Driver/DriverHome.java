package com.example.bus_uni.Driver;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.LoginUserActivity;
import com.example.bus_uni.R;
import com.example.bus_uni.Register.User;
import com.example.bus_uni.Street_Information;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class DriverHome extends AppCompatActivity implements LocationListener {


    ImageView driverImage;
    TextView driverName;
    Button checkBookings, editBusSchedule, addPost, profile;
    //
    // here for get the id of current user and save in the string
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //
    // firebase auth
    private FirebaseAuth firebaseAuth;
    //......//
    // firebase database
    private DatabaseReference mUserDatabaseReference;


    // for locations
    private LocationManager locationManager;


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


        // init database reference
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference();



        /*
         *
         * here for getting the current locations
         *
         * */


        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            }, 101);

        }


        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    5000, 5, this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }




        /*
         *
         * // end of getting the current locations
         *
         * */


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

    }// end of onCreate

    @Override
    public void onLocationChanged(Location location) {

        // here for get the longitude and latitude
        double getLongitude = location.getLongitude();
        double getLatitude = location.getLatitude();


        // after we get the longitude and latitude we uploaded to firebase
        mUserDatabaseReference.child("Users").child(currentuser).child("longitude").setValue(getLongitude);
        mUserDatabaseReference.child("Users").child(currentuser).child("latitude").setValue(getLatitude);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    //
    //
    //
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
