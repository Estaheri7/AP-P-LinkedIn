package com.example.server.database_conn;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDB extends BaseDB {

    public UserDB() throws SQLException {
    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) UNIQUE NOT NULL,"
                + "password VARCHAR(255) NOT NULL,"
                + "name VARCHAR(20) NOT NULL,"
                + "lastName VARCHAR(40) NOT NULL,"
                + "additionalName VARCHAR(40),"
                + "avatar_url VARCHAR(255),"
                + "background_url VARCHAR(255),"
                + "headline VARCHAR(220),"
                + "country VARCHAR(60),"
                + "city VARCHAR(60)"
                + ")";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }
}
