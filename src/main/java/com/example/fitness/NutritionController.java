package com.example.fitness;

import com.example.fitness.FoodItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class NutritionController {
    @FXML
    private TextField breakfastField;
    @FXML
    private TextField breakfastWeightField;
    @FXML
    private TableView<FoodItem> breakfastTable;

    @FXML
    private TextField lunchField;
    @FXML
    private TextField lunchWeightField;
    @FXML
    private TableView<FoodItem> lunchTable;

    @FXML
    private TextField dinnerField;
    @FXML
    private TextField dinnerWeightField;
    @FXML
    private TableView<FoodItem> dinnerTable;

    @FXML
    private TextField snacksField;
    @FXML
    private TextField snacksWeightField;
    @FXML
    private TableView<FoodItem> snacksTable;

    @FXML
    private TableView<SummaryItem> summaryTable;

    private final Map<String, FoodItem> predefinedFoodMap = loadFoodItemsFromDatabase();
    static double totalCalories = 0.0;
    static double totalProtein = 0.0;
    static double totalFat = 0.0;
    static double totalCarbs = 0.0;


    private Map<String, FoodItem> loadFoodItemsFromDatabase() {
        Map<String, FoodItem> foodItems = new HashMap<>();
        DatabaseConnection db = new DatabaseConnection();

        try{
             PreparedStatement statement = db.getConnection().prepareStatement("SELECT * FROM food_items");
             ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                String foodItemName = resultSet.getString("food_item");
                double calories = resultSet.getDouble("calories");
                double protein = resultSet.getDouble("protein");
                double fat = resultSet.getDouble("fat");
                double carbs = resultSet.getDouble("carbs");

                FoodItem foodItem = new FoodItem(foodItemName, calories, protein, fat, carbs);
                foodItems.put(foodItemName, foodItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foodItems;
    }
    @FXML
    private void initialize() {
        initializeTable(breakfastTable, "Breakfast");
        initializeTable(lunchTable, "Lunch");
        initializeTable(dinnerTable, "Dinner");
        initializeTable(snacksTable, "Snacks");

        initializeSummaryTable();

        // Load data from the database into the tables
        loadTableDataFromDatabase(breakfastTable, "Breakfast");
        loadTableDataFromDatabase(lunchTable, "Lunch");
        loadTableDataFromDatabase(dinnerTable, "Dinner");
        loadTableDataFromDatabase(snacksTable, "Snacks");
    }





    private void initializeTable(TableView<FoodItem> table, String mealType) {
        TableColumn<FoodItem, String> foodItemColumn = new TableColumn<>("Food Item");
        TableColumn<FoodItem, Double> caloriesColumn = new TableColumn<>("Calories");
        TableColumn<FoodItem, Double> proteinColumn = new TableColumn<>("Protein");
        TableColumn<FoodItem, Double> fatColumn = new TableColumn<>("Fat");
        TableColumn<FoodItem, Double> carbsColumn = new TableColumn<>("Carbs");
        TableColumn<FoodItem, Void> deleteColumn = new TableColumn<>("Delete");

        foodItemColumn.setCellValueFactory(new PropertyValueFactory<>("foodItem"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        carbsColumn.setCellValueFactory(new PropertyValueFactory<>("carbs"));

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white;");

                // Add hover effect
                deleteButton.setOnMouseEntered(event -> {
                    deleteButton.setStyle("-fx-background-color: #141313; -fx-text-fill: white;");
                });

                // Remove hover effect
                deleteButton.setOnMouseExited(event -> {
                    deleteButton.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white;");
                });
                deleteButton.setOnAction(event -> {
                    FoodItem item = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(item);

                    // Delete the item from the database
                    deleteItemFromDatabase(item, mealType);

                    // Update the summary table
                    updateSummaryTable();
                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(foodItemColumn, caloriesColumn, proteinColumn, fatColumn, carbsColumn, deleteColumn);

        // Load data from the database into the table
        loadTableDataFromDatabase(table, mealType);
    }



    private Map<String, FoodItem> createPredefinedFoodMap() {
        Map<String, FoodItem> map = new HashMap<>();
        map.put("Salmon", new FoodItem("Salmon", 206.0, 22.0, 13.0, 0.0));
        map.put("Chicken", new FoodItem("Chicken", 335.0, 25.0, 3.6, 0.0));
        map.put("Broccoli", new FoodItem("Broccoli", 55.0, 3.7, 0.6, 11.2));
        map.put("Oatmeal", new FoodItem("Oatmeal", 68.0, 2.4, 1.4, 12.4));
        map.put("Banana", new FoodItem("Banana", 105.0, 1.3, 0.3, 27.0));
        return map;
    }

    @FXML
    private void addItemToTable(ActionEvent event) {
        TextField foodField = null;
        TextField weightField = null;
        TableView<FoodItem> table = null;

        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            String userData = (String) button.getUserData();

            switch (userData) {
                case "Breakfast":
                    foodField = breakfastField;
                    weightField = breakfastWeightField;
                    table = breakfastTable;
                    break;
                case "Lunch":
                    foodField = lunchField;
                    weightField = lunchWeightField;
                    table = lunchTable;
                    break;
                case "Dinner":
                    foodField = dinnerField;
                    weightField = dinnerWeightField;
                    table = dinnerTable;
                    break;
                case "Snacks":
                    foodField = snacksField;
                    weightField = snacksWeightField;
                    table = snacksTable;
                    break;
            }

            if (foodField != null && weightField != null && table != null && !foodField.getText().isEmpty() && !weightField.getText().isEmpty()) {
                String foodName = foodField.getText();
                double weight = Double.parseDouble(weightField.getText());

                if (predefinedFoodMap.containsKey(foodName)) {
                    FoodItem predefinedFood = predefinedFoodMap.get(foodName);
                    FoodItem foodItem = new FoodItem(
                            predefinedFood.getFoodItem(),
                            predefinedFood.getCalories() * (weight / 100.0),
                            predefinedFood.getProtein() * (weight / 100.0),
                            predefinedFood.getFat() * (weight / 100.0),
                            predefinedFood.getCarbs() * (weight / 100.0));

                    table.getItems().add(foodItem);

                    // Save the item to the database with the corresponding meal type
                    foodItem.saveToDatabase(userData);

                    // Update the summary table
                    updateSummaryTable();
                } else {
                    showAlert("Invalid Food Item", "Please enter a valid food item from the predefined list.");
                }

                foodField.clear();
                weightField.clear();
            }
        }
    }

    private void loadTableDataFromDatabase(TableView<FoodItem> table, String mealType) {
        table.getItems().clear(); // Clear existing items before loading from the database
        DatabaseConnection db=new DatabaseConnection();

        try {
             PreparedStatement statement = db.getConnection().prepareStatement("SELECT * FROM fooddata WHERE meal_type = ?");
            statement.setString(1, mealType);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String foodItemName = resultSet.getString("food_item");
                double calories = resultSet.getDouble("calories");
                double protein = resultSet.getDouble("protein");
                double fat = resultSet.getDouble("fat");
                double carbs = resultSet.getDouble("carbs");

                FoodItem foodItem = new FoodItem(foodItemName, calories, protein, fat, carbs);
                table.getItems().add(foodItem);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in a real application
        }
    }



    private boolean isDailyGoalAchieved() {
        double totalCaloriesAll = totalCalories + calculateTableTotal(breakfastTable) +
                calculateTableTotal(lunchTable) + calculateTableTotal(dinnerTable) +
                calculateTableTotal(snacksTable);

        return totalCaloriesAll >= 2000.0;
    }

    private double calculateTableTotal(TableView<FoodItem> table) {
        double total = 0.0;
        for (FoodItem item : table.getItems()) {
            total += item.getCalories();
        }
        return total;
    }

    private void initializeSummaryTable() {
        TableColumn<SummaryItem, String> metricColumn = new TableColumn<>("Metric");
        TableColumn<SummaryItem, Double> caloriesColumn = new TableColumn<>("Calories");
        TableColumn<SummaryItem, Double> proteinColumn = new TableColumn<>("Protein");
        TableColumn<SummaryItem, Double> fatColumn = new TableColumn<>("Fat");
        TableColumn<SummaryItem, Double> carbsColumn = new TableColumn<>("Carbs");

        metricColumn.setCellValueFactory(new PropertyValueFactory<>("metric"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        carbsColumn.setCellValueFactory(new PropertyValueFactory<>("carbs"));

        summaryTable.getColumns().setAll(metricColumn, caloriesColumn, proteinColumn, fatColumn, carbsColumn);
        updateSummaryTable();
    }
    private void deleteItemFromDatabase(FoodItem item, String mealType) {
        DatabaseConnection db=new DatabaseConnection();

        try {
             PreparedStatement statement = db.getConnection().prepareStatement(
                     "DELETE FROM fooddata WHERE food_item = ? AND meal_type = ?");

            statement.setString(1, item.getFoodItem());
            statement.setString(2, mealType);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in a real application
        }
    }

    private void updateSummaryTable() {

            totalCalories = 0.0;
            totalProtein = 0.0;
            totalFat = 0.0;
            totalCarbs = 0.0;

            calculateTotalsForTable(breakfastTable);
            calculateTotalsForTable(lunchTable);
            calculateTotalsForTable(dinnerTable);
            calculateTotalsForTable(snacksTable);

            // Initialize totals with existing data from the database
            calculateTotalsForTableFromDatabase("Breakfast");
            calculateTotalsForTableFromDatabase("Lunch");
            calculateTotalsForTableFromDatabase("Dinner");
            calculateTotalsForTableFromDatabase("Snacks");

            summaryTable.getItems().clear();

            summaryTable.getItems().add(new SummaryItem("Totals", totalCalories, totalProtein, totalFat, totalCarbs));
            summaryTable.getItems().add(new SummaryItem("Daily Goals", 2500.0, 50.0, 70.0, 200.0));

            SummaryItem dailyGoalsRow = summaryTable.getItems().get(1);
            SummaryItem remainingRow = new SummaryItem(
                    "Remaining",
                    dailyGoalsRow.getCalories() - totalCalories,
                    dailyGoalsRow.getProtein() - totalProtein,
                    dailyGoalsRow.getFat() - totalFat,
                    dailyGoalsRow.getCarbs() - totalCarbs
            );

            summaryTable.getItems().add(remainingRow);

    }

    private void calculateTotalsForTable(TableView<FoodItem> table) {
    }
    private void calculateTotalsForTableFromDatabase(String mealType) {
        DatabaseConnection db=new DatabaseConnection();
        try {
             PreparedStatement statement = db.getConnection().prepareStatement(
                     "SELECT SUM(calories) AS totalCalories, SUM(protein) AS totalProtein, " +
                             "SUM(fat) AS totalFat, SUM(carbs) AS totalCarbs FROM fooddata WHERE meal_type = ?");

            statement.setString(1, mealType);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalCalories += resultSet.getDouble("totalCalories");
                totalProtein += resultSet.getDouble("totalProtein");
                totalFat += resultSet.getDouble("totalFat");
                totalCarbs += resultSet.getDouble("totalCarbs");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in a real application
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void getPieChart(){

    }

}