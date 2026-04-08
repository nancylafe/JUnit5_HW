package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("Product Test Class")
@Execution(ExecutionMode.CONCURRENT)
public class TestProduct {

    Product p;

    @BeforeEach
    void setUp() {
        p = new Product("Laptop", 1000);
    }

    @Test
    @DisplayName("Valid product creation")
    void testValidProduct() {
        assertEquals("Laptop", p.getName());
        assertEquals(1000, p.getPrice());
    }

    @Test
    @DisplayName("Invalid price should throw exception")
    void testInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("Phone", -100);
        });
    }

    @Test
    @DisplayName("Apply valid discount")
    void testApplyDiscount() {
        p.applyDiscount(20);
        assertEquals(20, p.getDiscount());
        assertEquals(800, p.getFinalPrice());
    }

    @Test
    @DisplayName("Invalid discount should throw exception")
    void testInvalidDiscount() {
        assertThrows(IllegalArgumentException.class, () -> {
            p.applyDiscount(60);
        });
    }

    @Test
    @DisplayName("Multiple assertions test")
    void testMultipleAssertions() {
        p.applyDiscount(10);

        assertAll(
                () -> assertEquals("Laptop", p.getName()),
                () -> assertTrue(p.getPrice() > 0),
                () -> assertEquals(900, p.getFinalPrice())
        );
    }

    @ParameterizedTest
    @CsvSource({
        "10, 900",
        "20, 800",
        "50, 500"
    })
    @DisplayName("Parameterized discount test")
    void testDiscountParameterized(double discount, double expectedFinalPrice) {
        p.applyDiscount(discount);
        assertEquals(expectedFinalPrice, p.getFinalPrice());
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Timeout test for final price")
    void testTimeout() {
        p.applyDiscount(10);
        p.getFinalPrice();
    }
}