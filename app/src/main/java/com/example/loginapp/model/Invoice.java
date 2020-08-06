package com.example.loginapp.model;

import android.util.Log;

import com.example.loginapp.RestAPIClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private static String BASE_URL = "http://10.0.2.2:8080/";

    private int pId;
    private int invoiceId;
    private String itemName;
    private double price;
    private int quantity;

    public int getpId() { return this.pId; }
    public int getInvoiceId() { return this.invoiceId; }
    public String getItemName() { return this.itemName; }
    public double getPrice() { return this.price; }
    public int getQuantity() { return this.quantity; }

    public Invoice() {
    }

    public Invoice(String itemName, double price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public Invoice(RestAPIClient client, String itemName, double price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public  Invoice getInvoice(int invoiceId) {
        String GET_INVOICE_URL = String.format(BASE_URL + "invoice?id=%s", invoiceId);
        try {
            String result = new RestAPIClient().execute(GET_INVOICE_URL).get();
            Log.d("Invoice Model", result);
            convertToInvoice(result);
        } catch (Exception e) {
            Log.e("Invoice Model", "Unable to get the Invoice data", e);
            e.printStackTrace();
        }
        return this;
    }

    public Invoice createInvoice(int pId, String itemName, double price, int quantity) {
        String CREATE_INVOICE_URL = String.format(BASE_URL + "project/%s/invoice/create?item_Name=%s&price=%s&quantity=%s", pId, itemName, price, quantity);
        try {
            String result = new RestAPIClient().execute(CREATE_INVOICE_URL).get();
            Log.e("Invoice Model", result);
            convertToInvoice(result);
        } catch (Exception e) {
            Log.e("Invoice Model", "Unable to create invoice");
            e.printStackTrace();
        }
        return this;
    }

    public static List<Invoice> getAllInvoices(int pId) {
        String GET_ALL_INVOICES_URL = String.format(BASE_URL + "project/%s/invoices", pId);
        List<Invoice> invoices = new ArrayList<>();
        try {
            String result = new RestAPIClient().execute(GET_ALL_INVOICES_URL).get();
            invoices = convertToList(result);
        } catch (Exception e) {
            Log.e("Invoice Model", "Unable to get all the checklists = " + pId, e);
        }
        return invoices;
    }

    protected void convertToInvoice(String jsonString) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonString);
        this.pId = jsonObject.getInt("pId");
        this.invoiceId = jsonObject.getInt("invoiceId");
        this.itemName = jsonObject.getString("item_Name");
        this.price = jsonObject.getDouble("price");
        this.quantity = jsonObject.getInt("quantity");
    }

    protected static List<Invoice> convertToList(String jsonString) throws Exception {
        Log.d(Invoice.class.getName(), "Result - converToList: " + jsonString);
        JSONArray array = new JSONArray(jsonString);
        List<Invoice> invoices = new ArrayList<>();
        for(int i = 0; i<array.length();i++) {
            JSONObject obj = array.getJSONObject(i);
            Log.d(Invoice.class.getName(), "Json Object = " + obj.toString());
            Invoice newinvoice = new Invoice();
            newinvoice.pId = obj.getInt("pId");
            newinvoice.invoiceId = obj.getInt("invoiceId");
            newinvoice.itemName = obj.getString("item_Name");
            newinvoice.price = obj.getDouble("price");
            newinvoice.quantity = obj.getInt("quantity");
            invoices.add(newinvoice);
        }
        return invoices;
    }

    @Override
    public String toString() {
        return String.format("Invoice [pId = %s, invoiceId = %s, name = %s, price = %s, quantity = %s", pId, invoiceId, itemName, price, quantity);
    }

}
