package com.yjinpk.eatgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PartyActivity extends AppCompatActivity {

    private ListView chat_list;
    private Button user_next;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        user_next = findViewById(R.id.user_next);
        chat_list = findViewById(R.id.chat_list);

        user_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartyActivity.this, ChatStartActivity.class);
                startActivity(intent);
            }
        });

        showChatList();

        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chatInfo = (String) parent.getItemAtPosition(position);
                String chatName = chatInfo.split(" \\(")[0]; // CHAT_NAME만 추출
                showUsernameDialog(chatName);
            }
        });
    }

    private void showChatList() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.chat_item, R.id.chat_item_text);
        chat_list.setAdapter(adapter);

        databaseReference.child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String chatName = dataSnapshot.getKey();
                String location = dataSnapshot.child("location").getValue(String.class);
                if (location != null) {
                    String chatInfo = chatName + " (" + location + ")";
                    adapter.add(chatInfo);
                } else {
                    adapter.add(chatName);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String chatName = dataSnapshot.getKey();
                String location = dataSnapshot.child("location").getValue(String.class);
                String chatInfo = chatName + " (" + location + ")";
                // 리스트에서 업데이트된 항목을 찾아서 변경
                for (int i = 0; i < adapter.getCount(); i++) {
                    String item = adapter.getItem(i);
                    if (item != null && item.startsWith(chatName)) {
                        adapter.remove(item);
                        adapter.insert(chatInfo, i);
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String chatName = dataSnapshot.getKey();
                for (int i = 0; i < adapter.getCount(); i++) {
                    String item = adapter.getItem(i);
                    if (item != null && item.startsWith(chatName)) {
                        adapter.remove(item);
                        break;
                    }
                }
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

    private void showUsernameDialog(final String chatName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Username");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userName = input.getText().toString();
                if (!userName.isEmpty()) {
                    Intent intent = new Intent(PartyActivity.this, ChatActivity.class);
                    intent.putExtra("chatName", chatName);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
