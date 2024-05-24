package com.example.server.database_conn;

import com.example.server.models.User;

import java.sql.*;
import java.util.ArrayList;

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
        String query = "UPDATE users SET password = ?, name = ?, lastName = ?, additionalName = ?, avatar_url = ?, background_url = ?, headline = ?, country = ?, city = ? WHERE email = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getLastName());
        preparedStatement.setString(4, user.getAdditionalName());
        preparedStatement.setString(5, user.getAvatar_url());
        preparedStatement.setString(6, user.getBackground_url());
        preparedStatement.setString(7, user.getHeadline());
        preparedStatement.setString(8, user.getCountry());
        preparedStatement.setString(9, user.getCity());
        preparedStatement.setString(10, user.getEmail());
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

    public User getUser(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String userEmail = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("name");
            String lastName = resultSet.getString("lastName");
            String additionalName = resultSet.getString("additionalName");
            String avatarUrl = resultSet.getString("avatar_url");
            String backgroundUrl = resultSet.getString("background_url");
            String headline = resultSet.getString("headline");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            int followers = resultSet.getInt("followers");
            int followings = resultSet.getInt("followings");
            int connections = resultSet.getInt("connections");

            return new User(id, userEmail, password, firstName, lastName, additionalName, avatarUrl,
                    backgroundUrl, headline, country, city, followers, followings, connections);
        }

        return null;
    }

    public ArrayList<User> getAllUsers() throws SQLException {
        String query = "SELECT * FROM users";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String userEmail = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("name");
            String lastName = resultSet.getString("lastName");
            String additionalName = resultSet.getString("additionalName");
            String avatarUrl = resultSet.getString("avatar_url");
            String backgroundUrl = resultSet.getString("background_url");
            String headline = resultSet.getString("headline");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            int followers = resultSet.getInt("followers");
            int followings = resultSet.getInt("followings");
            int connections = resultSet.getInt("connections");

            User user = new User(id, userEmail, password, firstName, lastName, additionalName,
                    avatarUrl, backgroundUrl, headline, country, city, followers, followings, connections);

            users.add(user);
        }

        return users;
    }
}
