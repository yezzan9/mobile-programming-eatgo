package com.yjinpk.eatgo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextNickname;
    private Spinner spinnerOptions;
    private Button buttonRegister;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextNickname = findViewById(R.id.editTextNickname);
        spinnerOptions = findViewById(R.id.spinnerOptions);
        buttonRegister = findViewById(R.id.buttonRegister);

        sqLiteHelper = new SQLiteHelper(this);

        // 드롭다운 메뉴에 데이터 추가
        String[] options = {"1생활관", "2생활관", "3생활관"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerOptions.setAdapter(adapter);

        // 버튼 클릭 이벤트 처리
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String nickname = editTextNickname.getText().toString();
                String selectedOption = spinnerOptions.getSelectedItem().toString();

                // 데이터베이스에 사용자 추가
                boolean isInserted = sqLiteHelper.addUser(username, password, nickname, selectedOption);
                if (isInserted) {
                    Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}