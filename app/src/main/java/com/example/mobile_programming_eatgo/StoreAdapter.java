package com.example.mobile_programming_eatgo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private final List<Store> storeList;

    public StoreAdapter(List<Store> storeList) {
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store currentStore = storeList.get(position);
        holder.storeImage.setImageResource(currentStore.getImageResId());
        holder.storeName.setText(currentStore.getName());
        holder.storeDetails.setText(currentStore.getDetails());
        holder.deliveryStatus.setText(currentStore.getDeliveryStatus());
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        public ImageView storeImage;
        public TextView storeName;
        public TextView storeDetails;
        public TextView deliveryStatus;

        public StoreViewHolder(View itemView) {
            super(itemView);
            storeImage = itemView.findViewById(R.id.storeImage);
            storeName = itemView.findViewById(R.id.storeName);
            storeDetails = itemView.findViewById(R.id.storeDetails);
            deliveryStatus = itemView.findViewById(R.id.deliveryStatus);
        }
    }
}