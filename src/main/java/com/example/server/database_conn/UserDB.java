package com.example.server.database_conn;

import com.example.server.models.User;

import java.sql.*;

public class UserDB extends BaseDB {

    public UserDB() throws SQLException {
    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) UNIQUE NOT NULL,"
                + "password VARCHAR(255) NOT NULL,"
                + "name VARCHAR(20) NOT NULL,"
                + "lastName VARCHAR(40) NOT NULL,"
                + "additionalName VARCHAR(40),"
                + "avatar_url VARCHAR(255),"
                + "background_url VARCHAR(255),"
                + "headline VARCHAR(220),"
                + "country VARCHAR(60),"
                + "city VARCHAR(60),"
                + "followers INT NOT NULL DEFAULT 0,"
                + "followings INT NOT NULL DEFAULT 0,"
                + "connections INT NOT NULL DEFAULT 0"
                + ")";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(User user) throws SQLException {
        String query = "INSERT INTO users (email, password, name, lastName, additionalName, avatar_url, background_url, headline, country, city)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getAdditionalName());
        preparedStatement.setString(6, user.getAvatar_url());
        preparedStatement.setString(7, user.getBackground_url());
        preparedStatement.setString(8, user.getHeadline());
        preparedStatement.setString(9, user.getCountry());
        preparedStatement.setString(10, user.getCity());
        preparedStatement.executeUpdate();
    }

    public void updateData(User user) throws SQLException {
        String query = "UPDATE users SET email = ?, password = ?, name = ?, lastName = ?, additionalName = ?, avatar_url = ?, background_url = ?, headline = ?, country = ?, city = ? WHERE id = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getAdditionalName());
        preparedStatement.setString(6, user.getAvatar_url());
        preparedStatement.setString(7, user.getBackground_url());
        preparedStatement.setString(8, user.getHeadline());
        preparedStatement.setString(9, user.getCountry());
        preparedStatement.setString(10, user.getCity());
        preparedStatement.setInt(11, user.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM users";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
