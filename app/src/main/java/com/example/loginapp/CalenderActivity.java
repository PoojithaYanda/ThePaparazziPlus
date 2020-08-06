package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.loginapp.model.Project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalenderActivity extends AppCompatActivity {

    Toolbar toolbar;
    CalendarView calendarView;
    TextView myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        toolbar = findViewById(R.id.toolbar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);

        Date currentDate = new Date(calendarView.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        renderProjectsForDate(dateFormat.format(currentDate));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = String.format("%02d/%02d/%04d", (i1 + 1), i2, i);
                myDate.setText(date);
                renderProjectsForDate(date);
            }
        });
    }

    private void renderProjectsForDate(String date) {
        // Get All Projects for User
        final List<Project> projects = Project.getAllProjects(AppState.getUserInstance().getUserId());

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.projectsListLayout);
        TableRow headerRow =  (TableRow) tableLayout.getChildAt(0);
        tableLayout.removeAllViews();
        tableLayout.addView(headerRow);

        int counter=0;
        for(int j=0;j<projects.size();j++) {
            final Project thisProject = projects.get(j);
            Log.d(ProjectActivity.class.getName(), thisProject.getDate() + " , " + date);
            if(date.equals(thisProject.getDate())) {
                counter++;
                TableRow newRow = new TableRow(CalenderActivity.this);
                newRow.setPadding(10, 10, 10, 10);
                newRow.setBackgroundColor(Color.parseColor("#F1F1F1"));

                final TextView nameView = new TextView(calendarView.getContext());
                TableRow.LayoutParams nameParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                nameView.setLayoutParams(nameParams);
                nameView.setText(projects.get(j).getProjectName());
                nameView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CalenderActivity.this, ProjectInteractionActivity.class);
                        intent.putExtra("projectId", String.valueOf(thisProject.getpId()));
                        intent.putExtra("projectName", thisProject.getProjectName());
                        startActivity(intent);
                    }
                });

                newRow.addView(nameView);
                tableLayout.addView(newRow, counter);
            }
        }
        if(counter==0) {
            TableRow newRow = new TableRow(CalenderActivity.this);
            newRow.setPadding(10, 10, 10, 10);
            newRow.setBackgroundColor(Color.parseColor("#F1F1F1"));
            final TextView nameView = new TextView(calendarView.getContext());
            TableRow.LayoutParams nameParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            nameView.setLayoutParams(nameParams);
            nameView.setText("No Projects for this day");
            newRow.addView(nameView);
            tableLayout.addView(newRow, 1);
        }
    }
}