package com.example.server.database_conn;

import java.sql.SQLException;
import java.sql.Statement;

public class HashtagDB extends BaseDB {

    public HashtagDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS hashtags ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "post_id INT NOT NULL,"
                + "hashtag VARCHAR(50) NOT NULL,"
                + "FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }
}
