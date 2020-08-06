package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.loginapp.model.Invoice;
import com.example.loginapp.model.Project;

import java.util.List;

public class InvoiceActivity extends AppCompatActivity {

    private static String TAG = InvoiceActivity.class.getName();
    private int projectId;
    private String projectName;
    ImageView newInvoiceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        String pId = getIntent().getStringExtra("projectId");
        if (pId == null || pId.isEmpty()) {
            Log.e(TAG, "Unable to get the project name. Setting it to -1.");
            projectId = -1;
            return;
        } else {
            projectId = Integer.parseInt(pId);
        }
        projectName = getIntent().getStringExtra("projectName");

        //Invoice title
        TextView titleView = findViewById(R.id.invoicepageTitle);
        if (titleView != null) {
            titleView.setText(String.format("Invoice for %s", projectName));
        }

        newInvoiceId = findViewById(R.id.newInvoiceId);
        newInvoiceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Invoice Activity", "On Click Listener is called");
                Intent intent = new Intent(InvoiceActivity.this, CreateInvoiceActivity.class);
                intent.putExtra("projectId", String.valueOf(projectId));
                intent.putExtra("projectName",projectName);
                startActivity(intent);
            }
        });

        renderInvoice();
    }

    private void renderInvoice() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.invoiceTblLayout);

        if(tableLayout == null || projectId == -1) {
            Log.e(InvoiceActivity.class.getName(), "Unable to render invoice table.");
            return;
        }

        final List<Invoice> invoices = Invoice.getAllInvoices(projectId);

        for(int i = 0; i<invoices.size();i++) {
            final Invoice thisInvoice = invoices.get(i);

            TableRow newRow = new TableRow(this);
            newRow.setPadding(10, 10, 10, 10);
            newRow.setBackgroundColor(Color.parseColor("#F1F1F1"));

            final TextView nameView = new TextView(this);
            TableRow.LayoutParams nameParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            nameView.setLayoutParams(nameParams);
            nameView.setText(thisInvoice.getItemName());

            final TextView priceView = new TextView(this);
            TableRow.LayoutParams priceParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            priceView.setLayoutParams(priceParams);
            priceView.setText(String.valueOf(thisInvoice.getPrice()));

            final TextView quantityView = new TextView(this);
            TableRow.LayoutParams quantityParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            quantityView.setLayoutParams(quantityParams);
            quantityView.setText(String.valueOf(thisInvoice.getQuantity()));

            newRow.addView(nameView);
            newRow.addView(priceView);
            newRow.addView(quantityView);

            tableLayout.addView(newRow, i + 1);
        }

        if (invoices.size() > 0) {
            TableRow newRow = new TableRow(this);
            newRow.setPadding(10, 10, 10, 10);
            newRow.setBackgroundColor(Color.parseColor("#F1F1F1"));

            final TextView resultView = new TextView(this);
            TableRow.LayoutParams priceParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            resultView.setLayoutParams(priceParams);
            resultView.setText("");

            final Button sendBtn = new Button(this);
            TableRow.LayoutParams nameParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f);
            sendBtn.setLayoutParams(nameParams);
            sendBtn.setText("Send Invoice");

            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Project thisProject = new Project();
                    thisProject.getProject(projectId);
                    String result = thisProject.sendInvoice();
                    resultView.setText(result);
                }
            });

            newRow.addView(sendBtn);
            newRow.addView(resultView);
            tableLayout.addView(newRow, invoices.size() + 1);
        }
    }

}