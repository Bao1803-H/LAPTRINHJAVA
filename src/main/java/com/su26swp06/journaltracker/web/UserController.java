package com.su26swp06.journaltracker.web;

import com.su26swp06.journaltracker.domain.entity.AppUser;
import com.su26swp06.journaltracker.domain.enums.UserRole;
import com.su26swp06.journaltracker.domain.enums.UserStatus;
import com.su26swp06.journaltracker.dto.user.ChangePasswordRequest;
import com.su26swp06.journaltracker.dto.user.UpdateUserRequest;
import com.su26swp06.journaltracker.dto.user.UserResponse;
import com.su26swp06.journaltracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request,
            @AuthenticationPrincipal AppUser currentUser) {
        UserResponse user = userService.updateUser(id, request, currentUser);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal AppUser currentUser) {
        userService.changePassword(id, request, currentUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @AuthenticationPrincipal AppUser currentUser) {
        userService.deleteUser(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserResponse> updateUserRole(
            @PathVariable Long id,
            @RequestParam UserRole role,
            @AuthenticationPrincipal AppUser currentUser) {
        UserResponse user = userService.updateUserRole(id, role, currentUser);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<UserResponse> updateUserStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status,
            @AuthenticationPrincipal AppUser currentUser) {
        UserResponse user = userService.updateUserStatus(id, status, currentUser);
        return ResponseEntity.ok(user);
    }
}
