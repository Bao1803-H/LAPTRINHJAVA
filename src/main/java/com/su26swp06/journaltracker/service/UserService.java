package com.su26swp06.journaltracker.service;

import com.su26swp06.journaltracker.domain.entity.AppUser;
import com.su26swp06.journaltracker.domain.enums.UserRole;
import com.su26swp06.journaltracker.domain.enums.UserStatus;
import com.su26swp06.journaltracker.dto.user.ChangePasswordRequest;
import com.su26swp06.journaltracker.dto.user.UpdateUserRequest;
import com.su26swp06.journaltracker.dto.user.UserResponse;
import com.su26swp06.journaltracker.exception.AuthException;
import com.su26swp06.journaltracker.exception.ResourceNotFoundException;
import com.su26swp06.journaltracker.repository.AppUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .filter(u -> u.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return UserResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return appUserRepository.findByStatusNot(UserStatus.DELETED, pageable)
                .map(UserResponse::fromEntity);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest request, AppUser currentUser) {
        AppUser user = appUserRepository.findById(userId)
                .filter(u -> u.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        boolean isSelf = currentUser.getUserId().equals(userId);
        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;

        if (!isSelf && !isAdmin) {
            throw new AuthException("You do not have permission to update this user");
        }

        if (request.getDisplayName() != null) {
            user.setDisplayName(request.getDisplayName());
        }
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        AppUser savedUser = appUserRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request, AppUser currentUser) {
        if (!currentUser.getUserId().equals(userId)) {
            throw new AuthException("You can only change your own password");
        }

        if (currentUser.getAuthProvider() != null &&
            currentUser.getAuthProvider().name().equals("GOOGLE")) {
            throw new AuthException("Password change is not available for OAuth accounts");
        }

        AppUser user = appUserRepository.findById(userId)
                .filter(u -> u.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new AuthException("Current password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        appUserRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId, AppUser currentUser) {
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new AuthException("Only administrators can delete users");
        }

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        user.setStatus(UserStatus.DELETED);
        appUserRepository.save(user);
    }

    @Transactional
    public UserResponse updateUserRole(Long userId, UserRole newRole, AppUser currentUser) {
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new AuthException("Only administrators can update user roles");
        }

        AppUser user = appUserRepository.findById(userId)
                .filter(u -> u.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        user.setRole(newRole);
        AppUser savedUser = appUserRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    @Transactional
    public UserResponse updateUserStatus(Long userId, UserStatus newStatus, AppUser currentUser) {
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new AuthException("Only administrators can update user status");
        }

        AppUser user = appUserRepository.findById(userId)
                .filter(u -> u.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        user.setStatus(newStatus);
        AppUser savedUser = appUserRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }
}
