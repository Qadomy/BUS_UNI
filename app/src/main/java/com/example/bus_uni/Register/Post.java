package com.example.bus_uni.Register;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Post implements Parcelable {

    private String text="This is a post";
    private String image_url="";
    private String date= Calendar.getInstance().getTime().toString();
    private String user_name="";
    private String user_image="https://firebasestorage.googleapis.com/v0/b/unibus-5f23b.appspot.com/o/upload%2Fprofile_images%2Fman.png?alt=media&token=cb3f7bb5-1104-4f81-b341-ef525aa0caa4";
    //private String image_url="https://firebasestorage.googleapis.com/v0/b/unibus-5f23b.appspot.com/o/upload%2Fprofile_images%2Fman.png?alt=media&token=cb3f7bb5-1104-4f81-b341-ef525aa0caa4";
    private boolean seen; //For later
    public static int id=0;

    public Post(){

    }
    public Post(String text, String image_url,String user_name,String date,String user_image) {
        this.setText(text);
        this.setImage_url(image_url);
        this.setUser_name(user_name);
        this.setDate(date);
        this.setUser_image(user_image);
        id++;
    }

    protected Post(Parcel in) {
        text = in.readString();
        image_url = in.readString();
        date = in.readString();
        user_name = in.readString();
        seen = in.readByte() != 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(image_url);
        dest.writeString(date);
        dest.writeString(user_name);
        dest.writeByte((byte) (seen ? 1 : 0));
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
