package com.example.wheeler.ModelClass;

public class StoreUserData {
    String email, username, phone;

    public StoreUserData() {}

    public StoreUserData(String email, String username, String phone) {
        this.email = email;
        this.username = username;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
