package com.example.bus_uni;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.bus_uni.BusCompany.CompanyHome;
import com.example.bus_uni.Driver.DriverHome;
import com.example.bus_uni.Driver.MapsActivity;
import com.example.bus_uni.Register.LoginUserActivity;
import com.example.bus_uni.Register.SignupForFree;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_Screen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    // Firebase
    private DatabaseReference databaseReference;
    private FirebaseAuth mFirebaseAuth;
    String currentuser ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        //Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

        mFirebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

                if (currentUser == null) {

                    // here if there is no user already sign in
                    Intent homeIntent = new Intent(Splash_Screen.this, SignupForFree.class);
                    startActivity(homeIntent);
                    finish();

                } else {

                     currentuser = mFirebaseAuth.getCurrentUser().getUid();
                  //  Toast.makeText(Splash_Screen.this, currentuser, Toast.LENGTH_SHORT).show();

                    // init database reference
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");

                    databaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            checkType();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        }, SPLASH_TIME_OUT);
    }// end of onCreate


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    /// method for check the type of user and send for right activity
    private void checkType() {

      //  String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference user= databaseReference.child(currentuser);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               int type=dataSnapshot.child("type").getValue(Integer.class);

                if (type == 0) {

                    Intent signInSuccessfully = new Intent(Splash_Screen.this, Home.class);
                    startActivity(signInSuccessfully);

                } else if (type == 1) {


                    Intent signInSuccessfully = new Intent(Splash_Screen.this, CompanyHome.class);
                    startActivity(signInSuccessfully);

                } else if (type == 2) {

                    Intent signInSuccessfully = new Intent(Splash_Screen.this, MapsActivity.class);
                    startActivity(signInSuccessfully);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
