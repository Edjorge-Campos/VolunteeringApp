package com.example.primeiraapp.domain;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthException;

public final class RegisterErrorMapper {
    private RegisterErrorMapper() {
    }

    public static String toMessage(Exception error) {
        if (error instanceof FirebaseNetworkException) {
            return "Não foi possível ligar ao serviço. Verifica a tua ligação à internet e tenta novamente.";
        }

        if (error instanceof FirebaseAuthException) {
            String errorCode = ((FirebaseAuthException) error).getErrorCode();
            if ("ERROR_EMAIL_ALREADY_IN_USE".equals(errorCode)) {
                return "Este email já está registado.";
            }
            if ("ERROR_INVALID_EMAIL".equals(errorCode)) {
                return "Introduz um email válido.";
            }
            if ("ERROR_WEAK_PASSWORD".equals(errorCode)) {
                return "A palavra-passe não cumpre os requisitos mínimos.";
            }
            if ("ERROR_TOO_MANY_REQUESTS".equals(errorCode)) {
                return "Foram feitas demasiadas tentativas. Tenta novamente mais tarde.";
            }
        }

        return "Não foi possível criar a conta. Tenta novamente.";
    }
}
