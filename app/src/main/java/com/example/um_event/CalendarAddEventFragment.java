package com.example.um_event;

import static com.example.um_event.CalendarUtils.daysInWeekArray;
import static com.example.um_event.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAddEventFragment extends Fragment implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_add_event, container, false);
        initWidgets(view);
        setWeekView();

        Button previousWeekButton = view.findViewById(R.id.previous_week_button);
        previousWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousWeekAction(view);
            }
        });

        Button nextWeekButton = view.findViewById(R.id.next_week_button);
        nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextWeekAction(view);
            }
        });

        Button newEventButton = view.findViewById(R.id.new_event_button);
        newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newEventAction(view);
            }
        });

        return view;
    }

    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        eventListView = view.findViewById(R.id.eventListView);
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<CalendarEvents> dailyEvents = CalendarEvents.eventsForDate(CalendarUtils.selectedDate);
        CalendarEventAdapter eventAdapter = new CalendarEventAdapter(getContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void previousWeekAction(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        }
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        }
        setWeekView();
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(getActivity(), CalendarEventEditFragment.class));
    }
}