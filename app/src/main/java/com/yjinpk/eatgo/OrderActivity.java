package com.yjinpk.eatgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";
    private String CHAT_NAME;
    private String USER_NAME;
    private ListView orderListView;
    private TextView totalAmountView, statusMessageView;
    private Button addOrderButton;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private int totalAmount = 0;
    private int orderLimit = 20000;  // Default order limit
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderListView = findViewById(R.id.order_list_view);
        totalAmountView = findViewById(R.id.total_amount);
        statusMessageView = findViewById(R.id.status_message);
        addOrderButton = findViewById(R.id.add_order_button);

        CHAT_NAME = getIntent().getStringExtra("chatName");
        USER_NAME = getIntent().getStringExtra("userName");
        databaseReference = firebaseDatabase.getReference().child("chat").child(CHAT_NAME).child("orders");

        // OrderAdapter 생성
        adapter = new OrderAdapter(this, databaseReference, R.layout.chat_item, R.id.chat_item_text);
        orderListView.setAdapter(adapter);

        // Intent에서 최소 주문 금액 값을 가져옴
        if (getIntent().hasExtra("minOrder")) {
            orderLimit = getIntent().getIntExtra("minOrder", 20000);
        }

        calculateTotal(adapter); // 초기 총 금액 계산

        addOrderButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, AddOrderActivity.class);
            intent.putExtra("chatName", CHAT_NAME);
            intent.putExtra("userName", USER_NAME);
            startActivity(intent);
        });

        orderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(adapter.getItem(position), position);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 최소 주문 금액을 Firebase에서 가져오기
        DatabaseReference minOrderRef = firebaseDatabase.getReference().child("chat").child(CHAT_NAME).child("minOrder");
        minOrderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer minOrder = dataSnapshot.getValue(Integer.class);
                if (minOrder != null) {
                    orderLimit = minOrder;
                }
                // 어댑터가 데이터를 로드한 후에 계산하기 위해 calculateTotal() 호출
                calculateTotal(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read min order value.", databaseError.toException());
            }
        });

        calculateTotal(adapter); // 초기 총 금액 계산
    }

    private void showDeleteConfirmationDialog(OrderDTO order, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("삭제하시겠습니까?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteOrder(order, position);
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void deleteOrder(OrderDTO order, int position) {
        databaseReference.orderByChild("menuItem").equalTo(order.getMenuItem())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                        adapter.removeItem(position); // 어댑터에서 아이템 제거
                        calculateTotal(adapter); // 총 금액 다시 계산
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "Failed to delete order: " + databaseError.getMessage());
                    }
                });
    }

    private void calculateTotal(OrderAdapter adapter) {
        totalAmount = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            totalAmount += adapter.getItem(i).getMenuPrice();
        }
        totalAmountView.setText(String.format("%,d / %,d 원", totalAmount, orderLimit));
        if (totalAmount >= orderLimit) {
            statusMessageView.setText("주문이 가능해요!");
        } else {
            statusMessageView.setText("");
        }
    }
}
