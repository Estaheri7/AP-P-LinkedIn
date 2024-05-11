package com.example.server.database_conn;

import java.sql.SQLException;
import java.sql.Statement;

public class PostDB extends BaseDB {

    public PostDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS posts ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) NOT NULL,"
                + "title VARCHAR(255) NOT NULL,"
                + "content VARCHAR(3000) NOT NULL,"
                + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "likes INT NOT NULL DEFAULT 0,"
                + "comments INT NOT NULL DEFAULT 0,"
                + "FOREIGN KEY(email) REFERENCES users (email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }
}
