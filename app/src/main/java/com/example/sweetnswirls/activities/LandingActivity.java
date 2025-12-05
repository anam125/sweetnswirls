package com.example.sweetnswirls.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.sweetnswirls.R;

public class LandingActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnLogin, btnSignup;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        prefs = getSharedPreferences("SweetNSwirlsApp", MODE_PRIVATE);

        boolean logged = prefs.getBoolean("isLoggedIn", false);

        // 🔸 If already logged in, show welcome text normally
        // 🔸 If not logged in, user can choose Login or Signup from this screen
        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // 🍦 Set welcome text dynamically (if applicable)
        tvWelcome = findViewById(R.id.tvWelcome);

        if (tvWelcome != null) {
            String fullName = prefs.getString("userFullName", "Guest 🍨");
            String message = getString(R.string.dynamic_welcome_message, fullName);
            tvWelcome.setText(message);
        }




        // 🍰 Login button → opens LoginActivity
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(intent);
            });
        }

        // 🍓 Signup button → opens SignupActivity
        if (btnSignup != null) {
            btnSignup.setOnClickListener(v -> {
                Intent intent = new Intent(LandingActivity.this, SignupActivity.class);
                startActivity(intent);
            });
        }

        // 🔸 If user was previously logged in, can still choose to stay or logout later
        if (logged) {
            Toast.makeText(this, "Welcome back 🍦!", Toast.LENGTH_SHORT).show();
        }
    }
}
