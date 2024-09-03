package org.jsp.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/atm_db"; // Database URL
    private static final String USER = "root"; // MySQL User name
    private static final String PASSWORD = ""; // MySQL Password (leave blank if there's no password)

    public static Connection getConnection() throws SQLException {
        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL Driver not found.");
        }
        
        // Establish a connection to the database
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
