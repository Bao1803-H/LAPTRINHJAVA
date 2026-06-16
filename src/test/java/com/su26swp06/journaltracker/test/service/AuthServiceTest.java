package com.su26swp06.journaltracker.test.service;

import com.su26swp06.journaltracker.dto.auth.AuthResponse;
import com.su26swp06.journaltracker.dto.auth.LoginRequest;
import com.su26swp06.journaltracker.dto.auth.RegisterRequest;
import com.su26swp06.journaltracker.dto.user.UserResponse;
import com.su26swp06.journaltracker.exception.AuthException;
import com.su26swp06.journaltracker.repository.AppUserRepository;
import com.su26swp06.journaltracker.security.JwtService;
import com.su26swp06.journaltracker.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(appUserRepository, passwordEncoder, jwtService);
    }

    @Nested
    @DisplayName("Register Tests")
    class RegisterTests {

        @Test
        @DisplayName("Register thành công")
        void register_Success() {
            RegisterRequest request = new RegisterRequest("test@example.com", "password123", "Test User", "Test Full Name");

            when(appUserRepository.existsByEmail("test@example.com")).thenReturn(false);
            when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
            when(appUserRepository.save(any())).thenAnswer(invocation -> {
                var user = invocation.getArgument(0, com.su26swp06.journaltracker.domain.entity.AppUser.class);
                user.setUserId(1L);
                return user;
            });
            when(jwtService.generateToken(any(), anyString(), anyString())).thenReturn("jwt-token");
            when(jwtService.getExpirationMs()).thenReturn(3600000L);

            AuthResponse response = authService.register(request);

            assertNotNull(response);
            assertEquals("jwt-token", response.getAccessToken());
            assertEquals(3600000L, response.getExpiresIn());
            assertNotNull(response.getUser());
            verify(appUserRepository).save(any());
        }

        @Test
        @DisplayName("Register thất bại - email trùng")
        void register_EmailAlreadyExists() {
            RegisterRequest request = new RegisterRequest("existing@example.com", "password123", "Test User", null);

            when(appUserRepository.existsByEmail("existing@example.com")).thenReturn(true);

            AuthException exception = assertThrows(AuthException.class, () -> authService.register(request));
            assertEquals("Email already exists", exception.getMessage());
            verify(appUserRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Login Tests")
    class LoginTests {

        @Test
        @DisplayName("Login thành công")
        void login_Success() {
            LoginRequest request = new LoginRequest("test@example.com", "password123");

            com.su26swp06.journaltracker.domain.entity.AppUser user =
                    createTestUser(com.su26swp06.journaltracker.domain.enums.UserStatus.ACTIVE);
            user.setPasswordHash("hashedPassword");

            when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
            when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);
            when(appUserRepository.save(any())).thenReturn(user);
            when(jwtService.generateToken(any(), anyString(), anyString())).thenReturn("jwt-token");
            when(jwtService.getExpirationMs()).thenReturn(3600000L);

            AuthResponse response = authService.login(request);

            assertNotNull(response);
            assertEquals("jwt-token", response.getAccessToken());
            verify(appUserRepository).save(any());
        }

        @Test
        @DisplayName("Login thất bại - user không tồn tại")
        void login_UserNotFound() {
            LoginRequest request = new LoginRequest("notfound@example.com", "password123");

            when(appUserRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

            AuthException exception = assertThrows(AuthException.class, () -> authService.login(request));
            assertEquals("Invalid email or password", exception.getMessage());
        }

        @Test
        @DisplayName("Login thất bại - sai password")
        void login_WrongPassword() {
            LoginRequest request = new LoginRequest("test@example.com", "wrongpassword");

            com.su26swp06.journaltracker.domain.entity.AppUser user =
                    createTestUser(com.su26swp06.journaltracker.domain.enums.UserStatus.ACTIVE);
            user.setPasswordHash("hashedPassword");

            when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
            when(passwordEncoder.matches("wrongpassword", "hashedPassword")).thenReturn(false);

            AuthException exception = assertThrows(AuthException.class, () -> authService.login(request));
            assertEquals("Invalid email or password", exception.getMessage());
        }

        @Test
        @DisplayName("Login thất bại - user bị SUSPENDED")
        void login_UserSuspended() {
            LoginRequest request = new LoginRequest("test@example.com", "password123");

            com.su26swp06.journaltracker.domain.entity.AppUser user =
                    createTestUser(com.su26swp06.journaltracker.domain.enums.UserStatus.SUSPENDED);

            when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

            AuthException exception = assertThrows(AuthException.class, () -> authService.login(request));
            assertEquals("Account is suspended", exception.getMessage());
        }

        @Test
        @DisplayName("Login thất bại - user bị DELETED")
        void login_UserDeleted() {
            LoginRequest request = new LoginRequest("test@example.com", "password123");

            com.su26swp06.journaltracker.domain.entity.AppUser user =
                    createTestUser(com.su26swp06.journaltracker.domain.enums.UserStatus.DELETED);

            when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

            AuthException exception = assertThrows(AuthException.class, () -> authService.login(request));
            assertEquals("Account has been deleted", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Get Current User Tests")
    class GetCurrentUserTests {

        @Test
        @DisplayName("Get current user thành công")
        void getCurrentUser_Success() {
            com.su26swp06.journaltracker.domain.entity.AppUser user =
                    createTestUser(com.su26swp06.journaltracker.domain.enums.UserStatus.ACTIVE);

            UserResponse response = authService.getCurrentUser(user);

            assertNotNull(response);
            assertEquals(1L, response.getUserId());
            assertEquals("test@example.com", response.getEmail());
        }
    }

    private com.su26swp06.journaltracker.domain.entity.AppUser createTestUser(
            com.su26swp06.journaltracker.domain.enums.UserStatus status) {
        com.su26swp06.journaltracker.domain.entity.AppUser user =
                new com.su26swp06.journaltracker.domain.entity.AppUser();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setDisplayName("Test User");
        user.setFullName("Test Full Name");
        user.setRole(com.su26swp06.journaltracker.domain.enums.UserRole.RESEARCHER);
        user.setStatus(status);
        user.setAuthProvider(com.su26swp06.journaltracker.domain.enums.AuthProvider.LOCAL);
        user.setEmailVerified(false);
        return user;
    }
}
