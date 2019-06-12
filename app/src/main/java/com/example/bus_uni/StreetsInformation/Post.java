package com.example.bus_uni.StreetsInformation;

public class Post {

    private String userName = "";
    private String userImage = "https://firebasestorage.googleapis.com/v0/b/unibus-5f23b.appspot.com/o/upload%2Fprofile_images%2Fman.png?alt=media&token=cb3f7bb5-1104-4f81-b341-ef525aa0caa4";
    private String postDate = "";
    private String postText = "";
    private String postImage = "https://firebasestorage.googleapis.com/v0/b/unibus-5f23b.appspot.com/o/upload%2Fpost_photo%2FScreen%20Shot%202019-06-04%20at%208.51.14%20PM.png?alt=media&token=7ff577aa-6f3a-437c-8096-9fe90e732f47";


    public Post() {
    }

    public Post(String userName, String userImage, String postDate, String postText, String postImage) {
        this.userName = userName;
        this.userImage = userImage;
        this.postDate = postDate;
        this.postText = postText;
        this.postImage = postImage;
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

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }
}
