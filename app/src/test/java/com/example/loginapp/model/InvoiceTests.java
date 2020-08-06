package com.example.loginapp.model;

import com.example.loginapp.RestAPIClient;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class InvoiceTests {
    private static String INVOICE_JSON = "{\"invoiceId\":1,\"pId\":1,\"item_Name\":\"Bean bag\",\"price\":250.0,\"quantity\":1}";
    private static String BAD_JSON_STRING = "BAD JSON STRING";
    private static String LIST_OF_INVOICE_JSON = "[{\"invoiceId\":1,\"pId\":1,\"item_Name\":\"Bean bag\",\"price\":250.0,\"quantity\":1},{\"invoiceId\":2,\"pId\":1,\"item_Name\":\"throw\",\"price\":300.0,\"quantity\":1},{\"invoiceId\":3,\"pId\":1,\"item_Name\":\"throw\",\"price\":300.0,\"quantity\":1}]";

    @Test
    public void convertToInvoice() throws Exception {
        Invoice testInvoice = new Invoice();
        testInvoice.convertToInvoice(INVOICE_JSON);

        assert testInvoice.getpId() == 1;
        assert testInvoice.getInvoiceId() == 1;
        assert testInvoice.getItemName().equals("Bean bag");
        assert testInvoice.getPrice() == 250.0;
        assert  testInvoice.getQuantity() == 1;
    }

    @Test(expected = JSONException.class)
    public void testConvertToInvoiceForInvalidJsonString() throws Exception {
        Invoice testInvoice = new Invoice();
        testInvoice.convertToInvoice(BAD_JSON_STRING);
    }

    @Test
    public void testConvertToList() throws Exception {
        List<Invoice> invoices = Invoice.convertToList(LIST_OF_INVOICE_JSON);

        assert invoices.size() == 3;

        assert invoices.get(0).getpId() == 1;
        assert invoices.get(1).getpId() == 1;
        assert invoices.get(2).getpId() == 1;

        assert invoices.get(0).getInvoiceId() == 1;
        assert invoices.get(1).getInvoiceId() == 2;
        assert invoices.get(2).getInvoiceId() == 3;

        assert invoices.get(0).getItemName().equals("Bean bag");
        assert invoices.get(1).getItemName().equals("throw");
        assert invoices.get(2).getItemName().equals("throw");

        assert invoices.get(0).getPrice() == 250.0;
        assert invoices.get(1).getPrice() == 300.0;
        assert invoices.get(2).getPrice() == 300.0;
    }

    @Test(expected = JSONException.class)
    public void testConvertToListForInvalidJsonString() throws Exception {
        List<Invoice> invoices = new ArrayList<>();
        invoices = Invoice.convertToList(BAD_JSON_STRING);
    }

}
