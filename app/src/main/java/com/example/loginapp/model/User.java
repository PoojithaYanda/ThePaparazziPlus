package com.example.loginapp.model;

import android.util.Log;

import androidx.annotation.VisibleForTesting;

import com.example.loginapp.RestAPIClient;

import org.json.JSONObject;


public class User {

    private static String BASE_URL = "http://10.0.2.2:8080/";

    private int userId;
    private String userName;
    private String emailAddress;
    private int status;
    private String errorMessage;
    private String password;

    private RestAPIClient client;

    public User() {
        this.client = new RestAPIClient();
    }

    public User(RestAPIClient client) {
        this.client = client;
    }

    public int getUserId() { return this.userId; }
    public String getUserName() { return this.userName; }
    public String getEmailAddress() { return this.emailAddress; }
    public int getStatus() { return this.status; }
    public String getErrorMessage() { return this.errorMessage; }

    public User validateUser(String userEmail, String password) {
        String VALIDATE_USER_URL = String.format( BASE_URL + "user/validate?email=%s&password=%s", userEmail, password);
        try {
            String result = client.execute(VALIDATE_USER_URL).get();
            Log.d("User Model", result);
            convertToUser(result);
        } catch (Exception e) {
            Log.e("User Model", "Unable to validate user", e);
            e.printStackTrace();
        }
        return this;
    }

    public User createUser(String userName, String emailAddress, String password){

        String CREATE_USER_URL = String.format(BASE_URL + "user/create?name=%s&email=%s&password=%s",userName,emailAddress,password);
        try{
            String result = client.execute(CREATE_USER_URL).get();
            Log.d("User Model",result);
            convertToUser(result);
        } catch (Exception e) {
            Log.e("User Model","Unable to create an User", e);
            e.printStackTrace();;
        }

        return this;
    }

    protected void convertToUser(String jsonString) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonString);
        this.userId = jsonObject.getInt("userId");
        this.userName = jsonObject.getString("userName");
        this.emailAddress = jsonObject.getString("emailAddress");
        this.status = jsonObject.getInt("status");
        this.errorMessage = jsonObject.getString("errorMessage");
    }
}
