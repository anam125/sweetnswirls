package com.example.sweetnswirls.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.sweetnswirls.adapters.MenuAdapter;
import com.example.sweetnswirls.models.IceCream;
import com.example.sweetnswirls.models.CartItem;
import com.example.sweetnswirls.R;
import com.example.sweetnswirls.database.DBHelper;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerMenu;
    private MenuAdapter adapter;
    private List<IceCream> iceCreamList;
    private List<IceCream> filteredList;
    private EditText searchBar;
    private TextView cartCount;
    private Button goToCart;
    public static List<CartItem> cartItems = new ArrayList<>(); // Global cart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        DBHelper db = new DBHelper(MenuActivity.this);
        db.exportDatabase(MenuActivity.this);

        recyclerMenu = findViewById(R.id.recyclerMenu);
        searchBar = findViewById(R.id.searchBar);
        cartCount = findViewById(R.id.cartCount);
        goToCart = findViewById(R.id.goToCart);

        iceCreamList = new ArrayList<>();
        populateIceCreams();
        filteredList = new ArrayList<>(iceCreamList);

        adapter = new MenuAdapter(this, filteredList);
        recyclerMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenu.setAdapter(adapter);

        updateCartCount();

        goToCart.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, CartActivity.class));
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


    }

    private void populateIceCreams(){
        iceCreamList.add(new IceCream("Chocolate", "Rich chocolate ice cream", 200, R.drawable.chocolate));
        iceCreamList.add(new IceCream("Vanilla", "Classic vanilla flavor", 180, R.drawable.vanilla));
        iceCreamList.add(new IceCream("Strawberry", "Fresh strawberry ice cream", 220, R.drawable.strawberry));
        iceCreamList.add(new IceCream("Mango", "Sweet mango delight", 210, R.drawable.mango));
        iceCreamList.add(new IceCream("Oreo", "Oreo cookie ice cream", 250, R.drawable.oreo));
        iceCreamList.add(new IceCream("Pistachio", "Nutty pistachio delight", 230, R.drawable.pistachio));
        iceCreamList.add(new IceCream("Blueberry Cheesecake", "Sweet blueberry with cheesecake bits", 240, R.drawable.blueberry_cheesecake));
        iceCreamList.add(new IceCream("Caramel Swirl", "Rich caramel flavor with swirls", 220, R.drawable.caramel_swirl));
        iceCreamList.add(new IceCream("Red Velvet", "Classic red velvet cake pieces in creamy ice cream", 260, R.drawable.red_velvet));
        iceCreamList.add(new IceCream("Cotton Candy", "Joyful mix of flavors", 250, R.drawable.cotton_candy));
    }

    private void filter(String text){
        filteredList.clear();
        for(IceCream ice : iceCreamList){
            if(ice.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(ice);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void updateCartCount(){
        int totalItems = 0;
        for(CartItem item : cartItems){
            totalItems += item.getQuantity();
        }
        cartCount.setText("Cart: " + totalItems + " items");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount(); // Refresh cart count dynamically
    }


}
