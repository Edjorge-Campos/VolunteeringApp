package com.example.primeiraapp;

import com.example.primeiraapp.domain.LoginFormState;
import com.example.primeiraapp.domain.LoginValidator;
import com.example.primeiraapp.domain.RegisterFormState;
import com.example.primeiraapp.domain.RegisterValidationResult;
import com.example.primeiraapp.domain.RegisterCredentialsValidator;
import com.example.primeiraapp.model.LoginCredentials;
import com.example.primeiraapp.model.RegisterCredentials;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FormValidationStateTest {
    @Test
    public void loginInicialNaoMostraErros() {
        LoginFormState state = LoginFormState.initial();

        assertFalse(state.isSubmitAttempted());
        assertNull(state.getValidation().getEmailError());
        assertNull(state.getValidation().getPasswordError());
    }

    @Test
    public void loginMostraErrosDepoisDaSubmissao() {
        LoginFormState state = LoginFormState.initial().afterValidation(
                LoginValidator.validate(new LoginCredentials("", ""))
        );

        assertTrue(state.isSubmitAttempted());
        assertTrue(state.getValidation().getEmailError() != null);
        assertTrue(state.getValidation().getPasswordError() != null);
    }

    @Test
    public void loginErroDesapareceQuandoCampoEValidado() {
        LoginFormState invalidState = LoginFormState.initial().afterValidation(
                LoginValidator.validate(new LoginCredentials("", "Password1"))
        );
        LoginFormState state = invalidState.afterValidation(
                LoginValidator.validate(new LoginCredentials("ana@example.com", "Password1"))
        );

        assertTrue(invalidState.getValidation().getEmailError() != null);
        assertNull(state.getValidation().getEmailError());
        assertNull(state.getValidation().getPasswordError());
    }

    @Test
    public void registoInicialNaoMostraErros() {
        RegisterFormState state = RegisterFormState.initial();

        assertFalse(state.isSubmitAttempted());
        assertNull(state.getValidation().getNameError());
        assertNull(state.getValidation().getEmailError());
    }

    @Test
    public void registoMostraErrosDepoisDaSubmissao() {
        RegisterValidationResult validation = RegisterCredentialsValidator.validate(
                new RegisterCredentials("", "", "", "")
        );
        RegisterFormState state = RegisterFormState.initial().afterValidation(validation);

        assertTrue(state.isSubmitAttempted());
        assertTrue(state.getValidation().getNameError() != null);
        assertTrue(state.getValidation().getEmailError() != null);
    }

    @Test
    public void registoErroDesapareceQuandoCamposFicamValidos() {
        RegisterFormState invalidState = RegisterFormState.initial().afterValidation(
                RegisterCredentialsValidator.validate(
                        new RegisterCredentials("", "ana@example.com", "Password1", "Password1")
                )
        );
        RegisterFormState state = invalidState.afterValidation(
                RegisterCredentialsValidator.validate(
                        new RegisterCredentials("Ana", "ana@example.com", "Password1", "Password1")
                )
        );

        assertTrue(invalidState.getValidation().getNameError() != null);
        assertNull(state.getValidation().getNameError());
        assertNull(state.getValidation().getEmailError());
        assertNull(state.getValidation().getPasswordError());
        assertNull(state.getValidation().getConfirmationError());
    }
}
