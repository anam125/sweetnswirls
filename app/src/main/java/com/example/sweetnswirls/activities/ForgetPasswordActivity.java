package com.example.sweetnswirls.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;

import com.example.sweetnswirls.R;
import com.example.sweetnswirls.database.DBHelper;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnFind;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etEmail = findViewById(R.id.etEmail);
        btnFind = findViewById(R.id.btnFindAccount);

        dbHelper = new DBHelper(this);

        btnFind.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim().toLowerCase();
            if (email.isEmpty()) {
                Toast.makeText(this, "🍦 Please enter your email!", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Check if email exists in DB
            boolean exists = dbHelper.isEmailExists(email);
            if (!exists) {
                Toast.makeText(this, "❌ No account found with this email.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Go to Reset Password activity
            Intent i = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
            i.putExtra("email", email);
            startActivity(i);
            finish();
        });
    }
}
