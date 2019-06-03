package com.example.bus_uni.Booking;

public class BlackList {

    private String userId = "";
    private String userName = "";
    private String userEmail = "";
    private String userPhone = "";
    private String rfidNum = "";
    private String city = "";
    private String driverId = "";

    public BlackList() {
    }

    public BlackList(String userId, String userName, String userEmail, String userPhone, String rfidNum, String city, String driverId) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.rfidNum = rfidNum;
        this.city = city;
        this.driverId = driverId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRfidNum() {
        return rfidNum;
    }

    public void setRfidNum(String rfidNum) {
        this.rfidNum = rfidNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
