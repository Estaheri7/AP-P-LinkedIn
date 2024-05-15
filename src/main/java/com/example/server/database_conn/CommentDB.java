package com.example.server.database_conn;

import com.example.server.models.Comment;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CommentDB extends BaseDB {

    public CommentDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS comments ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "post_id INT NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "comment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "message VARCHAR(1250) NOT NULL,"
                + "FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Comment comment) throws SQLException {
        String query = "INSERT INTO comments (post_id, email, message) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, comment.getPostId());
        preparedStatement.setString(2, comment.getEmail());
        preparedStatement.setString(3, comment.getComment());
    }

    public void updateData(Comment comment) throws SQLException {
        String query = "UPDATE comments SET comment = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, comment.getComment());
        preparedStatement.setInt(2, comment.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM comments WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM comments";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
