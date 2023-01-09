package com.example.um_event;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsertEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertEvent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsertEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsertEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static InsertEvent newInstance(String param1, String param2) {
        InsertEvent fragment = new InsertEvent();
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

    EditText eventName, eventDetail, eventVenue;
    DatePicker pickDate; TimePicker pickTime;
    Button insertBtn;
    String eventCategory, eventTime, eventDate;
    ImageView eventImg;
    CheckBox sportCB, carnivalCB, talkCB, artCB, webinarCB, showcaseCB, educationalCB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_event, container, false);

        eventName = view.findViewById(R.id.ETEventName);
        eventDetail = view.findViewById(R.id.ETEventDetail);
        eventVenue = view.findViewById(R.id.ETEventVenue);
        pickDate = view.findViewById(R.id.datePicker);
        pickTime = view.findViewById(R.id.timePicker);
        sportCB = view.findViewById(R.id.CBSport); carnivalCB = view.findViewById(R.id.CBCarnival); talkCB = view.findViewById(R.id.CBTalk);
        artCB = view.findViewById(R.id.CBArt); webinarCB = view.findViewById(R.id.CBWebinar); showcaseCB = view.findViewById(R.id.CBShowcase);
        educationalCB = view.findViewById(R.id.CBEducational);
        insertBtn = view.findViewById(R.id.InserEventBtn);
        eventImg = view.findViewById(R.id.octaverseImg);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (eventName.getText().toString().isEmpty() || eventDetail.getText().toString().isEmpty() || eventVenue.getText().toString().isEmpty()){
                    Toast.makeText(null,"Please fill in",Toast.LENGTH_LONG).show();
                }else{
                    if (!sportCB.isChecked() && !carnivalCB.isChecked() && !talkCB.isChecked() && !artCB.isChecked() && !webinarCB.isChecked() &&
                            !showcaseCB.isChecked() && !educationalCB.isChecked()){

                        Toast.makeText(getContext(),"Please select a category",Toast.LENGTH_LONG).show();

                    }else{
                        eventCategory = setCategory(sportCB,carnivalCB,talkCB, artCB, webinarCB, showcaseCB, educationalCB );

                        eventDate = setDateString(pickDate);

                        eventTime = setTime(pickTime);

                        String eventImage = setImage(eventImg);

                        InsertEventData(eventName.getText().toString(),eventVenue.getText().toString(),eventDetail.getText().toString(),
                                eventCategory,eventDate,eventTime,eventImage);
                    }
                }
            }
        });

        return view;
    }

    public boolean InsertEventData(String eventName, String eventVenue, String eventDetail, String eventCategory, String eventDate,
                              String eventTime, String eventImage){
        EventData insertEvent = new EventData(eventName,eventVenue,eventDetail,eventCategory,eventDate,eventTime, eventImage);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference eventTable = db.getReference("Event_Node");
        eventTable.child(eventName).setValue(insertEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(),"Successfully added",Toast.LENGTH_LONG).show();
            }
        });
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String setTime(TimePicker timePicker){
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        String timeString = timeFormat.format(calendar.getTime());

        return timeString;
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

    public String setImage(ImageView imageView){

        // Get the image view's drawable
        Drawable drawable = imageView.getDrawable();
        // Convert the drawable to a bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        // Convert the bitmap to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageBytes = stream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public String setDateString(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        return dateFormat.format(calendar.getTime());
    }
}