package com.example.um_event;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button buttonregister, buttonLogin, buttonregisterOrganiser, buttonTest;
    private String[] credential = {"Students", "Organizers", "Admin"};
    private EditText username, password;
    private Spinner credentials;
    private TextView viewUsername, viewPassword, viewCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Testing back button


        // Fetch information from the registration page.
        username = (EditText) findViewById(R.id.loginUsername);
        password = (EditText) findViewById(R.id.loginPassword);
        credentials = (Spinner) findViewById(R.id.credentials_spinner);

        // This part is for the spinner
        // Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spinner = (Spinner) findViewById(R.id.credentials_spinner);
        spinner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the credential lists
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

        // This part is for register organisation button
        buttonregisterOrganiser = (Button) findViewById(R.id.createOrganizersButton);
        buttonregisterOrganiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrganisationRegistrationActivity();
            }
        });

        //----------------------------------------------------------------------------------------------------------------------
        // This part is for testing input button
        viewUsername = (TextView) findViewById(R.id.showUsername);
        viewPassword = (TextView) findViewById(R.id.showPassword);
        viewCredentials = (TextView) findViewById(R.id.showCredentials);

        buttonTest = (Button) findViewById(R.id.testInfo);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            // .getText() is for fetching the text entered at the EditText
            // .getSelectedItem() is for fetching the text choosen at the Spinner
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Username : " + username.getText() + "\n" + "Password : " + password.getText() + "\n" + "Credentials : " + spinner.getSelectedItem().toString() +"\n", Toast.LENGTH_LONG).show();
            }
        });
        //----------------------------------------------------------------------------------------------------------------------


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
}