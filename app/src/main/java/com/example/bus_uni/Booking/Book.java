package com.example.bus_uni.Booking;

public class Book {


    private String userID = "";
    private String userName = "";
    private String driverID = "";
    private String driverName = "";
    private String driverPhone = "";
    private String busLine = "";
    private String leavingTime = "";
    private String latitude = "";
    private String longitude = "";
    private String seatNumber = "";
    private String rfidNumber = "";
    private String company = "";
    private String city = "";
    private String busNum = "";
    private String userPhone = "";
    private String userEmail = "";
    private String costPaid="";


    public Book() {
    }

    public Book(String userID, String userName, String driverID, String driverName, String driverPhone,
                String busLine, String leavingTime, String latitude, String longitude, String seatNumber,
                String rfidNumber, String company, String city, String busNum, String userPhone,
                String userEmail, String costPaid) {

        this.userID = userID;
        this.userName = userName;
        this.driverID = driverID;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.busLine = busLine;
        this.leavingTime = leavingTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.seatNumber = seatNumber;
        this.rfidNumber = rfidNumber;
        this.company = company;
        this.city = city;
        this.busNum = busNum;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.costPaid = costPaid;
    }

    public Book(String userName, String seatNumber) {
        this.userName = userName;
        this.seatNumber = seatNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(String leavingTime) {
        this.leavingTime = leavingTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getRfidNumber() {
        return rfidNumber;
    }

    public void setRfidNumber(String rfidNumber) {
        this.rfidNumber = rfidNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCostPaid() {
        return costPaid;
    }

    public void setCostPaid(String costPaid) {
        this.costPaid = costPaid;
    }
}




