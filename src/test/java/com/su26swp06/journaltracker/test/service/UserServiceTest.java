package com.su26swp06.journaltracker.test.service;

import com.su26swp06.journaltracker.domain.entity.AppUser;
import com.su26swp06.journaltracker.domain.enums.AuthProvider;
import com.su26swp06.journaltracker.domain.enums.UserRole;
import com.su26swp06.journaltracker.domain.enums.UserStatus;
import com.su26swp06.journaltracker.dto.user.ChangePasswordRequest;
import com.su26swp06.journaltracker.dto.user.UpdateUserRequest;
import com.su26swp06.journaltracker.dto.user.UserResponse;
import com.su26swp06.journaltracker.exception.AuthException;
import com.su26swp06.journaltracker.exception.ResourceNotFoundException;
import com.su26swp06.journaltracker.repository.AppUserRepository;
import com.su26swp06.journaltracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private AppUser testUser;
    private AppUser testAdmin;
    private AppUser testAttacker;

    @BeforeEach
    void setUp() {
        userService = new UserService(appUserRepository, passwordEncoder);

        testUser = createUser(1L, "user@test.com", UserRole.RESEARCHER, UserStatus.ACTIVE);
        testAdmin = createUser(2L, "admin@test.com", UserRole.ADMIN, UserStatus.ACTIVE);
        testAttacker = createUser(3L, "attacker@test.com", UserRole.RESEARCHER, UserStatus.ACTIVE);
    }

    @Nested
    @DisplayName("Get User By ID Tests")
    class GetUserByIdTests {

        @Test
        @DisplayName("Get user thành công")
        void getUserById_Success() {
            when(appUserRepository.findById(1L)).thenReturn(Optional.of(testUser));

            UserResponse response = userService.getUserById(1L);

            assertNotNull(response);
            assertEquals(1L, response.getUserId());
            assertEquals("user@test.com", response.getEmail());
        }

        @Test
        @DisplayName("Get user không tồn tại")
        void getUserById_NotFound() {
            when(appUserRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(999L));
        }
    }

    @Nested
    @DisplayName("Get All Users Tests")
    class GetAllUsersTests {

        @Test
        @DisplayName("Get all users với pagination")
        void getAllUsers_Success() {
            Pageable pageable = PageRequest.of(0, 20);
            Page<AppUser> userPage = new PageImpl<>(List.of(testUser, testAdmin));

            when(appUserRepository.findByStatusNot(eq(UserStatus.DELETED), any(Pageable.class))).thenReturn(userPage);

            Page<UserResponse> response = userService.getAllUsers(pageable);

            assertNotNull(response);
            assertEquals(2, response.getTotalElements());
        }
    }

    @Nested
    @DisplayName("Update User Tests")
    class UpdateUserTests {

        @Test
        @DisplayName("Update profile thành công - self update")
        void updateUser_SelfUpdate_Success() {
            UpdateUserRequest request = new UpdateUserRequest("New Name", "New Full Name", null);

            when(appUserRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(appUserRepository.save(any(AppUser.class))).thenReturn(testUser);

            UserResponse response = userService.updateUser(1L, request, testUser);

            assertNotNull(response);
            verify(appUserRepository).save(any(AppUser.class));
        }

        @Test
        @DisplayName("Update profile thành công - ADMIN update user khác")
        void updateUser_AdminUpdate_Success() {
            UpdateUserRequest request = new UpdateUserRequest("Admin Update", null, null);

            when(appUserRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(appUserRepository.save(any(AppUser.class))).thenReturn(testUser);

            UserResponse response = userService.updateUser(1L, request, testAdmin);

            assertNotNull(response);
            verify(appUserRepository).save(any(AppUser.class));
        }

        @Test
        @DisplayName("Update profile thất bại - không phải self hoặc ADMIN")
        void updateUser_NoPermission() {
            UpdateUserRequest request = new UpdateUserRequest("New Name", null, null);

            when(appUserRepository.findById(1L)).thenReturn(Optional.of(testUser));

            AuthException exception = assertThrows(AuthException.class,
                    () -> userService.updateUser(1L, request, testAttacker));
            assertEquals("You do not have permission to update this user", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Change Password Tests")
    class ChangePasswordTests {

        @Test
        @DisplayName("Change password thành công")
        void changePassword_Success() {
            ChangePasswordRequest request = new ChangePasswordRequest("currentPass", "newPass123");

            testUser.setPasswordHash("hashedCurrentPass");
            when(appUserRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches("currentPass", "hashedCurrentPass")).thenReturn(true);
            when(passwordEncoder.encode("newPass123")).thenReturn("hashedNewPass");

            assertDoesNotThrow(() -> userService.changePassword(1L, request, testUser));
            verify(appUserRepository).save(any(AppUser.class));
        }

        @Test
        @DisplayName("Change password thất bại - không phải self")
        void changePassword_NotSelf() {
            ChangePasswordRequest request = new ChangePasswordRequest("currentPass", "newPass123");

            assertThrows(AuthException.class,
                    () -> userService.changePassword(1L, request, testAdmin));
        }

        @Test
        @DisplayName("Change password thất bại - sai current password")
        void changePassword_WrongCurrentPassword() {
            ChangePasswordRequest request = new ChangePasswordRequest("wrongPass", "newPass123");

            testUser.setPasswordHash("hashedCurrentPass");
            when(appUserRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches("wrongPass", "hashedCurrentPass")).thenReturn(false);

            AuthException exception = assertThrows(AuthException.class,
                    () -> userService.changePassword(1L, request, testUser));
            assertEquals("Current password is incorrect", exception.getMessage());
        }

        @Test
        @DisplayName("Change password không áp dụng cho OAuth accounts")
        void changePassword_OAuthAccount() {
            ChangePasswordRequest request = new ChangePasswordRequest("currentPass", "newPass123");

            testUser.setAuthProvider(AuthProvider.GOOGLE);

            AuthException exception = assertThrows(AuthException.class,
                    () -> userService.changePassword(1L, request, testUser));
            assertEquals("Password change is not available for OAuth accounts", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Delete User Tests")
    class DeleteUserTests {

        @Test
        @DisplayName("Delete user thành công - ADMIN xóa user")
        void deleteUser_AdminSuccess() {
            when(appUserRepository.findById(1L)).thenReturn(Optional.of(testUser));

            assertDoesNotThrow(() -> userService.deleteUser(1L, testAdmin));
            verify(appUserRepository).save(any(AppUser.class));
        }

        @Test
        @DisplayName("Delete user thất bại - non-ADMIN")
        void deleteUser_NotAdmin() {
            assertThrows(AuthException.class, () -> userService.deleteUser(1L, testUser));
        }

        @Test
        @DisplayName("Delete user thất bại - user không tồn tại")
        void deleteUser_NotFound() {
            when(appUserRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class,
                    () -> userService.deleteUser(999L, testAdmin));
        }
    }

    @Nested
    @DisplayName("Update User Role/Status Tests")
    class UpdateRoleStatusTests {

        @Test
        @DisplayName("Update user role thành công")
        void updateUserRole_Success() {
            when(appUserRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(appUserRepository.save(any(AppUser.class))).thenReturn(testUser);

            UserResponse response = userService.updateUserRole(1L, UserRole.ADMIN, testAdmin);

            assertNotNull(response);
            verify(appUserRepository).save(any(AppUser.class));
        }

        @Test
        @DisplayName("Update user role thất bại - non-ADMIN")
        void updateUserRole_NotAdmin() {
            assertThrows(AuthException.class,
                    () -> userService.updateUserRole(1L, UserRole.ADMIN, testUser));
        }

        @Test
        @DisplayName("Update user status thành công")
        void updateUserStatus_Success() {
            when(appUserRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(appUserRepository.save(any(AppUser.class))).thenReturn(testUser);

            UserResponse response = userService.updateUserStatus(1L, UserStatus.SUSPENDED, testAdmin);

            assertNotNull(response);
            verify(appUserRepository).save(any(AppUser.class));
        }
    }

    private AppUser createUser(Long id, String email, UserRole role, UserStatus status) {
        AppUser user = new AppUser();
        user.setUserId(id);
        user.setEmail(email);
        user.setDisplayName("Test User");
        user.setRole(role);
        user.setStatus(status);
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setEmailVerified(false);
        return user;
    }
}
