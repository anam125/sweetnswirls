package com.example.sweetnswirls;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.*;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister, tvForgot;
    SharedPreferences prefs;


    private final String HARD_USER = "admin";
    private final String HARD_PASS = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("SweetNSwirlsApp", MODE_PRIVATE);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgot = findViewById(R.id.tvForgotPassword);

        // Register click
        tvRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

        // Forgot click
        tvForgot.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class)));

        // Login click
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim().toLowerCase();
            String pass = etPassword.getText().toString();

            // empty check
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_empty), Toast.LENGTH_SHORT).show();
                return;
            }

            // 1) Hard-coded admin check
            if (email.equals(HARD_USER) && pass.equals(HARD_PASS)) {
                loginSuccess(email);
                return;
            }

            // 2) Registered users check
            Map<String, String> users = UserUtils.getUsersMap(this);

            if (!users.containsKey(email)) {
                Toast.makeText(this, getString(R.string.toast_no_account), Toast.LENGTH_SHORT).show();
                return;
            }

            String storedHash = users.get(email);
            String inputHash = UserUtils.sha256(pass);

            // Null-safe password check
            if (!Objects.equals(storedHash, inputHash)) {
                Toast.makeText(this, getString(R.string.toast_wrong_password), Toast.LENGTH_SHORT).show();
                return;
            }

            loginSuccess(email);
        });
    }

    private void loginSuccess(String email) {
        prefs.edit()
                .putBoolean("isLoggedIn", true)
                .putString("userEmail", email)
                .apply();
        startActivity(new Intent(LoginActivity.this, LandingActivity.class));
        finish();
    }
}
