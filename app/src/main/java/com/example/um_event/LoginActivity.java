package com.example.um_event;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button buttonregister, buttonLogin;
    private String[] credential = {"Students", "Organizers", "Admin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // This part is for the spinner
        // Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spinner = (Spinner) findViewById(R.id.credentials_spinner);
        spinner.setOnItemSelectedListener(this);
        //Creating the AArrayAdapter instance having the credential lists
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, credential);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        // This part is for login button - Go to the home page
        buttonLogin = (Button) findViewById(R.id.button);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openHomePage(); }
        });

        // This part is for register button
        buttonregister = (Button) findViewById(R.id.createAccountButton);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationActivity();
            }
        });
    }

    // Methods for go to registration
    public void openRegistrationActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
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
}