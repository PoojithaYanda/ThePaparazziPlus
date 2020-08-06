package com.example.loginapp.model;

import android.util.Log;

import com.example.loginapp.RestAPIClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private static String BASE_URL = "http://10.0.2.2:8080/";

    private int userId;
    private int pId;
    private String customerName;
    private String customerEmail;
    private String projectName;
    private String date;

    public int getUserId() { return this.userId;}
    public int getpId() { return this.pId;}
    public String getCustomerName() { return this.customerName;}
    public String getCustomerEmail() { return this.customerEmail;}
    public String getProjectName() { return this.projectName;}
    public String getDate() { return this.date;}

    public Project() {
    }

    public Project(String name, String date) {
        this.projectName = name;
        this.date = date;
    }

    public Project getProject(int pId) {
        String GET_PROJECT_URL = String.format(BASE_URL + "project?id=%s",pId);
        try {
            String result = new RestAPIClient().execute(GET_PROJECT_URL).get();
            Log.d("Project Model", result);
            convertToProject(result);
        } catch (Exception e) {
            Log.e("Project Model", "Unable to get  the project data:", e);
            e.printStackTrace();
        }
        return this;
    }

    public Project createProject(int userId, String projectName, String customerName, String customerEmail, String date) {
        String CREATE_PROJECT_URL = String.format(BASE_URL + "user/%s/project/create?projectName=%s&customerName=%s&customerEmail=%s&date=%s", userId, projectName, customerName, customerEmail, date);
        try {
            String result = new RestAPIClient().execute(CREATE_PROJECT_URL).get();
            Log.d("Project Model", result);
            convertToProject(result);
        } catch (Exception e) {
            Log.e("Project Model", "Unable to create a Project", e);
            e.printStackTrace();
        }
        return this;
    }

    public static List<Project> getAllProjects(int userId) {
        String GET_ALL_PROJECTS_URL = String.format(BASE_URL + "user/%s/projects", userId);
        List<Project> projects= new ArrayList<>();
        try {
            String result = new RestAPIClient().execute(GET_ALL_PROJECTS_URL).get();
            projects =  convertToList(result);
        } catch (Exception e) {
            Log.e("Project Model", "Unable to get all projects for user = " + userId, e);
        }

        return projects;
    }

    protected void convertToProject(String jsonString) throws Exception {
        Log.d(Project.class.getName(), "Results - convertToProject : " + jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        this.userId = jsonObject.getInt("userId");
        this.customerEmail = jsonObject.getString("customerEmail");
        this.customerName = jsonObject.getString("customerName");
        this.projectName = jsonObject.getString("projectName");
        this.date = jsonObject.getString("date");
        this.pId = jsonObject.getInt("pId");
    }

    protected static List<Project> convertToList(String jsonString) throws Exception {
        Log.d(Project.class.getName(), "Results - convertToList : " + jsonString);
        JSONArray array = new JSONArray(jsonString);
        List<Project> projects = new ArrayList<>();
        for (int i=0 ; i<array.length();i++) {
            JSONObject obj = array.getJSONObject(i);
            Project newProject = new Project();
            newProject.userId = obj.getInt("userId");
            newProject.customerEmail = obj.getString("customerEmail");
            newProject.customerName = obj.getString("customerName");
            newProject.projectName = obj.getString("projectName");
            newProject.date = obj.getString("date");
            newProject.pId = obj.getInt("pId");
            projects.add(newProject);
        }

        return projects;
    }

    public String sendInvoice() {
        String SEND_INVOICE_URL = String.format(BASE_URL + "project/%s/sendinvoice", pId);

        String result = "Unable to send email.";
        try {
            result = new RestAPIClient().execute(SEND_INVOICE_URL).get();
        } catch (Exception e) {
            Log.e("Project Model", "Unable to get all projects for user = " + userId, e);
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("Project [pId = %s, name = %s, uId = %s, date = %s]", pId, projectName, userId, date);
    }

}
