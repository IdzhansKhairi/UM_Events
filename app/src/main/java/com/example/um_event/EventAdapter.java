package com.example.um_event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    //This part fetch the data from the xml file
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView TVName, TVDetail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.CVImage);
            TVName = itemView.findViewById(R.id.CVName);
            TVDetail = itemView.findViewById(R.id.CVDetail);

        }
    }

    EventData[] myEventData;
    Context context;
    //constructor
    public EventAdapter(EventData[] myEventData) {
        this.myEventData = myEventData;
       // this.context = activity;
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
        final EventData myEventDataList = myEventData[position];
        holder.TVName.setText(myEventDataList.getEventName());
        holder.TVDetail.setText(myEventDataList.getEventDate());
       // holder.movieImage.setImageResource(myEventDataList.getEventImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"YOu clicked RIck Roll!!!" , Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return myEventData.length;
    }


}
