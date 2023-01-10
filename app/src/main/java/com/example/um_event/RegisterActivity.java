package com.example.um_event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.um_event.LoginActivity;
import com.example.um_event.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton buttonBackLogin;
    private Button newCreatingAccountButton;
    private EditText matricNumber, username, password, conPassword;
    private CheckBox sportsCheck, carnivalCheck, talksCheck, artsCheck, webinarCheck, showcaseCheck, educationalCheck;
    private String checks = "";
    private FirebaseAuth auth;

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
        webinarCheck = (CheckBox) findViewById(R.id.webinarCheckbox);
        showcaseCheck = (CheckBox) findViewById(R.id.showcaseCheckbox);
        educationalCheck = (CheckBox) findViewById(R.id.educationalCheckbox);

        String matricNo = unifyMatricNo(matricNumber.getText().toString());

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
                if (matricNumber.getText().toString().isEmpty() || username.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter all info",Toast.LENGTH_LONG).show();
                }else{
                    if (password.getText().toString().equals(conPassword.getText().toString())){
                        if (!sportsCheck.isChecked() && !carnivalCheck.isChecked() && !talksCheck.isChecked() && !artsCheck.isChecked() &&
                                !webinarCheck.isChecked() && !showcaseCheck.isChecked() && !educationalCheck.isChecked()){
                                Toast.makeText(getApplicationContext(),"Please pick one category",Toast.LENGTH_LONG).show();
                        }else{

                           // boolean isStudent = checkStudent(matricNo);
                          // boolean registered = checkUser(matricNo);
                          //  System.out.println("HERE : "+  registered);
                          //  if(isStudent && !registered){
                                InsertRegisterData(matricNo,username.getText().toString(),
                                        password.getText().toString(),Check());
                           // }
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    // Methods to check what is being checked
    public String Check() {

        if(sportsCheck.isChecked())
            checks += "Sport ";
        if(carnivalCheck.isChecked())
            checks += "Carnival ";
        if(talksCheck.isChecked())
            checks += "Talk ";
        if(artsCheck.isChecked())
            checks += "Art ";
        if(webinarCheck.isChecked())
            checks += "Webinar ";
        if(showcaseCheck.isChecked())
            checks += "Showcase ";
        if(educationalCheck.isChecked())
            checks += "Educational";
        return checks;
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

    public boolean InsertRegisterData(String matricNo, String username, String password, String desiredEvent){
        UserData insertUser = new UserData(matricNo,username,password, desiredEvent);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userNode = db.getReference("Users_Credentials");
        userNode.child(matricNo).setValue(insertUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_LONG).show();
            }
        });
        return true;
    }

    public String unifyMatricNo(String matricNo){
        String unified = matricNo.toUpperCase();
        return unified;
    }

    public boolean checkStudent(String matricNo){
        final boolean[] exists = {false};
        String student = unifyMatricNo(matricNo);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Student_Info");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot Snapshot: snapshot.getChildren() ){
                    if (Snapshot.child("matricNumber").getValue().toString().equals(student)){
                        //this person is a UM student
                        exists[0] = true;
                        System.out.println(exists[0]+" 1");
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return exists[0];
    }

    public boolean checkUser(String matricNo){
        final boolean[] exists = {false};
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users_Credentials");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot Snapshot: snapshot.getChildren() ){
                    if (Snapshot.child("matricNumber").getValue().toString().equals(matricNo)){
                        exists[0] = Snapshot.child("matricNumber").getValue().toString().equals(matricNo);
                        Toast.makeText(getApplicationContext(),"You have already registered",Toast.LENGTH_LONG).show();
                        System.out.println(exists[0]+" 2");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println(exists[0]);
        return exists[0];
    }
}