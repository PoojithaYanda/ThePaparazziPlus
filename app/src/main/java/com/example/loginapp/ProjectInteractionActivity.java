package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ProjectInteractionActivity extends AppCompatActivity {

    private static String TAG = ProjectInteractionActivity.class.getName();
    private int projectId;
    private String projectName;
    TextView checklisttextView,invoicetextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_interaction);

        checklisttextView = (TextView) findViewById(R.id.checklisttextView);
        invoicetextView = (TextView) findViewById(R.id.invoicetextView);

        String pId = getIntent().getStringExtra("projectId");
        if (pId == null || pId.isEmpty()) {
            Log.e(TAG, "Unable to get project name. Setting it to -1.");
            projectId = -1;
            return;
        } else {
            projectId = Integer.parseInt(pId);
        }
        projectName = getIntent().getStringExtra("projectName");

        invoicetextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProjectInteractionActivity.this,InvoiceActivity.class);
                intent.putExtra("projectId", String.valueOf(projectId));
                intent.putExtra("projectName", projectName);
                startActivity(intent);
            }
        });

        // Invoice title
        TextView titleView = findViewById(R.id.projectInteractionpage);
        titleView.setText(String.format("Project Details for %s", projectName));

        checklisttextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProjectInteractionActivity.this,ChecklistActivity.class);
                intent.putExtra("projectId", String.valueOf(projectId));
                intent.putExtra("projectName", projectName);
                startActivity(intent);
            }
        });
    }
}