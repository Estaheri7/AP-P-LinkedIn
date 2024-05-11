package com.example.server.database_conn;

import java.sql.SQLException;
import java.sql.Statement;

public class CommentDB extends BaseDB {

    public CommentDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE DATABASE IF NOT EXISTS comments ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "post_id INT NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "comment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "message VARCHAR(1250) NOT NULL,"
                + "FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE,"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }
}
