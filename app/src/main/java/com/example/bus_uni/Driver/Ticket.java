package com.example.bus_uni.Driver;

public class Ticket {


    private String busLine;
    private String Price;
    private String leavingTime;
    private String seatNum = "";

    public Ticket() {

    }

    public Ticket(String busLine, String price, String leavingTime) {
        this.busLine = busLine;
        Price = price;
        this.leavingTime = leavingTime;
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

    public String getleavingTime() {
        return leavingTime;
    }

    public void setleavingTime(String time) {
        this.leavingTime = time;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }
}
