package com.example.server.database_conn;

import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB extends BaseDB {

    public ConnectionDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS connections ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "sender VARCHAR(255) NOT NULL,"
                + "receiver VARCHAR(255) NOT NULL,"
                + "commited BOOLEAN DEFAULT FALSE,"
                + "FOREIGN KEY (sender) REFERENCES users(email) ON DELETE CASCADE,"
                + "FOREIGN KEY (receiver) REFERENCES users(email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }
}
