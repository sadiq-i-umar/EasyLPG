package com.example.easylpg;

public class LoginDetails extends MainActivity {
    private String email;

    public LoginDetails(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
