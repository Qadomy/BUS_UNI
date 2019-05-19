package com.example.bus_uni.BusSchedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.R;

import java.util.ArrayList;


public class ResycleViewAdpter extends RecyclerView.Adapter<ResycleViewAdpter.ViewHolder> {


    private static final String Tag = "ResyclerViewAdapter";

    private ArrayList<Bus> buses = new ArrayList<>();

    private Context mContext;


    public ResycleViewAdpter(ArrayList<Bus> buses, Context mContext) {
        this.buses = buses;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.schedule_bus_list_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Log.d(Tag, "onBindViewHolder: caleld.");

        final Bus bus = buses.get(i);
        viewHolder.busLineName.setText(bus.getName());
        viewHolder.seatsNumbers.setText(String.valueOf(bus.getSeats_num()));
        viewHolder.leavingTime.setText(bus.getLeavingTime());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here I guss we make Intent to BusInformationCard
                Log.d(Tag, "onClicked: cliked on: "+ bus.getName());
                Toast.makeText(mContext, bus.getName() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        // here return how many items in the list
        if (buses == null)
            return 0;
        return buses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView busLineName, seatsNumbers, leavingTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            busLineName = itemView.findViewById(R.id.busLineName);
            seatsNumbers = itemView.findViewById(R.id.seatsNumber);
            leavingTime = itemView.findViewById(R.id.busLeavingTime);
            cardView = itemView.findViewById(R.id.cardViewParent);
        }
    }

}
