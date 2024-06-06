package com.example.server.database_conn;

import com.example.server.models.Chat;

import java.sql.*;
import java.util.ArrayList;

public class ChatDB extends BaseDB {

    public ChatDB() throws SQLException {
    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS chat ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "sender VARCHAR(255) NOT NULL,"
                + "receiver VARCHAR(255) NOT NULL,"
                + "message VARCHAR(1900) NOT NULL,"
                + "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (sender) REFERENCES users(email) ON DELETE CASCADE ON UPDATE CASCADE,"
                + "FOREIGN KEY (receiver) REFERENCES users(email) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Chat chat) throws SQLException {
        String query = "INSERT INTO chat (sender, receiver, message) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, chat.getSender());
        preparedStatement.setString(2, chat.getReceiver());
        preparedStatement.setString(3, chat.getMessage());
        preparedStatement.executeUpdate();
    }

    public void updateData(Chat chat) throws SQLException {
        String query = "UPDATE chat SET sender = ?, receiver = ?, message = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, chat.getSender());
        preparedStatement.setString(2, chat.getReceiver());
        preparedStatement.setString(3, chat.getMessage());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM chat WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public ArrayList<Chat> getChats(String sender, String receiver) throws SQLException {
        String query = "SELECT * FROM chat WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?) ORDER BY timestamp ASC";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, sender);
        preparedStatement.setString(2, receiver);
        preparedStatement.setString(3, receiver);
        preparedStatement.setString(4, sender);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Chat> chats = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String senderUser = resultSet.getString("sender");
            String receiverUser = resultSet.getString("receiver");
            String message = resultSet.getString("message");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");

            Chat chat = new Chat(id, senderUser, receiverUser, message, timestamp);
            chats.add(chat);
        }
        return chats;
    }
}
