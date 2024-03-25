package com.example.fitness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


import javafx.scene.control.*;
import javafx.stage.Window;


public class LoginController{
    @FXML
    private Button loginButton;
    @FXML
    private Button inscrireButton;
    @FXML
    private  Label loginMessagelabel;
    @FXML
    private ImageView lockImageView;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    private int accountId;

    public void setAccountId(int accountId) {

        this.accountId = accountId;
    }


    @FXML
    public  void loginButtonOnAction() {
        Window owner= loginButton.getScene().getWindow();
        if (usernameTextField.getText().isEmpty() ||enterPasswordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                    " Please enter your username and password  ");
        }
        else {
            validateLogin();
        }
    }


    public void inscrireButtonOnAction () throws IOException {
        Stage stage =(Stage) inscrireButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    public  void  validateLogin(){
        DatabaseConnection connectNow =new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        try {
            Statement statement= connectDB.createStatement();
            String verifyLogin = "SELECT  count(1)  FROM  user_account  WHERE username = '"+ usernameTextField.getText() + "' AND password ='" + enterPasswordField.getText() + "'";
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1)== 1){
                    Stage stage =(Stage) loginButton.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("hello-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    stage.setScene(scene);
                    stage.show();

                }
                else {
                    loginMessagelabel.setText("invalid login");
                    clearFields();
                }
            }

        }catch (Exception e) {
            Window owner= loginButton.getScene().getWindow();
            e.printStackTrace();
            e.getCause();
            clearFields();
        }
    }

   public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    public void clearFields(){
        usernameTextField.clear();
        enterPasswordField.clear();

    }
}