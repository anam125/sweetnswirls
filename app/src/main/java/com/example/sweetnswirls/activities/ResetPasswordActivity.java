package com.example.sweetnswirls.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;

import com.example.sweetnswirls.R;
import com.example.sweetnswirls.database.DBHelper;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText etNewPass, etConfirm;
    Button btnReset;
    String email;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etNewPass = findViewById(R.id.etNewPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        btnReset = findViewById(R.id.btnReset);

        dbHelper = new DBHelper(this);

        email = getIntent().getStringExtra("email");
        if (email == null) {
            Toast.makeText(this, "No email provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnReset.setOnClickListener(v -> {
            String newPass = etNewPass.getText().toString().trim();
            String confirmPass = etConfirm.getText().toString().trim();

            if (newPass.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Update password in DB
            boolean updated = dbHelper.updatePassword(email, newPass);

            if (updated) {
                Toast.makeText(this, "Password updated! Please login again.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Error updating password. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
