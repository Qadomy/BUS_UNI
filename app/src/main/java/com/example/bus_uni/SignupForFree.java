package com.example.bus_uni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.bus_uni.BusCompany.CompanyHome;
import com.example.bus_uni.BusSchedule.Bus_Schedule;
import com.example.bus_uni.Register.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupForFree extends AppCompatActivity {


    TextView signIn, tryAsGuest;


    // Firebase
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_for_free);

        signIn = (TextView) findViewById(R.id.signIn);
        tryAsGuest = (TextView) findViewById(R.id.tryAsGuest);



        // init firebase auth
        mFirebaseAuth = FirebaseAuth.getInstance();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(SignupForFree.this, LoginUserActivity.class);
                startActivity(signIn);
            }
        });

        tryAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backHome = new Intent(SignupForFree.this, Bus_Schedule.class);
                startActivity(backHome);
            }
        });




    }
    ////////////////////////////////////////////////////////


    // here when open the app, check if the app logged in from user just take him to Main Activity
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            sendToMain();
        }
    }



    private void sendToMain() {
        Intent sendToMain = new Intent(SignupForFree.this, Home.class);
        startActivity(sendToMain);
        finish();
    }


    public void signUpForFree(View view) {
        Intent signupforfree = new Intent(SignupForFree.this, SignupActivity.class);
        startActivity(signupforfree);
    }


}
