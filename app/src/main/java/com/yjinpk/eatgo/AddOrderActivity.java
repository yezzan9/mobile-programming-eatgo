package com.yjinpk.eatgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddOrderActivity extends AppCompatActivity {

    private EditText menuItemEditText, menuPriceEditText;
    private Button addOrderButton;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private String CHAT_NAME;
    private String USER_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        menuItemEditText = findViewById(R.id.menu_item_edit_text);
        menuPriceEditText = findViewById(R.id.menu_price_edit_text);
        addOrderButton = findViewById(R.id.add_order_button);

        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAME = intent.getStringExtra("userName");
        databaseReference = firebaseDatabase.getReference().child("chat").child(CHAT_NAME).child("orders");

        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menuItem = menuItemEditText.getText().toString();
                int menuPrice = Integer.parseInt(menuPriceEditText.getText().toString());

                OrderDTO order = new OrderDTO(USER_NAME, menuItem, menuPrice);
                databaseReference.push().setValue(order);

                // 주문 추가 후 OrderActivity로 이동
                Intent orderIntent = new Intent(AddOrderActivity.this, OrderActivity.class);
                orderIntent.putExtra("chatName", CHAT_NAME);
                orderIntent.putExtra("userName", USER_NAME);
                startActivity(orderIntent);
                finish(); // AddOrderActivity 종료
            }
        });
    }
}
