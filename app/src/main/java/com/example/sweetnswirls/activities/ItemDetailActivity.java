package com.example.sweetnswirls.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sweetnswirls.R;
import com.example.sweetnswirls.models.CartItem;
import com.example.sweetnswirls.models.IceCream;
import com.example.sweetnswirls.activities.MenuActivity;
import com.example.sweetnswirls.database.DBHelper;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView detailName, detailDesc, detailPrice, quantityText;
    private ImageView detailImage;
    private Button addToCartBtn, plusBtn, minusBtn;

    private int quantity = 1;
    private int itemPrice;
    private String itemName, itemDesc;
    private int itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        detailName = findViewById(R.id.detailName);
        detailDesc = findViewById(R.id.detailDesc);
        detailPrice = findViewById(R.id.detailPrice);
        detailImage = findViewById(R.id.detailImage);
        quantityText = findViewById(R.id.quantityText);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);

        // Receive data
        itemName = getIntent().getStringExtra("name");
        itemDesc = getIntent().getStringExtra("desc");
        itemPrice = getIntent().getIntExtra("price", 0);
        itemImage = getIntent().getIntExtra("image", R.drawable.ic_launcher_foreground);

        // Set data on UI
        detailName.setText(itemName);
        detailDesc.setText(itemDesc);
        detailPrice.setText("Rs " + itemPrice);
        detailImage.setImageResource(itemImage);
        quantityText.setText(String.valueOf(quantity));

        // Quantity buttons
        plusBtn.setOnClickListener(v -> {
            quantity++;
            quantityText.setText(String.valueOf(quantity));
        });

        minusBtn.setOnClickListener(v -> {
            if(quantity > 1){
                quantity--;
                quantityText.setText(String.valueOf(quantity));
            }
        });

        // Add to Cart
        addToCartBtn.setOnClickListener(v -> {
            boolean updatedExisting = false;
            for(CartItem item : MenuActivity.cartItems){
                if(item.getIceCream().getName().equals(itemName)){
                    item.setQuantity(item.getQuantity() + quantity);
                    updatedExisting = true;
                    break;
                }
            }

            if(!updatedExisting){
                IceCream ice = new IceCream(itemName, itemDesc, itemPrice, itemImage);
                MenuActivity.cartItems.add(new CartItem(ice, quantity));
            }

            // Save cart to SQLite
            DBHelper dbHelper = new DBHelper(this);
            dbHelper.clearCart();
            for(CartItem c : MenuActivity.cartItems){
                dbHelper.addToCart(c.getIceCream().getName(), c.getIceCream().getPrice(), c.getQuantity());
            }

            Toast.makeText(this, "Added to Cart!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
