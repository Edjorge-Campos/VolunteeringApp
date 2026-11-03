package com.example.primeiraapp.data;

import com.example.primeiraapp.model.UserProfile;
import com.google.android.gms.tasks.Task;

public interface UserRepository {
    Task<Void> saveProfile(UserProfile profile);
}
