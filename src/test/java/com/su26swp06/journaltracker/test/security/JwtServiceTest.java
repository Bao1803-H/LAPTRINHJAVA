package com.su26swp06.journaltracker.test.security;

import com.su26swp06.journaltracker.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private static final String SECRET = "dGVzdC1zZWNyZXQta2V5LWZvci1qd3QtdG9rZW4tZ2VuZXJhdGlvbi1tdXN0LWJlLTI1Ni1iaXRz";
    private static final long EXPIRATION_MS = 3600000L;
    private static final String ISSUER = "journal-tracker-test";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(SECRET, EXPIRATION_MS, ISSUER);
    }

    @Nested
    @DisplayName("Generate Token Tests")
    class GenerateTokenTests {

        @Test
        @DisplayName("Generate token thành công")
        void generateToken_Success() {
            String token = jwtService.generateToken(1L, "test@example.com", "RESEARCHER");

            assertNotNull(token);
            assertFalse(token.isEmpty());
        }
    }

    @Nested
    @DisplayName("Parse Token Tests")
    class ParseTokenTests {

        @Test
        @DisplayName("Parse token - lấy userId")
        void getUserIdFromToken_Success() {
            String token = jwtService.generateToken(42L, "user@test.com", "ADMIN");

            Long userId = jwtService.getUserIdFromToken(token);

            assertEquals(42L, userId);
        }

        @Test
        @DisplayName("Parse token - lấy email")
        void getEmailFromToken_Success() {
            String token = jwtService.generateToken(1L, "user@example.com", "RESEARCHER");

            String email = jwtService.getEmailFromToken(token);

            assertEquals("user@example.com", email);
        }

        @Test
        @DisplayName("Parse token - lấy role")
        void getRoleFromToken_Success() {
            String token = jwtService.generateToken(1L, "admin@test.com", "ADMIN");

            String role = jwtService.getRoleFromToken(token);

            assertEquals("ADMIN", role);
        }
    }

    @Nested
    @DisplayName("Validate Token Tests")
    class ValidateTokenTests {

        @Test
        @DisplayName("Validate token hợp lệ")
        void validateToken_Valid() {
            String token = jwtService.generateToken(1L, "test@example.com", "RESEARCHER");

            boolean isValid = jwtService.validateToken(token);

            assertTrue(isValid);
        }

        @Test
        @DisplayName("Validate token không hợp lệ - null")
        void validateToken_Null() {
            boolean isValid = jwtService.validateToken(null);

            assertFalse(isValid);
        }

        @Test
        @DisplayName("Validate token không hợp lệ - empty")
        void validateToken_Empty() {
            boolean isValid = jwtService.validateToken("");

            assertFalse(isValid);
        }

        @Test
        @DisplayName("Validate token không hợp lệ - fake token")
        void validateToken_FakeToken() {
            boolean isValid = jwtService.validateToken("fake.jwt.token");

            assertFalse(isValid);
        }

        @Test
        @DisplayName("Validate token không hợp lệ - wrong signature")
        void validateToken_WrongSignature() {
            JwtService differentService = new JwtService(
                    "c2ltLWRpZmZlcmVudC1zZWNyZXQta2V5LWZvci1qd3QtdG9rZW4tZ2VuZXJhdGlvbi1tdXN0LWJlLTI1Ni1iaXRz",
                    EXPIRATION_MS, ISSUER);
            String token = differentService.generateToken(1L, "test@example.com", "RESEARCHER");

            boolean isValid = jwtService.validateToken(token);

            assertFalse(isValid);
        }
    }

    @Nested
    @DisplayName("Get Expiration Tests")
    class GetExpirationTests {

        @Test
        @DisplayName("Get expiration ms")
        void getExpirationMs_Success() {
            long expiration = jwtService.getExpirationMs();

            assertEquals(EXPIRATION_MS, expiration);
        }
    }
}
