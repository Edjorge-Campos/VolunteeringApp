package com.example.primeiraapp.model;

public final class LoginCredentials {
    private final String email;
    private final String password;

    public LoginCredentials(String email, String password) {
        this.email = email == null ? "" : email.trim();
        this.password = password == null ? "" : password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
