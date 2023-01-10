package com.example.um_event;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.um_event.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            // We wanted when the users tap, it will replace it with its particular fragment
            switch (item.getItemId()) {

                case R.id.nav_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.nav_search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.nav_calendar:
                    replaceFragment(new CalendarFragment());
                    break;
                case R.id.nav_setting:
                    //replaceFragment(new Test_appearance());
                   // replaceFragment(new InsertEvent());
//                    replaceFragment(new SettingsFragment());
                    replaceFragment(new SettingClientFragment());
                    break;
            }

            return true;
        });

    }


    //method to change fragment based on the buttons user tap
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frame_Layout, fragment);
        fragmentTransaction.commit();
    }

}