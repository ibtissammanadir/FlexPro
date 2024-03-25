package com.example.fitness;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class CalorieCalculatorController {

    @FXML
    private TextField poidsTextField;

    @FXML
    private TextField hauteurTextField;


    @FXML
    private ChoiceBox<String> butChoiceBox;

    @FXML
    private Button calculateButton;

    @FXML
    private Label resultLabel;
    @FXML
    private TextField ageTextField;
    @FXML
    private ChoiceBox<String> genreChoiceBox;



    @FXML
    private void initialize() {

        butChoiceBox.getItems().addAll("gain", "lose", "maintain");
        genreChoiceBox.getItems().addAll("Male", "Female");
        calculateButton.setOnAction(event -> calculateCalories());
    }
    @FXML
    private void calculateCalories() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sex = genreChoiceBox.getValue();
        String age = ageTextField.getText();
        String height = hauteurTextField.getText();
        String weight = poidsTextField.getText();
        String goals = butChoiceBox.getValue();
        String insertFields = "insert into donnees(sex, age,height,weight, goals,calories) values('";

        double poids = Double.parseDouble(poidsTextField.getText());
        double hauteur = Double.parseDouble(hauteurTextField.getText());
        int ages = Integer.parseInt(ageTextField.getText());

        double calories = calculateCalories(poids, hauteur, ages, sex, goals);

        String insertValues = sex + "','" + age + "','" + height + "','" + weight + "','" + goals + "','" + calories + "')";
        String insertToRegister = insertFields + insertValues;
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);



            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
           /* HelloController helloController = loader.getController();
            helloController.displayUserData(ages, hauteur, poids);*/
            Scene scene = new Scene(root);
            Stage stage = (Stage) calculateButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException | SQLException e) {
            resultLabel.setText("Veuillez entrer des valeurs valides.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static double calculateCalories(double poids, double hauteur, int age, String sex, String goal) {
        double mb;
        double activityFactor = 1.55; // Fixed activity factor
        double calorieAdjustment = 0;

        // Calculate Basal Metabolic Rate (BMR) based on sex
        if (sex.equalsIgnoreCase("male")) {
            mb = 88.362 + (13.397 * poids) + (4.799 * hauteur) - (5.677 * age);
        } else if (sex.equalsIgnoreCase("female")) {
            mb = 447.593 + (9.247 * poids) + (3.098 * hauteur) - (4.330 * age);
        } else {
            throw new IllegalArgumentException("Invalid value for 'sex'. Use 'male' or 'female'.");
        }

        switch (goal.toLowerCase()) {
            case "lose":
                calorieAdjustment = -500;
                break;
            case "maintain":
                break;
            case "gain":
                calorieAdjustment = 300;

                break;
            default:
                throw new IllegalArgumentException("Invalid value for 'goal'. Use 'lose', 'maintain', or 'gain'.");
        }
        // Calculate total daily calorie needed for the day
        return (mb + calorieAdjustment) * activityFactor;
    }


}
