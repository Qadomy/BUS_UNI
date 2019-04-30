package com.example.bus_uni;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {


    private TextView resetPassword;
    private EditText inputEmail;
    private ProgressBar progressBarReset;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetPassword = (TextView) findViewById(R.id.resetPasswordTextView);
        inputEmail = (EditText) findViewById(R.id.email_resetPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBarReset = (ProgressBar) findViewById(R.id.progressBarResetPasswrod);
        progressBarReset.setVisibility(View.INVISIBLE);


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString();
                if (email.isEmpty()) {

                    showMessageDialog(getText(R.string.errorMessage), getText(R.string.enterYourEmail), R.drawable.ic_error_red_color_30dp);
                    return;
                }
                resetPassword.setVisibility(View.INVISIBLE);
                progressBarReset.setVisibility(View.VISIBLE);


                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showMessageDialog(getText(R.string.successfully), getText(R.string.sendEmailForReset),
                                            R.drawable.ic_check_circle_30dp);

                                } else {
                                    showMessageDialog(getText(R.string.faildSendEmailReset), task.getException().getMessage(),
                                            R.drawable.ic_error_red_color_30dp);

                                    resetPassword.setVisibility(View.VISIBLE);
                                }

                                progressBarReset.setVisibility(View.GONE);
                            }
                        });
            }
        });


    }

    //R.drawable.ic_check_circle_30dp

    private void showMessageDialog(CharSequence title, CharSequence message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(ResetPassword.this).create();
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
