package com.example.sweetnswirls;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import java.util.Map;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText etEmail;
    Button btnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etEmail = findViewById(R.id.etEmail);
        btnFind = findViewById(R.id.btnFindAccount);

        btnFind.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim().toLowerCase();
            if (email.isEmpty()) {
                Toast.makeText(this, "🍦 Please enter your email!", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> users = UserUtils.getUsersMap(this);
            if (!users.containsKey(email)) {
                Toast.makeText(this, "❌ No account found with this email.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent i = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
            i.putExtra("email", email);
            startActivity(i);
            finish();
        });
    }
}
