package com.yjinpk.eatgo;

public class OrderDTO {
    private String userName;
    private String menuItem;
    private int menuPrice;

    public OrderDTO() {
        // Default constructor required for calls to DataSnapshot.getValue(OrderDTO.class)
    }

    public OrderDTO(String userName, String menuItem, int menuPrice) {
        this.userName = userName;
        this.menuItem = menuItem;
        this.menuPrice = menuPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }
}
