package com.example.fitness;


import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ExecrcicesController {

    @FXML
    private Pane day1Pane; // Pane for Day 1 exercises
    private Pane day2Pane;
    private Pane day3Pane;
    private Pane day4Pane;


    // List to store exercises for Day 1
    private List<Exercise> day1Exercises = new ArrayList<>();
    private List<Exercise> day2Exercises = new ArrayList<>();
    private List<Exercise> day3Exercises = new ArrayList<>();
    private List<Exercise> day4Exercises = new ArrayList<>();

    // Initialize method to set up exercises
    @FXML
    public void initialize() {
        // Day 1: PUSH exercises
        day1Exercises.add(new Exercise("Barbell Bench Press", "@workout/Barbell-Bench-Press.gif"));
        day1Exercises.add(new Exercise("Incline Bench Press", "@workout/Incline-Barbell-Bench-Press.gif"));
        day1Exercises.add(new Exercise("Cable Crossover", "@workout/Cable-Crossover.gif"));
        day1Exercises.add(new Exercise("One arm pushdown", "@workout/One-arm-triceps-pushdown.gif"));
        day1Exercises.add(new Exercise("Pushdown", "@workout/Pushdown.gif"));

        // Day 2: PULL exercises
        day2Exercises.add(new Exercise("Barbell Curl", "@workout/Barbell-Curl.gif"));
        day2Exercises.add(new Exercise("Dumbbell Curl", "@workout/Dumbbell-Curl.gif"));
        day2Exercises.add(new Exercise("Lat Pulldown", "@workout/Lat-Pulldown.gif"));
        day2Exercises.add(new Exercise("Pull ups", "@workout/Pull-up.gif"));
        day2Exercises.add(new Exercise("Rowing Machine", "@workout/Rowing-Machine.gif"));

        // Day 3: LEG exercises
        day3Exercises.add(new Exercise("Barbell Squat", "@workout/BARBELL-SQUAT.gif"));
        day3Exercises.add(new Exercise("Dumbbell Squat", "@workout/Dumbbell-Squat.gif"));
        day3Exercises.add(new Exercise("Sumo Deadlift", "@workout/dumbbell-sumo-deadlift.gif"));
        day3Exercises.add(new Exercise("Leg Press", "@workout/Leg-Press.gif"));
        day3Exercises.add(new Exercise("Static Lunges", "@workout/Static-Lunge.gif"));

        // Day 4: CARDIO exercises
        day4Exercises.add(new Exercise("Run", "@workout/Run.gif"));
        day4Exercises.add(new Exercise("Sprint", "@workout/sprint.gif"));
        day4Exercises.add(new Exercise("Bicycling", "@workout/Riding-Outdoor-Bicycle.gif"));
        day4Exercises.add(new Exercise("Cross Crunch", "@workout/Cross-Crunch.gif"));
        day4Exercises.add(new Exercise("Crunch", "@workout/Crunch.gif"));
    }


    // Method to retrieve exercises for a specific day
    public List<Exercise> getExercisesForDay(String day) {
        switch (day) {
            case "Day1" -> {
                return day1Exercises;
            }
            case "Day2" -> {
                return day2Exercises;
            }
            case "Day3" -> {
                return day3Exercises;
            }
            case "Day4" -> {
                return day4Exercises;
            }
            default -> {
                return null;
            }
        }
    }

}
