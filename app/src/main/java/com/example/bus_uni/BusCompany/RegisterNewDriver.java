package com.example.bus_uni.BusCompany;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bus_uni.R;
import com.example.bus_uni.Register.Encrypt;
import com.example.bus_uni.Register.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.crypto.SecretKey;

public class RegisterNewDriver extends AppCompatActivity {


//    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    String companyName;
    FirebaseUser companyUser;
    private Button addNewDriver;

    private EditText dName, dEmail, dPass, dPhone, dBusLine_Num,busLine_price;

    private Spinner bus_line_spinner, bus_seat_number_spinner;

    private ProgressBar loadingProgress;

    // Firebase Auth
    private FirebaseAuth mFirebaseAuth;

    // Firebase Database
    private DatabaseReference mCreataUseDriverDatabaseReference, mUserDatabaseReference;

    //method for generate new random strings
   /* public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_driver);

        Intent getCompany = getIntent();
        companyName = getCompany.getStringExtra(CompanyHome.EXTRA_COMPANY_NAME);
        companyUser = getCompany.getParcelableExtra(CompanyHome.EXTRA_COMPANY_OBJECT);

        // init firebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();


        dName =  findViewById(R.id.driverNameEditText);
        dEmail =  findViewById(R.id.driverEmailEditText);
        dPass =  findViewById(R.id.driverPasswordEditText);
        dPhone =  findViewById(R.id.driverPhoneEditText);
        dBusLine_Num =  findViewById(R.id.busNumber);


        bus_seat_number_spinner =(Spinner)findViewById(R.id.busSeatNumbers_spinner);
        bus_line_spinner =  findViewById(R.id.bus_line_name_spinner);
        busLine_price=findViewById(R.id.bus_line_price);

        addNewDriver =  findViewById(R.id.addNewDriverButton);
        loadingProgress =  findViewById(R.id.driverProgressBar);


        // here for init the spinner and get her the data from string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bus_line_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bus_line_spinner.setAdapter(adapter);


        // init bus seat number spinner
        ArrayAdapter<CharSequence> seatnumber = ArrayAdapter.createFromResource(this, R.array.bus_seat_numbers, android.R.layout.simple_spinner_item);
        seatnumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bus_seat_number_spinner.setAdapter(seatnumber);


        addNewDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = dName.getText().toString();
                final String email = dEmail.getText().toString();
                final String pass = dPass.getText().toString();
                final String phone = dPhone.getText().toString();
                final String busNum = dBusLine_Num.getText().toString();

                final String busSeat = bus_seat_number_spinner.getSelectedItem().toString();
                final String bus_line = bus_line_spinner.getSelectedItem().toString();
                final String line_price=busLine_price.getText().toString();

                if (name.isEmpty() || pass.isEmpty() || email.isEmpty() ||
                        busNum.isEmpty() || line_price.isEmpty() ||
                        bus_line.isEmpty() || busSeat.isEmpty()) {

                    // here show message dialog if there's a field empty
                    showMessageDialog(getString(R.string.errorMessage), getString(R.string.fieldsMessageError),
                            R.drawable.ic_error_red_color_30dp);

                    addNewDriver.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);

                } else {
                    createUserAccount(email, pass, name, phone, busSeat, busNum, bus_line,line_price);
                }


            }
        });

    }

    private void createUserAccount(final String email, final String pass, final String name, final String phone, final String bus_seat, final String bus_num, final String bus_line,final String line_price) {


        // here we create driver account with email and password
        mFirebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // here we make the progressbar disappear
                        loadingProgress.setVisibility(View.GONE);

                        if (task.isSuccessful()) {


                            addNewDriver.setVisibility(View.INVISIBLE);
                            loadingProgress.setVisibility(View.VISIBLE);


                            SecretKey sec = Encrypt.generateKey();
                            final String encPass = Encrypt.encryptPass(pass, sec);


                            User user = new User();
                            user.setName(name);
                            user.setEmail(email);
                            user.setPassword(encPass);
                            user.setMobile(phone);
                            user.setBus_num(bus_num);
                            user.setBus_seat(bus_seat);
                            user.setBus_line(bus_line);
                            user.setType(2);
                            user.setBus_company(companyName);
                            user.setLine_price(line_price);

                           // String gena = randomAlphaNumeric(28);

                            String current=mFirebaseAuth.getCurrentUser().getUid();
                           // mCreataUseDriverDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(gena);
                           mCreataUseDriverDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(current);
                            mCreataUseDriverDatabaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        //TODO: Send Sign in link (Or credentials) to driver email
                                        mFirebaseAuth.updateCurrentUser(companyUser);
                                        Toast.makeText(RegisterNewDriver.this, "Driver " + name + " is added successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterNewDriver.this, CompanyHome.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        startActivity(intent);


                                    } else {

                                        Toast.makeText(RegisterNewDriver.this, "Failed to add new driver", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {

                            showMessageDialog(getString(R.string.account_creation_faild), task.getException().getMessage(),
                                    R.drawable.ic_error_red_color_30dp);

                            addNewDriver.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });


    }// end of onCreate

    // for message dialog
    private void showMessageDialog(String title, String message, int messageIcon) {
        // here method for messages dialogs:
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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

    // for back sign in toolbar
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
