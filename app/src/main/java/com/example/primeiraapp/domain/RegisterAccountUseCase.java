package com.example.primeiraapp.domain;

import com.example.primeiraapp.data.AuthRepository;
import com.example.primeiraapp.model.RegisterCredentials;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;

public final class RegisterAccountUseCase {
    private final AuthRepository authRepository;

    public RegisterAccountUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public RegisterValidationResult validate(RegisterCredentials credentials) {
        return RegisterCredentialsValidator.validate(credentials);
    }

    public Task<FirebaseUser> execute(RegisterCredentials credentials) {
        RegisterValidationResult validation = validate(credentials);
        if (!validation.isValid()) {
            return Tasks.forException(new IllegalArgumentException(validation.getFirstError()));
        }
        return authRepository.createUser(credentials.getEmail(), credentials.getPassword());
    }
}
