package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.loginapp.model.Checklist;

public class CreateChecklistActivity extends AppCompatActivity {

    private static String TAG = CreateChecklistActivity.class.getName();
    private int projectId;
    private String projectName;

    private Button createChecklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_checklist);

        // Read project details from intent
        String pId = getIntent().getStringExtra("projectId");
        if (pId == null || pId.isEmpty()) {
            Log.e(TAG, "Unable to get project name. Setting it to -1.");
            projectId = -1;
            return;
        } else {
            projectId = Integer.parseInt(pId);
        }
        projectName = getIntent().getStringExtra("projectName");

        final EditText item_Name = (EditText) findViewById(R.id.nameofchecklist);
        final EditText date = (EditText) findViewById(R.id.dateofchecklist);

        createChecklist = (Button) findViewById(R.id.createbutton);
        createChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checklist newChecklist = new Checklist();
                newChecklist.createChecklist(projectId, item_Name.getText().toString(), date.getText().toString());
                if(newChecklist.getItemId() != -1) {
                    Log.d("Create Checklist", "Checklist creation successfull");
                    Intent intent = new Intent(CreateChecklistActivity.this, ChecklistActivity.class);
                    intent.putExtra("projectId", String.valueOf(projectId));
                    intent.putExtra("projectName", projectName);
                    startActivity(intent);
                }
            }
        });

    }
}