package com.example.primeiraapp.model;

public class UserProfile {
    private final String userId;
    private final String email;
    private final String displayName;
    private final String availability;

    public UserProfile(String userId, String email, String displayName, String availability) {
        this.userId = userId;
        this.email = email;
        this.displayName = displayName;
        this.availability = availability;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvailability() {
        return availability;
    }
}
