package com.example.bus_uni.BusCompany;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bus_uni.R;
import com.example.bus_uni.Register.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DisplayDrivers_Adapter extends RecyclerView.Adapter<DisplayDrivers_Adapter.ViewHolder> {


    private ArrayList<User> drivers = new ArrayList<>();
    private Context mContext;


    public DisplayDrivers_Adapter(ArrayList<User> drivers, Context mContext) {
        this.drivers = drivers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.all_drivers_card, viewGroup, false);

        return new DisplayDrivers_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {


        viewHolder.driverName.setText(getDrivers().get(position).getName());
        viewHolder.driverPhone.setText(getDrivers().get(position).getMobile());
        viewHolder.driverEmail.setText(getDrivers().get(position).getEmail());
        viewHolder.driverBusLine.setText(getDrivers().get(position).getBus_line());
        viewHolder.driverBusNumber.setText(getDrivers().get(position).getBus_num());
        viewHolder.driverSeatNum.setText(getDrivers().get(position).getBus_seat());

        Picasso.with(mContext).load(drivers.get(position).getProfile_pic()).into(viewHolder.driverImage);


        // when click on call now in the driver card
        viewHolder.callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts("tel", getDrivers().get(position).getMobile(), null));
                mContext.startActivity(intent);
            }
        });

        // when click on send email in the driver card
        viewHolder.sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", getDrivers().get(position).getEmail(), null));
                emailIntent.putExtra(Intent.EXTRA_TEXT, "typeing.....");
                mContext.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        // when click on show on map button in driver card
        viewHolder.showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // passing info to show driver on map activity
                Intent driverInfo = new Intent(mContext, ShowDriversOnMap.class);

                driverInfo.putExtra("name", getDrivers().get(position).getName());
                driverInfo.putExtra("latitude", getDrivers().get(position).getLatitude());
                driverInfo.putExtra("longitude", getDrivers().get(position).getLongitude());

                mContext.startActivity(driverInfo);


            }
        });


    }

    @Override
    public int getItemCount() {

        // here return how many items in the list
        if (getDrivers() == null)
            return 0;
        return getDrivers().size();
    }


    public ArrayList<User> getDrivers() {
        return drivers;
    }

    public void setDrivers(ArrayList<User> drivers) {
        this.drivers = drivers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView driverName, driverPhone, driverEmail, driverBusLine, driverBusNumber, driverSeatNum,
                callNow, sendEmail;
        Button showOnMap;
        ImageView driverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            cardView = (CardView) itemView.findViewById(R.id.allDrivers_cardView);

            driverName = (TextView) itemView.findViewById(R.id.allDrivers_name);
            driverPhone = (TextView) itemView.findViewById(R.id.allDrivers_phone);
            driverEmail = (TextView) itemView.findViewById(R.id.allDrivers_email);
            driverBusLine = (TextView) itemView.findViewById(R.id.allDrivers_lineName);
            driverBusNumber = (TextView) itemView.findViewById(R.id.allDrivers_busNum);
            driverSeatNum = (TextView) itemView.findViewById(R.id.allDrivers_seatNum);

            callNow = (TextView) itemView.findViewById(R.id.allDrivers_callNow);
            sendEmail = (TextView)itemView.findViewById(R.id.allDrivers_sendEmail);

            showOnMap = (Button) itemView.findViewById(R.id.allDrivers_ShowMapButton);

            driverImage = (ImageView) itemView.findViewById(R.id.allDrivers_imageProfile);


        }
    }
}
