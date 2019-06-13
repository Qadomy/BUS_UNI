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

    String driverId;

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);


        Intent getFromBusCard = getIntent();
        driverId = getFromBusCard.getExtras().getString("driverId");


        Toast.makeText(this, driverId, Toast.LENGTH_SHORT).show();


//        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
//        mDatabaseReference.child(driverId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String latitude = dataSnapshot.child("latitude").getValue().toString();
//                String longitude = dataSnapshot.child("longitude").getValue().toString();
//
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

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
