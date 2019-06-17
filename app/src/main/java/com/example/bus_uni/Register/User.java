package com.example.bus_uni.Register;

public class User {

    private double longitude;
    private double latitude;
    private String name = "";
    private String email = "";
    private String password;
    private String mobile = "";
    private String city = "";
    private String bus_num = "";// here for bus number
    private String bus_line = "";
    private String bus_seat = "";
    private String bus_company = ""; // here the name of company for each driver
    private String profile_pic = "https://firebasestorage.googleapis.com/v0/b/unibus-5f23b.appspot.com/o/upload%2Fprofile_images%2Fman.png?alt=media&token=cb3f7bb5-1104-4f81-b341-ef525aa0caa4";
    private int type; // 0: Student , 1: Company , 2: Driver
    private String line_price="";

    //Default Const. for retrieving User Obj from firebase
    public User() {

    }

    public User(double longitude, double latitude, String name, String email, String password,
                String mobile, String city, String bus_num, String bus_line, String bus_seat,
                String bus_company, String profile_pic, int type, String line_price) {

        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.city = city;
        this.bus_num = bus_num;
        this.bus_line = bus_line;
        this.bus_seat = bus_seat;
        this.bus_company = bus_company;
        this.profile_pic = profile_pic;
        this.type = type;
        this.line_price = line_price;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBus_num() {
        return bus_num;
    }

    public void setBus_num(String bus_num) {
        this.bus_num = bus_num;
    }

    public String getBus_line() {
        return bus_line;
    }

    public void setBus_line(String bus_line) {
        this.bus_line = bus_line;
    }

    public String getBus_seat() {
        return bus_seat;
    }

    public void setBus_seat(String bus_seat) {
        this.bus_seat = bus_seat;
    }

    public String getBus_company() {
        return bus_company;
    }

    public void setBus_company(String bus_company) {
        this.bus_company = bus_company;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLine_price() {
        return line_price;
    }

    public void setLine_price(String line_price) {
        this.line_price = line_price;
    }
}
