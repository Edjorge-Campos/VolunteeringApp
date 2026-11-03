package com.example.primeiraapp.domain;

import com.example.primeiraapp.model.RegisterCredentials;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public final class RegisterRequestCoordinator {
    public interface StateListener {
        void onStateChanged(RegisterState state);
    }

    private final RegisterAccountUseCase registerAccountUseCase;
    private RegisterState state = RegisterState.idle();

    public RegisterRequestCoordinator(RegisterAccountUseCase registerAccountUseCase) {
        this.registerAccountUseCase = registerAccountUseCase;
    }

    public RegisterState getState() {
        return state;
    }

    public boolean submit(RegisterCredentials credentials, StateListener listener) {
        if (state.getStatus() == RegisterState.Status.LOADING) {
            return false;
        }

        RegisterValidationResult validation = registerAccountUseCase.validate(credentials);
        if (!validation.isValid()) {
            updateState(RegisterState.error(validation.getFirstError()), listener);
            return false;
        }

        updateState(RegisterState.loading(), listener);
        Task<FirebaseUser> request = registerAccountUseCase.execute(credentials);
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateState(RegisterState.success("Registado com sucesso"), listener);
            } else {
                Exception error = task.getException();
                updateState(RegisterState.error(error == null
                        ? "Não foi possível criar a conta. Tenta novamente."
                        : RegisterErrorMapper.toMessage(error)), listener);
            }
        });
        return true;
    }

    private void updateState(RegisterState newState, StateListener listener) {
        state = newState;
        listener.onStateChanged(newState);
    }
}
