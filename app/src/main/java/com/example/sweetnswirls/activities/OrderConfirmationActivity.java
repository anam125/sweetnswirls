package com.example.sweetnswirls.activities;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sweetnswirls.R;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView summaryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        summaryText = findViewById(R.id.summaryText);

        String items = getIntent().getStringExtra("items");
        int total = getIntent().getIntExtra("total",0);
        String payment = getIntent().getStringExtra("payment");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String address = getIntent().getStringExtra("address");

        String summary = "Order Confirmed!\n\nCustomer: " + name +
                "\nPhone: " + phone +
                "\nAddress: " + address +
                "\n\nItems:\n" + items +
                "\nTotal: Rs " + total +
                "\nPayment: " + payment;

        summaryText.setText(summary);
    }
}
