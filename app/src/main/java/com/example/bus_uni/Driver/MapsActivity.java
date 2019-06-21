package com.example.bus_uni.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bus_uni.R;
import com.example.bus_uni.Register.LoginUserActivity;
import com.example.bus_uni.StreetsInformation.StreetInformation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


     static final String TAG = MapsActivity.class.getSimpleName();
    private HashMap<String, Marker> mMarkers = new HashMap<>();
    private GoogleMap mMap;
    private Button logoutButton;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        logoutButton=findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                stopService(intent);
                Intent signOut = new Intent(MapsActivity.this, LoginUserActivity.class);
                signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signOut);
                finish();

            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        startTrackerService();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Authenticate with Firebase when the Google map is loaded
        mMap = googleMap;
        mMap.setMaxZoomPreference(16);
        subscribeToUpdates();
    }


  private void subscribeToUpdates() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getString(R.string.locations));
        //TODO: lazem bl Driver maps ma ybyyen 3l-map ella el position tb3 had el driver (mesh kulhum)
        ref.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              // setMarker(dataSnapshot);

           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               setMarker(dataSnapshot);

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        }
        private void setMarker(DataSnapshot dataSnapshot) {
        // When a location update is received, put or update
        // its value in mMarkers, which contains all the markers
        // for locations received, so that we can build the
        // boundaries required to show them all on the map at once
        String key = dataSnapshot.getKey();
        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
        double lat = Double.parseDouble(value.get("latitude").toString());
        double lng = Double.parseDouble(value.get("longitude").toString());
        LatLng location = new LatLng(lat, lng);
        if (!mMarkers.containsKey(key)) {
            mMarkers.put(key, mMap.addMarker(new MarkerOptions().title(key).position(location)));
        } else {
            mMarkers.get(key).setPosition(location);
        }
        //TODO: animate or moveCamera?
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,12.0f));


    }

    private void startTrackerService() {
       // TrackerService service=new TrackerService();
       // service.stopSelf();
        //service.onDestroy();
        intent=new Intent(getBaseContext(), TrackerService.class);
       intent.putExtra(TAG,firebaseAuth.getCurrentUser().getUid());
        startService(intent);
        //   finish();
    }


    public void checkBooking(View view) {
        Intent checkBookings = new Intent(MapsActivity.this, CheckBookings.class);
        startActivity(checkBookings);
    }

    public void EditSchedule(View view) {
        Intent editBusSchedule = new Intent(MapsActivity.this, EditBusSchedule.class);
        startActivity(editBusSchedule);

    }

    public void DriverAddPost(View view) {
        Intent addPost = new Intent(MapsActivity.this, StreetInformation.class);
        startActivity(addPost);
    }

    public void DriverProfile(View view) {
        Intent driverProfile = new Intent(MapsActivity.this, DriverProfile.class);
        startActivity(driverProfile);
    }


    public void logoutFromDriver(View view) {
        firebaseAuth.signOut();

        Intent signOut = new Intent(MapsActivity.this, LoginUserActivity.class);
        signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        signOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signOut);
        finish();

    }
}