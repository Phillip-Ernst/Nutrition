package Boundary;

import Control.CalculateCalories;
import Entity.Food;
import Entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
    private static Button graphSettingBtn;
    private static User currentUser;
    private static GenerateGraph generateGraph;
    private static GenerateReport generateReport;
    @Override
    public void start(Stage stage) throws ClassNotFoundException {
        SignIn signIn = new SignIn();
        LogFood logFood = new LogFood();
        generateReport = new GenerateReport();
        generateGraph = new GenerateGraph();
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

        //update graph and report then sets the root to menuPane when backBtn in logFoodPane is clicked
        logFood.getBackBtn().setOnAction(e -> {
            updateGraph("Grams    ", "Daily     ");
            updateReport();

            scene.setRoot(menuPane);
        });

        //Resets current user and sets the root to signInPane when signOutBtn is clicked
        signOutBtn.setOnAction(e -> {
            currentUser = new User("<Empty>");
            scene.setRoot(signIn.getSignInPane());
        });

        //Creates a popup window from GenerateGraph to change graph settings
        graphSettingBtn.setOnAction(e -> {
            generateGraph.getPopUpStage().show();
        });

        //updates pie chart with new settings the closes the popup window after saveBtn in popup window is pressed
        generateGraph.getSaveBtn().setOnAction(e -> {
            String gramOrCal = ((RadioButton) generateGraph.getGramOrCal().getSelectedToggle()).getText();
            String dailyOrWeekly = ((RadioButton) generateGraph.getDailyOrWeekly().getSelectedToggle()).getText();
            updateGraph(gramOrCal, dailyOrWeekly);
            generateGraph.getPopUpStage().close();
        });

        //Creates report then sets the root to reportPane when reportBtn is clicked
        reportBtn.setOnAction(e -> {
            generateReport.createReport(currentUser.getDailyIntake(), currentUser.getWeeklyIntake());
            scene.setRoot(generateReport.getReportPane());

        });

        //Sets the root to menuPane when backBtn in reportPane is clicked
        generateReport.getBackBtn().setOnAction(e -> {
            scene.setRoot(menuPane);
        });
    }

    public UserInterface() {
        //Create and add modules to menuPane
        menuPane = new BorderPane();
        HBox bottomPane = new HBox();
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setSpacing(150);
        signOutBtn = new Button("Sign Out");
        logFoodBtn = new Button("Log Food");
        reportBtn = new Button("Generate Report");
        graphSettingBtn = new Button("Graph Settings");
        bottomPane.getChildren().addAll(logFoodBtn, reportBtn);
        menuPane.setLeft(signOutBtn);
        menuPane.setRight(graphSettingBtn);
        menuPane.setBottom(bottomPane);
    }

    /**
     * Switches to menuPane
     * @param user
     */
    public void switchMenuPane(User user) {
        currentUser = user;
        updateGraph("Grams    ","Daily     ");
        updateReport();
        scene.setRoot(menuPane);
    }

    public void updateReport() {
        generateReport.createReport(currentUser.getDailyIntake(), currentUser.getWeeklyIntake());
        generateReport.loadReport();
    }
    /**
     * Updates pie graph when called.
     * @param gramOrCal String that lets the method know to make the graph in grams or calories (string should either be gram or calorie)
     * @param dailyOrWeekly String that lets the method know to use daily intake or weekly intake (string should either be daily or weekly)
     */
    public void updateGraph(String gramOrCal, String dailyOrWeekly) {
        CalculateCalories calculateCalories = new CalculateCalories();

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
        if(gramOrCal.equals("Grams    ")) {
            if(dailyOrWeekly.equals("Daily     ")) {
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
                pieChart.setTitle("Weekly Intake of Macronutrients in Grams");
                pieChart.getData().forEach(data -> {
                    String label = (data.getPieValue() + " grams");
                    Tooltip toolTip = new Tooltip(label);
                    Tooltip.install(data.getNode(), toolTip);
                });
                menuPane.setCenter(pieChart);
            }
        }
        else {
            if (dailyOrWeekly.equals("Daily     ")) {
                PieChart pieChart = generateGraph.generateGraphInCalories(dailyMacroCals.get(0), dailyMacroCals.get(1), dailyMacroCals.get(2));
                pieChart.getData().forEach(data -> {
                    String label = (data.getPieValue() + " calories");
                    Tooltip toolTip = new Tooltip(label);
                    Tooltip.install(data.getNode(), toolTip);
                });
                menuPane.setCenter(pieChart);
            }
            else {
                PieChart pieChart = generateGraph.generateGraphInCalories(weeklyMacroCals.get(0), weeklyMacroCals.get(1), weeklyMacroCals.get(2));
                pieChart.setTitle("Weekly Intake of Macronutrients in Calories");
                pieChart.getData().forEach(data -> {
                    String label = (data.getPieValue() + " calories");
                    Tooltip toolTip = new Tooltip(label);
                    Tooltip.install(data.getNode(), toolTip);
                });
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