package com.example.fitness;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable{
    @FXML
    private Button workout;
    @FXML
    private Button profile;
    @FXML
    private Button nutrition;
    @FXML
    private Button Logout;
    @FXML
    private PieChart pieChart;
    @FXML
    private NutritionController pieController =new NutritionController();
    @FXML
    private Pane pushpane;
    @FXML
    private Pane pullpane;
    @FXML
    private Pane legpane;
    @FXML
    private Pane cardiopane;

    @FXML
    private Label heightLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label caloriesDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        if (pieChart != null) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Carbs", pieController.totalCarbs),
                    new PieChart.Data("Fats", pieController.totalFat),
                    new PieChart.Data("Protein", pieController.totalProtein));
            pieChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " amount: ", data.pieValueProperty())));

            pieChart.getData().addAll(pieChartData);
            DayOfWeek currentDay = LocalDate.now().getDayOfWeek();

            // Set visibility based on the current day of the week
            if (currentDay == DayOfWeek.MONDAY || currentDay == DayOfWeek.FRIDAY) {
                pushpane.setVisible(true);
                pushpane.setManaged(true);
            } else {
                pushpane.setVisible(false);
                pushpane.setManaged(false);
            }

            if (currentDay == DayOfWeek.TUESDAY || currentDay == DayOfWeek.SATURDAY) {
                pullpane.setVisible(true);
                pullpane.setManaged(true);
            } else {
                pullpane.setVisible(false);
                pullpane.setManaged(false);
            }

            if (currentDay == DayOfWeek.WEDNESDAY || currentDay == DayOfWeek.SUNDAY ) {
                legpane.setVisible(true);
                legpane.setManaged(true);
            } else {
                legpane.setVisible(false);
                legpane.setManaged(false);
            }

            if (currentDay == DayOfWeek.THURSDAY) {
                cardiopane.setVisible(true);
                cardiopane.setManaged(true);
            } else {
                cardiopane.setVisible(false);
                cardiopane.setManaged(false);
            }

        }
        fetchDataFromDatabase();


    }
    private void fetchDataFromDatabase() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {

            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM donnees");

            if (resultSet.next()) {
                double height = resultSet.getDouble("height");
                double weight = resultSet.getDouble("weight");
                int age = resultSet.getInt("age");
                double calories = resultSet.getDouble("calories");

                heightLabel.setText("Height: " +"\n"+ height);
                weightLabel.setText("Weight: " + "\n"+weight);
                ageLabel.setText("Age: " +"\n"+ age);
                caloriesDB.setText(" " + pieController.totalCalories + " \\" + calories);
            }

            resultSet.close();
            statement.close();
            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}