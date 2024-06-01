package com.yjinpk.eatgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatStartActivity extends AppCompatActivity {

    private EditText user_chat, user_edit, location_edit;
    private Button user_next;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_party);

        user_chat = findViewById(R.id.user_chat);
        user_edit = findViewById(R.id.user_edit);
        location_edit = findViewById(R.id.location_edit);
        user_next = findViewById(R.id.user_next);

        user_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatName = user_chat.getText().toString();
                String userName = user_edit.getText().toString();
                String location = location_edit.getText().toString();

                if (chatName.isEmpty() || userName.isEmpty() || location.isEmpty())
                    return;

                // Firebase에 채팅방 정보 저장
                databaseReference = firebaseDatabase.getReference().child("chat").child(chatName);
                databaseReference.child("location").setValue(location);

                Intent intent = new Intent(ChatStartActivity.this, ChatActivity.class);
                intent.putExtra("chatName", chatName);
                intent.putExtra("userName", userName);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
    }
}
