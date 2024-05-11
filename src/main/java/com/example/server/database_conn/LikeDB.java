package com.example.server.database_conn;

import java.sql.SQLException;
import java.sql.Statement;

public class LikeDB extends BaseDB {

    public LikeDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS likes("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "post_id INT NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "like_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }
}
