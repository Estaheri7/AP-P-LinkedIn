package com.example.server.database_conn;

import com.example.server.models.Notification;

import java.sql.*;
import java.util.ArrayList;

public class NotificationDB extends BaseDB {

    public NotificationDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS notifications ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) NOT NULL,"
                + "title VARCHAR(40) NOT NULL,"
                + "message VARCHAR(200) NOT NULL,"
                + "post_id INT,"
                + "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Notification notification) throws SQLException {
        String query = "INSERT INTO notifications (email, title, message, post_id) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, notification.getEmail());
        preparedStatement.setString(2, notification.getTitle());
        preparedStatement.setString(3, notification.getMessage());
        preparedStatement.setInt(4, notification.getPostId());
        preparedStatement.executeUpdate();
    }

    public Notification getNotification(String email) throws SQLException {
        String query = "SELECT * FROM notifications WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String message = resultSet.getString("message");
            int postId = resultSet.getInt("post_id");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");
            return new Notification(id, email, title, message, postId, timestamp);
        }
        return null;
    }

    public ArrayList<Notification> getAllNotifications(String email) throws SQLException {
        String query = "SELECT * FROM notifications WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Notification> notifications = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String message = resultSet.getString("message");
            int postId = resultSet.getInt("post_id");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");
            notifications.add(new Notification(id, email, title, message, postId, timestamp));
        }
        return notifications;
    }
}
