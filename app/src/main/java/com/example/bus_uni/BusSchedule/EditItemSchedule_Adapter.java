package com.example.bus_uni.BusSchedule;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bus_uni.Driver.Ticket;
import com.example.bus_uni.R;

import java.util.ArrayList;

public class EditItemSchedule_Adapter extends RecyclerView.Adapter<EditItemSchedule_Adapter.ViewHolder> {


    private ArrayList<Ticket> tickets = new ArrayList<>();
    private Context mContext;

    public interface EditTicketAdapterOnClickHandler {
        void onClick(Ticket ticket);
    }

    // constructor for EditItemSchedule_Adapter
    public EditItemSchedule_Adapter(ArrayList<Ticket> tickets, Context mContext) {
        this.tickets = tickets;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.schedule_bus_list_item, viewGroup, false);

        return new EditItemSchedule_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        viewHolder.busLineName.setText(tickets.get(position).getBusLine());
        viewHolder.seatsNumbers.setText(String.valueOf(tickets.get(position).getSeatNum()));
        viewHolder.leavingTime.setText(tickets.get(position).getLeavingTime());
        viewHolder.companyName.setText(tickets.get(position).getCompany());
        viewHolder.ticketPrice.setText(tickets.get(position).getPrice());


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // passing data to EditSingleScheduleItem activity to make edit on them
                Intent editTicket = new Intent(mContext, EditSingleScheduleItem.class);

                editTicket.putExtra("busLine", tickets.get(position).getBusLine());
                editTicket.putExtra("companyName", tickets.get(position).getCompany());
                editTicket.putExtra("driverName", tickets.get(position).getName());
                editTicket.putExtra("driverPhone", tickets.get(position).getDriverPhone());
                editTicket.putExtra("seatNum", tickets.get(position).getSeatNum());
                editTicket.putExtra("leavingTime", tickets.get(position).getLeavingTime());
                editTicket.putExtra("latitude", tickets.get(position).getLatitude());
                editTicket.putExtra("longitude", tickets.get(position).getLongitude());
                editTicket.putExtra("ticketPrice", tickets.get(position).getPrice());
                editTicket.putExtra("keyId", tickets.get(position).getId());
                editTicket.putExtra("driverId", tickets.get(position).getDriverId());
                editTicket.putExtra("busNum", tickets.get(position).getBusNum());
                editTicket.putExtra("expectedTime", tickets.get(position).getExpectedTime());

                mContext.startActivity(editTicket);
            }
        });
    }

    @Override
    public int getItemCount() {
        // here return how many items in the list
        if (tickets == null)
            return 0;
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView busLineName, seatsNumbers, leavingTime, companyName, ticketPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            busLineName = (TextView) itemView.findViewById(R.id.busLineName);
            seatsNumbers = (TextView) itemView.findViewById(R.id.seatsNumber);
            leavingTime = (TextView) itemView.findViewById(R.id.busLeavingTime);
            companyName = (TextView) itemView.findViewById(R.id.companyBusTicket);
            ticketPrice = (TextView)itemView.findViewById(R.id.busTicktPrice);
            cardView = (CardView) itemView.findViewById(R.id.cardViewParent);
        }
    }

}
