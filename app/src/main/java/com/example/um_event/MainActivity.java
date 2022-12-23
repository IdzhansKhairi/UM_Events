package com.example.um_event;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //hide the bar
        setContentView(R.layout.activity_main);

        //set navigation listener for bottom navigation
        BottomNavigationView BtmNav = findViewById(R.id.bottom_navigation_box);
        BtmNav.setOnNavigationItemSelectedListener(navListener);

        //set the first page when app is running
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

try {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
}catch (Exception e){
    e.printStackTrace();
}

    }


    //handle the navigation listener for bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.nav_setting:
                    selectedFragment = new SettingFragment();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_calendar:
                    selectedFragment = new CalendarFragment();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value" );
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true ;
        }
    };
}