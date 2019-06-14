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

import com.example.bus_uni.Home;
import com.example.bus_uni.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.crypto.SecretKey;

public class SignupActivity extends AppCompatActivity {

    private EditText userName, password, confirmPassword, email_signup;
    private Button createAccount;
    private ProgressBar loadingProgress;


    // get the currentuser
    //private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    // Firebase Auth
    private FirebaseAuth mFirebaseAuth;

    // Firebase Database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        userName = (EditText) findViewById(R.id.user_name_signup);
        password = (EditText) findViewById(R.id.password_signup);
        confirmPassword = (EditText) findViewById(R.id.confirm_password_signup);
        email_signup = (EditText) findViewById(R.id.email_signup);

        createAccount = findViewById(R.id.createAccount);
        loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);

        //in begging the Progress Bar is Invisible
        loadingProgress.setVisibility(View.INVISIBLE);


        // init Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        // init Firebase database real time
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        // here when press on create account in signup activity
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);


                final String name = userName.getText().toString();
                final String pass = password.getText().toString();
                final String confirmPass = confirmPassword.getText().toString();
                final String email = email_signup.getText().toString();

                if (name.isEmpty() || pass.isEmpty() || email.isEmpty() || confirmPass.isEmpty()) {

                    // here show message dialog if there's a field empty
                    showMessageDialog(getString(R.string.errorMessage), getString(R.string.fieldsMessageError),
                            R.drawable.ic_error_red_color_30dp);

                    createAccount.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);

                } else if (!pass.equals(confirmPass)) {

                    showMessageDialog(getString(R.string.errorMessage), getString(R.string.passwordNOTmatch),
                            R.drawable.ic_error_red_color_30dp);
                    loadingProgress.setVisibility(View.INVISIBLE);
                    createAccount.setVisibility(View.VISIBLE);
                } else {
                    createUserAccount(email, pass, name);
                }


            }
        });

    }


    private void createUserAccount(final String email, final String pass, final String name) {

        // here we create user account with email and password
        mFirebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // here we make the progressbar disappear
                        loadingProgress.setVisibility(View.GONE);

                        if (task.isSuccessful()) {

                            // for get the current user id
                            // for get the current user id
                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            SecretKey sec = Encrypt.generateKey();
                            String encPass = Encrypt.encryptPass(pass, sec);

                            mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);

                            User user = new User();
                            user.setName(name);
                            user.setEmail(email);
                            user.setPassword(encPass);
                            user.setType(0);

                            // user account created successfully
                            showMessageDialog(getString(R.string.accountCreated), getString(R.string.successfully),
                                    R.drawable.ic_check_circle_30dp);
                            mDatabaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Intent mainIntent = new Intent(SignupActivity.this, Home.class);
                                        //TODO: this flags for what? here and in other places (StackOverflow also)
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        //finish();
                                    }
                                }
                            });

                        } else {
                            try {
                                throw task.getException();
                            }
                            // if user enters wrong password.
                            catch (FirebaseAuthWeakPasswordException weakPassword) {
                                showMessageDialog(getString(R.string.weak_password), task.getException().getMessage(),
                                        R.drawable.ic_error_red_color_30dp);

                            }
                            // if user enters wrong email.
                            catch (FirebaseAuthInvalidCredentialsException malformedEmail) {

                                showMessageDialog(getString(R.string.malformed_email), task.getException().getMessage(),
                                        R.drawable.ic_error_red_color_30dp);

                            } catch (Exception ex) {
                                showMessageDialog(getString(R.string.accountCreationFaild), task.getException().getMessage(),
                                        R.drawable.ic_error_red_color_30dp);
                            }
                            createAccount.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }


    // here method for make a message dialog on android
    private void showMessageDialog(String title, String message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
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