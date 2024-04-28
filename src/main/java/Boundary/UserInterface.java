package Boundary;

import Control.CalculateCalories;
import Entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class controls the user interface by changing the pane to the corresponding view that the user has chosen
 * and also provides the pane for the main user interface
 *
 * @author Isaac Hotop
 */
public class UserInterface extends Application {
    private static Scene scene;
    private static BorderPane menuPane;
    private static Button signOutBtn;
    private static Button logFoodBtn;
    private static Button reportBtn;
    private static User currentUser;
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        SignIn signIn = new SignIn();
        LogFood logFood = new LogFood();
        currentUser = new User("Empty");

        //Initially set pane to signInPane
        scene = new Scene(signIn.getSignInPane(), 700, 550);
        stage.setTitle("Calorie Calculator");
        stage.setScene(scene);
        stage.show();

        //sets the root of the scene to addUserPane when addUserBtn is clicked
        signIn.getAddUserBtn().setOnAction(e -> {
            scene.setRoot(signIn.getAddUserPane());
        });

        //resets the scene's root back to SignInPane when backBtn in addUserPane is clicked
        signIn.getBackBtn().setOnAction(e -> {
            scene.setRoot(signIn.getSignInPane());
        });

        //sets the root to logFoodPane when logFoodBtn is clicked
        logFoodBtn.setOnAction(e -> {
            scene.setRoot(logFood.getLogFoodPane());
        });

        //Sets the root to menuPane when backBtn in logFoodPane is clicked
        logFood.getBackBtn().setOnAction(e -> {
            updateGraph("gram", "daily");
            scene.setRoot(menuPane);
        });

        //Resets current user and sets the root to signInPane when signOutBtn is clicked
        signOutBtn.setOnAction(e -> {
            currentUser = new User("<Empty>");
            scene.setRoot(signIn.getSignInPane());
        });


    }

    public UserInterface() throws ClassNotFoundException {
        //Create and add modules to menuPane
        menuPane = new BorderPane();
        HBox bottomPane = new HBox();
        signOutBtn = new Button("Sign Out");
        logFoodBtn = new Button("Log Food");
        reportBtn = new Button("Generate Report");
        Label calorieTotal = new Label("Total: Calories");
        bottomPane.getChildren().addAll(logFoodBtn, reportBtn);
        menuPane.setTop(signOutBtn);
        menuPane.setBottom(bottomPane);
    }

    /**
     * Switches to menuPane
     * @param user
     */
    public void switchMenuPane(User user) {
        currentUser = user;
        updateGraph("gram","daily");
        scene.setRoot(menuPane);
    }

    /**
     * Updates pie graph when called.
     * @param gramOrCal String that lets the method know to make the graph in grams or calories (string should either be gram or calorie)
     * @param dailyOrWeekly String that lets the method know to use daily intake or weekly intake (string should either be daily or weekly)
     */
    public void updateGraph(String gramOrCal, String dailyOrWeekly) {
        CalculateCalories calculateCalories = new CalculateCalories();
        GenerateGraph generateGraph = new GenerateGraph();

        //Get total daily calories, proteins, carbs, and fats
        ArrayList<Double> dailyMacroGrams;
        ArrayList<Double> dailyMacroCals;
        int dailyCal = calculateCalories.calculateTotalCal(currentUser.getDailyIntake());
        dailyMacroGrams = calculateCalories.calculateTotalMacronutrientGrams(currentUser.getDailyIntake());
        dailyMacroCals = calculateCalories.calculateTotalMacronutrientCals(currentUser.getDailyIntake());

        //Get total weekly calories, proteins, carbs, and fats
        ArrayList<Double> weeklyMacroGrams;
        ArrayList<Double> weeklyMacroCals;
        int weeklyCal = calculateCalories.calculateTotalCal(currentUser.getWeeklyIntake());
        weeklyMacroGrams = calculateCalories.calculateTotalMacronutrientGrams(currentUser.getWeeklyIntake());
        weeklyMacroCals = calculateCalories.calculateTotalMacronutrientCals(currentUser.getWeeklyIntake());

        //Create graph with eiter weekly or daily and with either Calories or Grams data then add the graph to the menuPane
        if(gramOrCal.equals("gram")) {
            if(dailyOrWeekly.equals("daily")) {
                PieChart pieChart = generateGraph.generateGraphInGrams(dailyMacroGrams.get(0), dailyMacroGrams.get(1), dailyMacroGrams.get(2));
                pieChart.getData().forEach(data -> {
                    String label = (data.getPieValue() + " grams");
                    Tooltip toolTip = new Tooltip(label);
                    Tooltip.install(data.getNode(), toolTip);
                });
                menuPane.setCenter(pieChart);
            }
            else {
                PieChart pieChart = generateGraph.generateGraphInGrams(weeklyMacroGrams.get(0), weeklyMacroGrams.get(1), weeklyMacroGrams.get(2));
                menuPane.setCenter(pieChart);
            }
        }
        else {
            if (dailyOrWeekly.equals("daily")) {
                PieChart pieChart = generateGraph.generateGraphInCalories(dailyMacroCals.get(0), dailyMacroCals.get(1), dailyMacroCals.get(2));
                menuPane.setCenter(pieChart);
            }
            else {
                PieChart pieChart = generateGraph.generateGraphInCalories(weeklyMacroCals.get(0), weeklyMacroCals.get(1), weeklyMacroCals.get(2));
                menuPane.setCenter(pieChart);
            }
        }
    }

    /**
     *
     * @return currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        launch();
    }
}