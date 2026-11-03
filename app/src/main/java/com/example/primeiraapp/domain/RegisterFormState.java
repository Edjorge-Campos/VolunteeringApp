package com.example.primeiraapp.domain;

public final class RegisterFormState {
    private final boolean submitAttempted;
    private final RegisterValidationResult validation;

    private RegisterFormState(boolean submitAttempted, RegisterValidationResult validation) {
        this.submitAttempted = submitAttempted;
        this.validation = validation;
    }

    public static RegisterFormState initial() {
        return new RegisterFormState(false, RegisterValidationResult.valid());
    }

    public RegisterFormState afterValidation(RegisterValidationResult validation) {
        return new RegisterFormState(true, validation);
    }

    public boolean isSubmitAttempted() {
        return submitAttempted;
    }

    public RegisterValidationResult getValidation() {
        return validation;
    }
}
