package com.su26swp06.journaltracker.dto.user;

import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(max = 150, message = "Display name must not exceed 150 characters")
    private String displayName;

    @Size(max = 255, message = "Full name must not exceed 255 characters")
    private String fullName;

    @Size(max = 1024, message = "Avatar URL must not exceed 1024 characters")
    private String avatarUrl;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String displayName, String fullName, String avatarUrl) {
        this.displayName = displayName;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
