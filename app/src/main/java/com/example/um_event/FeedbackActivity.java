package com.example.um_event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity{

    private ImageButton backToSettingButton;
    private Button submitFeedbackButton;
    private RatingBar ratingStars;
    private double ratingValue;
    private EditText feedbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        TextView ratingText;

        // Rating related stuff
        ratingText = findViewById(R.id.textViewFeedback);
        ratingStars = findViewById(R.id.ratingBar);
        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                int rating = (int) v;
                String message = "";

                switch(rating) {
                    case 0:
                        message="Please rate our application!";
                        ratingValue = 0.0;
                        break;
                    case 1:
                        message = "Tell us a bit more about why you chose 1";
                        ratingValue = 1.0;
                        break;
                    case 2:
                        message = "Tell us a bit more about why you chose 2";
                        ratingValue = 2.0;
                        break;
                    case 3:
                        message = "Tell us a bit more about why you chose 3";
                        ratingValue = 3.0;
                        break;
                    case 4:
                        message = "Tell us a bit more about why you chose 4";
                        ratingValue = 4.0;
                        break;
                    case 5:
                        message = "Tell us a bit more about why you chose 5";
                        ratingValue = 5.0;
                        break;
                }
                ratingText.setText(message);
            }
        });

        // Edit Text Feedback Stuff
        feedbackText = findViewById(R.id.feedbackTextEdit);

        // Back Button to the Settings Page
        backToSettingButton = (ImageButton) findViewById(R.id.backFeedbackButton);
        backToSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });

        // Submit Button and back to the home page
        submitFeedbackButton = (Button) findViewById(R.id.submitFeedbackButton);
        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senEmail();
                Toast.makeText(getApplicationContext(), "Thank you for your feedback!", Toast.LENGTH_LONG).show();
                openHomePage();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frame_Layout, fragment);
        fragmentTransaction.commit();

    }

    private void openHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void senEmail() {
        String memail = "codertestingemail@gmail.com";
        String msubject = "User's Feedback";
        String mtext = "Your star rating:" + this.ratingValue + "/5.0" + "\n\n" + "Customer's Feedback:\n" + feedbackText.getText().toString();

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, memail, msubject, mtext);
        javaMailAPI.execute();
    }

}