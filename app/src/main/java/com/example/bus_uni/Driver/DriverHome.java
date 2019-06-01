package com.example.bus_uni.Driver;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bus_uni.BusSchedule.CurrentLocation;
import com.example.bus_uni.LoginUserActivity;
import com.example.bus_uni.R;
import com.example.bus_uni.Street_Information;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverHome extends FragmentActivity implements LocationListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;
    //
    //
    //...///////......
    // here for get the id of current user and save in the string
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    MapFragment mapFragment;
    //
    //
    double getLatitude;
    double getLongitude;
    // GeoFire, for store the locations of drivers in firebase database realtime
    GeoFire geoFire;
    private GoogleMap map;
    //
    //
    // firebase auth
    private FirebaseAuth firebaseAuth;
    //
    //......//
    // firebase database
    private DatabaseReference mUserDatabaseReference, mDriverAvailabilityRef, nameBusssLine;
    //
    // for locations
    private LocationManager locationManager;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);


        // init the fragment of map
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.driverMap_currentLocations);
        mapFragment.getMapAsync(this);

        //
        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();


        // init database reference
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");


        // Check GPS is enabled
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
            //finish();
        }

        /*
         *
         * here for getting the location permissions
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


    }// end of onCreate


    // method for OnMapReadyCallback abstract
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        //map.setMaxZoomPreference(16);

        buildGoogleAPIClient();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true); // for my current location button above the screen


    }


    // 4 methods for LocationListener Listener
    @Override
    public void onLocationChanged(Location location) {


        lastLocation = location;   //------->        //********??

        // here for get the longitude and latitude
        getLatitude = location.getLatitude();
        getLongitude = location.getLongitude();

        // after we get the longitude and latitude we uploaded to firebase
        mUserDatabaseReference.child(currentuser).child("latitude").setValue(getLatitude);
        mUserDatabaseReference.child(currentuser).child("longitude").setValue(getLongitude);

        /*
         *
         *
         * */


//        nameBusssLine = FirebaseDatabase.getInstance().getReference("Users");
//        nameBusssLine.child(currentuser).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String busLine = dataSnapshot.child("bus_line").getValue().toString();
//                Intent intent = new Intent(DriverHome.this, CurrentLocation.class);
//                intent.putExtra("busLine", busLine);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        LatLng currentLocation = new LatLng(getLatitude, getLongitude);
        map.addMarker(new MarkerOptions().position(currentLocation).title("My Current Location"));

        CameraPosition target = CameraPosition.builder().target(currentLocation).zoom(16).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(target));


        // using GeoFire to save the location
        mDriverAvailabilityRef = FirebaseDatabase.getInstance().getReference().child("Driver_Availability");
        geoFire = new GeoFire(mDriverAvailabilityRef);
        geoFire.setLocation(currentuser, new GeoLocation(location.getLatitude(), location.getLongitude()),
                new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error != null) {
                            Toast.makeText(DriverHome.this, "There was an error saving the location to GeoFire: " + error, Toast.LENGTH_SHORT).show();
                        } else {
                            // Location saved on server successfully!
                        }
                    }
                });
    }


    protected void onStop() {

        super.onStop();


//        // here when the driver logout or close the app the location of his bus gone from the map
//        mDriverAvailabilityRef = FirebaseDatabase.getInstance().getReference().child("Driver_Availability");
//        geoFire = new GeoFire(mDriverAvailabilityRef);
//        geoFire.removeLocation(currentuser);

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


    // 3 methods for GoogleApiClient ,........

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //********??
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000); // the time to change the locations
        locationRequest.setFastestInterval(1000); // get the time very rapidly if exist

        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //// method
    protected synchronized void buildGoogleAPIClient() {

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();

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


    //////////////////////////////////
    //////////////////////////////////

    public void checkBooking(View view) {
        Intent checkBookings = new Intent(DriverHome.this, CheckBookings.class);
        startActivity(checkBookings);
    }

    public void EditSchedule(View view) {
        Intent editBusSchedule = new Intent(DriverHome.this, EditBusSchedule.class);
        startActivity(editBusSchedule);

    }

    public void DriverAddPost(View view) {
        Intent addPost = new Intent(DriverHome.this, Street_Information.class);
        startActivity(addPost);
    }

    public void DriverProfile(View view) {
        Intent driverProfile = new Intent(DriverHome.this, DriverProfile_for_driver.class);
        startActivity(driverProfile);
    }


    public void logoutFromDriver(View view) {
        firebaseAuth.signOut();

        Intent signOut = new Intent(DriverHome.this, LoginUserActivity.class);

        // here for when sign out of the account and when we make a back we can`t retrieve information
        // again of the user until we Login again

        signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signOut);
    }
}
