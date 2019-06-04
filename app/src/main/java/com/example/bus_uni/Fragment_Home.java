package com.example.bus_uni;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bus_uni.Register.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment implements PostAdapter2.PostAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private PostAdapter2 mPostAdapter2;
    private TextView mErrorMessageDisplay;
    private ArrayList<Post> posts = new ArrayList<>();
    private ProgressBar mLoadingIndicator;
    private LinearLayoutManager linearLayoutManager;
    private Context context;
    private DatabaseReference mDatabaseReference;


    @SuppressLint("ValidFragment")
    public Fragment_Home(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_fragment__home, container, false);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

    /*    mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> users=dataSnapshot.getChildren();
                for(DataSnapshot user: users)
                {
                    DataSnapshot posts_child=user.child("posts");
                   Iterable<DataSnapshot> user_posts=posts_child.getChildren();
                   for(DataSnapshot p:user_posts)
                   {
                       Post post=p.getValue(Post.class);
                         posts.add(post);
                   }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        mRecyclerView = rootView.findViewById(R.id.posts_rv);
        // mLoadingIndicator = rootView.findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = rootView.findViewById(R.id.tv_error_message_display);
        mPostAdapter2 = new PostAdapter2(context, this);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mPostAdapter2);
        mPostAdapter2.setPostsData(posts);
        showPosts();
        return rootView;
    }


    private void showPosts() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Post post) {

        //If we need to do something when clicking on any post
    }


}
