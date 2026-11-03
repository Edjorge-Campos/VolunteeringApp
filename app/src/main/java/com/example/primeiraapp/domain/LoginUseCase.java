package com.example.primeiraapp.domain;

import com.example.primeiraapp.data.AuthRepository;
import com.example.primeiraapp.model.LoginCredentials;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;

public final class LoginUseCase {
    private final AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LoginValidationResult validate(LoginCredentials credentials) {
        return LoginValidator.validate(credentials);
    }

    public Task<FirebaseUser> execute(LoginCredentials credentials) {
        LoginValidationResult validation = validate(credentials);
        if (!validation.isValid()) {
            return Tasks.forException(new IllegalArgumentException(validation.getFirstError()));
        }
        return authRepository.signIn(credentials.getEmail(), credentials.getPassword());
    }
}
