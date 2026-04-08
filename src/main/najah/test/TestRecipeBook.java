package main.najah.test;

import main.najah.code.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RecipeBook Test Class")
public class TestRecipeBook {

    RecipeBook book;
    Recipe recipe;

    @BeforeEach
    void setUp() {
        book = new RecipeBook();
        recipe = new Recipe();
        recipe.setName("Coffee");
    }

    // == Add Valid Recipe ==
    @Test
    @DisplayName("Add Valid Recipe")
    void testAddRecipe() {
        assertTrue(book.addRecipe(recipe));
    }

    // == Add Duplicate Recipe ==
    @Test
    @DisplayName("Add Duplicate Recipe Should Fail")
    void testAddDuplicateRecipe() {
        book.addRecipe(recipe);
        assertFalse(book.addRecipe(recipe));
    }

    // == Delete Existing Recipe ==
    @Test
    @DisplayName("Delete Existing Recipe")
    void testDeleteRecipe() {
        book.addRecipe(recipe);
        String deletedName = book.deleteRecipe(0);

        assertEquals("Coffee", deletedName);
    }

    // == Delete Empty Slot ==
    @Test
    @DisplayName("Delete Empty Recipe Returns Null")
    void testDeleteEmptyRecipe() {
        assertNull(book.deleteRecipe(0));
    }

    // == Edit Existing Recipe ==
    @Test
    @DisplayName("Edit Existing Recipe")
    void testEditRecipe() {
        book.addRecipe(recipe);

        Recipe newRecipe = new Recipe();
        newRecipe.setName("Tea");

        String oldName = book.editRecipe(0, newRecipe);

        assertEquals("Coffee", oldName);
    }

    // == Edit Empty Slot ==
    @Test
    @DisplayName("Edit Empty Recipe Returns Null")
    void testEditEmptyRecipe() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Tea");

        assertNull(book.editRecipe(0, newRecipe));
    }

    // == Multiple Assertions ==
    @Test
    @DisplayName("Multiple Assertions Test")
    void testMultipleAssertions() {
        book.addRecipe(recipe);

        assertAll(
                () -> assertNotNull(book.getRecipes()),
                () -> assertEquals(4, book.getRecipes().length),
                () -> assertEquals("Coffee", book.getRecipes()[0].getName())
        );
    }

    // == Parameterized Delete Test ==
    @ParameterizedTest
    @CsvSource({
        "Coffee, Coffee",
        "Tea, Tea"
    })
    @DisplayName("Parameterized Delete Test")
    void testDeleteRecipeParameterized(String recipeName, String expectedName) {
        Recipe r = new Recipe();
        r.setName(recipeName);
        book.addRecipe(r);

        assertEquals(expectedName, book.deleteRecipe(0));
    }

    // == Timeout Test ==
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Timeout Test for RecipeBook")
    void testTimeout() {
        book.getRecipes();
    }
    
 // == Recipe Price Exception ==
    @Test
    @DisplayName("Recipe Invalid Price Should Throw Exception")
    void testRecipeInvalidPrice() {
        Recipe r = new Recipe();
        assertThrows(RecipeException.class, () -> r.setPrice("-1"));
    }

    // == Recipe Coffee Exception ==
    @Test
    @DisplayName("Recipe Invalid Coffee Amount Should Throw Exception")
    void testRecipeInvalidCoffeeAmount() {
        Recipe r = new Recipe();
        assertThrows(RecipeException.class, () -> r.setAmtCoffee("-1"));
    }

    // == Recipe ToString And Equals ==
    @Test
    @DisplayName("Recipe ToString And Equals Test")
    void testRecipeToStringAndEquals() {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();
        r1.setName("Latte");
        r2.setName("Latte");

        assertAll(
                () -> assertEquals("Latte", r1.toString()),
                () -> assertTrue(r1.equals(r2))
        );
    }

    // == Recipe Valid Setters ==
    @Test
    @DisplayName("Recipe Valid Setters Test")
    void testRecipeValidSetters() throws RecipeException {
        Recipe r = new Recipe();
        r.setPrice("10");
        r.setAmtCoffee("2");
        r.setAmtMilk("1");
        r.setAmtSugar("3");
        r.setAmtChocolate("4");

        assertAll(
                () -> assertEquals(10, r.getPrice()),
                () -> assertEquals(2, r.getAmtCoffee()),
                () -> assertEquals(1, r.getAmtMilk()),
                () -> assertEquals(3, r.getAmtSugar()),
                () -> assertEquals(4, r.getAmtChocolate())
        );
    }
}