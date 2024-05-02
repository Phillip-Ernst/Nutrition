package Entity;

import java.util.ArrayList;

/**
 * This class stores user data retrieved from the database after the user signs in.
 * It includes attributes such as name, goal, and dailyTotal, and tracks the user's food intake
 * over different time periods using ArrayLists.
 * 
 * @author Isaac Hotop
 */
public class User {
    private final String username;
    private final ArrayList<Food> dailyIntake;
    private final ArrayList<Food> weeklyIntake;


    public User(String username) {
        this.username = username;
        this.dailyIntake = new ArrayList<>();
        this.weeklyIntake = new ArrayList<>();
    }


    /**
     * Adds current food entered by the user (not retrieved from the user's table) to dailyIntake and weeklyIntake
     * @param food the current food to add
     */
    public void addCurrentFood(Food food) {
        dailyIntake.add(food);
        weeklyIntake.add(food);
    }

    /**
     * Adds food fromm the user's table that fits into the current week to weeklyIntake
     * @param food The weekly food to add
     */
    public void addWeeklyIntake(Food food) {
        weeklyIntake.add(food);
    }

    /**
     * Adds food from the user's table from today to dailyIntake
     * @param food the daily food to add
     */
    public void addDailyIntake(Food food) {
        dailyIntake.add(food);
    }

    public ArrayList<Food> getDailyIntake() {
        return dailyIntake;
    }

    public ArrayList<Food> getWeeklyIntake() {
        return weeklyIntake;
    }

    public String getUsername() {
        return username;
    }
}


