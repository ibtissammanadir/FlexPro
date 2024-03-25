// FoodItem.java
package com.example.fitness;
import java.sql.*;

public class FoodItem {

    private String foodItem;
    private double calories;
    private double protein;
    private double fat;
    private double carbs;

    public FoodItem(String foodItem, double calories, double protein, double fat, double carbs) {
        this.foodItem = foodItem;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    // getters and setters

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void saveToDatabase(String mealType) {
        DatabaseConnection db = new DatabaseConnection();
        try {
            PreparedStatement statement = db.getConnection().prepareStatement(
                    "INSERT INTO fooddata (food_item, calories, protein, fat, carbs, meal_type) VALUES (?, ?, ?, ?, ?, ?)");

            statement.setString(1, foodItem);
            statement.setDouble(2, calories);
            statement.setDouble(3, protein);
            statement.setDouble(4, fat);
            statement.setDouble(5, carbs);
            statement.setString(6, mealType);

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in a real application
        }
    }
}

