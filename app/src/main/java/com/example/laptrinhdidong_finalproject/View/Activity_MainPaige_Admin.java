package com.example.laptrinhdidong_finalproject.View;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.laptrinhdidong_finalproject.Cotroller.AdminHandler;
import com.example.laptrinhdidong_finalproject.Cotroller.Fragment_ManageProductCatalog;
import com.example.laptrinhdidong_finalproject.Cotroller.Fragment_ProductManagement;
import com.example.laptrinhdidong_finalproject.Cotroller.Fragment_Revenue;
import com.example.laptrinhdidong_finalproject.R;
import com.google.android.material.navigation.NavigationView;

public class Activity_MainPaige_Admin extends AppCompatActivity {

    private static final String DB_NAME = "drinkingmanager";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Admin";
    private static final String adminID = "AdminID";
    private static final String adminName = "AdminName";
    private static final String loginAccount = "LoginAccount";
    private static final String loginPassword = "LoginPassword";
    private static final String PATH = "/data/data/com.example.laptrinhdidong_finalproject/database/drinkingmanager.db";

    AdminHandler adminHandler;

    SQLiteDatabase sqLiteDatabase;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    TextView tvTenAD, tvTKAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mainpage_admin);
        addControl();
        adminHandler = new AdminHandler(Activity_MainPaige_Admin.this,
                DB_NAME, null, DB_VERSION);
        //----------------Nhận us và pass từ trang login admin
        Intent intent = getIntent();
        String us = intent.getStringExtra("us");
        String pass = intent.getStringExtra("pass");
        //Log.d("Us va Pass", us + pass);
        //----------------Gọi getADName bên AdminHandler để lấy tên của admin hiện tại
        String nameAD = adminHandler.getADName(us, pass);
        Log.d("Name admin: ", nameAD);

        //----------------Gắn thông tin đăng nhập cho các text view bên header_layout
        View headerView = navigationView.getHeaderView(0);
        tvTenAD = headerView.findViewById(R.id.tvTenAD);
        tvTKAD = headerView.findViewById(R.id.tvTKAD);

        tvTenAD.setText(nameAD);
        tvTKAD.setText("Account: " + us);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)
                findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_drawer);
        Fragment_Revenue fragmentDF = new Fragment_Revenue();
        replaceFragment(fragmentDF);
        drawerLayout.closeDrawer(GravityCompat.START);
        addEvent();

    }

    void addControl() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_drawer);
        tvTenAD = (TextView) findViewById(R.id.tvTenAD);
        tvTKAD = (TextView) findViewById(R.id.tvTKAD);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Activity_Login_Admin.class);
        startActivity(intent);
        finish();
    }

    void addEvent()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logOut:
                        Intent intent = new Intent(Activity_MainPaige_Admin.this,
                                Activity_Login_Admin.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        return true;
                    case R.id.revenue:
                        Fragment_Revenue fragment1 = new Fragment_Revenue();
                        replaceFragment(fragment1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.manageProductCatalog:
                        Fragment_ManageProductCatalog fragment2 = new Fragment_ManageProductCatalog();
                        replaceFragment(fragment2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.productManagement:
                        Fragment_ProductManagement fragment3 = new Fragment_ProductManagement();
                        replaceFragment(fragment3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    default:

                        return false;
                }
            }
        });
    }
    // Method to replace fragment in content_frame
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}


