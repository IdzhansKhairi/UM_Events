package com.example.um_event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.um_event.LoginActivity;
import com.example.um_event.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton buttonBackLogin;
    private Button newCreatingAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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