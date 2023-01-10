package com.example.um_event;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {



    ArrayList<EventData> myEventData;
    //EventData[] myEventData;
    FragmentActivity activity;
    //constructor
    public EventAdapter(ArrayList<EventData> myEventData, FragmentActivity activity) {
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
        holder.TVDate.setText(eventData.getEventDate());
        holder.TVTime.setText(eventData.getEventTime());
        holder.TVVenue.setText(eventData.getEventVenue());
        holder.movieImage.setImageBitmap(convertImage(eventData.getEventImage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                SingleEventViewFragment open = new SingleEventViewFragment();

                Bundle bundle = new Bundle();
                bundle.putString("EventName",holder.TVName.getText().toString());
                open.setArguments(bundle);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Frame_Layout,open).addToBackStack(null).commit();
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
