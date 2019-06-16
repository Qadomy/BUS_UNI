package com.example.bus_uni.BusCompany;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bus_uni.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowDriversOnMap extends AppCompatActivity implements OnMapReadyCallback{


    private GoogleMap map;



    String getLatitude, getLongitude, getDriverName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_drivers_on_map);


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.companyMap_currentDriversLocations);
        mapFragment.getMapAsync(this);


        Intent getLatLong = getIntent();

        getDriverName = getLatLong.getExtras().getString("name");
        getLatitude = getLatLong.getExtras().getString("latitude");
        getLongitude = getLatLong.getExtras().getString("longitude");


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double latitude = Double.parseDouble(getLatitude);
        double longitude = Double.parseDouble(getLongitude);

        LatLng currentBus = new LatLng(32.406492852732576 , 35.34384525885372);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true);
        map.addMarker(new MarkerOptions().position(currentBus).title(""));

        CameraPosition target = CameraPosition.builder().target(currentBus).zoom(17).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(target));


    }
}
