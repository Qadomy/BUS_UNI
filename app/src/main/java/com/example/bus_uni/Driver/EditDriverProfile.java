package com.example.bus_uni.Driver;

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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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

public class EditDriverProfile extends AppCompatActivity {


    static int PReqCode = 1;
    static int REQUESCODE = 1;

    //
    // here for get the id of current user and save in the string
    String email, currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();;

    //
    //
    //

    private CircleImageView newDriverProfilePhoto;
    private TextView clickHereChangePhoto;
    private Button save, cancel;
    private EditText newDriverName, newDriverPhone, newDriverBusNum, newDriverSeatNum;
    private Spinner spinnerNewBusLine;
    private ProgressBar progressBarSave;

    //
    private Uri pickedImageUri;


    // firebase database
    private DatabaseReference mUserDatabaseReference;

    // Firebase Storage Reference
    private StorageReference mStorageReference;


    /*
     *
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_driver_profile);


        newDriverProfilePhoto = (CircleImageView) findViewById(R.id.newDriverProfile_photo);
        clickHereChangePhoto = (TextView) findViewById(R.id.newDriverProfile_clickHereText);
        save = (Button) findViewById(R.id.saveDriverProfileButton);
        cancel = (Button) findViewById(R.id.cancelSaveDriverProfileButton);
        newDriverName = (EditText) findViewById(R.id.newDriverProfile_name);
        newDriverPhone = (EditText) findViewById(R.id.newDriverProfile_Phone);
        newDriverBusNum = (EditText) findViewById(R.id.newDriverProfile__BusNumber);
        newDriverSeatNum = (EditText)findViewById(R.id.newDriverProfile_SeatNum);
        spinnerNewBusLine = (Spinner) findViewById(R.id.newDriverProfile__LineName);
        progressBarSave = (ProgressBar) findViewById(R.id.driverProgressBarSave);

        progressBarSave.setVisibility(View.INVISIBLE);



        // here for init the spinner and get her the data from string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bus_line_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNewBusLine.setAdapter(adapter);


        // mean there is no photo
        pickedImageUri = null;


        // init Storage Reference
        mStorageReference = FirebaseStorage.getInstance().getReference("upload");


        final String[] oldImage = {""};

        // init database for driver
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mUserDatabaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                email = dataSnapshot.child("email").getValue().toString();

                // here using Picasso for get the image url and set in ImageView
                String imageUrl = dataSnapshot.child("profile_pic").getValue().toString();
                Picasso.with(EditDriverProfile.this).load(imageUrl).into(newDriverProfilePhoto);


                // oldImage[0] this we save the string of link of existing image
                oldImage[0] = imageUrl;


                User user = dataSnapshot.getValue(User.class);


                // here we get the data from
                newDriverName.setText(user.getName());
                newDriverPhone.setText(user.getMobile());
                newDriverBusNum.setText(user.getBus_num());
                newDriverSeatNum.setText(user.getBus_seat());

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


                final String name = newDriverName.getText().toString();
                final String phone = newDriverPhone.getText().toString();
                final String busNum = newDriverBusNum.getText().toString();
                final String busSeat = newDriverSeatNum.getText().toString();
                final String lineName = spinnerNewBusLine.getSelectedItem().toString();

                if (pickedImageUri != null) {

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
                                        saveNewDriverData(name, phone, busNum, busSeat, lineName, downloadUri);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {

                                        Toast.makeText(EditDriverProfile.this, "Can't update your profile", Toast.LENGTH_SHORT).show();

                                    }

                                });

                                Toast.makeText(EditDriverProfile.this, "The Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(EditDriverProfile.this, "Photo faild saved in database", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                } else {

                    // if we don`t change the image in edit profile we send the real image again to firebase when we save
                    saveNewDriverData(name, phone, busNum, busSeat, lineName, oldImage[0]);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cancel = new Intent(EditDriverProfile.this, DriverProfile.class);
                startActivity(cancel);
            }
        });




    }// end of onCreate

    private void saveNewDriverData(String name, String phone,
                                   String busNum, String busSeat, String lineName, String downloadUri) {


        User user = new User();

        user.setName(name);
        user.setMobile(phone);
        user.setBus_seat(busSeat);
        user.setBus_num(busNum);
        user.setBus_line(lineName);
        user.setType(2);
        user.setProfile_pic(downloadUri);
        user.setEmail(email);


        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);
        mUserDatabaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                    //todo: here when successfully saved data it back to DriverHome not DriverProfile like in Intent

                    // after saving successfully return to Driver Profile
                    Intent saveProfile = new Intent(EditDriverProfile.this, DriverProfile.class);
//                    saveProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(saveProfile);

                    Toast.makeText(EditDriverProfile.this, R.string.accountSaved, Toast.LENGTH_SHORT).show();



                } else {

                    save.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    progressBarSave.setVisibility(View.GONE);


                    Toast.makeText(EditDriverProfile.this, "Your data failed saved", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }// end saveDriverData


    public void chooseImageToEdit(View view) {
        if (Build.VERSION.SDK_INT >= 22) {

            checkAndRequestForPermission();
        } else {

            openGallery();
        }
    }

    // here method for make a message dialog on android
    private void showMessageDialog(String title, String message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(EditDriverProfile.this).create();
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

        if (ContextCompat.checkSelfPermission(EditDriverProfile.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(EditDriverProfile.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(EditDriverProfile.this, getString(R.string.please_accept_permissions),
                        Toast.LENGTH_LONG).show();

            } else {


                ActivityCompat.requestPermissions(EditDriverProfile.this,
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
            newDriverProfilePhoto.setImageURI(pickedImageUri);

        }
    }


    // here for back symbol in action bar
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
