package com.example.server.database_conn;

import com.example.server.models.Like;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeDB extends BaseDB {

    public LikeDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS likes("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "post_id INT NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "userName VARCHAR(255) NOT NULL,"
                + "like_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE ON UPDATE CASCADE,"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Like like) throws SQLException {
        String query = "INSERT INTO likes (post_id, email, userName) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, like.getPostId());
        preparedStatement.setString(2, like.getEmail());
        preparedStatement.setString(3, like.getUserName());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id, String email) throws SQLException {
        String query = "DELETE FROM likes WHERE post_id = ? AND email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, email);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM likes";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    public ArrayList<Like> getLikes(int postId) throws SQLException {
        String query = "SELECT * FROM likes WHERE post_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, postId);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Like> likes = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String userName = resultSet.getString("userName");
            Timestamp likeTime = resultSet.getTimestamp("like_time");

            likes.add(new Like(id, postId, email, userName, likeTime));
        }

        return likes;
    }


    public List<Like> getLike(String email) throws SQLException {
        String query = "SELECT * FROM likes WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Like> likes = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int postId = resultSet.getInt("post_id");
            String userName = resultSet.getString("userName");
            Timestamp likeTime = resultSet.getTimestamp("like_time");

            likes.add(new Like(id, postId, email, userName, likeTime));
        }

        return likes;
    }

    public boolean likeExists(Like like) throws SQLException {
        String query = "SELECT * FROM likes WHERE post_id = ? AND email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, like.getPostId());
        preparedStatement.setString(2, like.getEmail());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
