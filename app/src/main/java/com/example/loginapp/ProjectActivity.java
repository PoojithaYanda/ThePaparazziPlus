package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.loginapp.model.Project;
import com.example.loginapp.model.User;

import java.util.List;

public class ProjectActivity extends AppCompatActivity {

    private boolean table_flag = false;

    ImageView newProject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        // Create New Project
        newProject = (ImageView) findViewById(R.id.newProjectId);
        newProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProjectActivity.this, CreateProjectActivity.class);
                startActivity(intent);
            }
        });

        renderProjects();
    }

    private void renderProjects() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.projectTblLayout);

        if (tableLayout == null) {
            Log.e(ProjectActivity.class.getName(), "Unable to find table layout.");
            return;
        }

        User user = AppState.getUserInstance();
        if (user == null) {
            Log.e(ProjectActivity.class.getName(), "User is not set in AppState.");
            return;
        }

        final List<Project> projects = Project.getAllProjects(AppState.getUserInstance().getUserId());

        for (int i=0; i < projects.size(); i++) {
            final Project thisProject = projects.get(i);
            Log.d(ProjectActivity.class.getName(), thisProject.toString());
            TableRow newRow = new TableRow(this);
            newRow.setPadding(10, 10, 10, 10);
            newRow.setBackgroundColor(Color.parseColor("#F1F1F1"));

            final TextView nameView = new TextView(this);
            TableRow.LayoutParams nameParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3.0f);
            nameView.setLayoutParams(nameParams);
            nameView.setText(projects.get(i).getProjectName());
            nameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(ProjectActivity.this, ProjectInteractionActivity.class);
                    Log.d(ProjectActivity.class.getName(), String.format("pId = %s , name = %s", thisProject.getpId(), thisProject.getProjectName()));
                    intent.putExtra("projectId", String.valueOf(thisProject.getpId()));
                    intent.putExtra("projectName", thisProject.getProjectName());
                    startActivity(intent);
//                    Intent intent = new Intent(ProjectActivity.this, ChecklistActivity.class);
//                    Log.d(ProjectActivity.class.getName(), String.format("pId = %s , name = %s", thisProject.getpId(), thisProject.getProjectName()));
//                    intent.putExtra("projectId", String.valueOf(thisProject.getpId()));
//                    intent.putExtra("projectName", thisProject.getProjectName());
//                    startActivity(intent);
                }
            });

            final TextView dateView = new TextView(this);
            TableRow.LayoutParams dateParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            dateView.setLayoutParams(dateParams);
            dateView.setText(projects.get(i).getDate());

            newRow.addView(nameView);
            newRow.addView(dateView);

            tableLayout.addView(newRow, i+1);
        }

    }

}