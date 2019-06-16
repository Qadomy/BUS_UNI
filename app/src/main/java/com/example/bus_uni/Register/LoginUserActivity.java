package com.example.bus_uni.Register;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bus_uni.BusCompany.CompanyHome;
import com.example.bus_uni.Driver.DriverHome;
import com.example.bus_uni.Driver.DriverLocation;
import com.example.bus_uni.Home;
import com.example.bus_uni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUserActivity extends AppCompatActivity {

    private Button signin, resetPssword;
    private EditText inputEmail, inputPassword;
    private ProgressBar progressBarLogin;
    String currentuser ="";

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        signin = findViewById(R.id.signinUserButton);
        resetPssword =  findViewById(R.id.reset_password);

        inputEmail =  findViewById(R.id.email_loginUser);
        inputPassword =  findViewById(R.id.password_loginUser);
        progressBarLogin =  findViewById(R.id.progressBarLoginUser);

        firebaseAuth = FirebaseAuth.getInstance();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        resetPssword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetPassword = new Intent(LoginUserActivity.this, ResetPassword.class);
                startActivity(resetPassword);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                signin.setVisibility(View.GONE);
                progressBarLogin.setVisibility(View.VISIBLE);

                if (email.isEmpty() || password.isEmpty()) {

                    showMessageDialog(getString(R.string.errorMessage), getString(R.string.fieldsMessageError),
                            R.drawable.ic_error_red_color_30dp);

                    signin.setVisibility(View.VISIBLE);
                    progressBarLogin.setVisibility(View.GONE);

                } else {

                    signUserAcoount(email, password);
                }

            }
        });
    }

    private void signUserAcoount(final String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                LoginUserActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // if the sign in failed display a message dialog for user
                        // and if sign in succeeds will user notified
                        if (task.isSuccessful()) {
                            currentuser = firebaseAuth.getCurrentUser().getUid();

                            showMessageDialog(getString(R.string.accountSignin), getString(R.string.successfully), R.drawable.ic_check_circle_30dp);
                            checkType();



                        } else {

                            showMessageDialog(getString(R.string.accountSignInFaild), task.getException().getMessage(), R.drawable.ic_error_red_color_30dp);

                            signin.setVisibility(View.VISIBLE);
                            progressBarLogin.setVisibility(View.INVISIBLE);
                        }
                    }
                }
        );
    }

    private void checkType() {
      DatabaseReference user= databaseReference.child(currentuser);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // int type = -1;
              /*  Iterable<DataSnapshot> users = dataSnapshot.getChildren();
                for (DataSnapshot user : users) {
                    String current_email = user.child("email").getValue().toString();
                    if (email.equals(current_email)) {
                        type = user.child("type").getValue(Integer.class);
                        break;
                    }
                }*/
             int type=dataSnapshot.child("type").getValue(Integer.class);

                if (type == 0) {

                    Intent signInSuccessfully = new Intent(LoginUserActivity.this, Home.class);
                    startActivity(signInSuccessfully);
                    finish();

                } else if (type == 1) {


                    Intent signInSuccessfully = new Intent(LoginUserActivity.this, CompanyHome.class);
                    startActivity(signInSuccessfully);
                    finish();

                } else if (type == 2) {

                    Intent signInSuccessfully = new Intent(LoginUserActivity.this, DriverHome.class);
                    startActivity(signInSuccessfully);
                    finish();

                    // 🤣🤣

                }
            }

           @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void showMessageDialog(String title, String message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(LoginUserActivity.this).create();
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


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
