package com.example.sweetnswirls.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;

import com.example.sweetnswirls.R;
import com.example.sweetnswirls.database.DBHelper;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirm;
    Button btnRegister;
    TextView tvLogin;
    DBHelper dbHelper; // DBHelper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Connect UI elements
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnSignup);
        tvLogin = findViewById(R.id.tvLoginLink);

        // Already have account → go to Login
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });

        // Register button click
        btnRegister.setOnClickListener(v -> attemptRegister());
    }

    private void attemptRegister() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim().toLowerCase();
        String pass = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();

        // Validation
        if (name.isEmpty()) { showToast("Please enter your name"); return; }
        if (email.isEmpty()) { showToast("Please enter your email"); return; }
        if (pass.length() < 6) { showToast("Password must be at least 6 characters"); return; }
        if (!pass.equals(confirm)) { showToast("Passwords do not match"); return; }

        // Insert user in SQLite DB
        boolean inserted = dbHelper.insertUser(name, email, pass); // Note: plain password for DBHelper login
        if (inserted) {
            showToast("🎉 Welcome " + name + "! Please log in.");
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        } else {
            showToast("⚠ Email already registered! Please log in.");
        }
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
