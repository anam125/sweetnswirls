package com.example.sweetnswirls;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText etNewPass, etConfirm;
    Button btnReset;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etNewPass = findViewById(R.id.etNewPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        btnReset = findViewById(R.id.btnReset);

        email = getIntent().getStringExtra("email");
        if (email == null) {
            Toast.makeText(this, "No email provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnReset.setOnClickListener(v -> {
            String p = etNewPass.getText().toString();
            String c = etConfirm.getText().toString();

            if (p.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!p.equals(c)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> users = UserUtils.getUsersMap(this);
            users.put(email, UserUtils.sha256(p));
            UserUtils.saveUsersMap(this, users);

            Toast.makeText(this, "Password updated! Please login again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            finish();
        });
    }
}
