package com.example.bus_uni.BusSchedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bus_uni.R;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private final CityAdapterOnClickHandler mClickHandler;
    private final Context context;
    private String[] cities;

    public CityAdapter(Context context, CityAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        this.context = context;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int id = R.layout.city_locations;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean attachImmediatelyToViewGroup = false;
        View view = inflater.inflate(id, viewGroup, attachImmediatelyToViewGroup);
        return new CityViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder CityViewHolder, int position) {
        String city = cities[position];
        CityViewHolder.CityName.setText(city);

    }

    @Override
    public int getItemCount() {
        if (cities == null)
            return 0;
        return cities.length;
    }

    public void setCitiesData(String[] cities) {
        this.cities = cities;
        notifyDataSetChanged(); //when data changed and add it to recicleView
    }

    public interface CityAdapterOnClickHandler {
        void onClick(String city);
    }

    public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView CityName;

        CityViewHolder(@NonNull View itemView) {
            super(itemView);
            CityName = itemView.findViewById(R.id.city_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String city = cities[adapterPosition];
            mClickHandler.onClick(city);
        }
    }
}