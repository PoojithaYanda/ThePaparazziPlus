package com.example.loginapp.model;

import com.example.loginapp.RestAPIClient;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ChecklistTests {

    private static String CHECKLIST_JSON = "{\"cId\":1,\"pId\":1,\"item_Name\":\"checklist1\",\"date\":\"1/2/2009\"}";
    private static String BAD_JSON_STRING = "BAD JSON STRING";
    private static String LIST_OF_CHECKLIST_JSON = "[{\"cId\":1,\"pId\":1,\"item_Name\":\"checklist1\",\"date\":\"1/2/2009\"},{\"cId\":2,\"pId\":1,\"item_Name\":\"checklist2\",\"date\":\"2/2/2010\"},{\"cId\":3,\"pId\":1,\"item_Name\":\"check3\",\"date\":\"3/3/2010\"},{\"cId\":4,\"pId\":1,\"item_Name\":\"Check my batteries\",\"date\":\"08/04/2020\"},{\"cId\":5,\"pId\":1,\"item_Name\":\"Tripod required\",\"date\":\"08/05/2020\"}]";

    @Test
    public void convertToChecklist() throws Exception {
        Checklist testChecklist = new Checklist();
        testChecklist.convertToChecklist(CHECKLIST_JSON);

        assert testChecklist.getpId() == 1;
        assert testChecklist.getItemId() == 1;
        assert testChecklist.getItemName().equals("checklist1");
        assert testChecklist.getDate().equals("1/2/2009");
    }

    @Test(expected = JSONException.class)
    public void testConvertToChecklistForInvalidJsonString() throws Exception {
        Checklist testChecklist = new Checklist();
        testChecklist.convertToChecklist(BAD_JSON_STRING);
    }

    @Test
    public void testConvertToList() throws Exception {
        List<Checklist> checklists = Checklist.convertToList(LIST_OF_CHECKLIST_JSON);

        assert checklists.size() == 5;
        assert checklists.get(0).getpId() == 1;
        assert checklists.get(1).getpId() == 1;
        assert checklists.get(2).getpId() == 1;
        assert checklists.get(3).getpId() == 1;
        assert checklists.get(4).getpId() == 1;

        assert checklists.get(0).getItemId() == 1;
        assert checklists.get(1).getItemId() == 2;
        assert checklists.get(2).getItemId() == 3;
        assert checklists.get(3).getItemId() == 4;
        assert checklists.get(4).getItemId() == 5;

        assert checklists.get(0).getItemName().equals("checklist1");
        assert checklists.get(1).getItemName().equals("checklist2");
        assert checklists.get(2).getItemName().equals("check3");
        assert checklists.get(3).getItemName().equals("Check my batteries");
        assert checklists.get(4).getItemName().equals("Tripod required");

        assert checklists.get(0).getDate().equals("1/2/2009");
        assert checklists.get(1).getDate().equals("2/2/2010");
        assert checklists.get(2).getDate().equals("3/3/2010");
        assert checklists.get(3).getDate().equals("08/04/2020");
        assert checklists.get(4).getDate().equals("08/05/2020");
    }

    @Test(expected = JSONException.class)
    public void testConvertToListForInvalidJsonString() throws Exception {
        List<Checklist> checklists = new ArrayList<>();
        checklists =  Checklist.convertToList(BAD_JSON_STRING);
    }


}

