package com.yjinpk.eatgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class activity_login extends AppCompatActivity {
    TextView sign;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        // 로그인 버튼
        login = findViewById(R.id.loginbutton);

        // 로그인 버튼 클릭시, 다음 페이지로 이동
        login.setOnClickListener(v -> {
            // username을 EditText에서 가져오기
            EditText editTextLoginUsername = findViewById(R.id.editTextLoginUsername);
            String username = editTextLoginUsername.getText().toString();

            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
                return;
            }

            // 다음 화면으로 이동하는 Intent 생성
            Intent intent = new Intent(this, PartyActivity.class);
            intent.putExtra("username", username); // username 추가
            startActivity(intent);
        });

        // 회원가입 버튼
        sign = findViewById(R.id.signin);

        // 회원가입 버튼 클릭시, 회원가입 페이지로 이동
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, activity_signup.class);
            startActivity(intent);
        });
    }
}
