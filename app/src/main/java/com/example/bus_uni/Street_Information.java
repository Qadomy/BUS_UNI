package com.example.bus_uni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.bus_uni.Register.Post;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Street_Information extends AppCompatActivity {


    private ImageView addPost;
//    private Toolbar homeToolbar;

    private BottomNavigationView mainBottomNav;
    private ArrayList<Post> posts=new ArrayList<>();


    // set fragments classes
    private Fragment_Home homeFragment;
    private Fragment_Account accountFragment;
    private Fragment_Notification notificationsFragment;


    // string for current user
    private String current_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street__community);

        addPost = findViewById(R.id.addPostIcon);

//        homeToolbar = (Toolbar) findViewById(R.id.home_toolbar);

        mainBottomNav = findViewById(R.id.mainBottomNav);

        Intent intent=getIntent();
        posts=intent.getParcelableArrayListExtra("List of posts");

        // init Fragments
        homeFragment = new Fragment_Home(this,posts);
        accountFragment = new Fragment_Account();
        notificationsFragment = new Fragment_Notification();

        //For first time by default, if the user didn't select yet
        replacingFragment(homeFragment);

        // init current user string
        //current_user = FirebaseAuth.getInstance().getCurrentUser().getUid();


        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addPost = new Intent(Street_Information.this, AddPost_Activity.class);
                startActivity(addPost);
            }
        });


        mainBottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.
                OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {


                //
                // here we make a switch for replace between fragments
                switch (menuItem.getItemId()) {

                    case R.id.bottom_action_home:
                        replacingFragment(homeFragment);
                        Toast.makeText(Street_Information.this, "home fragment", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.bottom_action_notif:
                        replacingFragment(notificationsFragment);
                        Toast.makeText(Street_Information.this, "notifications fragment", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.bottom_action_account:
                        replacingFragment(accountFragment);
                        Toast.makeText(Street_Information.this, "account Fragment", Toast.LENGTH_SHORT).show();
                        break;

               //     default:
                 //       Toast.makeText(Street_Information.this, "you decide error buutons", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    private void replacingFragment(Fragment fragment) {

        // here mothod for make replacing between three notifications in street
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();


    }
}
