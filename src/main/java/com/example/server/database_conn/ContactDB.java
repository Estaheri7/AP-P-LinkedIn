package com.example.server.database_conn;

import com.example.server.models.Contact;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE"
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
        String query = "UPDATE contact"
                + "SET view_link = ?, phone_number = ?, address = ?, birth_date = ?, fast_connect = ? WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, contact.getViewLink());
        preparedStatement.setString(2, contact.getPhoneNumber());
        preparedStatement.setString(3, contact.getAddress());
        preparedStatement.setDate(4, (Date) contact.getBirthDate());
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
}
