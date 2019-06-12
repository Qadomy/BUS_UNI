package com.example.bus_uni.Driver;

public class Ticket {


    private String driverId="";
    private String busLine="";
    private String company="";
    private String driverPhone="";
    private String latitude="";
    private String leavingTime="";
    private String longitude="";
    private String name="";
    private String price="";
    private String seatNum="";
    private String id="";
    private String busNum="";


    public Ticket() {

    }

    public Ticket(String driverId, String name, String line, String price, String time, String seat, String company, String phone, String latitude, String longitude, String id, String busNum) {

        this.driverId = driverId;
        this.busLine = line;
        this.company = company;
        this.driverPhone = phone;
        this.latitude = latitude;
        this.leavingTime = time;
        this.longitude = longitude;
        this.name = name;
        this.price = price;
        this.seatNum = seat;
        this.id = id;
        this.busNum = busNum;
    }


    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        price = price;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }
}
