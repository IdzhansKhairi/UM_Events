package com.example.um_event;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    long numChildren;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        EventAdapter myEventAdapter;
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //EventData[] dataArray = new EventData[6];
        ArrayList<EventData> myEventData = new ArrayList<>();
        myEventAdapter = new EventAdapter(myEventData,getActivity());

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference database = db.getReference("Event_Node");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot :snapshot.getChildren() ){
                    EventData getData = dataSnapshot.getValue(EventData.class);
                    myEventData.add(getData);

                }
                myEventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        recyclerView.setAdapter(myEventAdapter);
        return v;
    }
}



//private void readData(String username) {
//
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                if (task.isSuccessful()){
//
//                    if (task.getResult().exists()){
//
//                        Toast.makeText(ReadData.this,"Successfully Read",Toast.LENGTH_SHORT).show();
//                        DataSnapshot dataSnapshot = task.getResult();
//                        String firstName = String.valueOf(dataSnapshot.child("firstName").getValue());
//                        String lastName = String.valueOf(dataSnapshot.child("lastName").getValue());
//                        String age = String.valueOf(dataSnapshot.child("age").getValue());
//                        binding.tvFirstName.setText(firstName);
//                        binding.tvLastName.setText(lastName);
//                        binding.tvAge.setText(age);
//
//
//                    }else {
//
//                        Toast.makeText(ReadData.this,"User Doesn't Exist",Toast.LENGTH_SHORT).show();
//
//                    }
//
//
//                }else {
//
//                    Toast.makeText(ReadData.this,"Failed to read",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//    }

// Iterate over all the children of the database reference
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        // Get the value at this snapshot and create an eventData object
//
//                        String name = snapshot.child("eventName").getValue(String.class);
//        String date = snapshot.child("eventDate").getValue(String.class);
//        String venue = snapshot.child("eventVenue").getValue(String.class);
//        String time = snapshot.child("eventTime").getValue(String.class);
//        String detail = snapshot.child("eventDetail").getValue(String.class);
//        String category = snapshot.child("eventCategory").getValue(String.class);
//        String image = snapshot.child("eventImage").getValue(String.class);
//        EventData data = new EventData(name, venue, detail, category,date,time,image);