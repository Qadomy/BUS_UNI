package com.example.bus_uni.StreetsInformation;

import java.util.Calendar;

public class Post {

    private String userName = "";
    private String userImage = "https://firebasestorage.googleapis.com/v0/b/unibus-5f23b.appspot.com/o/upload%2Fprofile_images%2Fman.png?alt=media&token=cb3f7bb5-1104-4f81-b341-ef525aa0caa4";
    private String postDate = Calendar.getInstance().getTime().toString();
    private String postText = "";


    public Post() {
    }

    public Post(String userName, String userImage, String postDate, String postText) {
        this.userName = userName;
        this.userImage = userImage;
        this.postDate = postDate;
        this.postText = postText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
