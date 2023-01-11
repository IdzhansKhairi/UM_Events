package com.example.um_event;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingClientFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String SHARED_PREFS = "sharedPrefs";


    private String mParam1;
    private String mParam2;

    private Switch switcher;
    private boolean nightMODE;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView stat, userId, phone, email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_client, container, false);

        // Light and Dark Mode Stuff
        switcher = view.findViewById(R.id.switchLightDark);
        // We used shared Preferences to save modes if exit the app and go back again
        sharedPreferences = getActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night", false); // Light mode is the default mode

//       // if (nightMODE) {
//            switcher.setChecked(true);
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//      //  }

        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nightMODE) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
            }
        });

        SharedPreferences sp1 = getActivity().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        String IDorg = sp1.getString("ID","NOPE");
        String desiredEvent = sp1.getString("Desired","NAH");

        stat = view.findViewById(R.id.status);
        userId = view.findViewById(R.id.profileName);
        phone = view.findViewById(R.id.phoneTV);
        email = view.findViewById(R.id.emailTV);
        if (LoginActivity.statusAcc.equals("Students")){
        stat.setText("Logged in as a Student");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users_Credentials");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        if (dataSnapshot.child("desiredEvent").getValue().toString().equals(desiredEvent)){
                            String matricNo = dataSnapshot.child("matricNumber").getValue().toString();
                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Student_Info").child(matricNo);
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    userId.setText(snapshot.child("fullName").getValue().toString());
                                    email.setText(snapshot.child("siswamail").getValue().toString());
                                    phone.setText(matricNo);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Organizers_Credentials");
            ref3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        if (dataSnapshot.child("organizerID").getValue().toString().equals(IDorg)){
                            userId.setText(IDorg);
                            email.setText(dataSnapshot.child("email").getValue().toString());
                            phone.setText(dataSnapshot.child("phoneNo").getValue().toString());

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        // Sign Out Button Stuff
        Button signOutButton = view.findViewById(R.id.signOutBtn);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        // Feedback Button
        Button feedbackButton = view.findViewById(R.id.feedbackBtn);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFeedbackActivity();
            }
        });

        // Contact Us Button
        Button contactUsButton = view.findViewById(R.id.contactUsBtn);
        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContactUsActivity();
            }
        });

        return view;
    }

    public SettingClientFragment() {
        // Required empty public constructor
    }

    public static SettingClientFragment newInstance(String param1, String param2) {
        SettingClientFragment fragment = new SettingClientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void signOut() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
    }

    public void openFeedbackActivity() {
        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
        startActivity(intent);
    }

    public void openContactUsActivity() {
        Intent intent = new Intent(getActivity(), ContactUsActivity.class);
        startActivity(intent);
    }
}