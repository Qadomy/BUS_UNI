package com.example.bus_uni.StreetsInformation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StreetInformation extends AppCompatActivity {


    RecyclerView postListRecycleView;
    FloatingActionButton addNewPost;


    // for recycle view and TicketAdapter
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private ArrayList<Post> posts = new ArrayList<>();


    /*
    *
    * */
    String userName;


    // get the current user id
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


    // firebase databaseReference
    DatabaseReference mUserDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_information);


        postListRecycleView = (RecyclerView) findViewById(R.id.postsListRecycleView);
        addNewPost = (FloatingActionButton) findViewById(R.id.addNewPostButton);


        // init database realtime and get the data
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mUserDatabaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userName = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // when click on button add new post
        addNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popAddNewPost();
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.postsListRecycleView);
        showPostsInRecycleView();
    }


    // method for pop an form and add new post
    private void popAddNewPost() {

        // get activity_add_new_bus_date.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.add_new_post, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set activity_add_new_bus_date.xml to alert dialog builder
        alertDialogBuilder.setView(promptsView);


        ImageButton newPostImage = (ImageButton)promptsView.findViewById(R.id.addPostImage);
        TextView newPostText = (TextView)promptsView.findViewById(R.id.addPostText);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {



                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    private void showPostsInRecycleView() {
    }
}
