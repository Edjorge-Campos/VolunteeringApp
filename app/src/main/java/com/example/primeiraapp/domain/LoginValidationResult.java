package com.example.primeiraapp.domain;

public final class LoginValidationResult {
    private final String emailError;
    private final String passwordError;

    private LoginValidationResult(String emailError, String passwordError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
    }

    public static LoginValidationResult valid() {
        return new LoginValidationResult(null, null);
    }

    public static LoginValidationResult invalid(String emailError, String passwordError) {
        return new LoginValidationResult(emailError, passwordError);
    }

    public String getEmailError() {
        return emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public boolean isValid() {
        return emailError == null && passwordError == null;
    }

    public String getFirstError() {
        return emailError == null ? passwordError : emailError;
    }
}
