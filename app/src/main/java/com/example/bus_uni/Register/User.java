package com.example.bus_uni.Register;

public class User {

    private double longitude;
    private double latitude;
    private String name;
    private String email;
    private String password;
    private String rfid = "";
    private String mobile = "";
    private String city = "";
    private String bus_num = "";// here for bus number
    private String bus_line = "";
    private String bus_seat = "";
    private String bus_company = ""; // here the name of company for each driver
    private String buses_numbers = ""; // here for how company buses numbers have
    private String profile_pic = "https://firebasestorage.googleapis.com/v0/b/unibus-5f23b.appspot.com/o/upload%2Fprofile_images%2Fman.png?alt=media&token=cb3f7bb5-1104-4f81-b341-ef525aa0caa4";
    private int type; // 0: Student , 1: Company , 2: Driver

    //private ArrayList<Post> posts = new ArrayList<>();

    //Default Const. for retrieving User Obj from firebase
    public User() {

    }

    // here for longitude and latitude
    public User(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //For User sign up (first time)
    public User(String name, String email, String password, int type) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.type = type;

    }

    //For user Edit profile
    public User(String name, String email, String pass, String rfid, String mobile, String city, String profile_pic) {
        this.setRefid(rfid);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(pass);
        this.setMobile(mobile);
        this.setCity(city);
        this.setProfile_pic(profile_pic);
    }

    //For edit profile if the pass isn't updated
    public User(String name, String email, String rfid, String mobile, String city, String profile_pic) {
        this.setRefid(rfid);
        this.setName(name);
        this.setEmail(email);
        this.setMobile(mobile);
        this.setCity(city);
        this.setProfile_pic(profile_pic);
    }

    //For edit Company profile if there is a image profile picked
    public User(String name, String email, String mobile, String bus_num, String bus_line, String profile_pic, int type) {
        this.setName(name);
        this.setEmail(email);
        this.setMobile(mobile);
        this.setBus_num(bus_num);
        this.setBus_line(bus_line);
        this.setProfile_pic(profile_pic);
        this.type = type;

    }


    // For Driver edit profile
    public User(String name, String email, String mobile, String bus_seat, String bus_num, String bus_line, int type, String busCompany, String profile_pic) {
        this.setName(name);
        this.setEmail(email);
        this.setMobile(mobile);
        this.setBus_num(bus_num);
        this.setBus_line(bus_line);
        this.setBus_seat(bus_seat);
        this.type = type;
        this.setBus_company(busCompany);
        this.setProfile_pic(profile_pic);
    }

    // For Driver sign up (first time) from company activity (RegisterNewDriver Activity)
    public User(String name, String email, String pass, String mobile, String bus_seat, String bus_num, String bus_line, int type, String busCompany) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(pass);
        this.setMobile(mobile);
        this.setBus_num(bus_num);
        this.setBus_line(bus_line);
        this.setBus_seat(bus_seat);
        this.type = type;
        this.setBus_company(busCompany);
    }

    //////////////////////////////////////////////////////// end of constructors
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getRefid() {
        return rfid;
    }

    public void setRefid(String refid) {
        this.rfid = refid;
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

    /*public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
    */

    public int getType() {
        return type;
    }

    public String getBus_line() {
        return bus_line;
    }

    public void setBus_line(String bus_line) {
        this.bus_line = bus_line;
    }

    public String getBus_num() {
        return bus_num;
    }

    public void setBus_num(String bus_num) {
        this.bus_num = bus_num;
    }


    public void addPost(Post post) {
        //     posts.add(post);
    }

    public String getBuses_numbers() {
        return buses_numbers;
    }

    public void setBuses_numbers(String buses_numbers) {
        this.buses_numbers = buses_numbers;
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

    ////////
    ////

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


    //No need for this, we initialize type once when register "constructor"
   /* public void setType(int type) {
        this.type = type;
    }*/
}
