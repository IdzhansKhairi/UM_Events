package com.example.um_event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.um_event.LoginActivity;
import com.example.um_event.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton buttonBackLogin;
    private Button newCreatingAccountButton;
    private EditText matricNumber, username, password, conPassword;
    private CheckBox sportsCheck, carnivalCheck, talksCheck, artsCheck, webminarCheck, showcaseCheck, educationalCheck;
    private String checks = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Fetch information from the registration page.
        matricNumber = (EditText) findViewById(R.id.registerMatricNumber);
        username = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.registerPassword);
        conPassword = (EditText) findViewById(R.id.registerConfirmPassword);

        // Fetch information from the checkboxes
        sportsCheck = (CheckBox) findViewById(R.id.sportCheckbox);
        carnivalCheck = (CheckBox) findViewById(R.id.carnivalCheckbox);
        talksCheck = (CheckBox) findViewById(R.id.talkCheckbox);
        artsCheck = (CheckBox) findViewById(R.id.artsCheckbox);
        webminarCheck = (CheckBox) findViewById(R.id.webinarCheckbox);
        showcaseCheck = (CheckBox) findViewById(R.id.showcaseCheckbox);
        educationalCheck = (CheckBox) findViewById(R.id.educationalCheckbox);

        // Back Button to the Login Page
        buttonBackLogin = (ImageButton) findViewById(R.id.backRegisterImageButton);
        buttonBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        // Creating new account button
        newCreatingAccountButton = (Button) findViewById(R.id.creatingAccountButton);
        newCreatingAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });
    }

    // Methods to check what is being checked
    public void Check(View v) {

        if(sportsCheck.isChecked())
            checks += "Sports ";
        if(carnivalCheck.isChecked())
            checks += "Carnival ";
        if(talksCheck.isChecked())
            checks += "Talks ";
        if(artsCheck.isChecked())
            checks += "Arts ";
        if(webminarCheck.isChecked())
            checks += "Webinar ";
        if(showcaseCheck.isChecked())
            checks += "Showcase ";
        if(educationalCheck.isChecked())
            checks += "Educational";

    }

    // Methods to go back to login page
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Methods to go to the main home page
    public void openHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}