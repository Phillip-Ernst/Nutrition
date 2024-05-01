package Boundary;

import Control.InputValidation;
import Entity.Food;
import Entity.User;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Isaac Hotop
 *
 * This class alows the user to securely sign in, sign out, or sign up to access or create their account
 */
public class SignIn {
    private GridPane signInPane;
    private GridPane addUserPane;
   private Button addUserBtn;
    private Button backBtn;
    public SignIn() throws ClassNotFoundException {
        InputValidation inputValidation = new InputValidation();
        FoodDatabaseManagement foodDatabaseManagement = new FoodDatabaseManagement();
        UserInterface userinterface = new UserInterface();

        //Create signIn pane
        signInPane = new GridPane();
        HBox hBox = new HBox();
        signInPane.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        signInPane.add(hBox, 0, 0);

        //Create addUser pane
        addUserPane = new GridPane();
        HBox addUserHbox = new HBox();
        addUserPane.setAlignment(Pos.CENTER);
        addUserHbox.setAlignment(Pos.CENTER);
        addUserPane.add(addUserHbox, 1, 1);

        //Create sign in fields and buttons and add them to SignInPane
        TextField usrNameField = new TextField();
        PasswordField passwdField = new PasswordField();
        Button signInBtn = new Button("Sign In");
        addUserBtn = new Button("Create New User");
        Label status = new Label("Enter your username and password above");
        usrNameField.setPromptText("Username:");
        passwdField.setPromptText("Password:");
        hBox.getChildren().addAll(usrNameField, passwdField);
        signInPane.add(signInBtn, 0, 1);
        signInPane.add(addUserBtn, 0,2);
        signInPane.add(status, 0, 3);
        signInPane.setHalignment(signInBtn, HPos.CENTER);
        signInPane.setHalignment(addUserBtn, HPos.CENTER);
        signInPane.setHalignment(status, HPos.CENTER);

        //Create add user fields and buttons and add them to addUserPane
        TextField addUsrNamefield = new TextField();
        PasswordField addPasswdField = new PasswordField();
        PasswordField passwdConfirmation = new PasswordField();
        Button createUserBtn = new Button("Create User");
        backBtn = new Button("Back to Sign in Page");
        Label status2 = new Label("Enter your username and password above");
        addUsrNamefield.setPromptText("Username:");
        addPasswdField.setPromptText("Password:");
        passwdConfirmation.setPromptText("Confirm Password:");
        addUserHbox.getChildren().addAll(addPasswdField, passwdConfirmation);
        addUserPane.add(addUsrNamefield,1,0);
        addUserPane.add(status2,1,2);
        addUserPane.add(backBtn,0,3);
        addUserPane.add(createUserBtn,2,3);

        //gets credentials from the textFields, validates them, then attempts a connection to the database when signInBtn is clicked
        signInBtn.setOnAction(e -> {
            if (inputValidation.UsernamePasswordValidation(usrNameField.getText(), passwdField.getText())) {
                try {
                    User user = foodDatabaseManagement.signInUser(usrNameField.getText(), passwdField.getText());
                    if (!user.getUsername().equals("<Error>")) {
                        userinterface.switchMenuPane(user);
                    }
                    else {
                        status.setText("This does not match any current username/passwords, please try again.");
                    }

                }
                catch (SQLException e1) {
                    status.setText("Could not load user data");
                    throw new RuntimeException(e1);
                }
            }
            else {
                status.setText("Login Credentials were not valid");
            }
        });

        //Gets credentials from the textFields, validates them, then creates a query to add the user and their
        //associated table to the database when the createUserBtn is clicked
        createUserBtn.setOnAction(e -> {
            if(addPasswdField.getText().equals(passwdConfirmation.getText())) {
                if (inputValidation.UsernamePasswordValidation(addUsrNamefield.getText(), addPasswdField.getText())) {
                        if(foodDatabaseManagement.addUser(addUsrNamefield.getText(), addPasswdField.getText())) {
                            status2.setText(addUsrNamefield.getText()+" was added");
                        }
                        else {
                            status2.setText("Could not add user...");
                        }
                }
                else {
                    status2.setText("Don't use these characters: ', \", ;, `, -, _, (), [], *, =, /, \\, %, >, <, $, &, |");
                }
            }
            else {
                status2.setText("Both password fields need to match!");
            }

        });

    }

    /**
     * @return signInPane
     */
    public GridPane getSignInPane() {
        return signInPane;
    }

    /**
     * @return addUserPane
     */
    public GridPane getAddUserPane() {
        return addUserPane;
    }

    /**
     *
     * @return addUserBtn
     */
    public Button getAddUserBtn() {
        return addUserBtn;
    }

    /**
     *
     * @return backBtn
     */
    public Button getBackBtn() {
        return backBtn;
    }
}
