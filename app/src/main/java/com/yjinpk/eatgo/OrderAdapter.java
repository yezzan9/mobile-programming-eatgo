package com.yjinpk.eatgo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends ArrayAdapter<OrderDTO> {
    private DatabaseReference databaseReference;
    private List<OrderDTO> orderList = new ArrayList<>();
    private static final String TAG = "OrderAdapter";
    private int layoutResource;
    private int textViewResourceId;

    public OrderAdapter(Context context, DatabaseReference ref, int layoutResource, int textViewResourceId) {
        super(context, layoutResource);
        this.databaseReference = ref;
        this.layoutResource = layoutResource;
        this.textViewResourceId = textViewResourceId;
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                OrderDTO order = dataSnapshot.getValue(OrderDTO.class);
                if (order != null) {
                    orderList.add(order);
                    notifyDataSetChanged();
                    Log.d(TAG, "Order added: " + order.getMenuItem() + ", User: " + order.getUserName());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                OrderDTO order = dataSnapshot.getValue(OrderDTO.class);
                if (order != null) {
                    orderList.remove(order);
                    notifyDataSetChanged();
                    Log.d(TAG, "Order removed: " + order.getMenuItem());
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public OrderDTO getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderDTO order = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
        }

        TextView userName = convertView.findViewById(textViewResourceId);
        userName.setText(order.getUserName() + " : " + order.getMenuItem() + " (" + order.getMenuPrice() + " 원)");

        return convertView;
    }

    public void removeItem(int position) {
        orderList.remove(position);
        notifyDataSetChanged();
    }
}
