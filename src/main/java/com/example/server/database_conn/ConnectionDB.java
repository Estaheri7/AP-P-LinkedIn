package com.example.server.database_conn;

import com.example.server.models.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB extends BaseDB {

    public ConnectionDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS connections ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "sender VARCHAR(255) NOT NULL,"
                + "receiver VARCHAR(255) NOT NULL,"
                + "commited BOOLEAN DEFAULT FALSE,"
                + "FOREIGN KEY (sender) REFERENCES users(email) ON DELETE CASCADE,"
                + "FOREIGN KEY (receiver) REFERENCES users(email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Connection connection) throws SQLException {
        String query = "INSERT INTO connections(sender, receiver) VALUES (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, connection.getSender());
        preparedStatement.setString(2, connection.getReceiver());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM connections WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM connections";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
