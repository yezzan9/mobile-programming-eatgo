package com.yjinpk.eatgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class activity_login extends AppCompatActivity {
    TextView sign;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 로그인 버튼
        login = findViewById(R.id.loginbutton);

        // 로그인 버튼 클릭시, 다음 페이지로 이동
        login.setOnClickListener(v -> {
            // 다음 화면으로 이동하는 Intent 생성
            Intent intent = new Intent(this, activity_create.class); // 다음 화면의 클래스 이름으로 변경 필요
            startActivity(intent); // Intent를 사용하여 다음 화면으로 이동
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
