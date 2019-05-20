package com.example.bus_uni.BusSchedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bus_uni.R;

import java.util.ArrayList;
//Bus == BusLine?
public class  BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private ArrayList<Bus> buses=new ArrayList<>();

    private final BusAdapterOnClickHandler mClickHandler;
    private final Context context;

    public interface BusAdapterOnClickHandler {
        void onClick(Bus bus);
    }

    // constructor
    public BusAdapter(Context context, BusAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        this.context = context;
    }


    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int id = R.layout.schedule_bus_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean attachImmediatelyToViewGroup = false;
        View view = inflater.inflate(id, viewGroup, attachImmediatelyToViewGroup);
        return new BusViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder busViewHolder, int position) {
        Bus bus = buses.get(position);
        busViewHolder.busName.setText(bus.getBusLine());
        busViewHolder.seats_num.setText(String.valueOf(bus.getSeats_num()));
        busViewHolder.leaving_time.setText(bus.getLeavingTime());
        busViewHolder.ticketPrice.setText(bus.getTicketPrice());

    }

    @Override
    public int getItemCount() {
        if (buses == null)
            return 0;
        return buses.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView busName,seats_num,leaving_time, ticketPrice;

        BusViewHolder(@NonNull View itemView) {
            super(itemView);
            busName = itemView.findViewById(R.id.busLineName);
            seats_num=itemView.findViewById(R.id.seatsNumber);
            leaving_time=itemView.findViewById(R.id.busLeavingTime);
            ticketPrice=itemView.findViewById(R.id.busTicktPrice);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Bus bus = buses.get(adapterPosition);
            mClickHandler.onClick(bus);
        }
    }

    public void setBusesData(ArrayList<Bus> buses) {
        this.buses = buses;
        notifyDataSetChanged();
    }
}