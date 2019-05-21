package com.example.bus_uni.Driver;

public class Ticket {


    private String name;
    private String busLine;
    private String Price;
    private String leavingTime;
    private String seatNum;
    private String company;
    private String driverPhone;

    public Ticket() {

    }

    public Ticket(String name, String busLine, String price, String leavingTime, String seatNum, String company, String driverPhone) {

        this.name = name;
        this.busLine = busLine;
        Price = price;
        this.leavingTime = leavingTime;
        this.seatNum = seatNum;
        this.company = company;
        this.driverPhone = driverPhone;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }
}
