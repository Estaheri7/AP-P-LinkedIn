package com.example.server.database_conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/linkedin";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static Connection conn = null;

    private SQLConnection() {
        // Singleton design pattern
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }

        return conn;
    }
}
