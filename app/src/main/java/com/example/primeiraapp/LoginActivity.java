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
import com.example.primeiraapp.domain.LoginUseCase;
import com.example.primeiraapp.domain.LoginFormState;
import com.example.primeiraapp.domain.LoginValidationResult;
import com.example.primeiraapp.domain.RegisterErrorMapper;
import com.example.primeiraapp.model.LoginCredentials;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private final AuthRepository authRepository = new FirebaseAuthRepository();
    private final LoginUseCase loginUseCase = new LoginUseCase(authRepository);
    private LoginFormState formState = LoginFormState.initial();

    private EditText emailInput;
    private EditText passwordInput;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private Button loginButton;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (authRepository.getCurrentUser() != null) {
            openHomePage();
            return;
        }

        setContentView(R.layout.activity_login);
        emailInput = findViewById(R.id.loginEmail);
        passwordInput = findViewById(R.id.loginPassword);
        emailLayout = findViewById(R.id.loginEmailLayout);
        passwordLayout = findViewById(R.id.loginPasswordLayout);
        loginButton = findViewById(R.id.loginButton);
        message = findViewById(R.id.loginMessage);

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
        emailInput.addTextChangedListener(watcher);
        passwordInput.addTextChangedListener(watcher);
        updateValidation(formState.isSubmitAttempted());

        loginButton.setOnClickListener(view -> login());
        findViewById(R.id.createAccountLink).setOnClickListener(view ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void login() {
        LoginCredentials credentials = readCredentials();
        LoginValidationResult validation = loginUseCase.validate(credentials);
        formState = formState.afterValidation(validation);
        renderValidation();
        if (!validation.isValid()) {
            return;
        }

        setLoading(true);
        loginUseCase.execute(credentials).addOnCompleteListener(task -> {
            setLoading(false);
            if (task.isSuccessful()) {
                openHomePage();
            } else {
                String error = task.getException() == null
                        ? "Não foi possível iniciar sessão. Tenta novamente."
                        : RegisterErrorMapper.toMessage(task.getException());
                showMessage(error);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private LoginCredentials readCredentials() {
        return new LoginCredentials(
                emailInput.getText().toString(),
                passwordInput.getText().toString()
        );
    }

    private void updateValidation(boolean showErrors) {
        LoginValidationResult validation = loginUseCase.validate(readCredentials());
        if (showErrors) {
            formState = formState.afterValidation(validation);
            renderValidation();
        } else {
            clearValidation();
        }
        loginButton.setEnabled(validation.isValid());
    }

    private void renderValidation() {
        LoginValidationResult validation = formState.getValidation();
        emailLayout.setError(validation.getEmailError());
        passwordLayout.setError(validation.getPasswordError());
    }

    private void clearValidation() {
        emailLayout.setError(null);
        passwordLayout.setError(null);
    }

    private void setLoading(boolean loading) {
        loginButton.setEnabled(!loading && loginUseCase.validate(readCredentials()).isValid());
        loginButton.setText(loading ? R.string.login_submitting : R.string.login_button);
    }

    private void showMessage(String text) {
        message.setText(text);
        message.setVisibility(VISIBLE);
    }

    private void openHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
