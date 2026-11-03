package com.example.primeiraapp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primeiraapp.data.AuthRepository;
import com.example.primeiraapp.data.FirebaseAuthRepository;
import com.example.primeiraapp.domain.RegisterAccountUseCase;
import com.example.primeiraapp.domain.RegisterFormState;
import com.example.primeiraapp.domain.RegisterRequestCoordinator;
import com.example.primeiraapp.domain.RegisterState;
import com.example.primeiraapp.domain.RegisterValidationResult;
import com.example.primeiraapp.model.RegisterCredentials;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private final RegisterAccountUseCase registerAccountUseCase =
            new RegisterAccountUseCase(new FirebaseAuthRepository());
    private final RegisterRequestCoordinator requestCoordinator =
            new RegisterRequestCoordinator(registerAccountUseCase);
    private RegisterFormState formState = RegisterFormState.initial();

    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmationInput;
    private TextInputLayout nameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout confirmationLayout;
    private Button registerButton;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.registerName);
        emailInput = findViewById(R.id.registerEmail);
        passwordInput = findViewById(R.id.registerPassword);
        confirmationInput = findViewById(R.id.registerPasswordConfirmation);
        nameLayout = findViewById(R.id.registerNameLayout);
        emailLayout = findViewById(R.id.registerEmailLayout);
        passwordLayout = findViewById(R.id.registerPasswordLayout);
        confirmationLayout = findViewById(R.id.registerPasswordConfirmationLayout);
        registerButton = findViewById(R.id.registerButton);
        message = findViewById(R.id.registerMessage);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateValidation(formState.isSubmitAttempted());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        nameInput.addTextChangedListener(watcher);
        emailInput.addTextChangedListener(watcher);
        passwordInput.addTextChangedListener(watcher);
        confirmationInput.addTextChangedListener(watcher);
        updateValidation(formState.isSubmitAttempted());

        registerButton.setOnClickListener(view -> register());
        findViewById(R.id.loginLink).setOnClickListener(view -> finish());
    }

    private void register() {
        if (requestCoordinator.getState().getStatus() == RegisterState.Status.LOADING) {
            return;
        }
        RegisterCredentials credentials = readCredentials();
        RegisterValidationResult validation = registerAccountUseCase.validate(credentials);
        formState = formState.afterValidation(validation);
        renderValidation();
        requestCoordinator.submit(credentials, this::onStateChanged);
    }

    private void onStateChanged(RegisterState state) {
        boolean loading = state.getStatus() == RegisterState.Status.LOADING;
        registerButton.setEnabled(!loading && registerAccountUseCase.validate(readCredentials()).isValid());
        registerButton.setText(loading ? R.string.register_submitting : R.string.register_button);

        if (state.getStatus() == RegisterState.Status.SUCCESS) {
            Toast.makeText(this, state.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, RegisterMenu.class));
            finish();
        } else if (state.getStatus() == RegisterState.Status.ERROR) {
            showMessage(state.getMessage());
            Toast.makeText(this, state.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private RegisterCredentials readCredentials() {
        return new RegisterCredentials(
                nameInput.getText().toString(),
                emailInput.getText().toString(),
                passwordInput.getText().toString(),
                confirmationInput.getText().toString()
        );
    }

    private void updateValidation(boolean showErrors) {
        RegisterValidationResult validation = registerAccountUseCase.validate(readCredentials());
        if (showErrors) {
            formState = formState.afterValidation(validation);
            renderValidation();
        } else {
            clearValidation();
        }
        if (requestCoordinator.getState().getStatus() != RegisterState.Status.LOADING) {
            registerButton.setEnabled(validation.isValid());
        }
    }

    private void renderValidation() {
        RegisterValidationResult validation = formState.getValidation();
        nameLayout.setError(validation.getNameError());
        emailLayout.setError(validation.getEmailError());
        passwordLayout.setError(validation.getPasswordError());
        confirmationLayout.setError(validation.getConfirmationError());
    }

    private void clearValidation() {
        nameLayout.setError(null);
        emailLayout.setError(null);
        passwordLayout.setError(null);
        confirmationLayout.setError(null);
    }

    private void showMessage(String text) {
        message.setText(text);
        message.setVisibility(VISIBLE);
    }
}
