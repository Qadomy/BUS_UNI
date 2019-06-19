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


public class TicketAdpter extends RecyclerView.Adapter<TicketAdpter.ViewHolder> {


    private ArrayList<Ticket> tickets = new ArrayList<>();
    private Context mContext;


    // constructor for TicketAdapter
    public TicketAdpter(ArrayList<Ticket> tickets, Context mContext) {
        this.setTickets(tickets);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.schedule_bus_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {


        viewHolder.busLineName.setText(getTickets().get(position).getBusLine());
        viewHolder.seatsNumbers.setText(String.valueOf(getTickets().get(position).getSeatNum()));
        viewHolder.leavingTime.setText(getTickets().get(position).getLeavingTime());
        viewHolder.companyName.setText(getTickets().get(position).getCompany());
        viewHolder.ticketPrice.setText(getTickets().get(position).getPrice());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // here passing data to BusInformationCard
                Intent ticketInfo = new Intent(mContext, BusInformationsCard.class);

                ticketInfo.putExtra("busLine", getTickets().get(position).getBusLine());
                ticketInfo.putExtra("companyName", getTickets().get(position).getCompany());
                ticketInfo.putExtra("driverName", getTickets().get(position).getName());
                ticketInfo.putExtra("driverPhone", getTickets().get(position).getDriverPhone());
                ticketInfo.putExtra("seatNum", getTickets().get(position).getSeatNum());
                ticketInfo.putExtra("leavingTime", getTickets().get(position).getLeavingTime());
                ticketInfo.putExtra("latitude", getTickets().get(position).getLatitude());
                ticketInfo.putExtra("longitude", getTickets().get(position).getLongitude());
                ticketInfo.putExtra("driverId", getTickets().get(position).getDriverId());
                ticketInfo.putExtra("keyId", getTickets().get(position).getId());
                ticketInfo.putExtra("busNum", getTickets().get(position).getBusNum());
                ticketInfo.putExtra("expectedTime", getTickets().get(position).getExpectedTime());


                mContext.startActivity(ticketInfo);


            }
        });
    }

    @Override
    public int getItemCount() {
        // here return how many items in the list
        if (getTickets() == null)
            return 0;
        return getTickets().size();
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
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
            ticketPrice = (TextView) itemView.findViewById(R.id.busTicktPrice);
            cardView = (CardView) itemView.findViewById(R.id.cardViewParent);
        }
    }

}
