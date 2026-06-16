package com.su26swp06.journaltracker.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 320, message = "Email must not exceed 320 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Display name is required")
    @Size(max = 150, message = "Display name must not exceed 150 characters")
    private String displayName;

    @Size(max = 255, message = "Full name must not exceed 255 characters")
    private String fullName;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String displayName, String fullName) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
