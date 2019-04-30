package com.example.bus_uni.BusSchedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bus_uni.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CurrentLocation extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        LatLng faruon = new LatLng(32.290771, 35.036253);
        map.addMarker(new MarkerOptions().position(faruon).title("My Home"));

        CameraPosition target = CameraPosition.builder().target(faruon).zoom(11).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }
}
