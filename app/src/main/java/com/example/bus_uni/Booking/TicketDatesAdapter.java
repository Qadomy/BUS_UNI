package com.example.bus_uni.Booking;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bus_uni.BusSchedule.BusInformationsCard;
import com.example.bus_uni.Driver.DetailedBookings;
import com.example.bus_uni.Driver.Ticket;
import com.example.bus_uni.R;

import java.util.ArrayList;


public class TicketDatesAdapter extends RecyclerView.Adapter<TicketDatesAdapter.ViewHolder> {


    private ArrayList<String> ticketDates = new ArrayList<>();
    private Context mContext;

    // constructor for TicketAdapter
    public TicketDatesAdapter(ArrayList<String> ticketDates, Context mContext) {
        this.setTickets(ticketDates);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TicketDatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.ticket_date_item, viewGroup, false);

        return new TicketDatesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketDatesAdapter.ViewHolder viewHolder, final int position) {


        viewHolder.leavingTime.setText(getTickets().get(position));
        viewHolder.leavingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DetailedBookings.class);
                intent.putExtra("Leaving Time",ticketDates.get(position));
                mContext.startActivity(intent);
            }
        });

        /*viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    @Override
    public int getItemCount() {
        // here return how many items in the list
        if (getTickets() == null)
            return 0;
        return getTickets().size();
    }

    public ArrayList<String> getTickets() {
        return ticketDates;
    }

    public void setTickets(ArrayList<String> tickets) {
        this.ticketDates = tickets;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        //CardView cardView;
        TextView leavingTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            leavingTime =  itemView.findViewById(R.id.ticket_leaving);
//            cardView = (CardView) itemView.findViewById(R.id.cardViewParent);
        }
    }

}
