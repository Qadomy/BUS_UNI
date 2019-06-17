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

public class ConnectWithUs extends AppCompatActivity {

    public static String QADOMY_FACEBOOK_URL = "https://www.facebook.com/QadomyAli";
    public static String ABUSHOMER_FACEBOOK_URL = "https://www.facebook.com/QadomyAli";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_with_us);


    }


    // to open Ali Qadomy Facebook
    public void QadomyFacebookConnect(View view) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getQadomyFacebookPageURL(this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }

    //method to get the right URL to use in the intent
    public String getQadomyFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {

            int versionCode = packageManager
                    .getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + QADOMY_FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + QADOMY_FACEBOOK_URL;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return QADOMY_FACEBOOK_URL; //normal web url
        }
    }



    // to open Ala` Abushomer Facebook
    public void AbuShomerFacebookConnect(View view) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getQadomyFacebookPageURL(this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }

    //method to get the right URL to use in the intent
    public String getAbuShomerFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {

            int versionCode = packageManager
                    .getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + ABUSHOMER_FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + ABUSHOMER_FACEBOOK_URL;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return ABUSHOMER_FACEBOOK_URL; //normal web url
        }
    }


    // For make a Ali Qadomy call from call icon button
    public void QadomyCallPhone(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.fromParts("tel", "00972595466467", null));
        startActivity(intent);
    }

    // For make a Ala Abushomer call from call icon button
    public void AbuShomerCallPhone(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.fromParts("tel", "00972595466467", null));
        startActivity(intent);
    }

    // To send email to Ali Qadomy
    public void QadomyEmailConnect(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "ali.qadomy@hotmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "typeing.....");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    // To send email to Ala` Abushomer
    public void AbuShomerEmailConnect(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "roroalhobroro@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "typeing.....");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    // For open Ala` Abushomer LinkedIn
    public void AbuShomerlinkedInConnect(View view) {
        String linkedInPageUrl = "https://www.linkedin.com/in/ala-%E2%80%8B-abu-shomer/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedInPageUrl));
        startActivity(intent);
    }

    //For open Ali Qadomy LinkedIn
    public void QadomylinkedInConnect(View view) {
        String linkedInPageUrl = "https://www.linkedin.com/in/ali-qadomy-a0556b110/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedInPageUrl));
        startActivity(intent);
    }


    // for back
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
