package com.example.server.database_conn;

import com.example.server.models.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectionDB extends BaseDB {

    public ConnectionDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS connections ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "sender VARCHAR(255) NOT NULL,"
                + "receiver VARCHAR(255) NOT NULL,"
                + "notes VARCHAR(500),"
                + "commited BOOLEAN DEFAULT FALSE,"
                + "FOREIGN KEY (sender) REFERENCES users(email) ON DELETE CASCADE,"
                + "FOREIGN KEY (receiver) REFERENCES users(email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Connection connection) throws SQLException {
        String query = "INSERT INTO connections(sender, receiver, notes) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, connection.getSender());
        preparedStatement.setString(2, connection.getReceiver());
        preparedStatement.setString(3, connection.getNotes());
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

    public Connection getConnection(String sender, String receiver) throws SQLException {
        String query = "SELECT * FROM connections WHERE sender = ? AND receiver = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, sender);
        preparedStatement.setString(2, receiver);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            boolean commited = resultSet.getBoolean("commited");
            String notes = resultSet.getString("notes");
            return new Connection(id, sender, receiver, commited, notes);
        }

        return null;
    }

    public boolean connectionExists(String sender, String receiver) throws SQLException {
        String query = "SELECT * FROM connections WHERE sender = ? AND receiver = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, sender);
        preparedStatement.setString(2, receiver);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public ArrayList<Connection> getSenderNotification(String sender) throws SQLException {
        String query = "SELECT * FROM connections WHERE sender = ? AND commited = FALSE";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, sender);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Connection> connections = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String receiver = resultSet.getString("receiver");
            boolean commited = resultSet.getBoolean("commited");
            String notes = resultSet.getString("notes");
            Connection connection = new Connection(id, sender, receiver, commited, notes);
            connections.add(connection);
        }

        return connections;
    }

    public ArrayList<Connection> getReceiverNotification(String receiver) throws SQLException {
        String query = "SELECT * FROM connections WHERE receiver = ? AND commited = FALSE";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, receiver);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Connection> connections = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String sender = resultSet.getString("sender");
            boolean commited = resultSet.getBoolean("commited");
            String notes = resultSet.getString("notes");
            Connection connection = new Connection(id, sender, receiver, commited, notes);
            connections.add(connection);
        }

        return connections;
    }

    public ArrayList<Connection> getConnections(String email) throws SQLException {
        String query = "SELECT * FROM connections WHERE receiver = ? AND commited = TRUE";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Connection> connections = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String sender = resultSet.getString("sender");
            boolean commited = resultSet.getBoolean("commited");
            String notes = resultSet.getString("notes");
            Connection connection = new Connection(id, sender, email, commited, notes);
            connections.add(connection);
        }

        return connections;
    }

    public boolean getConnectionByPending(String sender, String receiver) throws SQLException {
        String query = "SELECT * FROM connections WHERE sender = ? AND receiver = ? AND commited = FALSE";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, sender);
        preparedStatement.setString(2, receiver);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public boolean isConnectionAccepted(String sender, String receiver) throws SQLException {
        String query = "SELECT * FROM connections WHERE sender = ? AND receiver = ? AND commited = TRUE";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, sender);
        preparedStatement.setString(2, receiver);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public void acceptConnection(String sender, String receiver) throws SQLException {
        String query = "UPDATE connections SET commited = TRUE WHERE sender = ? AND receiver = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, sender);
        preparedStatement.setString(2, receiver);
        preparedStatement.executeUpdate();
    }

}
