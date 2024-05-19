package com.example.server.database_conn;

import com.example.server.models.Hashtag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public void insertData(Hashtag hashtag) throws SQLException {
        String query = "INSERT INTO hashtags (post_id, hashtag) VALUES (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, hashtag.getPostId());
        preparedStatement.setString(2, hashtag.getHashtag());
        preparedStatement.executeUpdate();
    }

    public void updateData(Hashtag hashtag) throws SQLException {
        String query = "UPDATE hashtags SET hashtag = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, hashtag.getHashtag());
        preparedStatement.setInt(2, hashtag.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM hashtags WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM hashtags";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    public List<String> getHashtag(int postId) throws SQLException {
        String query = "SELECT hashtag FROM hashtags WHERE post_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, postId);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> hashtags = new ArrayList<>();
        while (((ResultSet) resultSet).next()) {
            String hashtag = resultSet.getString("hashtag");
            hashtags.add(hashtag);
        }

        return hashtags;
    }
}
