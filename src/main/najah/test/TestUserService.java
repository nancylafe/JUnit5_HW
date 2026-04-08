package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService Test Class")
public class TestUserService {

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    // == Valid Email ==
    @Test
    @DisplayName("Valid Email Test")
    void testValidEmail() {
        assertTrue(userService.isValidEmail("test@gmail.com"));
    }

    // == Invalid Email ==
    @Test
    @DisplayName("Invalid Email Test")
    void testInvalidEmail() {
        assertFalse(userService.isValidEmail("testgmail.com"));
        assertFalse(userService.isValidEmail(null));
    }

    // == Valid Login ==
    @Test
    @DisplayName("Valid Authentication")
    void testValidLogin() {
        assertTrue(userService.authenticate("admin", "1234"));
    }

    // == Invalid Login ==
    @Test
    @DisplayName("Invalid Authentication")
    void testInvalidLogin() {
        assertFalse(userService.authenticate("user", "1234"));
        assertFalse(userService.authenticate("admin", "wrong"));
    }

    // == Multiple Assertions ==
    @Test
    @DisplayName("Multiple Assertions Test")
    void testMultipleAssertions() {
        assertAll(
                () -> assertTrue(userService.isValidEmail("a@b.com")),
                () -> assertFalse(userService.isValidEmail("abc")),
                () -> assertTrue(userService.authenticate("admin", "1234"))
        );
    }

    // == Parameterized Email Test ==
    @ParameterizedTest
    @CsvSource({
        "test@gmail.com, true",
        "abc, false"
    })
    @DisplayName("Parameterized Email Test")
    void testEmailParameterized(String email, boolean expected) {
        assertEquals(expected, userService.isValidEmail(email));
    }

    // == Timeout Test ==
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Timeout Test for Email Validation")
    void testTimeout() {
        userService.isValidEmail("test@gmail.com");
    }
}