package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.loginapp.model.Checklist;
import com.example.loginapp.model.Invoice;

public class CreateInvoiceActivity extends AppCompatActivity {

    private static String TAG = CreateChecklistActivity.class.getName();
    private int projectId;
    private String projectName;

    private Button createInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

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

        final EditText item_Name = (EditText) findViewById(R.id.nameofinvoice);
        final EditText price = (EditText) findViewById(R.id.priceofchecklist);
        final EditText quantity = (EditText) findViewById(R.id.quantityofchecklist);

        createInvoice = (Button) findViewById(R.id.createbutton);
        createInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Invoice newInvoice = new Invoice();
                newInvoice.createInvoice(projectId, item_Name.getText().toString(), Double.parseDouble(price.getText().toString().replaceAll("S\\$|\\.$", "")), Integer.parseInt(quantity.getText().toString()));
                if(newInvoice.getInvoiceId() != -1) {
                    Log.d("Create Invoice", "Invoice creation successfully");
                    Intent intent = new Intent(CreateInvoiceActivity.this, InvoiceActivity.class);
                    intent.putExtra("projectId", String.valueOf(projectId));
                    intent.putExtra("projectName", projectName);
                    startActivity(intent);
                }
            }
        });
    }
}