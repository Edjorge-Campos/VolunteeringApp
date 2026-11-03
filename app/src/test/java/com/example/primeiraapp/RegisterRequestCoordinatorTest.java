package com.example.primeiraapp;

import com.example.primeiraapp.data.AuthRepository;
import com.example.primeiraapp.domain.RegisterAccountUseCase;
import com.example.primeiraapp.domain.RegisterRequestCoordinator;
import com.example.primeiraapp.domain.RegisterState;
import com.example.primeiraapp.model.RegisterCredentials;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegisterRequestCoordinatorTest {

    private static final RegisterCredentials VALID_CREDENTIALS =
            new RegisterCredentials("Ana", "ana@example.com", "Password1", "Password1");

    @Test
    public void representaEstadoDeCarregamentoEnquantoPedidoEstáPendente() {
        FakeAuthRepository repository = new FakeAuthRepository();
        RegisterRequestCoordinator coordinator = newCoordinator(repository);

        assertTrue(coordinator.submit(VALID_CREDENTIALS, state -> {
        }));
        assertEquals(RegisterState.Status.LOADING, coordinator.getState().getStatus());
        assertEquals(1, repository.createUserCalls);
    }

    @Test
    public void representaSucessoQuandoRegistoTermina() {
        FakeAuthRepository repository = new FakeAuthRepository();
        RegisterRequestCoordinator coordinator = newCoordinator(repository);

        coordinator.submit(VALID_CREDENTIALS, state -> {
        });
        repository.completeSuccessfully();

        assertEquals(RegisterState.Status.SUCCESS, coordinator.getState().getStatus());
    }

    @Test
    public void representaErroQuandoRegistoFalha() {
        FakeAuthRepository repository = new FakeAuthRepository();
        RegisterRequestCoordinator coordinator = newCoordinator(repository);

        coordinator.submit(VALID_CREDENTIALS, state -> {
        });
        repository.completeWithError();

        assertEquals(RegisterState.Status.ERROR, coordinator.getState().getStatus());
        assertEquals(
                "Não foi possível criar a conta. Tenta novamente.",
                coordinator.getState().getMessage()
        );
    }

    @Test
    public void impedePedidosDuplicadosDuranteCarregamento() {
        FakeAuthRepository repository = new FakeAuthRepository();
        RegisterRequestCoordinator coordinator = newCoordinator(repository);

        assertTrue(coordinator.submit(VALID_CREDENTIALS, state -> {
        }));
        assertFalse(coordinator.submit(VALID_CREDENTIALS, state -> {
        }));

        assertEquals(1, repository.createUserCalls);
    }

    private RegisterRequestCoordinator newCoordinator(FakeAuthRepository repository) {
        return new RegisterRequestCoordinator(
                new RegisterAccountUseCase(repository)
        );
    }

    private static final class FakeAuthRepository implements AuthRepository {
        private final TaskCompletionSource<FirebaseUser> result = new TaskCompletionSource<>();
        private int createUserCalls;

        @Override
        public FirebaseUser getCurrentUser() {
            return null;
        }

        @Override
        public Task<FirebaseUser> createUser(String email, String password) {
            createUserCalls++;
            return result.getTask();
        }

        @Override
        public Task<FirebaseUser> signIn(String email, String password) {
            return result.getTask();
        }

        private void completeSuccessfully() {
            result.setResult(null);
        }

        private void completeWithError() {
            result.setException(new IllegalStateException("network failure"));
        }
    }
}
