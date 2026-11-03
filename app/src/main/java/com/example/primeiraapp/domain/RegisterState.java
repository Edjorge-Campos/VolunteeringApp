package com.example.primeiraapp.domain;

public final class RegisterState {
    public enum Status {
        IDLE,
        LOADING,
        SUCCESS,
        ERROR
    }

    private final Status status;
    private final String message;

    private RegisterState(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static RegisterState idle() {
        return new RegisterState(Status.IDLE, null);
    }

    public static RegisterState loading() {
        return new RegisterState(Status.LOADING, null);
    }

    public static RegisterState success(String message) {
        return new RegisterState(Status.SUCCESS, message);
    }

    public static RegisterState error(String message) {
        return new RegisterState(Status.ERROR, message);
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
