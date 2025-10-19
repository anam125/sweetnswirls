package com.example.sweetnswirls;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirm;
    Button btnRegister;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); // keep your same beautiful UI layout

        // 🍦 Connect all UI elements
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnSignup);
        tvLogin = findViewById(R.id.tvLoginLink);

        // 🍧 If user already has an account → go to LoginActivity
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });

        // 🍨 On click of Sign Up button → perform registration
        btnRegister.setOnClickListener(v -> attemptRegister());
    }

    private void attemptRegister() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim().toLowerCase();
        String pass = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();

        // 🧁 Validation logic (unchanged)
        if (email.isEmpty()) { showToast("🍦 Please enter your email"); return; }
        if (pass.length() < 6) { showToast("🍩 Password must be at least 6 characters"); return; }
        if (!pass.equals(confirm)) { showToast("🍧 Passwords do not match"); return; }

        // 🍫 Get existing users and check duplicates
        Map<String,String> users = UserUtils.getUsersMap(this);
        if (users.containsKey(email)) { showToast("🍰 Email already registered! Please log in."); return; }

        // 🍪 Save new user with hashed password
        users.put(email, UserUtils.sha256(pass));
        UserUtils.saveUsersMap(this, users);

        SharedPreferences prefs = getSharedPreferences("SweetNSwirlsApp", MODE_PRIVATE);
        prefs.edit()
                .putString("userFullName", name)
                .apply();

        // 🎉 Registration successful
        showToast("🎀 Welcome to Sweet n Swirls, " + name + "! Please log in 🍨");
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}


