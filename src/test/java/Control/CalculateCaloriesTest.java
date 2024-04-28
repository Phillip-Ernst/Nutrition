package Control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.Before;
import java.util.ArrayList;
import Entity.Food;


class CalculateCaloriesTest {
    private CalculateCalories calculator;
    private ArrayList<Food> foodList;
    @Before
    public void setUP() {
        calculator = new CalculateCalories();
        foodList = new ArrayList<>();
    }

    @Test
    public void testCalculatorTotalCal_EmptyList() {
        assertEquals(0, calculator.calculateTotalCal(foodList));
    }
    @Test
    public void testCalculateTotalCal_NonEmptyList() {
        foodList.add(new Food("Apple", 95, 0.3, 25, 0.5));  // Calories, fats, carbs, proteins
        foodList.add(new Food("Banana", 105, 0.4, 27, 1.3));
        assertEquals(200, calculator.calculateTotalCal(foodList));
    }
    @Test
    public void testCalculateTotalMacronutrientGrams_EmptyList() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(0.0);
        expected.add(0.0);
        expected.add(0.0);
        assertEquals(expected, calculator.calculateTotalMacronutrientGrams(foodList));
    }

    @Test
    public void testCalculateTotalMacronutrientGrams_NonEmptyList() {
        foodList.add(new Food("Apple", 95, 0.3, 25, 0.5));
        foodList.add(new Food("Banana", 105, 0.4, 27, 1.3));
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(1.8);  // Total proteins
        expected.add(52.0); // Total carbs
        expected.add(0.7);  // Total fats
        assertEquals(expected, calculator.calculateTotalMacronutrientGrams(foodList));
    }
    @Test
    public void testCalculateTotalMacronutrientCals_EmptyList() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(0.0);  // Calories from protein
        expected.add(0.0);  // Calories from carbs
        expected.add(0.0);  // Calories from fats
        assertEquals(expected, calculator.calculateTotalMacronutrientCals(foodList));
    }

    @Test
    public void testCalculateTotalMacronutrientCals_NonEmptyList() {
        foodList.add(new Food("Apple", 95, 0.3, 25, 0.5));
        foodList.add(new Food("Banana", 105, 0.4, 27, 1.3));
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(7.2);   // Calories from protein (1.8 * 4)
        expected.add(208.0); // Calories from carbs (52.0 * 4)
        expected.add(6.3);   // Calories from fats (0.7 * 9)
        assertEquals(expected, calculator.calculateTotalMacronutrientCals(foodList));
    }
}