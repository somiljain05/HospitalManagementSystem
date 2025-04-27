package com.hmapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final Logger logger = Logger.getLogger(DatabaseUtil.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/hmapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "acer";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            logger.severe("Failed to load MySQL JDBC driver: " + e.getMessage());
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.info("Database connection established successfully");
            return conn;
        } catch (SQLException e) {
            logger.severe("Failed to establish database connection: " + e.getMessage());
            throw e;
        }
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed successfully");
            } catch (SQLException e) {
                logger.warning("Error closing database connection: " + e.getMessage());
            }
        }
    }
    
    public static String checkConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                return "Connected";
            }
        } catch (SQLException e) {
            return "Not Connected: " + e.getMessage();
        }
        return "Not Connected";
    }
} 