package Boundary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class generates a pie graph that users can view on their home page that displays the percentage of
 * each macro nutrient (protein, carbohydrates, and fats) out of the total amount of calories consumed
 *
 * @author Isaac Hotop
 */
public class GenerateGraph {
    PieChart pieChart;
    private static Stage popUpStage;
    private static ToggleGroup gramOrCal;
    private static ToggleGroup dailyOrWeekly;
    private static Button saveBtn;

    public GenerateGraph() {
        //Create stage and layout
        popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setTitle("Pie Chart Settings");
        GridPane popUpLayout = new GridPane();
        popUpLayout.setVgap(20);
        popUpLayout.setAlignment(Pos.CENTER);
        HBox gramOrCalBox = new HBox();
        gramOrCalBox.setSpacing(8);
        HBox dailyOrWeeklyBox = new HBox();
        dailyOrWeeklyBox.setSpacing(8);

        //ToggleGroups for allowing only ONE selection of setting per group
        gramOrCal = new ToggleGroup();
        dailyOrWeekly = new ToggleGroup();

        RadioButton gramRadio = new RadioButton("Grams    ");
        gramRadio.setToggleGroup(gramOrCal);
        RadioButton calRadio = new RadioButton("Calories");
        calRadio.setToggleGroup(gramOrCal);
        RadioButton dailyRadio = new RadioButton("Daily     ");
        dailyRadio.setToggleGroup(dailyOrWeekly);
        RadioButton weeklyRadio = new RadioButton("Weekly ");
        weeklyRadio.setToggleGroup(dailyOrWeekly);

        //Populate popUpLayout with nodes and create scene then set it to popUpStage
        Label status = new Label("Select the display options for the pie chart");
        saveBtn = new Button("Save Settings");
        status.setStyle("-fx-font-weight: bold; -fx-font-size: 8pt;");
        gramOrCalBox.getChildren().addAll(gramRadio, calRadio);
        dailyOrWeeklyBox.getChildren().addAll(dailyRadio, weeklyRadio);
        popUpLayout.add(status,0,0);
        popUpLayout.add(gramOrCalBox,0,1);
        popUpLayout.add(dailyOrWeeklyBox,0,2);
        popUpLayout.add(saveBtn,0,3);
        Scene popUpScene = new Scene(popUpLayout, 300, 200);
        popUpStage.setScene(popUpScene);
    }
    private ObservableList<PieChart.Data> pieChartData;

    /**
     * Creates a graph that displays the total grams of protein, carbohydrates, and fats
     * @param totalProteinG amount of protein in grams that the user has consumed
     * @param totalCarbsG amount of carbohydrates in grams that the user has consumed
     * @param totalFatsG amount of fats in grams that the user has consumed
     * @return pieChart
     */
    public PieChart generateGraphInGrams(double totalProteinG, double totalCarbsG, double totalFatsG) {
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Grams of Protein", totalProteinG),
                new PieChart.Data("Grams of Carbohydrates", totalCarbsG),
                new PieChart.Data("Grams of Fats", totalFatsG)
        );
        pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Daily Intake of Macronutrients in Grams");
        return pieChart;
    }

    /**
     * Creates a graph that displays the total Calories of protein, carbohydrates, and fats
     * @param totalProteinC amount of protein in Calories that the user has consumed
     * @param totalCarbsC amount of carbohydrates in Calories that the user has consumed
     * @param totalFatsC amount of fats in Calories that the user has consumed
     * @return pieChart
     */
    public PieChart generateGraphInCalories(double totalProteinC, double totalCarbsC, double totalFatsC) {
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Calories in Proteins", totalProteinC),
                new PieChart.Data("Calories in Carbohydrates", totalCarbsC),
                new PieChart.Data("Calories in Fats", totalFatsC)
        );
        pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Daily Intake of Macronutrients in Calories");
        return pieChart;
    }

    public Stage getPopUpStage() {
        return popUpStage;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public ToggleGroup getGramOrCal() {
        return gramOrCal;
    }

    public ToggleGroup getDailyOrWeekly() {
        return dailyOrWeekly;
    }
}
