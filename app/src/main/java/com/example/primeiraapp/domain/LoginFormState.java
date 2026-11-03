package com.example.primeiraapp.domain;

public final class LoginFormState {
    private final boolean submitAttempted;
    private final LoginValidationResult validation;

    private LoginFormState(boolean submitAttempted, LoginValidationResult validation) {
        this.submitAttempted = submitAttempted;
        this.validation = validation;
    }

    public static LoginFormState initial() {
        return new LoginFormState(false, LoginValidationResult.valid());
    }

    public LoginFormState afterValidation(LoginValidationResult validation) {
        return new LoginFormState(true, validation);
    }

    public boolean isSubmitAttempted() {
        return submitAttempted;
    }

    public LoginValidationResult getValidation() {
        return validation;
    }
}
