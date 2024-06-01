package com.yjinpk.eatgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private String CHAT_NAME;
    private String USER_NAME;

    private ListView chat_view;
    private EditText chat_edit;
    private Button chat_send;
    private TextView chat_name;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 위젯 ID 참조
        chat_view = findViewById(R.id.chat_view);
        chat_edit = findViewById(R.id.chat_edit);
        chat_send = findViewById(R.id.chat_sent);
        chat_name = findViewById(R.id.chatname);

        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAME = intent.getStringExtra("userName");

        // 채팅 방 입장
        openChat(CHAT_NAME);
        chat_name.setText("채팅방: " + CHAT_NAME);

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send.setOnClickListener(v -> {
            if (chat_edit.getText().toString().equals(""))
                return;

            ChatDTO chat = new ChatDTO(USER_NAME, chat_edit.getText().toString()); // ChatDTO를 이용하여 데이터를 묶는다.
            databaseReference.child("chat").child(CHAT_NAME).child("messages").push().setValue(chat); // 데이터 푸쉬
            chat_edit.setText(""); // 입력창 초기화
        });

        ImageButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::showPopupMenu);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(ChatActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            Intent intent;
            if (itemId == R.id.action_add_order) {
                // '주문 추가하기' 메뉴를 선택한 경우
                intent = new Intent(ChatActivity.this, AddOrderActivity.class);
                intent.putExtra("chatName", CHAT_NAME);
                intent.putExtra("userName", USER_NAME);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.action_view_orders) {
                // '주문 확인하기' 메뉴를 선택한 경우
                intent = new Intent(ChatActivity.this, OrderActivity.class);
                intent.putExtra("chatName", CHAT_NAME);
                intent.putExtra("userName", USER_NAME); // userName 전달
                startActivity(intent);
                return true;
            } else if (itemId == R.id.action_change_min_order) {
                // '최소 주문 금액 변경하기' 메뉴를 선택한 경우
                showChangeMinOrderDialog();
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    private void showChangeMinOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("최소 주문 금액 변경하기");

        final EditText input = new EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("변경", (dialog, which) -> {
            String minOrderString = input.getText().toString();
            if (!minOrderString.isEmpty()) {
                int minOrder = Integer.parseInt(minOrderString);
                // 최소 주문 금액을 Firebase에 저장
                databaseReference.child("chat").child(CHAT_NAME).child("minOrder").setValue(minOrder);
                // OrderActivity로 전달
                Intent intent = new Intent(ChatActivity.this, OrderActivity.class);
                intent.putExtra("chatName", CHAT_NAME);
                intent.putExtra("userName", USER_NAME);
                intent.putExtra("minOrder", minOrder);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.add(chatDTO.getUserName() + " : " + chatDTO.getMessage());
    }

    private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO.getUserName() + " : " + chatDTO.getMessage());
    }

    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.chat_item, R.id.chat_item_text);
        chat_view.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName).child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot, adapter);
                Log.e("LOG", "s:" + s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // Do nothing
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot, adapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // Do nothing
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LOG", "Database error: " + databaseError.getMessage());
            }
        });
    }
}
