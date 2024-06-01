package com.yjinpk.eatgo;

public class ChatDTO {

    private String userName;
    private String message;
    private String location; // 추가된 필드

    public ChatDTO() {}

    public ChatDTO(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public ChatDTO(String userName, String message, String location) { // 새로운 생성자
        this.userName = userName;
        this.message = message;
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
