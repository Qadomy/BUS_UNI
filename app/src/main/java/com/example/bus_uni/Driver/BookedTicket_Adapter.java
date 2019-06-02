package com.example.bus_uni.Driver;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bus_uni.Booking.Book;
import com.example.bus_uni.Booking.ControlPassengers;
import com.example.bus_uni.BusSchedule.BusInformationsCard;
import com.example.bus_uni.R;

import java.util.ArrayList;

public class BookedTicket_Adapter extends RecyclerView.Adapter<BookedTicket_Adapter.ViewHolder> {

    private ArrayList<Book> booked = new ArrayList<>();
    private Context mContext;


    public BookedTicket_Adapter(ArrayList<Book> booked, Context mContext) {
        this.booked = booked;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.all_tickets_booked, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        viewHolder.userName.setText(booked.get(position).getUserName());
        viewHolder.seatNumber.setText(booked.get(position).getSeatNumber());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // send these data to Control Passengers activity
                Intent bookInfo = new Intent(mContext, ControlPassengers.class);

                bookInfo.putExtra("driverId", booked.get(position).getDriverID());
                bookInfo.putExtra("seatNum", booked.get(position).getSeatNumber());
                bookInfo.putExtra("userId", booked.get(position).getUserID());
                bookInfo.putExtra("userName", booked.get(position).getUserName());
                bookInfo.putExtra("userEmail", booked.get(position).getUserEmail());
                bookInfo.putExtra("userPhone", booked.get(position).getUserPhone());
                bookInfo.putExtra("city", booked.get(position).getCity());
                bookInfo.putExtra("rfid", booked.get(position).getRfidNumber());


                mContext.startActivity(bookInfo);

            }
        });


    }

    @Override
    public int getItemCount() {

        if (booked == null)
            return 0;
        return booked.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView userName, seatNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.ticketBookedCardView);
            userName = (TextView) itemView.findViewById(R.id.ticketBookedUserName);
            seatNumber = (TextView) itemView.findViewById(R.id.ticketBookedUserSeatNumber);
        }
    }
}
