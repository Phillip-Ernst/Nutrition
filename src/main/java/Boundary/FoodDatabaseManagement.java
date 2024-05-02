package Boundary;

import Entity.Food;
import Entity.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * This class allows the admin to make changes to the food database and contains a method that connects the program to
 * the database
 *
 * @author Isaac Hotop
 */
public class FoodDatabaseManagement {
    Connection connection;
    private static String currentUsername;
    public FoodDatabaseManagement() throws ClassNotFoundException {
         /*
        Load JDBC driver
        You can download this driver from this link: https://dev.mysql.com/downloads/connector/j/
        Then add the jar file to the project's dependencies
        the java.sql.* import will not work until you do this
         */
        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
            //host:port/Database name  root username, root password
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "Admin", "admin1");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * signInUser
     * Retrieves the user's data from the database and creates a User object to store the data in.
     * @param username The user's username
     * @param password The user's password
     * @return User object
     * @throws SQLException SQLException
     */
    public User signInUser(String username, String password) throws SQLException {
        try {
            //Gets current date and week
            LocalDate currentDate = LocalDate.now();
            LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate endOfWeek = startOfWeek.plusDays(6);

            //Connects to database using user's credentials
            Connection userConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", username, password);

            //Creates a User class with the info retrieved from the database
            User user = new User(username);

            //Creates a Food class for each food retrieved from the user's table
            PreparedStatement foodInfo = userConnection.prepareStatement("SELECT food, food_date FROM "+username+"_table");
            ResultSet foodResult = foodInfo.executeQuery();

            while(foodResult.next()) {
                String foodName = foodResult.getString("food");
                Food food = findFood(foodName);

                //If food exists in database, then add it to the corresponding food array in User
                if(!food.getName().equals("Error")) {
                    LocalDate foodDate = foodResult.getDate("food_date").toLocalDate();

                    //Checks if food is from today
                    if(foodDate.isEqual(currentDate)) {
                        user.addDailyIntake(food);

                    }
                    //Checks if foodDate falls within the current week
                    if(foodDate.isEqual(startOfWeek) || (foodDate.isAfter(startOfWeek) && foodDate.isBefore(endOfWeek))) {
                        user.addWeeklyIntake(food);
                    }
                }
            }
            //Sign in was successful
            currentUsername = username;
            return user;
        }
        catch (SQLException e) {
            //Sign in failed
            e.printStackTrace();
            return new User("<Error>");
            }
    }

    /**
     * addUser
     * Creates a query using the given username and password to add a user and their associated table to the database
     * @param username The user's username
     * @param password The user's password
     * @return true if the user and table was successfully added return false if not
     */
    public boolean addUser(String username, String password) {
        try {
            //Creates a user
            PreparedStatement addUser = connection.prepareStatement("CREATE USER ?@'localhost' IDENTIFIED BY ?");
            addUser.setString(1,username);
            addUser.setString(2, password);
            addUser.executeUpdate();

            //Creates the user's table
            PreparedStatement addUserTable = connection.prepareStatement("CREATE TABLE "+username+"_table (food_date DATE, food VARCHAR(255))");
            addUserTable.executeUpdate();

            //Grant permissions to the new user
            PreparedStatement grantUserPerms = connection.prepareStatement("GRANT SELECT ON "+username+"_table TO ?@'localhost'");
            grantUserPerms.setString(1,username);
            grantUserPerms.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }

    /**
     * Iterates through each line in FoodDatabase.csv until it finds the given food. Once found, it will take the food
     * name and its information and create a Food class from it
     *
     * @param foodName the name of the food that needs to be looked up in FoodDatabase.csv
     * @return Food Object that contains the food info from FoodDatabase.csv
     */
    public Food findFood (String foodName) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/FoodDatabase.csv"))) {
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                String[] foodInfo = line.split(",");

                if(foodInfo[0].equals(foodName)) {
                    // return the Food Object
                    return new Food(foodInfo[0].trim(), Integer.parseInt(foodInfo[1].trim()), Double.parseDouble(foodInfo[2].trim()),
                            Double.parseDouble(foodInfo[3].trim()), Double.parseDouble(foodInfo[4].trim()));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new Food("<Error>",0,0,0,0);
    }

    /**
     * Adds food and food date to user's table
     * @param date LocalDate
     * @param foodName String
     * @return true if successfully added to user's table, false if not
     */
    public boolean addFoodEntry(LocalDate date, String foodName) {
        try {
            PreparedStatement addFood = connection.prepareStatement("INSERT INTO " + currentUsername + "_table (food_date, food) VALUES ('" + date + "', ?)");
            addFood.setString(1, foodName);
            addFood.executeUpdate();

            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
