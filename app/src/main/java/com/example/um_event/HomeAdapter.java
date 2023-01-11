package com.example.um_event;

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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    ArrayList<EventData> myEventData;

    FragmentActivity activity;
    //constructor
    public HomeAdapter(ArrayList<EventData> myEventData, FragmentActivity activity) {
        this.myEventData = myEventData;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview,parent,false);
        HomeAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {

        EventData eventData = myEventData.get(position);

        holder.homeEventName.setText(eventData.getEventName());
        holder.homeEventName.setTextColor(Color.parseColor("#222222"));
        holder.homeEventDate.setText(eventData.getEventDate());
        holder.homeEventName.setTextColor(Color.parseColor("#222222"));
        holder.homeEventTime.setText(eventData.getEventTime());
        holder.homeEventName.setTextColor(Color.parseColor("#222222"));
        holder.homeEventVenue.setText(eventData.getEventVenue());
        holder.homeEventName.setTextColor(Color.parseColor("#222222"));
        holder.homeEventImage.setImageBitmap(convertImage(eventData.getEventImage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                SingleEventViewFragment open = new SingleEventViewFragment();

                Bundle bundle = new Bundle();
                bundle.putString("EventName",holder.homeEventName.getText().toString());
                open.setArguments(bundle);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Frame_Layout,open).addToBackStack(null).commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return myEventData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView homeEventImage;
        TextView homeEventName,homeEventTime,homeEventDate,homeEventVenue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homeEventImage = itemView.findViewById(R.id.eventImage);
            homeEventName = itemView.findViewById(R.id.name_label);
            homeEventTime = itemView.findViewById(R.id.time_label);
            homeEventDate = itemView.findViewById(R.id.date_label);
            homeEventVenue = itemView.findViewById(R.id.venue_label);


        }
    }

    public static Bitmap convertImage(String eventImage) {

        // Decode the Base64 encoded string
        byte[] imageBytes = Base64.decode(eventImage, Base64.DEFAULT);
        // Convert the image bytes into a Bitmap object
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

    }

    public void filterDesiredList(ArrayList<EventData> filteredList){
        myEventData = filteredList;
        notifyDataSetChanged();
    }

}
