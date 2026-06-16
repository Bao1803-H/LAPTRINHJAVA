# Auth & Users Module — Checklist

## ✅ Đã làm

### 1. Cấu hình & Dependencies
- [x] `pom.xml` — `spring-boot-starter-security`
- [x] `pom.xml` — `jjwt-api`, `jjwt-impl`, `jjwt-jackson` (v0.12.5)
- [x] `pom.xml` — `spring-security-test`
- [x] `application.properties` — `jwt.secret`, `jwt.expiration-ms`, `jwt.issuer`

### 2. Repository
- [x] `AppUserRepository` — `findByEmail()`
- [x] `AppUserRepository` — `existsByEmail()`
- [x] `AppUserRepository` — `findByStatusNot()`

### 3. DTOs
- [x] `LoginRequest` — email, password (validation)
- [x] `RegisterRequest` — email, password (min 8 ký tự), displayName, fullName
- [x] `AuthResponse` — accessToken, tokenType, expiresIn, user
- [x] `UserResponse` — user profile (không expose passwordHash)
- [x] `UpdateUserRequest` — displayName, fullName, avatarUrl
- [x] `ChangePasswordRequest` — currentPassword, newPassword

### 4. Exception Handling
- [x] `AuthException` — runtime exception cho auth errors
- [x] `ResourceNotFoundException` — cho user not found
- [x] `GlobalExceptionHandler` — AuthException, validation, access denied, generic errors

### 5. Security Layer
- [x] `JwtService` — generate, validate, parse claims (userId, email, role)
- [x] `JwtAuthenticationFilter` — Bearer token filter, set SecurityContext
- [x] `SecurityConfig` — stateless session, BCrypt encoder, filter chain, endpoint permissions

### 6. Services
- [x] `AuthService.register()` — hash BCrypt, default role=RESEARCHER, status=ACTIVE
- [x] `AuthService.login()` — verify password, check SUSPENDED/DELETED, update lastLoginAt
- [x] `AuthService.getCurrentUser()`
- [x] `UserService.getUserById()` — lọc DELETED users
- [x] `UserService.getAllUsers()` — pagination, lọc DELETED
- [x] `UserService.updateUser()` — self-update hoặc ADMIN update
- [x] `UserService.changePassword()` — verify current, hash new, check OAuth
- [x] `UserService.deleteUser()` — soft delete, ADMIN only
- [x] `UserService.updateUserRole()` — ADMIN only
- [x] `UserService.updateUserStatus()` — ADMIN only

### 7. Controllers
- [x] `AuthController` — register, login, logout, me (4 endpoints)
- [x] `UserController` — get all, get by id, update, change password, delete, update role, update status (7 endpoints)
- [x] Validation annotations + HTTP status codes đúng

### 8. Unit Tests
- [x] `JwtServiceTest` — 12 test cases
- [x] `AuthServiceTest` — 8 test cases
- [x] `UserServiceTest` — 14 test cases
- [x] Tất cả **34/34 tests pass**

### 9. Build
- [x] Compile thành công (Java 21)
- [x] Tests pass

---

## ❌ Chưa làm

### Security & Production Hardening
- [ ] Rate limiting trên `/api/auth/login` — chống brute force
- [ ] JWT secret production — cần set `JWT_SECRET` env var bắt buộc
- [ ] Refresh token — hiện chỉ có access token (1h)
- [ ] Email verification — `emailVerified` luôn `false`, cần SMTP + verification flow
- [ ] Password strength — thêm uppercase, number, special char

### Code Quality
- [ ] Integration tests (Controller) — `@WebMvcTest` cho AuthController và UserController
- [ ] Password reset flow — forgot-password, reset-token
- [ ] Audit log — tracking thay đổi role/status

### Documentation
- [ ] README.md — hướng dẫn chạy, API docs cho auth
- [ ] `.env.example` — thêm `JWT_SECRET`, `JWT_EXPIRATION_MS`

### Cleanup
- [ ] Kiểm tra folder rỗng `src/main/java/com/su26swp06/journaltracker/test/`

---

## Files tạo/sửa

### Thay đổi (3 files)
| File | Mô tả |
|---|---|
| `pom.xml` | Thêm dependencies |
| `src/main/resources/application.properties` | JWT config |
| `src/main/java/.../repository/AppUserRepository.java` | Custom queries |

### Tạo mới (19 files)
| File | Mô tả |
|---|---|
| `config/SecurityConfig.java` | Security filter chain |
| `security/JwtService.java` | JWT generate/validate |
| `security/JwtAuthenticationFilter.java` | Request filter |
| `dto/auth/LoginRequest.java` | |
| `dto/auth/RegisterRequest.java` | |
| `dto/auth/AuthResponse.java` | |
| `dto/user/UserResponse.java` | |
| `dto/user/UpdateUserRequest.java` | |
| `dto/user/ChangePasswordRequest.java` | |
| `exception/AuthException.java` | |
| `exception/ResourceNotFoundException.java` | |
| `exception/GlobalExceptionHandler.java` | |
| `service/AuthService.java` | |
| `service/UserService.java` | |
| `web/AuthController.java` | |
| `web/UserController.java` | |
| `src/test/.../test/security/JwtServiceTest.java` | |
| `src/test/.../test/service/AuthServiceTest.java` | |
| `src/test/.../test/service/UserServiceTest.java` | |

---

## Endpoints

### `/api/auth`
| Method | Endpoint | Auth | Mô tả |
|---|---|---|---|
| POST | `/api/auth/register` | ❌ | Đăng ký tài khoản LOCAL |
| POST | `/api/auth/login` | ❌ | Đăng nhập, trả JWT |
| POST | `/api/auth/logout` | ✅ | Logout (client discard token) |
| GET | `/api/auth/me` | ✅ | Thông tin user hiện tại |

### `/api/users`
| Method | Endpoint | Auth | Mô tả |
|---|---|---|---|
| GET | `/api/users` | ✅ | List all users (pagination) |
| GET | `/api/users/{id}` | ✅ | Get user by ID |
| PUT | `/api/users/{id}` | ✅ self/ADMIN | Cập nhật profile |
| PUT | `/api/users/{id}/password` | ✅ self | Đổi password |
| DELETE | `/api/users/{id}` | ✅ ADMIN | Soft delete user |
| PUT | `/api/users/{id}/role` | ✅ ADMIN | Cập nhật role |
| PUT | `/api/users/{id}/status` | ✅ ADMIN | Cập nhật status |
