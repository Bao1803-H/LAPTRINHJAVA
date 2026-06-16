package com.su26swp06.journaltracker.dto.user;

import com.su26swp06.journaltracker.domain.entity.AppUser;
import com.su26swp06.journaltracker.domain.enums.AuthProvider;
import com.su26swp06.journaltracker.domain.enums.UserRole;
import com.su26swp06.journaltracker.domain.enums.UserStatus;

import java.time.LocalDateTime;

public class UserResponse {

    private Long userId;
    private String email;
    private String displayName;
    private String fullName;
    private UserRole role;
    private UserStatus status;
    private AuthProvider authProvider;
    private String avatarUrl;
    private boolean emailVerified;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponse() {
    }

    public static UserResponse fromEntity(AppUser user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setDisplayName(user.getDisplayName());
        response.setFullName(user.getFullName());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setAuthProvider(user.getAuthProvider());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setEmailVerified(user.isEmailVerified());
        response.setLastLoginAt(user.getLastLoginAt());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
