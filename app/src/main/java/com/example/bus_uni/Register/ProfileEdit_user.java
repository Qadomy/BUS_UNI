package com.example.bus_uni.Register;

import android.Manifest;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bus_uni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import javax.crypto.SecretKey;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEdit_user extends AppCompatActivity {


    static int PReqCode = 1;
    static int REQUESCODE = 1;


    String currentUid;


    //TODO:same key or unique key attribute for each user (in Info or Encrypt class)?
    private SecretKey sec = Encrypt.generateKey();


    private Button save, cancel;
    private EditText newName, newEmail, newRfid, newMobile, newCity, newPass;

    private Uri pickedImageUri;
    private ProgressBar progressBarSave, imageProgressBar;


    // for Circle photo
    private CircleImageView photoPickerButton;


    // firebase database
    private DatabaseReference mUserDatabaseReference;
    private DatabaseReference mPhotoDatabaseReference;

    // for current user
    private FirebaseUser current_user;


    // Firebase Storage Reference
    private StorageReference mStorageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);


        photoPickerButton = (CircleImageView) findViewById(R.id.photoPickerButton);


        save = (Button) findViewById(R.id.save_button);
        cancel = (Button) findViewById(R.id.cancel_button);

        newName = (EditText) findViewById(R.id.name_editProfile);
        newEmail = (EditText) findViewById(R.id.email_editProfile);
        newPass = (EditText) findViewById(R.id.pass_editProfile);

        newMobile = (EditText) findViewById(R.id.mobile_editProfile);
        newCity = (EditText) findViewById(R.id.city_editProfile);


        progressBarSave = (ProgressBar) findViewById(R.id.progressBarEditProfile);
        imageProgressBar = (ProgressBar) findViewById(R.id.imageProgressBar);
        //
        // for image progress bar visibility in the beginning
        imageProgressBar.setVisibility(View.INVISIBLE);


        //
        // mean there is no photo
        pickedImageUri = null;


        // init database for users
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");


        // init database for images
        mPhotoDatabaseReference = FirebaseDatabase.getInstance().getReference("Upload");

        //


        //

        // init Storage Reference
        mStorageReference = FirebaseStorage.getInstance().getReference("upload");
        //
        //
        //


        // here for get the id of current user and save in the string
        current_user = FirebaseAuth.getInstance().getCurrentUser();
        currentUid = current_user.getUid();


        //////////
        //
        ///
        //////////
        //
        //
        //////////
        //


        final String[] oldImage = new String[1];
        // database realtime
        mUserDatabaseReference.child(currentUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // here using Picasso for get the image url and set in ImageView
                String imageUrl = dataSnapshot.child("profile_pic").getValue().toString();
                Picasso.with(ProfileEdit_user.this).load(imageUrl).into(photoPickerButton);

                // oldImage[0] this we save the string of link of existing image
                oldImage[0] = imageUrl;

                // here we get the data from database and shown in applications
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String mobile = dataSnapshot.child("mobile").getValue().toString();
                String rfid = dataSnapshot.child("refid").getValue().toString();
                String city = dataSnapshot.child("city").getValue().toString();

                newName.setText(name);
                newEmail.setText(email);
                newMobile.setText(mobile);
                newCity.setText(city);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                progressBarSave.setVisibility(View.VISIBLE);


                final String name = newName.getText().toString();
                final String email = newEmail.getText().toString();
                final String mobile = newMobile.getText().toString();
                final String rfid = "";
                final String city = newCity.getText().toString();
                final String pass = newPass.getText().toString();

                if (pickedImageUri != null) {

                    photoPickerButton.setVisibility(View.INVISIBLE);
                    //
                    imageProgressBar.setVisibility(View.VISIBLE);


                    // Save the image in link contain the id for photo and id for the
                    // current user who upload this photo
                    //

                    // Folder name: profile_images, and current user id and extinctions of image (jpg)
                    final StorageReference image_path = mStorageReference.child("profile_images")
                            .child(currentUid + ".jpg");

                    // here for upload the image to storage firebase
                    image_path.putFile(pickedImageUri).addOnCompleteListener(new OnCompleteListener
                            <UploadTask.TaskSnapshot>() {

                        @Override
                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {

                                // here for get the image url and save it in String
                                image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String downloadUri = uri.toString();
                                        saveNewUserData(name, email, pass, mobile, rfid, city, downloadUri);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors

                                        Toast.makeText(ProfileEdit_user.this, "Can't update your profile", Toast.LENGTH_SHORT).show();
                                        //saveNewUserData(name, email, mobile, rfid, city, "");

                                    }

                                });


                                Toast.makeText(ProfileEdit_user.this,
                                        "The Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                showMessageDialog(getString(R.string.photoSavedFaild), task.getException()
                                        .getMessage(), R.drawable.ic_error_red_color_30dp);

                            }
                        }
                    });


                } else {

                    // if we don`t change the image in edit profile we send the real image again to firebase when we save
                    saveNewUserData(name, email, pass, mobile, rfid, city, oldImage[0]);
                }


            }
        });


        //
        //

        //
        // when click on photo button for open the gallery and choose a photo
        photoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22) {

                    checkAndRequestForPermission();
                } else {

                    openGallery();
                }
            }
        });

        //
        //
        //
        // when click on cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(ProfileEdit_user.this, Profile_user.class);
                startActivity(cancel);
            }
        });


    }

    private void saveNewUserData(String name, String email, String pass, String mobile,
                                 String rfid, String city, String url) {
        //We must use (User.class) to retrieve the current user and then update necessary fields
        User user;

        // here for update the password with the new password
        if (pass != null && !pass.isEmpty()) {
            current_user.updatePassword(pass);

            String encPass = Encrypt.encryptPass(pass, sec);
            user = new User(name, email, encPass, mobile, rfid, city, url);

        } else {
            user = new User(name, email, mobile, rfid, city, url);
        }

        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUid);


        mUserDatabaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    showMessageDialog(getString(R.string.accountSaved), getString(R.string.successfully), R.drawable.ic_check_circle_30dp);

                    Intent saveProfile = new Intent(ProfileEdit_user.this, Profile_user.class);
                    saveProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(saveProfile);
                    finish();
                } else {

                    save.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    progressBarSave.setVisibility(View.GONE);

                    showMessageDialog(getString(R.string.accountSavedFaild), task.getException()
                            .getMessage(), R.drawable.ic_error_red_color_30dp);

                }
            }
        });


    }


    // here method for make a message dialog on android
    private void showMessageDialog(String title, String message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(ProfileEdit_user.this).create();
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


    // make a check if user accept the permission or no
    // and if user reject the permission there a toast message
    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(ProfileEdit_user.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileEdit_user.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(ProfileEdit_user.this, getString(R.string.please_accept_permissions),
                        Toast.LENGTH_LONG).show();

            } else {


                ActivityCompat.requestPermissions(ProfileEdit_user.this,
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
            photoPickerButton.setImageURI(pickedImageUri);

        }
    }


}
