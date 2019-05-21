package com.example.bus_uni;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ConnectWithUs extends AppCompatActivity {

//    public static String FACEBOOK_URL = "https://www.facebook.com/QadomyAli";
//    TextView signup;
//    ImageView backToMain;


    // Here for when click on Facebook icon and connect to Facebook applications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_with_us);


    }

//    public void facebookConnectClick(View view) {
//        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
//        String facebookUrl = getFacebookPageURL(this);
//        facebookIntent.setData(Uri.parse(facebookUrl));
//        startActivity(facebookIntent);
//    }
//
//    //method to get the right URL to use in the intent
//    public String getFacebookPageURL(Context context) {
//        PackageManager packageManager = context.getPackageManager();
//        try {
//
//            int versionCode = packageManager
//                    .getPackageInfo("com.facebook.katana", 0).versionCode;
//            if (versionCode >= 3002850) { //newer versions of fb app
//                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
//            } else { //older versions of fb app
//                return "fb://page/" + FACEBOOK_URL;
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            return FACEBOOK_URL; //normal web url
//        }
//    }
//
//    // For make a call from call icon button
//    public void callConnectClick(View view) {
//        Intent intent = new Intent(Intent.ACTION_DIAL,
//                Uri.fromParts("tel", "00972595466467", null));
//        startActivity(intent);
//    }
//
//    // For open Email and send email
//    public void emailConnectClick(View view) {
//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                "mailto", "ali.qadomy@hotmail.com", null));
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "typeing.....");
//        startActivity(Intent.createChooser(emailIntent, "Send email..."));
//    }
//
//
//    //For open LinkedIn
//    public void linkedConnetClick(View view) {
//        String linkedInPageUrl = "https://www.linkedin.com/in/ali-qadomy-a0556b110/";
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedInPageUrl));
//        startActivity(intent);
//    }
//
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.home) {
//            ActionBar actionBar = getActionBar();
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
