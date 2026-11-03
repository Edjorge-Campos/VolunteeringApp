package com.example.primeiraapp.data;

import com.example.primeiraapp.model.UserProfile;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirebaseUserRepository implements UserRepository {
    private static final String USERS_COLLECTION = "Users";
    private final FirebaseFirestore firestore;

    public FirebaseUserRepository() {
        this(FirebaseFirestore.getInstance());
    }

    FirebaseUserRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Task<Void> saveProfile(UserProfile profile) {
        Map<String, Object> values = new HashMap<>();
        values.put("displayName", profile.getDisplayName());
        values.put("email", profile.getEmail());
        values.put("availability", profile.getAvailability());

        return firestore.collection(USERS_COLLECTION)
                .document(profile.getUserId())
                .set(values, SetOptions.merge());
    }
}
