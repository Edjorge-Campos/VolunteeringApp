package com.example.primeiraapp.domain;

public final class RegisterValidationResult {
    private final String nameError;
    private final String emailError;
    private final String passwordError;
    private final String confirmationError;

    private RegisterValidationResult(
            String nameError,
            String emailError,
            String passwordError,
            String confirmationError
    ) {
        this.nameError = nameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmationError = confirmationError;
    }

    public static RegisterValidationResult valid() {
        return new RegisterValidationResult(null, null, null, null);
    }

    public static RegisterValidationResult invalid(
            String nameError,
            String emailError,
            String passwordError,
            String confirmationError
    ) {
        return new RegisterValidationResult(nameError, emailError, passwordError, confirmationError);
    }

    public String getNameError() {
        return nameError;
    }

    public String getEmailError() {
        return emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getConfirmationError() {
        return confirmationError;
    }

    public boolean isValid() {
        return nameError == null
                && emailError == null
                && passwordError == null
                && confirmationError == null;
    }

    public String getFirstError() {
        if (nameError != null) {
            return nameError;
        }
        if (emailError != null) {
            return emailError;
        }
        if (passwordError != null) {
            return passwordError;
        }
        return confirmationError;
    }
}
