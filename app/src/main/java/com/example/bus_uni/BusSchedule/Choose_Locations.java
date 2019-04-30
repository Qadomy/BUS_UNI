package com.example.bus_uni.BusSchedule;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bus_uni.R;

public class Choose_Locations extends AppCompatActivity implements CityAdapter.CityAdapterOnClickHandler {


    public static String CITY_EXTRA = "City";

    private RecyclerView mRecyclerView;
    private CityAdapter mCityAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    private String[] cities;

    //private final String[] cities = new String[]{"Ramallah","Tulkarm","Nablus","Jenin","Ya'bad","Ramallah","Tulkarm","Nablus","Jenin","Ya'bad"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__locations);


        mRecyclerView = findViewById(R.id.cities_rv);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicatorLocations);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_displayLocations);
        cities = getResources().getStringArray(R.array.cities_names);

        showRecyclerView();


    }

    private void showRecyclerView() {
        // if (findViewById(R.id.tablet) == null)
        //gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        //{
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(this, 7, GridLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(gridLayoutManager);

    /*    } else {
            gridLayoutManager = new GridLayoutManager(this, 3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
    */
        mRecyclerView.setHasFixedSize(true);
        mCityAdapter = new CityAdapter(Choose_Locations.this, this);
        mRecyclerView.setAdapter(mCityAdapter);
        mCityAdapter.setCitiesData(cities);
        showCities();
    }

    private void showCities() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String city) {
        Intent intent = new Intent(this, Bus_Schedule.class);
        intent.putExtra(CITY_EXTRA, city);
        setResult(RESULT_OK, intent);
        finish();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void chooseRecent(View view) {
        int id = view.getId();
        TextView recent = findViewById(id);
        Intent intent = new Intent(this, Bus_Schedule.class);
        intent.putExtra(CITY_EXTRA, recent.getText());
        setResult(RESULT_OK, intent);
        finish();

    }
}
