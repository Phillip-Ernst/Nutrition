package Boundary;

import Control.CalculateCalories;
import Entity.Food;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Isaac Hotop
 *
 * This class generates a report that users can view that displays their food, calorie, fat, carbohydrate, and protein intake
 */
public class GenerateReport {
    private final Button backBtn;
    private final ScrollPane reportPane;
    private static Text report;
    public GenerateReport() {
        //Create and add modules to reportPane
        reportPane = new ScrollPane();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        reportPane.setContent(vBox);

        report = new Text();
        report.setTabSize(4);
        report.setTextAlignment(TextAlignment.CENTER);
        loadReport();
        backBtn = new Button("Back to Menu");
        vBox.getChildren().addAll(report,backBtn);
    }

    /**
     * Creates report with information about both daily and weekly intake
     *
     * @param dailyIntake  Food ArrayList
     * @param weeklyIntake Food ArrayList
     */
    public void createReport(ArrayList<Food> dailyIntake, ArrayList<Food> weeklyIntake) {
        CalculateCalories calculateCalories = new CalculateCalories();

        //checks if a report exists already, if it does then delete it
        File report = new File("src/main/resources/report.txt");
        if (report.exists()) {
            if (!report.delete()) {
                return;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(report))) {
            //Daily part of the report
            bw.write("==========================================================================\n");
            bw.write("                        Daily Food Intake Report                          \n");
            bw.write("--------------------------------Food--------------------------------------\n");

            //Iterate through each food and add its name calories and macronutrients to the txt file
            for(Food food : dailyIntake) {
                bw.write("Name: "+food.getName()+"\t Calories: "+food.getCals()+"\t Protein: "
                        +food.getProtein()+"\t Carbohydrates: "+food.getCarbs()+"\t Fats: "+food.getFats()+"\n");
            }

            //Retrieve daily total of calories, protein, carbohydrates, and fats from dailyIntake
            int totalCal = calculateCalories.calculateTotalCal(dailyIntake);
            ArrayList<Double> totalGrams = calculateCalories.calculateTotalMacronutrientGrams(dailyIntake);
            bw.write("\n------------------------------Daily Totals---------------------------------\n");
            bw.write("Calories: "+totalCal+"\t Protein: "+totalGrams.get(0)+"\t Carbohydrates: "
                    +totalGrams.get(1)+"\t Fats: "+totalGrams.get(2)+"\n");

            //Weekly part of the report
            bw.write("\n\n==========================================================================\n");
            bw.write("                       Weekly Food Intake Report                          \n");
            bw.write("--------------------------------Food--------------------------------------\n");

            //Iterate through each food and add its name calories and macronutrients to the txt file
            for(Food food : weeklyIntake) {
                bw.write("Name: "+food.getName()+"\t Calories: "+food.getCals()+"\t Protein: "
                        +food.getProtein()+"\t Carbohydrates: "+food.getCarbs()+"\t Fats: "+food.getFats()+"\n");
            }

            //Retrieve daily total of calories, protein, carbohydrates, and fats from dailyIntake
            int totalCalW = calculateCalories.calculateTotalCal(weeklyIntake);
            ArrayList<Double> totalGramsW = calculateCalories.calculateTotalMacronutrientGrams(weeklyIntake);
            bw.write("\n-----------------------------Weekly Totals---------------------------------\n");
            bw.write("Calories: "+totalCalW+"\t Protein: "+totalGramsW.get(0)+"\t Carbohydrates: "
                    +totalGramsW.get(1)+"\t Fats: "+totalGramsW.get(2));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public ScrollPane getReportPane() {
        return reportPane;
    }

    /**
     * Converts report.txt into Text
     */
    public void loadReport() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/report.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        report.setText(content.toString());
    }
}
