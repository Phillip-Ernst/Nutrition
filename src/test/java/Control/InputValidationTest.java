package Control;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InputValidationTest {

    private InputValidation validator;

    @BeforeEach
    public void setUp() {
        validator = new InputValidation();
    }

    @Test
    public void testUsernamePasswordValidation_ValidInput() {
        assertTrue(validator.UsernamePasswordValidation("JohnDoe123", "SecurePass123"));
    }

    @Test
    public void testUsernamePasswordValidation_InvalidUsername() {
        assertFalse(validator.UsernamePasswordValidation("JohnDoe123;", "SecurePass123"));
    }

    @Test
    public void testUsernamePasswordValidation_InvalidPassword() {
        assertFalse(validator.UsernamePasswordValidation("JohnDoe123", "Secure;Pass"));
    }

    @Test
    public void testUsernamePasswordValidation_TooLongUsername() {
        assertFalse(validator.UsernamePasswordValidation("JohnDoe12345678901234567890", "SecurePass123"));
    }

    @Test
    public void testUsernamePasswordValidation_TooLongPassword() {
        assertFalse(validator.UsernamePasswordValidation("JohnDoe123", "SecurePass12345678901234567890"));
    }

    @Test
    public void testFoodInputValidation_ValidInput() {
        assertTrue(validator.foodInputValidation("Apple"));
    }

    @Test
    public void testFoodInputValidation_InvalidInput() {
        assertFalse(validator.foodInputValidation("Choco;late"));
    }

    @Test
    public void testFoodInputValidation_TooLongInput() {
        assertFalse(validator.foodInputValidation("SuperCalifragilisticexpialidociousFood"));
    }
}
