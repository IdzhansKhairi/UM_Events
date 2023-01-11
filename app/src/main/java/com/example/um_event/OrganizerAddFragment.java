package com.example.um_event;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrganizerAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrganizerAddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrganizerAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrganizerAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrganizerAddFragment newInstance(String param1, String param2) {
        OrganizerAddFragment fragment = new OrganizerAddFragment();
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

    private Button submitBtn;
    private ImageView eventImageEditText;
    private final int PICK_IMAGE_CODE = 172;
    private String eventCategory, eventStartTime, eventEndTime, eventStartDate, eventEndDate,eventDate,eventTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_organizer_add, container, false);

//        backButton
        ImageView backBtn = v.findViewById(R.id.HomeOragnizerBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Frame_Layout, new HomeOrganizerFragment());
                fragmentTransaction.commit();

            }
        });


//        image collecter

        eventImageEditText = v.findViewById(R.id.EventImageEditText);

        eventImageEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(getActivity())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, PICK_IMAGE_CODE);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                                permissionToken.continuePermissionRequest();

                            }
                        }).check();

            }
        });

        Calendar calender = Calendar.getInstance();

        TextView eventStartTimeTV = v.findViewById(R.id.EventStartTimeEditText);

        eventStartTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int hour = calender.get(Calendar.HOUR_OF_DAY);
                int mins = calender.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minutes);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat(("k:mm"));
                        eventStartTime = format.format(c.getTime());
                        eventStartTimeTV.setText(eventStartTime);

                    }
                }, hour, mins, true);
                timePickerDialog.show();

            }
        });

        TextView eventEndTimeTV = v.findViewById(R.id.EventEndTimeEditText);

        eventEndTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int hour = calender.get(Calendar.HOUR_OF_DAY);
                int mins = calender.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minutes);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat(("k:mm"));
                        eventEndTime = format.format(c.getTime());
                        eventEndTimeTV.setText(eventEndTime);

                    }
                }, hour, mins, true);
                timePickerDialog.show();

            }
        });

        TextView eventStartDateTV = v.findViewById(R.id.EventDateStartEditText);

        eventStartDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        eventStartDate = day+"/"+month+"/"+year;
                        eventStartDateTV.setText(eventStartDate);

                    }
                },year,month,day);
                dialog.show();
            }
        });

        TextView eventEndDateTV = v.findViewById(R.id.EventEndDateEditText);

        eventEndDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        eventEndDate = day+"/"+month+"/"+year;
                        eventEndDateTV.setText(eventEndDate);

                    }
                },year,month,day);
                dialog.show();
            }
        });

        submitBtn = v.findViewById(R.id.submitOrganizerBtn);

        EditText eventName = v.findViewById(R.id.EventNameEditText);
        EditText eventDetail = v.findViewById(R.id.EventDetailEditText);
        EditText eventVenue = v.findViewById(R.id.EventVenueEditText);
        CheckBox sportCB = v.findViewById(R.id.CBSport);
        CheckBox carnivalCB = v.findViewById(R.id.CBCarnival);
        CheckBox talkCB = v.findViewById(R.id.CBTalks);
        CheckBox artCB = v.findViewById(R.id.CBArts);
        CheckBox webinarCB = v.findViewById(R.id.CBWebinar);
        CheckBox showcaseCB = v.findViewById(R.id.CBShowcase);
        CheckBox educationalCB = v.findViewById(R.id.CBEducational);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (eventName.getText().toString().isEmpty() || eventDetail.getText().toString().isEmpty() || eventVenue.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Please fill in",Toast.LENGTH_LONG).show();
                }else {
                    if (!sportCB.isChecked() && !carnivalCB.isChecked() && !talkCB.isChecked() && !artCB.isChecked() && !webinarCB.isChecked() &&
                            !showcaseCB.isChecked() && !educationalCB.isChecked()) {

                        Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_LONG).show();

                    } else {
                        eventCategory = setCategory(sportCB, carnivalCB, talkCB, artCB, webinarCB, showcaseCB, educationalCB);

                        eventDate = eventStartDate + " - "+ eventEndDate;

                        eventTime = eventStartTime + " - " + eventEndTime;

                        upload(eventName.getText().toString(), eventVenue.getText().toString(), eventDetail.getText().toString(),
                                eventCategory, eventDate, eventTime, Base64.encodeToString(imageByte, Base64.DEFAULT));
                    }

                }}
        });

        return v;
    }

    public String setCategory(CheckBox C1, CheckBox C2, CheckBox C3, CheckBox C4, CheckBox C5, CheckBox C6, CheckBox C7 ){
        String eventCategory = "";
        if(C1.isChecked()){
            eventCategory +="Sport ";
        }
        if(C2.isChecked()){
            eventCategory +="Carnival ";
        }
        if(C3.isChecked()){
            eventCategory +="Talk ";
        }
        if(C4.isChecked()){
            eventCategory +="Art ";
        }
        if(C5.isChecked()){
            eventCategory +="Webinar ";
        }
        if(C6.isChecked()){
            eventCategory +="Showcase ";
        }
        if(C7.isChecked()){
            eventCategory +="Educational";
        }

        return eventCategory;
    }

    private byte[] imageByte;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            eventImageEditText.setImageURI(imageUri);


                try {
                    Bitmap original = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    original.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    eventImageEditText.setImageBitmap(original);
                    imageByte = stream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }




        }

    }

    public boolean upload(String eventName, String eventVenue, String eventDetail, String eventCategory, String eventDate,
                                   String eventTime, String eventImage){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Image Uploading...");
        progressDialog.show();

        EventData insertEvent = new EventData(eventName,eventVenue,eventDetail,eventCategory,eventDate,eventTime, eventImage);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference eventTable = db.getReference("Event_Node");
        eventTable.child(eventName).setValue(insertEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Successfully added",Toast.LENGTH_LONG).show();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Frame_Layout, new HomeOrganizerFragment());
                fragmentTransaction.commit();

            }
        });
        return true;
    }

}