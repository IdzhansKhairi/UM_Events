package com.example.um_event;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.sql.Time;
import java.text.DateFormat;
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
    RadioGroup radioGroup;
    String eventCategory, eventTime, eventDate;
    ImageView eventImg;
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
        radioGroup = view.findViewById(R.id.radioGroup);
        insertBtn = view.findViewById(R.id.InserEventBtn);
        eventImg = view.findViewById(R.id.octaverseImg);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (eventName.getText().toString().isEmpty() || eventDetail.getText().toString().isEmpty() || eventVenue.getText().toString().isEmpty()){
                    Toast.makeText(null,"Please fill in",Toast.LENGTH_LONG).show();
                }else{
                    if (radioGroup.getCheckedRadioButtonId()!=-1){

                        eventCategory = setCategory(radioGroup);
                        int dayOfMonth = pickDate.getDayOfMonth();
                        DateFormat dateFormat = DateFormat.getDateInstance();
                        eventDate = dateFormat.format(dayOfMonth);

                        eventTime = setTime(pickTime);

                        Integer eventImage = setImage(eventImg);

                InsertData(eventName.getText().toString(),eventVenue.getText().toString(),eventDetail.getText().toString(),
                        eventCategory,eventDate,eventTime,eventImage);
                    }else{
                        Toast.makeText(getContext(),"Please select a category",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    public static boolean InsertData(String eventName, String eventVenue, String eventDetail,String eventCategory, String eventDate,
                                     String eventTime,Integer eventImage){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference eventTable = db.getReference("Event_Node");

        EventData insertEvent = new EventData(eventName,eventVenue,eventDetail,eventCategory,eventDate,eventTime,eventImage);
        eventTable.push().setValue(insertEvent);
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String setTime(TimePicker timePicker){
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        DateFormat timeFormat = DateFormat.getTimeInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        String timeString = timeFormat.format(calendar.getTime());
        return timeString;
    }

    public static String setCategory(RadioGroup radioGroup){
        int checkedRBid = radioGroup.getCheckedRadioButtonId();
         String eventCategory = "";
        switch (checkedRBid){
            case R.id.RBSport:
                eventCategory =  "Sport";
                break;
            case R.id.RBCarnival:
                eventCategory =  "Carnival";
                break;
            case R.id.RBTalk:
                eventCategory =  "Talk";
                break;
            case R.id.RBArt:
                eventCategory =  "Art";
                break;
            case R.id.RBWebinar:
                eventCategory =  "Webinar";
                break;
            case R.id.RBShowcase:
                eventCategory =  "Showcase";
                break;
            case R.id.RBEducational:
                eventCategory =  "Educational";
                break;
            default:
                Toast.makeText(null, "Please select a category",Toast.LENGTH_LONG).show();
                break;
        }
        return eventCategory;
    }

    public static Integer setImage(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        // Convert the bytes into an integer
        ByteBuffer buffer = ByteBuffer.wrap(data);
        Integer imageAsInt = buffer.getInt();
        return  imageAsInt;
    }
}