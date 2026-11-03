package com.example.primeiraapp.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public interface AuthRepository {
    FirebaseUser getCurrentUser();

    Task<FirebaseUser> createUser(String email, String password);

    Task<FirebaseUser> signIn(String email, String password);
}
