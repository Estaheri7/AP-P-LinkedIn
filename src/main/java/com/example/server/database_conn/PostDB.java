package com.example.server.database_conn;

import com.example.server.models.Post;

import java.sql.PreparedStatement;
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

    public void insertData(Post post) throws SQLException {
        String query = "INSERT INTO posts (email, title, content) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, post.getAuthor());
        preparedStatement.setString(2, post.getTitle());
        preparedStatement.setString(3, post.getContent());
        preparedStatement.executeUpdate();
    }

    public void updateData(Post post) throws SQLException {
        String query = "UPDATE posts SET title=?, content=? WHERE email=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, post.getTitle());
        preparedStatement.setString(2, post.getContent());
        preparedStatement.setString(3, post.getAuthor());
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
}
