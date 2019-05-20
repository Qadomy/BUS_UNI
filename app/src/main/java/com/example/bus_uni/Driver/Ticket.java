package com.example.bus_uni.Driver;

public class Ticket {


    private String name;
    private String busLine;
    private String Price;
    private String leavingTime;
    private String seatNum;

    public Ticket() {

    }

    public Ticket(String name, String busLine, String price, String leavingTime, String seatNum) {

        this.name = name;
        this.busLine = busLine;
        Price = price;
        this.leavingTime = leavingTime;
        this.seatNum = seatNum;
    }


    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(String leavingTime) {
        this.leavingTime = leavingTime;
    }
}
