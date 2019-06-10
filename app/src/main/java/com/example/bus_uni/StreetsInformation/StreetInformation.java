package com.example.bus_uni.StreetsInformation;

import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_uni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

public class StreetInformation extends AppCompatActivity {

    static int PReqCode = 1;
    static int REQUESCODE = 1;
    RecyclerView postListRecycleView;
    FloatingActionButton addNewPost;

    /*
     *
     * */
    String userName, userImage, downloadUri, postText;
    // get the current user id
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


    // firebase databaseReference
    DatabaseReference mUserDatabaseReference, mPostDatabaseReference, mShowPostDatabaseReference;

    //
    //
    private Uri pickedImageUri;
    private ImageView newPostImage;
    private TextView newPostText;


    // for recycle view and TicketAdapter
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private ArrayList<Post> posts = new ArrayList<>();


    // firebase storageReference
    private StorageReference mStorageReference;

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
                userImage = dataSnapshot.child("profile_pic").getValue().toString();
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


        // init ImageButton and TextView on pop form add new post
        newPostImage = (ImageView) promptsView.findViewById(R.id.addPostImage);
        newPostText = (TextView) promptsView.findViewById(R.id.addPostText);


        // when click on imageButton to add a new image
        newPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22) {

                    checkAndRequestForPermission();
                } else {

                    openGallery();
                }
            }
        });


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {


                                uploadImage();


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

    private void uploadImage() {

        // get the text from EditText from add new post
        postText = newPostText.getText().toString();


        if (pickedImageUri != null) {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // init Storage Reference
            mStorageReference = FirebaseStorage.getInstance().getReference("upload");
            final StorageReference image_path = mStorageReference.child("post_photo")
                    .child(currentUser + ".jpg");

            image_path.putFile(pickedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful()) {

                        // here for get the image url and save it in String
                        image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                progressDialog.dismiss();
                                downloadUri = uri.toString();

                                // send to method for save data in database firebase
                                savePostInDatabase(userName, userImage, postText, downloadUri);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                progressDialog.dismiss();
                                Toast.makeText(StreetInformation.this, "Faild" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Toast.makeText(StreetInformation.this, "The Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(StreetInformation.this, "Photo falid saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {

            // default image when there is no image in post
            String nonImage = "https://firebasestorage.googleapis.com/v0/b/unibus-5f23b.appspot.com/o/upload%2Fpost_photo%2FScreen%20Shot%202019-06-04%20at%208.51.14%20PM.png?alt=media&token=7ff577aa-6f3a-437c-8096-9fe90e732f47";
            savePostInDatabase(userName, userImage, postText, nonImage);
        }


    }

    // method for save the data of new post in firebase database reference
    private void savePostInDatabase(String userName, String userImage, String postText, String downloadUri) {

        /*
         *
         * get the current time (Hour and Minute)
         *
         * */
        Calendar calendar = Calendar.getInstance();
        int getHour = calendar.get(Calendar.HOUR_OF_DAY);
        int getMinute = calendar.get(Calendar.MINUTE);
        int getYear = calendar.get(Calendar.YEAR);
        int getMonth = calendar.get(Calendar.MONTH);
        int getDay = calendar.get(Calendar.DAY_OF_MONTH);

        String getClock;
        if (getHour > 12) {
            getClock = getHour - 12 + ":" + getMinute + "PM";
        } else {
            getClock = getHour + ":" + getMinute + "AM";
        }

        String postTime = getYear + "/" + getMonth + "/" + getDay + " - " + getClock;

        Post post = new Post(userName, userImage, postTime, postText, downloadUri);

        mPostDatabaseReference = FirebaseDatabase.getInstance().getReference("Post");
        mPostDatabaseReference.child(currentUser).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(StreetInformation.this, "Your data saved successfully in database", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StreetInformation.this, "failed saved in database", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // method for display the posts in the list of recycle view
    private void showPostsInRecycleView() {

        mShowPostDatabaseReference = FirebaseDatabase.getInstance().getReference("Post");
        mShowPostDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    //TODO: sorting post by date, duplicated posts
                    Post post = childSnapshot.getValue(Post.class);
                    posts.add(post);


                }
                showRecycleView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showRecycleView() {

        // here how we want to display the list of tickets
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mPostAdapter = new PostAdapter(posts, StreetInformation.this);
        mRecyclerView.setAdapter(mPostAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);

    }


    // make a check if user accept the permission or no
    // and if user reject the permission there a toast message
    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(StreetInformation.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(StreetInformation.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(StreetInformation.this, getString(R.string.please_accept_permissions),
                        Toast.LENGTH_LONG).show();

            } else {


                ActivityCompat.requestPermissions(StreetInformation.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }

        } else {
            openGallery();
        }


    }

    // open the gallery of photos in device after accept the permission of read from external storage
    private void openGallery() {
        // here open gallaery intent and wait the user pick a photo
        Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, REQUESCODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {

            // the user has successfully picked an image
            // we need to save its refernce to a Uri variable
            pickedImageUri = data.getData();
            newPostImage.setImageURI(pickedImageUri);

        }
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
