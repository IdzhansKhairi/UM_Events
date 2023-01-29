package com.example.um_event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button buttonregister, buttonLogin, buttonregisterOrganiser;
    private String[] credential = {"Students", "Organizers"};
    private EditText username, password;
    private Spinner credentials;

    private TextView viewUsername, viewPassword, viewCredentials;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static String tag ;
    public static String statusAcc;
    Spinner spinner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Fetch information from the registration page.
        username = findViewById(R.id.loginUsername);
        password =  findViewById(R.id.loginPassword);
        credentials =  findViewById(R.id.credentials_spinner);

        checkLog();

        // This part is for the spinner
        // Getting the instance of Spinner and applying OnItemSelectedListener on it
        spinner = (Spinner) findViewById(R.id.credentials_spinner);
        spinner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the credential lists
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, credential);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);


        // This part is for login button
        buttonLogin = (Button) findViewById(R.id.button);
        buttonLogin.setOnClickListener(view -> {

            if (spinner.getSelectedItem().toString().equals("Students")){
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users_Credentials");
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean userExist = false;
                    if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(),"Please enter your username and password",Toast.LENGTH_SHORT).show();
                    }else{
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if (dataSnapshot.child("username").getValue().toString().equals(username.getText().toString())){
                                userExist = true;
                                if (dataSnapshot.child("password").getValue().toString().equals(password.getText().toString())){
                                    statusAcc = spinner.getSelectedItem().toString();
                                    authenticationUser(dataSnapshot.child("desiredEvent").getValue().toString());
                                }else
                                    Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                            }else{
                                if (!userExist)
                                    Toast.makeText(getApplicationContext(),"No Username Found",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage());
                }
            });}
            else{
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Organizers_Credentials");
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean userExist = false;
                        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),"Please enter your username and password",Toast.LENGTH_SHORT).show();
                        }else{
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if (dataSnapshot.child("organizerID").getValue().toString().equals(username.getText().toString())){
                                    userExist = true;
                                    if (dataSnapshot.child("password").getValue().toString().equals(password.getText().toString())){
                                        authenticationOrg(username.getText().toString());
                                    }else
                                        Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                                }else{
                                    if (!userExist)
                                        Toast.makeText(getApplicationContext(),"No Username Found",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getMessage());
                    }
                });
            }
        });

        // This part is for register button
        buttonregister = findViewById(R.id.createAccountButton);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationActivity();
            }
        });

        // This part is for register organisation button
        buttonregisterOrganiser = (Button) findViewById(R.id.createOrganizersButton);
        buttonregisterOrganiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrganisationRegistrationActivity();
            }
        });

    }

    // Methods for go to registration
    public void openRegistrationActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // Methods for go to Organiser registration
    public void openOrganisationRegistrationActivity() {
        Intent intent = new Intent(this, RegisterOrganizerActivity.class);
        startActivity(intent);
    }

    // Methods for go to home page
    public void openHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Performing action when Item Selected from spinner, Overiding onItemSelected method
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), credential[i], Toast.LENGTH_LONG);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void authenticationUser(String desire){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("User","true");
        editor.putString("Desired",desire);
        editor.putString("AccType",spinner.getSelectedItem().toString());
        editor.apply();
        openHomePage();
    }

    private void authenticationOrg(String orgID){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("User","true");
        editor.putString("ID",orgID);
        editor.putString("AccType",spinner.getSelectedItem().toString());
        editor.apply();
        openHomePage();
    }

    private void checkLog(){
        SharedPreferences SP = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String check = SP.getString("User","No");
        tag = SP.getString("Desired","");
        statusAcc = SP.getString("AccType","NAH");
        if(check.equals("true")){
            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_SHORT).show();
            openHomePage();
            finish();
        }
    }


}