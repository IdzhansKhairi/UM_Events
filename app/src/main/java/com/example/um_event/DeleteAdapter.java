package com.example.um_event;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.ViewHolder> {



    ArrayList<EventData> myEventData;
    FragmentActivity activity;
    //constructor
    public DeleteAdapter(ArrayList<EventData> myEventData, FragmentActivity activity) {
        this.myEventData = myEventData;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EventData eventData = myEventData.get(position);
        holder.TVName.setText(eventData.getEventName());
        holder.TVName.setTextColor(Color.parseColor("#222222"));
        holder.TVDate.setText(eventData.getEventDate());
        holder.TVDate.setTextColor(Color.parseColor("#222222"));
        holder.TVTime.setText(eventData.getEventTime());
        holder.TVTime.setTextColor(Color.parseColor("#222222"));
        holder.TVVenue.setText(eventData.getEventVenue());
        holder.TVVenue.setTextColor(Color.parseColor("#222222"));
        holder.movieImage.setImageBitmap(convertImage(eventData.getEventImage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to delete this event?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Yes" button, so delete the node.
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Event_Node");
                        reference.child(holder.TVName.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(activity.getApplicationContext(), "Event Successfully deleted",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(activity.getApplicationContext(), "Error deleting Event",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "No" button, so dismiss the dialog.
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

                // Create and show the AlertDialog.
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return myEventData.size();
    }

    //This part fetch the data from the xml file
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView TVName, TVDate,TVTime, TVVenue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.eventImage);
            TVName = itemView.findViewById(R.id.name_label);
            TVDate = itemView.findViewById(R.id.date_label);
            TVTime = itemView.findViewById(R.id.time_label);
            TVVenue = itemView.findViewById(R.id.venue_label);
        }
    }

    public static Bitmap convertImage(String imageData){
        // Decode the Base64 encoded string
        byte[] imageBytes = Base64.decode(imageData, Base64.DEFAULT);
        // Convert the image bytes into a Bitmap object
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public void filterList(ArrayList<EventData> filteredList){
        myEventData = filteredList;
        notifyDataSetChanged();
    }




}