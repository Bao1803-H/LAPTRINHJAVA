package com.su26swp06.journaltracker.service;

import com.su26swp06.journaltracker.domain.entity.AppUser;
import com.su26swp06.journaltracker.domain.enums.AuthProvider;
import com.su26swp06.journaltracker.domain.enums.UserRole;
import com.su26swp06.journaltracker.domain.enums.UserStatus;
import com.su26swp06.journaltracker.dto.auth.AuthResponse;
import com.su26swp06.journaltracker.dto.auth.LoginRequest;
import com.su26swp06.journaltracker.dto.auth.RegisterRequest;
import com.su26swp06.journaltracker.dto.user.UserResponse;
import com.su26swp06.journaltracker.exception.AuthException;
import com.su26swp06.journaltracker.repository.AppUserRepository;
import com.su26swp06.journaltracker.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AppUserRepository appUserRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email already exists");
        }

        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setDisplayName(request.getDisplayName());
        user.setFullName(request.getFullName());
        user.setRole(UserRole.RESEARCHER);
        user.setStatus(UserStatus.ACTIVE);
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setEmailVerified(false);

        AppUser savedUser = appUserRepository.save(user);

        return buildAuthResponse(savedUser);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("Invalid email or password"));

        if (user.getStatus() == UserStatus.SUSPENDED) {
            throw new AuthException("Account is suspended");
        }

        if (user.getStatus() == UserStatus.DELETED) {
            throw new AuthException("Account has been deleted");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AuthException("Invalid email or password");
        }

        user.setLastLoginAt(LocalDateTime.now());
        appUserRepository.save(user);

        return buildAuthResponse(user);
    }

    public UserResponse getCurrentUser(AppUser user) {
        return UserResponse.fromEntity(user);
    }

    private AuthResponse buildAuthResponse(AppUser user) {
        String token = jwtService.generateToken(
                user.getUserId(),
                user.getEmail(),
                user.getRole().name()
        );
        return new AuthResponse(token, jwtService.getExpirationMs(), UserResponse.fromEntity(user));
    }
}
