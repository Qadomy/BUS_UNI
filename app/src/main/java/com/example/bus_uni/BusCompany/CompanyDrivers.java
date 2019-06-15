package com.example.bus_uni.BusCompany;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bus_uni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompanyDrivers extends AppCompatActivity {
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_drivers);


        //TODO: here we put all_drivers_card.xml in recycleView
        //List of drivers account,
        //We can put their emails or UIDs in list,
        //and when company selects 1 or more drivers (Maybe using Checkbox)
        // we sent them in a list to removeAccount method.

    }
    private void removeAccount(ArrayList<String> emails){
        for(int i=0;i<emails.size();i++)
        {
            //  UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
          /*  Query query = reference.child("Users").orderByChild("email").equalTo(emails.get(i));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/
        }

    }
}
