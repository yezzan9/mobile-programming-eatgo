package com.example.mobile_programming_eatgo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class activity_menu extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StoreAdapter adapter;
    private List<Store> storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeList = getStoreList();
        adapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(adapter);

        // FloatingActionButton
        FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            // Handle the add button click
        });
    }

    private List<Store> getStoreList() {
        // Replace this with your actual data fetching logic
        List<Store> stores = new ArrayList<>();
        stores.add(new Store(R.drawable.logo, "가게 이름", "@원이 모자라요!\n마감까지 @분", "배달위치"));
        stores.add(new Store(R.drawable.logo, "가게 이름", "@원이 모자라요!\n마감까지 @분", "배달위치"));
        stores.add(new Store(R.drawable.logo, "가게 이름", "@원이 모자라요!\n마감까지 @분", "배달위치"));
        stores.add(new Store(R.drawable.logo, "가게 이름", "@원이 모자라요!\n마감까지 @분", "배달위치"));

        return stores;
    }
}
