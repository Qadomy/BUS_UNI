package com.example.bus_uni.Driver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bus_uni.R;

public class DriverLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_location);
    }
}
//        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            finish();
//        }
//
////Check whether this app has access to the location permission//
//
//        int permission = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//
////If the location permission has been granted, then start the TrackerService//
//
//        if (permission == PackageManager.PERMISSION_GRANTED) {
//            startTrackerService();
//        }
//    }
//    private void startTrackerService(){
//        startService(new Intent(this, TrackingService.class));
//
////Notify the user that tracking has been enabled//
//
//        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();
//
////Close MainActivity//
//
//        //finish();
//    }
//
//}
