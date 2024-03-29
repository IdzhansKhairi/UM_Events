package com.example.um_event;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Test_appearance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Test_appearance extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Test_appearance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Test_appearance.
     */
    // TODO: Rename and change types and number of parameters
    public static Test_appearance newInstance(String param1, String param2) {
        Test_appearance fragment = new Test_appearance();
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

    private EditText orgID, pass, email, phone;
    private Button sbmtBtn;
    private ImageView getImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test_appearance, container, false);

        sbmtBtn = v.findViewById(R.id.submitBtn);
        orgID = v.findViewById(R.id.get_org_id);
        pass = v.findViewById(R.id.getPassword);
        email = v.findViewById(R.id.get_email);
        phone = v.findViewById(R.id.getPhoneNo);
        //getImage = v.findViewById(R.id.convertImg);

        FirebaseDatabase db = FirebaseDatabase.getInstance();

        sbmtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////////////////////////////////////////////
                if(orgID.getText().toString().isEmpty() || pass.getText().toString().isEmpty() ||email.getText().toString().isEmpty()||
                        phone.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Fill All Blanks", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        OrganizerData orgData = new OrganizerData(orgID.getText().toString(),pass.getText().toString(),
                                email.getText().toString(),phone.getText().toString());

                        DatabaseReference OrgData = db.getReference("Organizers_Credentials");
                        OrgData.child(orgID.getText().toString()).setValue(orgData);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        return v;
    }

    public String createSiswamail(String matricNo){
        matricNo += "@siswa.um.edu.my";
        String siswa = matricNo.toLowerCase();
        return siswa;
    }
    public String unifyMatricNo(String matricNo){
    String unified = matricNo.toUpperCase();
    return unified;
    }
}