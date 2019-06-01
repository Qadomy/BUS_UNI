package com.example.bus_uni.BusSchedule;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bus_uni.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CurrentLocation extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{


    private GoogleMap map;
    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;

    String getLatitude, getLongitude, getDriverId, busLine, geoLat, geoLong;


    DatabaseReference mDatabaseReference, ticketLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent getLatLong = getIntent();
        getLatitude = getLatLong.getExtras().getString("latitude");
        getLongitude = getLatLong.getExtras().getString("longitude");
        getDriverId = getLatLong.getExtras().getString("driverId");
        busLine = getLatLong.getExtras().getString("busLine");



//        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Driver_Availability");
//        mDatabaseReference.child(getDriverId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                //geoLat = dataSnapshot.child("l").getValue().toString();
//
//                Toast.makeText(CurrentLocation.this, geoLat, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });





        getBusLocation();

    }

    private void getBusLocation() {

        // method for get the current location of the ticket

        ticketLocation = FirebaseDatabase.getInstance().getReference().child("Driver_Availability");

        GeoFire geoFire = new GeoFire(ticketLocation);

        // here we create an GeoQuery to get the lat and lng of the bus location
        //GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(ticketLocation.));


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        map = googleMap;

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        buildGoogleAPIClient();
        map.setMyLocationEnabled(true); // for my current location button above the screen
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

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

    @Override
    public void onLocationChanged(Location location) {


        // convert latitude and longitude from string to double
        double latitude = Double.parseDouble(getLatitude);
        double longitude = Double.parseDouble(getLongitude);

        LatLng currentBus = new LatLng(longitude , latitude);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true);
        map.addMarker(new MarkerOptions().position(currentBus).title(busLine));

        CameraPosition target = CameraPosition.builder().target(currentBus).zoom(17).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }


    //// method
    protected synchronized void buildGoogleAPIClient(){

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();

    }
}
