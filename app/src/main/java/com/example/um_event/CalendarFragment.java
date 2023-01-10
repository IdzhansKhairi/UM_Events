package com.example.um_event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.um_event.CalendarUtils.daysInMonthArray;
import static com.example.um_event.CalendarUtils.monthYearFromDate;

import com.example.um_event.databinding.FragmentCalendarAddEventBinding;
import com.example.um_event.databinding.FragmentCalendarBinding;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    //private NavController navController;
    //FragmentCalendarBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initWidgets(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = LocalDate.now();
        }
        setMonthView();
        Button previousMonthButton = view.findViewById(R.id.previous_month_button);
        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousMonthAction(view);
            }
        });

        Button nextMonthButton = view.findViewById(R.id.next_month_button);
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonthAction(view);
            }
        });

/*      Button weeklyButton = (Button)view.findViewById(R.id.weekly_button);
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarAddEventFragment nextFrag= new CalendarAddEventFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.calendarFragment, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });*/



/*
        Button weeklyButton = view.findViewById(R.id.weekly_button);
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weeklyAction(view);
            }
        });*/
/*
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        binding.weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarAddEventFragment CalendarAddEventFragment = new CalendarAddEventFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,CalendarAddEventFragment);
                transaction.commit();
            }
        });*/

        /*Button weeklyButton = view.findViewById(R.id.weekly_button);
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment nav_calendar = getParentFragmentManager().findFragmentById(R.id.calendarAddEventFragment);
                NavController c = NavHostFragment.findNavController(nav_calendar);
                c.navigate(R.id.calendarFragment);
            }
        });*/

        return view;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        //navController = Navigation.findNavController(getParentFragment().getView(), R.id.nav_calendar);
        Button weeklyButton = view.findViewById(R.id.weekly_button);
        View.OnClickListener OCLMessage = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.calendarToAddEvent);
            }
        };
        weeklyButton.setOnClickListener(OCLMessage);
    }


    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void previousMonthAction(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        }
        setMonthView();
    }

    public void nextMonthAction(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        }
        setMonthView();
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(getActivity(), CalendarAddEventFragment.class));
    }


}