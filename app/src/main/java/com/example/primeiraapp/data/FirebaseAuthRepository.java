package com.example.primeiraapp.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepository implements AuthRepository {
    private final FirebaseAuth auth;

    public FirebaseAuthRepository() {
        this(FirebaseAuth.getInstance());
    }

    FirebaseAuthRepository(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    @Override
    public Task<FirebaseUser> createUser(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password)
                .continueWith(task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException() != null
                                ? task.getException()
                                : new IllegalStateException("Não foi possível criar a conta.");
                    }
                    FirebaseUser user = auth.getCurrentUser();
                    if (user == null) {
                        throw new IllegalStateException("A conta foi criada, mas a sessão não ficou disponível.");
                    }
                    return user;
                });
    }

    @Override
    public Task<FirebaseUser> signIn(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password)
                .continueWith(task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException() != null
                                ? task.getException()
                                : new IllegalStateException("Não foi possível iniciar sessão.");
                    }
                    FirebaseUser user = auth.getCurrentUser();
                    if (user == null) {
                        throw new IllegalStateException("A sessão não ficou disponível.");
                    }
                    return user;
                });
    }
}
