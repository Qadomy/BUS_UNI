package com.example.bus_uni.BusSchedule;

public class Bus {

    private String busLine = "";
    private String leavingTime = "";
    private int seats_num = 1;
    private String station_num = "";
    private String ticketPrice = "";


    public Bus() {
    }

    public Bus(String busLine, String leavingTime, int seats_num, String ticketPrice) {
        this.busLine = busLine;
        this.leavingTime = leavingTime;
        this.seats_num = seats_num;
        this.ticketPrice = ticketPrice;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
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

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

}
