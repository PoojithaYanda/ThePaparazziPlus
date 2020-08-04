package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.loginapp.model.Checklist;
import com.example.loginapp.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ChecklistActivity extends AppCompatActivity {

    private static String TAG = ChecklistActivity.class.getName();
    private int projectId;
    private String projectName;
    ImageView newChecklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        String pId = getIntent().getStringExtra("projectId");
        if (pId == null || pId.isEmpty()) {
            Log.e(TAG, "Unable to get project name. Setting it to -1.");
            projectId = -1;
            return;
        } else {
            projectId = Integer.parseInt(pId);
        }
        projectName = getIntent().getStringExtra("projectName");

        // Checklist title
        TextView titleView = findViewById(R.id.checklistpageTitle);
        titleView.setText(String.format("Checklist for %s", projectName));

        newChecklist = findViewById(R.id.newChecklistId);
        newChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChecklistActivity.this, CreateChecklistActivity.class);
                intent.putExtra("projectId", String.valueOf(projectId));
                intent.putExtra("projectName", projectName);
                startActivity(intent);
            }
        });

        renderChecklist();
    }


    private void renderChecklist() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.checklistTblLayout);

        if (tableLayout == null || projectId == -1) {
            Log.e(ChecklistActivity.class.getName(), "Unable to render checklist table.");
            return;
        }

        final List<Checklist> checklists = Checklist.getAllChecklists(projectId);

        for (int i = 0; i < checklists.size(); i++) {
            final Checklist thisChecklist = checklists.get(i);

            TableRow newRow = new TableRow(this);
            newRow.setPadding(10, 10, 10, 10);
            newRow.setBackgroundColor(Color.parseColor("#F1F1F1"));

            final TextView nameView = new TextView(this);
            TableRow.LayoutParams nameParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3.0f);
            nameView.setLayoutParams(nameParams);
            nameView.setText(thisChecklist.getItemName());

            final TextView dateView = new TextView(this);
            TableRow.LayoutParams dateParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            dateView.setLayoutParams(dateParams);
            dateView.setText(thisChecklist.getDate());

            newRow.addView(nameView);
            newRow.addView(dateView);

            tableLayout.addView(newRow, i + 1);
        }
    }
}