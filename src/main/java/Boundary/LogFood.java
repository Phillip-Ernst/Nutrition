package Boundary;

import Control.InputValidation;
import Entity.Food;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDate;

/**
 * @author Isaac Hotop
 *
 * This class alows the user to input their food intake
 */
public class LogFood {
    private final GridPane logFoodPane;
    private final Button backBtn;

    public LogFood() throws ClassNotFoundException {
        FoodDatabaseManagement foodDatabaseManagement = new FoodDatabaseManagement();
        InputValidation inputValidation = new InputValidation();
        UserInterface userInterface = new UserInterface();

        //Create logFoodPane
        logFoodPane = new GridPane();
        HBox centerPane = new HBox();
        logFoodPane.add(centerPane,1,1);
        logFoodPane.setHgap(150);

        //Create and add nodes to logFoodPane
        backBtn = new Button("Back to Menu");
        Button enterBtn = new Button("Enter");
        TextField foodField = new TextField();
        Label status = new Label("Enter your food in the field above");
        logFoodPane.add(backBtn,0,0);
        centerPane.getChildren().addAll(foodField, enterBtn);
        logFoodPane.add(status,1,2);
        //searches for the user's food in the food database then adds that food to the user's database
        enterBtn.setOnAction(e -> {
            if (inputValidation.foodInputValidation(foodField.getText())) {
                Food userFood = foodDatabaseManagement.findFood(foodField.getText());

                //Checks if the food was found, if so then add food to User
                if (!userFood.getName().equals("<Error>")) {
                    userInterface.getCurrentUser().addCurrentFood(userFood);

                    if (foodDatabaseManagement.addFoodEntry(LocalDate.now(), userFood.getName())) {
                        status.setText(userFood.getName() + " was added!");
                    }
                    else {
                        status.setText(userFood.getName() + " could not be added");
                    }
                }
                else {
                    status.setText("Food was not found in the database");
                }
            }
            else {
                status.setText("Food input was not valid");
            }


        });
    }

    /**
     * @return logFoodPane
     */
    public GridPane getLogFoodPane() {
        return logFoodPane;
    }

    /**
     * @return logFoodPane
     */
    public Button getBackBtn() {
        return backBtn;
    }
}
