package com.example.mobile_programming_eatgo;

public class Store {
    private int imageResId;
    private String name;
    private String details;
    private String deliveryStatus;

    public Store(int imageResId, String name, String details, String deliveryStatus) {
        this.imageResId = imageResId;
        this.name = name;
        this.details = details;
        this.deliveryStatus = deliveryStatus;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}