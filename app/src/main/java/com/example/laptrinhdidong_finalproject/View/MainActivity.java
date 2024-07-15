package com.example.laptrinhdidong_finalproject.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.laptrinhdidong_finalproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActionBar actionBar;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar=getSupportActionBar();
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav);
        frameFragment=(FrameLayout)findViewById(R.id.frameFragment);
        Fragment_Home fragmentDF = new Fragment_Home();
        loadFragment(fragmentDF);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId())
                {
                    case R.id.navigation_shop:actionBar.setTitle("Shop");
                        loadFragment(new Fragment_Shop());
                        return true;

                    case R.id.navigation_feedback:actionBar.setTitle("Feedback");
                        loadFragment(new Fragment_Feedback());
                        return true;

                    case R.id.navigation_home:actionBar.setTitle("Home");
                        loadFragment(new Fragment_Home());
                        return true;

                    case R.id.navigation_profile:actionBar.setTitle("Profile");
                        loadFragment(new Fragment_Profile());
                        return true;
                }
                return false;
            }
        });
        actionBar.setTitle("Shop");
    }

    public void loadFragment(androidx.fragment.app.Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.frameFragment,fragment);
        ft.commit();
    }

}