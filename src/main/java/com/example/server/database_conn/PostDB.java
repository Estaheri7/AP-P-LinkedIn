package com.example.server.database_conn;

import com.example.server.models.Like;
import com.example.server.models.Post;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

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
                + "mediaUrl VARCHAR(255),"
                + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "likes INT NOT NULL DEFAULT 0,"
                + "comments INT NOT NULL DEFAULT 0,"
                + "FOREIGN KEY(email) REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Post post) throws SQLException {
        String query = "INSERT INTO posts (email, title, content) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, post.getAuthor());
        preparedStatement.setString(2, post.getTitle());
        preparedStatement.setString(3, post.getContent());
        preparedStatement.executeUpdate();
    }

    public void updateData(Post post) throws SQLException {
        String query = "UPDATE posts SET title=?, content=? WHERE id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, post.getTitle());
        preparedStatement.setString(2, post.getContent());
        preparedStatement.setInt(3, post.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM posts WHERE id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM posts";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    public Post getPost(int id) throws SQLException {
        String query = "SELECT * FROM posts WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int postId = resultSet.getInt("id");
            String author = resultSet.getString("email");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            String mediaURL = resultSet.getString("mediaUrl");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            int likes = resultSet.getInt("likes");
            int comments = resultSet.getInt("comments");

            return new Post(postId, author, title, content, mediaURL, createdAt, likes, comments);
        }

        return null;
    }

    public ArrayList<Post> getPosts(String email) throws SQLException {
        String query = "SELECT * FROM posts WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Post> posts = new ArrayList<>();

        while (resultSet.next()) {
            int postId = resultSet.getInt("id");
            String author = resultSet.getString("email");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            String mediaURL = resultSet.getString("mediaUrl");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            int likes = resultSet.getInt("likes");
            int comments = resultSet.getInt("comments");

            Post post = new Post(postId, author, title, content, mediaURL, createdAt, likes, comments);
            posts.add(post);
        }

        return posts;
    }

    public void addMedia(int id, String mediaUrl) throws SQLException {
        String query = "UPDATE posts SET mediaUrl=? WHERE id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, mediaUrl);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    public ArrayList<Post> getAllPosts() throws SQLException {
        String query = "SELECT * FROM posts";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Post> posts = new ArrayList<>();

        while (resultSet.next()) {
            int postId = resultSet.getInt("id");
            String author = resultSet.getString("email");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            String mediaURL = resultSet.getString("mediaUrl");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            int likes = resultSet.getInt("likes");
            int comments = resultSet.getInt("comments");

            Post post = new Post(postId, author, title, content, mediaURL, createdAt, likes, comments);

            posts.add(post);
        }

        return posts;
    }

    public void likePost(int postId) throws SQLException {
        String query = "UPDATE posts SET likes = likes + 1 WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, postId);
            preparedStatement.executeUpdate();
        }
    }

    public void dislikePost(int postId) throws SQLException {
        String query = "UPDATE posts SET likes = likes - 1 WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, postId);
            preparedStatement.executeUpdate();
        }
    }

    public void increaseComment(int postId) throws SQLException {
        String query = "UPDATE posts SET comments = comments + 1 WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, postId);
        preparedStatement.executeUpdate();
    }

    public void decreaseComment(int postId) throws SQLException {
        String query = "UPDATE posts SET comments = comments - 1 WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, postId);
        preparedStatement.executeUpdate();
    }
}
