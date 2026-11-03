package com.example.primeiraapp.domain;

import com.example.primeiraapp.model.RegisterCredentials;

import java.util.regex.Pattern;

public final class RegisterCredentialsValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d.*");

    private RegisterCredentialsValidator() {
    }

    public static RegisterValidationResult validate(RegisterCredentials credentials) {
        String emailError = null;
        String passwordError = null;
        String confirmationError = null;

        if (credentials.getName().isEmpty()) {
            return RegisterValidationResult.invalid(
                    "Introduz o teu nome.",
                    null,
                    null,
                    null
            );
        }

        if (!EMAIL_PATTERN.matcher(credentials.getEmail()).matches()) {
            emailError = "Introduz um email válido.";
        }

        String password = credentials.getPassword();
        if (password.length() < 8
                || !UPPERCASE_PATTERN.matcher(password).matches()
                || !LOWERCASE_PATTERN.matcher(password).matches()
                || !DIGIT_PATTERN.matcher(password).matches()) {
            passwordError = "A palavra-passe deve ter pelo menos 8 caracteres, uma maiúscula, uma minúscula e um número.";
        }

        if (!password.equals(credentials.getPasswordConfirmation())) {
            confirmationError = "A confirmação deve ser igual à palavra-passe.";
        }

        return emailError == null && passwordError == null && confirmationError == null
                ? RegisterValidationResult.valid()
                : RegisterValidationResult.invalid(
                        null,
                        emailError,
                        passwordError,
                        confirmationError
                );
    }
}
