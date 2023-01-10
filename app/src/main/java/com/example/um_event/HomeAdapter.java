package com.example.um_event;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    OnItemClickListener mlistener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mlistener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemCLick(int position);
    }




    ArrayList<EventData> myEventData;
    //EventData[] myEventData;
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
        View view = layoutInflater.inflate(R.layout.card_home_item,parent,false);
        HomeAdapter.ViewHolder viewHolder = new ViewHolder(view,mlistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {

        EventData eventData = myEventData.get(position);

        holder.homeEventName.setText(eventData.getEventName());
        holder.homeEventDate.setText(eventData.getEventDate());
        holder.homeEventTime.setText(eventData.getEventTime());
        holder.homeEventVenue.setText(eventData.getEventVenue());
        holder.homeEventImage.setImageBitmap(convertImage(eventData.getEventImage()));

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

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView homeEventImage;
        TextView homeEventName,homeEventTime,homeEventDate,homeEventVenue;

        public ViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);

            homeEventImage = itemView.findViewById(R.id.HomeEventImage);
            homeEventName = itemView.findViewById(R.id.HomeEventName);
            homeEventTime = itemView.findViewById(R.id.HomeEventTime);
            homeEventDate = itemView.findViewById(R.id.HomeEventDate);
            homeEventVenue = itemView.findViewById(R.id.HomeEventVenue);

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

    public static Bitmap convertImage(String eventImage) {

        // Decode the Base64 encoded string
        byte[] imageBytes = Base64.decode(eventImage, Base64.DEFAULT);
        // Convert the image bytes into a Bitmap object
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

    }

    public void filterList(ArrayList<EventData> filteredList){
        myEventData = filteredList;
        notifyDataSetChanged();
    }

}
