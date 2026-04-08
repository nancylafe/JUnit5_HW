package main.najah.test;

import main.najah.code.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Calculator Test Class")
public class TestCalculator {

    Calculator calc;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Setup before all tests");
    }
    
    @AfterAll
    static void afterAll() {
        System.out.println("All calculator tests finished");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("Setup before each test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Cleanup after each test");
    }

    //== ADD ==

    @Test
    @Order(1)
    @DisplayName("Test addition")
    void testAdd() {
        int result = calc.add(1, 2, 3);
        assertEquals(6, result);
        assertTrue(result > 0);
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "5,5,10",
            "10,20,30"
    })
    @DisplayName("Parameterized addition test")
    void testAddParameterized(int a, int b, int expected) {
        assertEquals(expected, calc.add(a, b));
    }

    //  == DIVIDE ==

    @Test
    @Order(2)
    @DisplayName("Valid division")
    void testDivideValid() {
        assertEquals(2, calc.divide(4, 2));
    }

    @Test
    @DisplayName("Divide by zero exception")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(5, 0));
    }

    //== Factorial ==

    @Test
    @Order(3)
    @DisplayName("Valid factorial")
    void testFactorial() {
        assertEquals(120, calc.factorial(5));
    }

    @Test
    @DisplayName("Negative factorial exception")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1));
    }

    // == Timeout ==

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Timeout test")
    void testTimeout() {
        calc.factorial(10);
    }

    // == DISABLED ==

    @Test
    @Disabled("Known issue - will fix later")
    @DisplayName("Failing test")
    void failingTest() {
        assertEquals(100, calc.add(2, 2));
    }
}