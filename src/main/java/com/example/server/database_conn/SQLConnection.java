package com.example.server.database_conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static final String URL = "jdbc:mysql://host:port/database";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

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
