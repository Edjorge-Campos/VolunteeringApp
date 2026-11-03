package com.example.primeiraapp;

import com.example.primeiraapp.domain.RegisterCredentialsValidator;
import com.example.primeiraapp.domain.RegisterValidationResult;
import com.example.primeiraapp.model.RegisterCredentials;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RegisterCredentialsValidatorTest {

    @Test
    public void rejeitaNomeVazio() {
        RegisterValidationResult result = RegisterCredentialsValidator.validate(
                new RegisterCredentials("", "ana@example.com", "Password1", "Password1")
        );

        assertEquals("Introduz o teu nome.", result.getNameError());
    }

    @Test
    public void rejeitaEmailInvalido() {
        RegisterValidationResult result = RegisterCredentialsValidator.validate(
                new RegisterCredentials("Ana", "email-invalido", "Password1", "Password1")
        );

        assertEquals("Introduz um email válido.", result.getEmailError());
    }

    @Test
    public void rejeitaPalavraPasseSemRequisitosMinimos() {
        RegisterValidationResult result = RegisterCredentialsValidator.validate(
                new RegisterCredentials("Ana", "ana@example.com", "password", "password")
        );

        assertEquals(
                "A palavra-passe deve ter pelo menos 8 caracteres, uma maiúscula, uma minúscula e um número.",
                result.getPasswordError()
        );
    }

    @Test
    public void rejeitaConfirmacaoDiferente() {
        RegisterValidationResult result = RegisterCredentialsValidator.validate(
                new RegisterCredentials("Ana", "ana@example.com", "Password1", "Password2")
        );

        assertEquals("A confirmação deve ser igual à palavra-passe.",
                result.getConfirmationError());
    }

    @Test
    public void aceitaCredenciaisValidas() {
        RegisterValidationResult result = RegisterCredentialsValidator.validate(
                new RegisterCredentials("Ana", "ana@example.com", "Password1", "Password1")
        );

        assertEquals(true, result.isValid());
        assertNull(result.getFirstError());
    }
}
