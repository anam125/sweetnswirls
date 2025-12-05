package com.example.sweetnswirls.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sweetnswirls.database.DBHelper;
import com.example.sweetnswirls.models.CartItem;
import com.example.sweetnswirls.R;

public class CheckoutActivity extends AppCompatActivity {

    private EditText nameEdit, phoneEdit, addressEdit;
    private RadioButton cashRadio, cardRadio;
    private Button confirmBtn;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        nameEdit = findViewById(R.id.nameEdit);
        phoneEdit = findViewById(R.id.phoneEdit);
        addressEdit = findViewById(R.id.addressEdit);
        cashRadio = findViewById(R.id.cashRadio);
        cardRadio = findViewById(R.id.cardRadio);
        confirmBtn = findViewById(R.id.confirmBtn);

        dbHelper = new DBHelper(this);

        confirmBtn.setOnClickListener(v -> {
            String name = nameEdit.getText().toString().trim();
            String phone = phoneEdit.getText().toString().trim();
            String address = addressEdit.getText().toString().trim();
            String payment = cashRadio.isChecked() ? "Cash" : "Card";

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder itemsStr = new StringBuilder();
            int total = 0;
            boolean savedAll = true;

            for (CartItem item : MenuActivity.cartItems) {
                itemsStr.append(item.getIceCream().getName())
                        .append(" x").append(item.getQuantity())
                        .append("\n");

                total += item.getIceCream().getPrice() * item.getQuantity();

                boolean saved = dbHelper.insertOrder(
                        name,                          // userEmail / name
                        item.getIceCream().getName(),  // itemName
                        item.getIceCream().getPrice(), // itemPrice
                        item.getQuantity(),            // quantity
                        total,                         // totalPrice
                        address,                       // address
                        phone,                         // phone
                        payment                        // paymentMethod
                );

                if (!saved) savedAll = false;
            }

            if (savedAll) {
                // Clear cart memory & DB
                MenuActivity.cartItems.clear();
                dbHelper.clearCart();

                // Go to confirmation screen
                Intent intent = new Intent(CheckoutActivity.this, OrderConfirmationActivity.class);
                intent.putExtra("items", itemsStr.toString());
                intent.putExtra("total", total);
                intent.putExtra("payment", payment);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("address", address);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Error saving order", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
