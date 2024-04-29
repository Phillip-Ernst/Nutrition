package Control;

import Entity.Food;

import java.util.ArrayList;

/**
 * @author Isaac Hotop
 *
 * This class calculates the total calories, fats, carbohydrates, and proteins that the user has consumed
 * by adding up the user's food intake
 */
public class CalculateCalories {

    /**
     * Calculates the total amount of Calories from foodIntake
     * @param foodIntake Food ArrayList
     * @return total amount of Calories
     */
    public int calculateTotalCal(ArrayList<Food> foodIntake) {
        int cals = 0;
        for (Food food : foodIntake) {
            cals += food.getCals();
        }
        return cals;
    }

    /**
     * Calculates the total amount of grams from macronutrients from foodIntake
     * @param foodIntake Food ArrayList
     * @return arrayList containing the total amount of grams from proteins, carbohydrates, and fats
     */
    public ArrayList<Double> calculateTotalMacronutrientGrams(ArrayList<Food> foodIntake) {
        double protein = 0;
        double carb = 0;
        double fat = 0;
        ArrayList<Double> gramArray = new ArrayList<>();

        for(Food food : foodIntake) {
            protein += food.getProtein();
            carb += food.getCarbs();
            fat += food.getFats();
        }

        gramArray.add(protein);
        gramArray.add(carb);
        gramArray.add(fat);
        return gramArray;
    }

    /**
     * Calculates the total amount of Calories from macronutrients from foodIntake
     * @param foodIntake Food ArrayList
     * @return arrayList containing the total amount of Calories from proteins, carbohydrates, and fats
     */
    public ArrayList<Double> calculateTotalMacronutrientCals(ArrayList<Food> foodIntake) {
        double protein = 0;
        double carb = 0;
        double fat = 0;
        ArrayList<Double> calArray = new ArrayList<>();

        for(Food food : foodIntake) {
            protein += food.getProtein();
            carb += food.getCarbs();
            fat += food.getFats();
        }

        //Calculate the calories from each macronutrient
        double proteinCal = protein * 4;
        double carbCal = carb * 4;
        double fatCal = fat * 9;

        calArray.add(proteinCal);
        calArray.add(carbCal);
        calArray.add(fatCal);
        return calArray;
    }
}
