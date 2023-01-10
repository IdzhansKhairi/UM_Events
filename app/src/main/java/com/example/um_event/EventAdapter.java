package com.example.um_event;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void onItemCLick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }


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
        ViewHolder viewHolder = new ViewHolder(view,mlistener);
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
               // System.out.println(" IMAN IMAN IMAN IMAN IMAN IMAN IMAN IMAN IMAN IMAN IMAN IMAN IMAN IMAN IMAN IMAN ");

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

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.eventImage);
            TVName = itemView.findViewById(R.id.name_label);
            TVDate = itemView.findViewById(R.id.date_label);
            TVTime = itemView.findViewById(R.id.time_label);
            TVVenue = itemView.findViewById(R.id.venue_label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onItemCLick(position);
                        }
                    }
                }
            });
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
