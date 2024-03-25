package com.example.fitness;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Objects;


public class MainController implements Initializable{
    @FXML
    private BorderPane root;
    @FXML
    private VBox menu;
    @FXML
    private AnchorPane contentPane;
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
    private Label nameLabel;
    private int age;
    private double height;
    private double weight;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    public void btnProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        AnchorPane view = loader.load();

       /* DashboardController dashboardController = loader.getController();
        dashboardController.displayData(age, height, weight);*/

        contentPane.getChildren().setAll(view);
    }


    @FXML
    public void btnWorkout(ActionEvent event) throws IOException {
        AnchorPane view =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("workout.fxml")));
        contentPane.getChildren().setAll(view);
    }
    @FXML
    public void btnNutrition(ActionEvent event) throws IOException {
        AnchorPane view =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("nutrition.fxml")));
        contentPane.getChildren().setAll(view);
    }
    @FXML
    public void btnLogout(ActionEvent event) throws IOException {
        AnchorPane view =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        contentPane.getChildren().setAll(view);
    }

}