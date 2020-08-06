package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import static com.example.loginapp.R.id.toolbar;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView textViewCalender, textViewProject;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        textViewCalender = (TextView) findViewById(R.id.textView);
        textViewProject = (TextView) findViewById(R.id.projecttextView);

        textViewCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this,CalenderActivity.class);
                startActivity(intent);
            }
        });

        textViewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this,ProjectActivity.class);
                startActivity(intent);
            }
        });



        /******* Hooks *******/

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);


        /********* Tool Bar *******/

        setSupportActionBar(toolbar);

        /******** Navigation Drawer Menu *******/

        //Hide or show items

        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.nav_home:
            break;
            case R.id.nav_project:
                Intent project_intent=new Intent(Homepage.this, ProjectActivity.class);
                startActivity(project_intent);
                break;
            case R.id.nav_checklist:
                Intent checklist_intent=new Intent(Homepage.this, CalenderActivity.class);
                startActivity(checklist_intent);
                break;
            case R.id.nav_logout:
                AppState.setUserInstance(null);
                Intent logout = new Intent(Homepage.this, MainActivity.class);
                startActivity(logout);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START); //if any menu options is selected it will close the drawer layout
        return true; //when the item is selected inside it should return true
    }
}