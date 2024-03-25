package com.example.fitness;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class DATABASE {
        Connection con;
        String url = "jdbc:mysql://127.0.0.1:3306/fitness";
        public Connection Connect () {

                Statement stmt;

                String DatabaseName = "fitness";

                try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection(url, "root", "Jawda123..");
                        stmt = con.createStatement();
                        stmt.executeUpdate("USE " + DatabaseName + ";");
                        System.out.println("OK");


                } catch (
                        SQLException e) {
                        e.printStackTrace();
                } catch (
                        ClassNotFoundException e) {

                }
                return con;
        }


}