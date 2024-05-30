package com.example.server.database_conn;

import com.example.server.models.Follow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Follow> getFollow(String follower) throws SQLException {
        String query = "SELECT * FROM follows WHERE follower = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, follower);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Follow> followers = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String followed = resultSet.getString("followed");
            Follow follow = new Follow(id ,follower, followed);
            followers.add(follow);
        }

        return followers;
    }

    public ArrayList<Follow> getFollowed(String followed) throws SQLException {
        String query = "SELECT * FROM follows WHERE followed = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, followed);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Follow> followeds = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String follower = resultSet.getString("follower");
            Follow follow = new Follow(id ,follower, followed);
            followeds.add(follow);
        }

        return followeds;
    }

    public Follow getFollow(String followerEmail, String followedEmail) throws SQLException {
        String query = "SELECT * FROM follows WHERE follower = ? AND followed = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, followerEmail);
        preparedStatement.setString(2, followedEmail);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            return new Follow(id ,followerEmail, followedEmail);
        }

        return null;
    }

    public boolean isFollowed(String followerEmail, String followedEmail) throws SQLException {
        String query = "SELECT * FROM follows WHERE follower = ? AND followed = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, followerEmail);
        preparedStatement.setString(2, followedEmail);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

}
