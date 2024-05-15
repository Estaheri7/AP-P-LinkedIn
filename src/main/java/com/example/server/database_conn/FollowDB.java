package com.example.server.database_conn;

import com.example.server.models.Follow;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class FollowDB extends BaseDB {

    public FollowDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS follows("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "follower VARCHAR(255) NOT NULL,"
                + "followed VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (follower) REFERENCES users(email) ON DELETE CASCADE,"
                + "FOREIGN KEY (followed) REFERENCES users(email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Follow follow) throws SQLException {
        String query = "INSERT INTO follows(follower,followed) VALUES(?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, follow.getFollower());
        preparedStatement.setString(2, follow.getFollowed());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM follows WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM follows";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
