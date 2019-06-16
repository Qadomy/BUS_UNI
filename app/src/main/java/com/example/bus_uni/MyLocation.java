package com.example.bus_uni;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DatabaseReference;

public class MyLocation extends AppCompatActivity {

    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);


    }


    // for back
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
