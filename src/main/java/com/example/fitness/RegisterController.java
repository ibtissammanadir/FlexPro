package com.example.fitness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class RegisterController {
    @FXML
    private Button closeButton;
    @FXML
    private TextField firstNameTextfield;
    @FXML
    private ImageView keyimageView;
    @FXML
    private TextField LastNameTextfield;
    @FXML
    private Button RegisterButton;
    @FXML
    private TextField SetPasswordField;
    @FXML
    private TextField UsernameTextfield;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label registrationMassageLabel;
    @FXML
    private Label confirmPasswordLabel;

    @FXML
    public void  closeButtonOnAction() throws IOException {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        FXMLLoader fxmlLoader =new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public  void registerUser(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstNameTextfield.getText();
        String lastname =LastNameTextfield.getText();
        String username =UsernameTextfield.getText();
        String password=SetPasswordField.getText();
        String insertFields ="insert into user_account(firstname, lastname,username,password) values('";

        String insertValues =firstname +"','"+lastname +"','"+ username  +"','"+ password +"')";
        String insertToregister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToregister);

            registrationMassageLabel.setText("User has been registred succefully");
            Stage stage =(Stage) RegisterButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("donnees.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }

    }
    static  void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}


