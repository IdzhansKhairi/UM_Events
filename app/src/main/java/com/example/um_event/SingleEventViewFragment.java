package com.example.um_event;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SingleEventViewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SingleEventViewFragment() {
        // Required empty public constructor
    }


    public static SingleEventViewFragment newInstance(String param1, String param2) {
        SingleEventViewFragment fragment = new SingleEventViewFragment();
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

    ArrayList<EventData> eventPageData;

    TextView eventTitle, eventDesc, eventdate, eventtime, eventvenue,eventtag;
    ImageView eventPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_single_event_view, container, false);

        String evtName = getArguments().getString("EventName");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Event_Node");

        eventTitle = v.findViewById(R.id.EventTitle);
        eventDesc = v.findViewById(R.id.eventDesc);
        eventdate= v.findViewById(R.id.TVdate);
        eventtime = v.findViewById(R.id.TVtime);
        eventvenue = v.findViewById(R.id.TVlocation);
        eventtag = v.findViewById(R.id.tag_list);
        eventPic = v.findViewById(R.id.eventDisplayImg);
        eventPageData = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.child("eventName").getValue().toString().equals(evtName)){
                        String eventName = dataSnapshot.child("eventName").getValue().toString();
                        String eventDetail = dataSnapshot.child("eventDetail").getValue().toString();
                        String eventVenue = dataSnapshot.child("eventVenue").getValue().toString();
                        String eventTime = dataSnapshot.child("eventTime").getValue().toString();
                        String eventDate = dataSnapshot.child("eventDate").getValue().toString();
                        String eventCategory = dataSnapshot.child("eventCategory").getValue().toString();
                        String eventImage = dataSnapshot.child("eventImage").getValue().toString();

                        eventTitle.setText(eventName);
                        eventDesc.setText(eventDetail);
                        eventdate.setText(eventDate);
                        eventtime.setText(eventTime);
                        eventvenue.setText(eventVenue);
                        eventtag.setText(eventCategory);
                        eventPic.setImageBitmap(EventAdapter.convertImage(eventImage));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println(eventPageData.size());
        //System.out.println("3. " + eventPageData.get(0).getEventName());
        eventTitle.setTextColor(Color.parseColor("#ffffff"));
        eventvenue.setTextColor(Color.parseColor("#252525"));
        eventdate.setTextColor(Color.parseColor("#252525"));
        eventtime.setTextColor(Color.parseColor("#252525"));

        ImageButton backBtn = v.findViewById(R.id.eventViewBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        CheckBox fav = v.findViewById(R.id.checkBox);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav.isChecked())
                fav.setButtonDrawable(R.drawable.ic_fav_clicked);
                else
                    fav.setButtonDrawable(R.drawable.selector_wishlist_item);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // get the button view
        ImageView mapimage = (ImageView) getView().findViewById(R.id.map);
        // set a onclick listener for when the button gets clicked
        mapimage.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(),
                        MapFragment.class);
                startActivity(mainIntent);
            }
        });
    }



}