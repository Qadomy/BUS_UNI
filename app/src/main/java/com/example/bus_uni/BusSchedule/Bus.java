package com.example.bus_uni.BusSchedule;

public class Bus {
    private int seats_num = 50;
    private String name = "bus bus bus";
    private String leavingTime = "12:12";
    private String station_num = "123456";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats_num() {
        return seats_num;
    }

    public void setSeats_num(int seats_num) {
        this.seats_num = seats_num;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(String leavingTime) {
        this.leavingTime = leavingTime;
    }

    public String getStation_num() {
        return station_num;
    }

    public void setStation_num(String station_num) {
        this.station_num = station_num;
    }
}
