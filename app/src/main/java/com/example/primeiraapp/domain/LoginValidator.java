package com.example.primeiraapp.domain;

import com.example.primeiraapp.model.LoginCredentials;

import java.util.regex.Pattern;

public final class LoginValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    private LoginValidator() {
    }

    public static LoginValidationResult validate(LoginCredentials credentials) {
        String emailError = EMAIL_PATTERN.matcher(credentials.getEmail()).matches()
                ? null
                : "Introduz um email válido.";
        String passwordError = credentials.getPassword().isEmpty()
                ? "Introduz a palavra-passe."
                : null;
        return emailError == null && passwordError == null
                ? LoginValidationResult.valid()
                : LoginValidationResult.invalid(emailError, passwordError);
    }
}
