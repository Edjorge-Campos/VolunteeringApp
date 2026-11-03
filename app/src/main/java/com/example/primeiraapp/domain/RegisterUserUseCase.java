package com.example.primeiraapp.domain;

import com.example.primeiraapp.data.AuthRepository;
import com.example.primeiraapp.data.UserRepository;
import com.example.primeiraapp.model.UserProfile;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;

public final class RegisterUserUseCase {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    public RegisterUserUseCase(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    public Task<Void> saveProfile(String displayName, String availability) {
        String validationError = RegisterValidator.validateProfile(displayName, availability);
        if (validationError != null) {
            return Tasks.forException(new IllegalArgumentException(validationError));
        }

        FirebaseUser user = authRepository.getCurrentUser();
        if (user == null || user.getUid() == null || user.getUid().trim().isEmpty()) {
            return Tasks.forException(new IllegalStateException("A sessão expirou. Inicia sessão novamente."));
        }

        String email = user.getEmail() == null ? "" : user.getEmail().trim();
        UserProfile profile = new UserProfile(
                user.getUid(),
                email,
                displayName.trim(),
                availability.trim()
        );
        return userRepository.saveProfile(profile);
    }
}
