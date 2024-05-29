package com.yjinpk.eatgo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yjinpk.eatgo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner menuSelection = findViewById(R.id.menu_selection);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuSelection.setAdapter(adapter);

        Button createDeliveryButton = findViewById(R.id.create_delivery_button);
        createDeliveryButton.setOnClickListener(v -> {
            // 버튼 클릭 시 동작할 코드 작성
            Toast.makeText(MainActivity.this, "배달팟 만들기 버튼 클릭됨", Toast.LENGTH_SHORT).show();
        });
    }
}
