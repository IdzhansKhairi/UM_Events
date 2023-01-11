package com.example.um_event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegisterOrganizerActivity extends AppCompatActivity {

    private Button organizerRegisterButton;
    private ImageButton buttonBackLogin;
    private EditText orgName, email, orgPhone, username, password, conPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_organizer);

        //Fetch information entered to register
        orgName = (EditText) findViewById(R.id.registerName);
        email = (EditText) findViewById(R.id.registerEmail);
        orgPhone = (EditText) findViewById(R.id.registerPhone) ;
        username = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.registerConfirmPassword);
        conPassword = (EditText) findViewById(R.id.registerConfirmPassword);

        // Back Button to the Login Page
        buttonBackLogin = (ImageButton) findViewById(R.id.backRegisterImageButton);
        buttonBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        // Register organizer button
        organizerRegisterButton = (Button) findViewById(R.id.creatingOrganizerButton);
        organizerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senEmail();
                thankYouRegister();
            }
        });
    }

    // Methods to go back to login page
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Methods to go to the "Thank You for register" page
    public void thankYouRegister() {
        Intent intent = new Intent(this, ThanksActivity.class);
        startActivity(intent);
    }

    private void senEmail() {
        String memail = "codertestingemail@gmail.com";
        String msubject = "Organizer's Registration";
        String mtext = "";

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, memail, msubject, mtext);
        javaMailAPI.execute();
    }
}