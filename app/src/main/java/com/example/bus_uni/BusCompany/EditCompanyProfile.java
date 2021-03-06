package com.example.bus_uni.BusCompany;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bus_uni.R;
import com.example.bus_uni.Register.User;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditCompanyProfile extends AppCompatActivity {


    static int PReqCode = 1;
    static int REQUESCODE = 1;
    //
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //
    //
    //


    private CircleImageView companyProfilePhoto;
    private Button save, cancel;
    private EditText newCompanyName, newCompanyPhone, newCompanyCity;
    private ProgressBar progressBarSave;
    ////
    ////
    ////
    private Uri pickedImageUri;
    //////
    //
    //
    // firebase database
    private DatabaseReference mUserDatabaseReference, mEditUserDatabaseFirebase;
    // Firebase Storage Reference
    private StorageReference mStorageReference;


    String companyEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company_profile);

        companyProfilePhoto = (CircleImageView) findViewById(R.id.newCompany_ProfilePhoto);
        //

        save = (Button) findViewById(R.id.saveCompanyProfileButton);
        cancel = (Button) findViewById(R.id.cancelSaveCompanyProfileButton);
        //

        newCompanyName = (EditText) findViewById(R.id.newCompany_name);
        newCompanyPhone = (EditText) findViewById(R.id.newCompany_Phone);
        newCompanyCity = (EditText) findViewById(R.id.newCompany_city);

        //
        progressBarSave = (ProgressBar) findViewById(R.id.companyProgressBarSave);
        progressBarSave.setVisibility(View.INVISIBLE);



        // mean there is no photo
        pickedImageUri = null;

        //
        // init database for company
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");


        //


        //

        // init Storage Reference
        mStorageReference = FirebaseStorage.getInstance().getReference("upload");
        //
        //
        //


        final String[] oldImage = new String[1];

        mUserDatabaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // here using Picasso for get the image url and set in ImageView
                String imageUrl = dataSnapshot.child("profile_pic").getValue().toString();
                Picasso.with(EditCompanyProfile.this).load(imageUrl).into(companyProfilePhoto);

                // oldImage[0] this we save the string of link of existing image
                oldImage[0] = imageUrl;


                User user = dataSnapshot.getValue(User.class);


                // here we get the data from
                newCompanyName.setText(user.getName());
                newCompanyPhone.setText(user.getMobile());
                newCompanyCity.setText(user.getCity());

                companyEmail = user.getEmail();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // once we press on save button
                save.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                progressBarSave.setVisibility(View.VISIBLE);


                final String name = newCompanyName.getText().toString();
                final String phone = newCompanyPhone.getText().toString();
                final String city = newCompanyCity.getText().toString();


                if (pickedImageUri != null) {

                    //
                    progressBarSave.setVisibility(View.VISIBLE);

                    // smart thing to save the image in link contain the id for photo and id for the
                    // current user who upload this photo
                    //

                    // Folder name: profile_images, and current user id and extinctions of image (jpg)
                    final StorageReference image_path = mStorageReference.child("profile_pic")
                            .child(currentUser + ".jpg");


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
                                        saveNewCompanyData(name, phone, city, downloadUri);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors

                                        Toast.makeText(EditCompanyProfile.this, "Can't update your profile", Toast.LENGTH_SHORT).show();

                                    }

                                });


                                Toast.makeText(EditCompanyProfile.this,
                                        "The Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                showMessageDialog(getString(R.string.photoSavedFaild), task.getException()
                                        .getMessage(), R.drawable.ic_error_red_color_30dp);

                            }
                        }
                    });


                }else{

                    // if we don`t change the image in edit profile we send the real image again to firebase when we save
                    saveNewCompanyData(name, phone, city, oldImage[0]);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cancel = new Intent(EditCompanyProfile.this, CompanyProfile.class);
                startActivity(cancel);
            }
        });

        //
        // when click on photo button for open the gallery and choose a photo
        companyProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22) {

                    checkAndRequestForPermission();
                } else {

                    openGallery();
                }
            }
        });
    }// end onCreate

    private void saveNewCompanyData(String name, String phone, String city, String downloadUri) {

        User user = new User();
        user.setName(name);
        user.setMobile(phone);
        user.setCity(city);
        user.setProfile_pic(downloadUri);
        user.setType(1);
        user.setEmail(companyEmail);




        mEditUserDatabaseFirebase = FirebaseDatabase.getInstance().getReference("Users");
        mEditUserDatabaseFirebase.child(currentUser).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(EditCompanyProfile.this, "Your data saved successfully", Toast.LENGTH_SHORT).show();

                    Intent saveProfile = new Intent(EditCompanyProfile.this, CompanyProfile.class);
                    startActivity(saveProfile);
                    finish();

                } else {

                    save.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    progressBarSave.setVisibility(View.GONE);

                    Toast.makeText(EditCompanyProfile.this, "Unsuccessfully saved", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }




    // here method for make a message dialog on android
    private void showMessageDialog(String title, String message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(EditCompanyProfile.this).create();
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

        if (ContextCompat.checkSelfPermission(EditCompanyProfile.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(EditCompanyProfile.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(EditCompanyProfile.this, getString(R.string.please_accept_permissions),
                        Toast.LENGTH_LONG).show();

            } else {


                ActivityCompat.requestPermissions(EditCompanyProfile.this,
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
            companyProfilePhoto.setImageURI(pickedImageUri);

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
