package com.example.loginapp.model;

import com.example.loginapp.RestAPIClient;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ProjectTests {


    private static String PROJECT_JSON = "{\"pId\":1,\"userId\":1,\"projectName\":\"p1\",\"customerName\":\"p1customer\",\"customerEmail\":\"p1@gmail.com\",\"date\":\"08/05/2020\"}";
    private static String BAD_JSON_STRING = "BAD JSON STRING";
    private static String LIST_OF_PROJECTS_JSON = "[{\"pId\":1,\"userId\":1,\"projectName\":\"p1\",\"customerName\":\"p1customer\",\"customerEmail\":\"p1@gmail.com\",\"date\":\"08/05/2020\"},{\"pId\":2,\"userId\":1,\"projectName\":\"p2\",\"customerName\":\"p2customer\",\"customerEmail\":\"p2@gmail.com\",\"date\":\"08/06/2020\"},{\"pId\":3,\"userId\":1,\"projectName\":\"Test Project Name\",\"customerName\":\"Poojitha Yanda\",\"customerEmail\":\"poojithayanda@gmail.com\",\"date\":\"08/03/2020\"}]";

    @Test
    public void convertToProject() throws Exception {
        Project testproject = new Project();
        testproject.convertToProject(PROJECT_JSON);

        assert testproject.getUserId() == 1;
        assert testproject.getpId() == 1;
        assert testproject.getProjectName().equals("p1");
        assert testproject.getCustomerName().equals("p1customer");
        assert testproject.getCustomerEmail().equals("p1@gmail.com");
        assert testproject.getDate().equals("08/05/2020");
    }

    @Test(expected = JSONException.class)
    public void testConvertToProjectForInvalidJsonString() throws Exception {
        Project testproject = new Project();
        testproject.convertToProject(BAD_JSON_STRING);
    }

    @Test
    public void testConvertToList() throws Exception {
        List<Project> projects = Project.convertToList(LIST_OF_PROJECTS_JSON);

        assert projects.size() == 3;
        assert projects.get(0).getpId() == 1;
        assert projects.get(1).getpId() == 2;
        assert projects.get(2).getpId() == 3;

        assert projects.get(0).getUserId() == 1;
        assert projects.get(1).getUserId() == 1;
        assert projects.get(2).getUserId() == 1;

        assert projects.get(0).getProjectName().equals("p1");
        assert projects.get(1).getProjectName().equals("p2");
        assert projects.get(2).getProjectName().equals("Test Project Name");

        assert projects.get(0).getCustomerName().equals("p1customer");
        assert projects.get(1).getCustomerName().equals("p2customer");
        assert projects.get(2).getCustomerName().equals("Poojitha Yanda");

        assert projects.get(0).getCustomerEmail().equals("p1@gmail.com");
        assert projects.get(1).getCustomerEmail().equals("p2@gmail.com");
        assert projects.get(2).getCustomerEmail().equals("poojithayanda@gmail.com");

        assert projects.get(0).getDate().equals("08/05/2020");
        assert projects.get(1).getDate().equals("08/06/2020");
        assert projects.get(2).getDate().equals("08/03/2020");
    }

    @Test(expected = JSONException.class)
    public void testConvertToListForInvalidJsonString() throws Exception {
        List<Project> projects = new ArrayList<Project>();
        projects = Project.convertToList(BAD_JSON_STRING);
    }


}
