package com.yjinpk.eatgo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginUsername, editTextLoginPassword;
    private Button buttonLogin, buttonRegister;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLoginUsername = findViewById(R.id.editTextLoginUsername);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        sqLiteHelper = new SQLiteHelper(this);

        buttonLogin.setOnClickListener(v -> {
            String username = editTextLoginUsername.getText().toString();
            String password = editTextLoginPassword.getText().toString();

            if (validateLogin(username, password)) {
                Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
                // 로그인 성공 시 ChatActivity로 이동
                Intent intent = new Intent(LoginActivity.this, PartyActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
            }
        });

        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateLogin(String username, String password) {
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }
}
