package com.example.fitness;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseUser = "root";
        String databasePassword = "Jawda123..";
        String url = "jdbc:mysql://127.0.0.1:3306/fitness";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

}
