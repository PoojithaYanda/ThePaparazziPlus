package com.example.loginapp.model;

import android.os.AsyncTask;

import com.example.loginapp.RestAPIClient;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

public class UserTests {

    private static String USER_JSON = "{\"userId\":1,\"userName\":\"Test User\",\"emailAddress\":\"test@gmail.com\",\"status\":1,\"errorMessage\":\"\"}";
    private static String BAD_JSON_STRING = "BAD JSON STRING";

    private RestAPIClient mockClient = Mockito.mock(RestAPIClient.class);

    @Test
    public void testConvertToUser() throws Exception {
        User testUser = new User(mockClient);
        testUser.convertToUser(USER_JSON);

        assert testUser.getUserId() == 1;
        assert testUser.getUserName().equals("Test User");
        assert testUser.getEmailAddress().equals("test@gmail.com");
    }

    @Test(expected = JSONException.class)
    public void testConvertToUserForInvalidJsonString() throws Exception {
        User testUser = new User(mockClient);
        testUser.convertToUser(BAD_JSON_STRING);
    }

    @Test
    public void testValidateUser() throws Exception {
        AsyncTask<String, Void, String> mockTask = Mockito.mock(AsyncTask.class);
        Mockito.when(mockTask.get()).thenReturn(USER_JSON);
        Mockito.when(mockClient.execute(Mockito.anyString())).thenReturn(mockTask);

        User testUser = new User(mockClient);
        testUser.validateUser("test@gmail.com", "test123");

        assert testUser.getUserId() == 1;
        assert testUser.getUserName().equals("Test User");
        assert testUser.getEmailAddress().equals("test@gmail.com");
        assert testUser.getErrorMessage().equals("");
    }

    @Test
    public void testInValidUser() throws Exception {
        AsyncTask<String ,Void, String> mockTask = Mockito.mock(AsyncTask.class);
        Mockito.when(mockTask.get()).thenReturn("{\"userId\":-1,\"userName\":\"\",\"emailAddress\":\"\",\"status\":1,\"errorMessage\":\"Invalid User Name and Password\"}");
        Mockito.when(mockClient.execute(Mockito.anyString())).thenReturn(mockTask);

        User testUser = new User(mockClient);
        testUser.validateUser("test@gmail.com", "test123");

        assert testUser.getUserId() == -1;
        assert testUser.getErrorMessage().equals("Invalid User Name and Password");
    }
}
