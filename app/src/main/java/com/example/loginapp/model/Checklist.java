package com.example.loginapp.model;

import android.util.Log;

import com.example.loginapp.RestAPIClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Checklist {

    private static String BASE_URL = "http://10.0.2.2:8080/";

    private int pId;
    private int cId;
    private String itemName;
    private String date;

    public int getpId() { return this.pId;}
    public int getItemId() { return this.cId; }
    public String getItemName() { return this.itemName; }
    public String getDate() { return this.date; }

    public Checklist() {
    }

    public Checklist(String name, String date) {
        this.itemName = name;
        this.date = date;
    }

    public Checklist(RestAPIClient client, String name, String date) {
        this.itemName = name;
        this.date = date;
    }

    public Checklist getChecklist(int cId) {
        String GET_CHECKLIST_URL = String.format(BASE_URL + "checklist?id=%s", cId);
        try {
            String result = new RestAPIClient().execute(GET_CHECKLIST_URL).get();
            Log.d("Checklist Model", result);
            convertToChecklist(result);
        } catch (Exception e) {
            Log.e("Checklist Model", "Unable to get the checklist data", e);
            e.printStackTrace();
        }
        return this;
    }

    public Checklist createChecklist(int pId, String item_Name, String date) {
        String CREATE_CHECKLIST_URL = String.format(BASE_URL + "project/%s/checklist/create?item_Name=%s&date=%s", pId, item_Name, date);
        try {
            String result = new RestAPIClient().execute(CREATE_CHECKLIST_URL).get();
            Log.e("Checklist Model", result);
            convertToChecklist(result);
        } catch (Exception e) {
            Log.e("Checklist Model", "Unable to create checklist");
            e.printStackTrace();
        }
        return this;
    }


    public static List<Checklist> getAllChecklists(int pId) {

        String GET_ALL_CHECKLISTS_URL = String.format(BASE_URL + "project/%s/checklists",pId);
        List<Checklist> checklists = new ArrayList<>();
        try {
            String result = new RestAPIClient().execute(GET_ALL_CHECKLISTS_URL).get();
            checklists = convertToList(result);
        } catch (Exception e) {
            Log.e("Checklist Model", "Unable to get all the checklists = " + pId, e);
        }
        return checklists;
    }

    protected void convertToChecklist(String jsonString) throws Exception {
        Log.d(Checklist.class.getName(), "Results - convertToChecklist: " + jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        this.pId = jsonObject.getInt("pId");
        this.cId = jsonObject.getInt("cId");
        this.itemName = jsonObject.getString("item_Name");
        this.date = jsonObject.getString("date");
    }


    protected static List<Checklist> convertToList(String jsonString) throws Exception {
        Log.d(Checklist.class.getName(), "Results - convertToList: " + jsonString);
        JSONArray array = new JSONArray(jsonString);
        List<Checklist> checklists = new ArrayList<>();
        for(int i=0; i<array.length();i++) {
            JSONObject obj = array.getJSONObject(i);
            Log.d(Checklist.class.getName(), "Json Object = " + obj.toString());
            Checklist newChecklist = new Checklist();
            newChecklist.pId = obj.getInt("pId");
            newChecklist.cId = obj.getInt("cId");
            newChecklist.itemName = obj.getString("item_Name");
            newChecklist.date = obj.getString("date");
            checklists.add(newChecklist);
        }
        return checklists;
    }

    @Override
    public String toString() {
        return String.format("Checklist [pId = %s, cId = %s, name = %s, date = %s]", pId, cId, itemName, date);
    }
}

