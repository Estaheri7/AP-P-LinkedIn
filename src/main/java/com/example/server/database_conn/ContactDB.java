package com.example.server.database_conn;

import com.example.server.models.Contact;
import com.example.server.models.Education;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDB extends BaseDB {

    public ContactDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS contact ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) UNIQUE NOT NULL,"
                + "view_link VARCHAR(40) NOT NULL,"
                + "phone_number VARCHAR(40) NOT NULL,"
                + "address VARCHAR(220),"
                + "birth_date DATE,"
                + "fast_connect VARCHAR(40),"
                + "visibility VARCHAR(40) NOT NULL DEFAULT 'private',"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Contact contact) throws SQLException {
        String query = "INSERT INTO contact (email, view_link, phone_number, address, birth_date, fast_connect)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, contact.getEmail());
        preparedStatement.setString(2, contact.getViewLink());
        preparedStatement.setString(3, contact.getPhoneNumber());
        preparedStatement.setString(4, contact.getAddress());
        preparedStatement.setDate(5, (Date) contact.getBirthDate());
        preparedStatement.setString(6, contact.getFastConnect());
        preparedStatement.executeUpdate();
    }

    public void updateData(Contact contact) throws SQLException {
        String query = "UPDATE contact "
                + "SET view_link = ?, phone_number = ?, address = ?, birth_date = ?, fast_connect = ? WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, contact.getViewLink());
        preparedStatement.setString(2, contact.getPhoneNumber());
        preparedStatement.setString(3, contact.getAddress());
        if (contact.getBirthDate() != null) {
            preparedStatement.setDate(4, new java.sql.Date(contact.getBirthDate().getTime()));
        } else {
            preparedStatement.setDate(4, null);
        }
        preparedStatement.setString(5, contact.getFastConnect());
        preparedStatement.setString(6, contact.getEmail());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM contact WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM contact";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    public Contact getContact(String email, String viewerEmail) throws SQLException {
        String query = "SELECT * FROM contact WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String viewLink = resultSet.getString("view_link");
            String phoneNumber = resultSet.getString("phone_number");
            String address = resultSet.getString("address");
            Date birthDate = resultSet.getDate("birth_date");
            String fastConnect = resultSet.getString("fast_connect");
            String visibility = resultSet.getString("visibility");
            if (visibility.equals(Contact.PRIVATE) && !email.equals(viewerEmail)) {
                birthDate = null;
            }

            return new Contact(id, email, viewLink, phoneNumber, address, birthDate, fastConnect, visibility);
        }

        return null;
    }

    public void changeVisibility(String email, String visibility) throws SQLException {
        String query = "UPDATE contact SET visibility = ? WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, visibility);
        preparedStatement.setString(2, email);
        preparedStatement.executeUpdate();
    }
}
