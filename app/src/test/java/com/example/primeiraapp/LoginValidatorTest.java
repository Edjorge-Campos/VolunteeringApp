package com.example.primeiraapp;

import com.example.primeiraapp.domain.LoginValidator;
import com.example.primeiraapp.domain.LoginValidationResult;
import com.example.primeiraapp.model.LoginCredentials;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginValidatorTest {
    @Test
    public void aceitaCredenciaisDeLoginValidas() {
        LoginValidationResult result = LoginValidator.validate(
                new LoginCredentials("ana@example.com", "Password1")
        );

        assertTrue(result.isValid());
    }

    @Test
    public void rejeitaEmailOuPasswordVazios() {
        LoginValidationResult result = LoginValidator.validate(
                new LoginCredentials("", "")
        );

        assertEquals("Introduz um email válido.", result.getEmailError());
        assertEquals("Introduz a palavra-passe.", result.getPasswordError());
    }
}
