package com.example.sweetnswirls.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import com.example.sweetnswirls.models.CartItem;
import com.example.sweetnswirls.database.DBHelper;
import com.example.sweetnswirls.R;

public class CartActivity extends AppCompatActivity {

    private LinearLayout cartLayout;
    private TextView totalText;
    private Button checkoutBtn;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartLayout = findViewById(R.id.cartLayout);
        totalText = findViewById(R.id.totalText);
        checkoutBtn = findViewById(R.id.checkoutBtn);

        dbHelper = new DBHelper(this);

        displayCart();

        checkoutBtn.setOnClickListener(v -> {
            if(MenuActivity.cartItems.isEmpty()){
                Toast.makeText(this,"Cart is empty",Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
            }
        });
    }

    private void displayCart() {
        cartLayout.removeAllViews();
        int total = 0;
        List<CartItem> items = MenuActivity.cartItems;

        for (CartItem item : items) {
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView name = new TextView(this);
            name.setText(item.getIceCream().getName() + " x" + item.getQuantity());
            name.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            itemLayout.addView(name);

            TextView price = new TextView(this);
            int itemTotal = item.getIceCream().getPrice() * item.getQuantity();
            price.setText("Rs " + itemTotal);
            itemLayout.addView(price);

            Button removeBtn = new Button(this);
            removeBtn.setText("Remove");
            removeBtn.setOnClickListener(v -> {
                MenuActivity.cartItems.remove(item);
                dbHelper.clearCart();
                for(CartItem c : MenuActivity.cartItems){
                    dbHelper.addToCart(c.getIceCream().getName(), c.getIceCream().getPrice(), c.getQuantity());
                }
                displayCart();
            });
            itemLayout.addView(removeBtn);

            cartLayout.addView(itemLayout);
            total += itemTotal;
        }

        totalText.setText("Total: Rs " + total);
    }
}
