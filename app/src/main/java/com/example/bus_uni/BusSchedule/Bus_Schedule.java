package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bus_uni.R;

import java.util.ArrayList;

public class Bus_Schedule extends AppCompatActivity implements BusAdapter.BusAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private BusAdapter mBusAdapter;
    private TextView mErrorMessageDisplay;
    private TextView origin, destination;
    private ArrayList<Bus> buses = new ArrayList<>();

    private ProgressBar mLoadingIndicator;
    // private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus__schedule);


        mRecyclerView = findViewById(R.id.buses_rv);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicatorSchedule);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_displaySchedule);

        origin = findViewById(R.id.origin);
        destination = findViewById(R.id.destination);


        Bus bus1 = new Bus();
        Bus bus2 = new Bus();
        Bus bus3 = new Bus();

        buses.add(bus1);
        buses.add(bus2);
        buses.add(bus3);

        showRecyclerView();

    }

    private void showRecyclerView() {
        // if (findViewById(R.id.tablet) == null)
        //gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        //{
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

    /*    } else {
            gridLayoutManager = new GridLayoutManager(this, 3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
    */
        mRecyclerView.setHasFixedSize(true);
        mBusAdapter = new BusAdapter(Bus_Schedule.this, this);
        mRecyclerView.setAdapter(mBusAdapter);
        mBusAdapter.setBusesData(buses);
        showBuses();
    }

    private void showBuses() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Bus bus) {
        // Intent intent = new Intent(this, DetailsActivity.class);
        //intent.putExtra("Recipe", recipe);
        // startActivity(intent);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
            origin.setText(data.getStringExtra(Choose_Locations.CITY_EXTRA));
        else if (requestCode == 2 && resultCode == RESULT_OK)
            destination.setText(data.getStringExtra(Choose_Locations.CITY_EXTRA));

    }


    public void chooseSource(View view) {
        Intent intent = new Intent(this, Choose_Locations.class);
        startActivityForResult(intent, 1);

    }

    public void chooseDestination(View view) {
        Intent intent = new Intent(this, Choose_Locations.class);
        startActivityForResult(intent, 2);
    }

}

