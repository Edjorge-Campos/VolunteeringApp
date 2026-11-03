package com.example.primeiraapp.model;

public final class RegisterCredentials {
    private final String name;
    private final String email;
    private final String password;
    private final String passwordConfirmation;

    public RegisterCredentials(
            String name,
            String email,
            String password,
            String passwordConfirmation
    ) {
        this.name = name == null ? "" : name.trim();
        this.email = email == null ? "" : email.trim();
        this.password = password == null ? "" : password;
        this.passwordConfirmation = passwordConfirmation == null ? "" : passwordConfirmation;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}
