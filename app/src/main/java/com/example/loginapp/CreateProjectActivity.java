package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.loginapp.model.Project;
import com.example.loginapp.model.User;

public class CreateProjectActivity extends AppCompatActivity {

    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        final User currentUser = AppState.getUserInstance();
        if (currentUser == null) {
            Log.e(CreateProjectActivity.class.getName(), "AppState User is not set.");
            return;
        }

        final EditText projectName = (EditText) findViewById(R.id.nameofproject);
        final EditText customerName = (EditText) findViewById(R.id.customername);
        final EditText customerEmail = (EditText) findViewById(R.id.customeremail);
        final EditText date = (EditText) findViewById(R.id.datecustomername);
        create= (Button) findViewById(R.id.createbutton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Project newProject = new Project();
                newProject.createProject(currentUser.getUserId(), projectName.getText().toString(),customerName.getText().toString(),customerEmail.getText().toString(),date.getText().toString());
                if(newProject.getpId() != -1) {
                    Log.d("Create Project","Creation Successfull");
                    Intent intent = new Intent(CreateProjectActivity.this,ProjectActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}