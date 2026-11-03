package com.example.primeiraapp.domain;

public final class RegisterValidator {
    private RegisterValidator() {
    }

    public static String validateProfile(String displayName, String availability) {
        if (displayName == null || displayName.trim().isEmpty()) {
            return "Introduz o teu nome.";
        }
        if (availability == null || availability.trim().isEmpty()) {
            return "Seleciona um dia de disponibilidade.";
        }
        return null;
    }
}
