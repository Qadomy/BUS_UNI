package com.example.bus_uni;

import android.Manifest;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bus_uni.Register.User;
import com.example.bus_uni.Register.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
import java.util.HashMap;

public class AddPost_Activity extends AppCompatActivity {

    static int PReqCode = 1;
    static int REQUESCODE = 1;
   // int postNo;
    String currentUser;
    private ProgressBar newPostProgressBar;

    private ImageButton newPostImage;
    private EditText newPostText;
    private Button newPostButton;
    private Uri pickedImageUri = null;
    // firebase storage
    private StorageReference mStorageReference;


    // firebase: database reference
    private DatabaseReference mDatabaseReference;

    private ArrayList<Post> posts=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        newPostImage = findViewById(R.id.addPostImageButton);
        newPostText = findViewById(R.id.addPostEditText);
        newPostButton = findViewById(R.id.addPostButton);
        newPostProgressBar = (ProgressBar) findViewById(R.id.addPostProgressBar);


        // init storage reference
        mStorageReference = FirebaseStorage.getInstance().getReference("upload");


        // init current user string and give him a id for the current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // when we press on image button to add new image form gallery
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

        // when press on add post button
        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPostButton.setVisibility(View.INVISIBLE);
                //TODO check here the exception
                //newPostProgressBar.setVisibility(View.VISIBLE);


                // get the text from edit text
                final String text = newPostText.getText().toString();

                if (text.isEmpty() && pickedImageUri == null) {
                    /*
                     * in this case there is no text and no image in field
                     * */

                    // here show message dialog if there a field empty
                    showMessageDialog(getString(R.string.errorMessage), getString(R.string.addPhoto_or_text),
                            R.drawable.ic_error_red_color_30dp);

                    newPostButton.setVisibility(View.VISIBLE);
                    //newPostProgressBar.setVisibility(View.INVISIBLE);


                } else if (text.isEmpty() && pickedImageUri != null) {
                    /*
                     * in this case if text empty but there uploaded photo
                     * */
                    final StorageReference file_path = mStorageReference.child("Posts_images")
                            .child(currentUser + ".jpg");
                    //final StorageReference file_path = mStorageReference.child("Posts_images").child(currentUser + postNo + ".jpg");
                    file_path.putFile(pickedImageUri).addOnCompleteListener(new OnCompleteListener
                            <UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                            if (task.isSuccessful()) {

//                                String msg = getString(R.string.imageUploadSuccessfully);
//                                Toast.makeText(AddPost_Activity.this, msg, Toast.LENGTH_SHORT).show();

                                file_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        /*
                                         * here after successfully uploaded photo we get the url to send it to database
                                         * */
                                        String downloadUri = uri.toString();

                                        addNewPost(downloadUri, text);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors

                                        Toast.makeText(AddPost_Activity.this, "errrrroooorrrrr", Toast.LENGTH_SHORT).show();


                                    }

                                });


                                finish();

                            } else {

                                // TODO: here in postError make check in string file what exactly write
                                showMessageDialog(getString(R.string.postError), task.getException().getMessage(), R.drawable.ic_error_red_color_30dp);

                            }
                        }
                    });

                } else if (!text.isEmpty() && pickedImageUri == null) {
                    /*
                     * in this case if there a text and no image uploaded
                     * */

                    String downloadUrl = "";

                    addNewPost(downloadUrl, text);

                } else {

                    //in this case if there a text and image uploaded


                    final StorageReference file_path = mStorageReference.child("Posts_images")
                            .child(currentUser + ".jpg");

                    //final StorageReference file_path = mStorageReference.child("Posts_images").child(currentUser + postNo + ".jpg");

                    file_path.putFile(pickedImageUri).addOnCompleteListener(new OnCompleteListener
                            <UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {

//                                String msg = getString(R.string.imageUploadSuccessfully);
//                                Toast.makeText(AddPost_Activity.this, msg, Toast.LENGTH_SHORT).show();

                                file_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        /*
                                         * here after successfully uploaded photo we get the url to send it to database
                                         * */
                                        String downloadUri = uri.toString();

                                        addNewPost(downloadUri, text);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors

                                        Toast.makeText(AddPost_Activity.this, "errrrroooorrrrr", Toast.LENGTH_SHORT).show();


                                    }

                                });


                                finish();

                            } else {

                                // TODO: here in postError make check in string file what exactly write
                                showMessageDialog(getString(R.string.postError), task.getException().getMessage(), R.drawable.ic_error_red_color_30dp);

                            }
                        }
                    });

                }

            }
        });


    }

    private void addNewPost(final String downloadUri, final String textPost) {
        DatabaseReference usersDB_Reference=FirebaseDatabase.getInstance().getReference("Users");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);
        final DatabaseReference postsReference = FirebaseDatabase.getInstance()
                .getReference("Posts").child(String.valueOf(Post.id));

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                final String date= Calendar.getInstance().getTime().toString();
                final String user_name=dataSnapshot.child("name").getValue().toString();
                final String user_image=dataSnapshot.child("profile_pic").getValue().toString();
                Toast.makeText(AddPost_Activity.this,user_image,Toast.LENGTH_LONG).show();

                final Post post=new Post(textPost,downloadUri,user_name,date,user_image);

                //user.addPost(post);
               // mDatabaseReference.setValue(user);

                postsReference.setValue(post);
           /* ArrayList<Post> posts=dataSnapshot.getValue(User.class).getPosts();
                for(int i=0;i<posts.size();i++)
                    ss+=posts.get(i).getText()+"\n";
                Toast.makeText(AddPost_Activity.this,ss,Toast.LENGTH_LONG).show();
          */

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        postsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> DBposts=dataSnapshot.getChildren();
                for(DataSnapshot post: DBposts)
                {
                 //   DataSnapshot posts_child=user.child("posts");
                   // Iterable<DataSnapshot> user_posts=posts_child.getChildren();
                   // for(DataSnapshot p:user_posts)
                    //{
                        Post p=post.getValue(Post.class);
                        posts.add(p);
                    //}
                }
                Intent intent=new Intent(AddPost_Activity.this,Street_Information.class);
                intent.putParcelableArrayListExtra("List of posts",posts);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
   /*  child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               finish();
               Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                String s="";
                for (DataSnapshot ch:children) {
                s+=ch.child("text").getValue().toString()+"     ";
                }
               Toast.makeText(AddPost_Activity.this,s,Toast.LENGTH_LONG).show();
            }
   });*/
    }


    // here method for make a message dialog in android
    private void showMessageDialog(String title, String message, int messageIcon) {
        AlertDialog alertDialog = new AlertDialog.Builder(AddPost_Activity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(messageIcon);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do stuff
                    }
                });

        alertDialog.show();
    }


    // open the gallery of photos in device after accept the permission of read from external storage
    private void openGallery() {
        // here open gallaery intent and wait the user pick a photo
        Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, REQUESCODE);
    }


    // make a check if user accept the permission or no
    // and if user reject the permission there a toast message
    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(AddPost_Activity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(AddPost_Activity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(AddPost_Activity.this, getString(R.string.please_accept_permissions),
                        Toast.LENGTH_LONG).show();

            } else {


                ActivityCompat.requestPermissions(AddPost_Activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }

        } else {
            openGallery();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImageUri = data.getData();
            newPostImage.setImageURI(pickedImageUri);

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}



