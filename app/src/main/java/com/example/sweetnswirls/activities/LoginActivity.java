package com.example.sweetnswirls.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sweetnswirls.R;
import com.example.sweetnswirls.database.DBHelper;

public class LoginActivity extends AppCompatActivity {

    EditText emailEdit, passwordEdit;
    Button loginBtn;
    TextView signupLink, forgotPassLink;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Initialize Views
        emailEdit = findViewById(R.id.etEmail);
        passwordEdit = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.btnLogin);
        signupLink = findViewById(R.id.tvRegister);
        forgotPassLink = findViewById(R.id.tvForgotPassword);

        // Login button click
        loginBtn.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim().toLowerCase();
            String password = passwordEdit.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Check user with plain password
            boolean checkUser = dbHelper.checkUser(email, password);
            if (checkUser) {
                // Successful login → go to MenuActivity
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.putExtra("userEmail", email); // pass email for future reference
                startActivity(intent);
                finish(); // Prevent back to login
            } else {
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Optional: Signup link
        signupLink.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

        // Optional: Forgot password link
        forgotPassLink.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class)));
    }
}
