package com.example.server.database_conn;


import com.example.server.models.Education;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class EducationDB extends BaseDB {

    public EducationDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS education ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) UNIQUE NOT NULL,"
                + "school_name VARCHAR(40) NOT NULL,"
                + "field VARCHAR(40) NOT NULL,"
                + "grade FLOAT NOT NULL,"
                + "start_date DATE NOT NULL,"
                + "end_date DATE,"
                + "community VARCHAR(500),"
                + "description VARCHAR(1000),"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Education education) throws SQLException {
        String query = "INSERT INTO education (email, school_name, field, grade, start_date, end_date, community, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, education.getEmail());
        preparedStatement.setString(2, education.getSchoolName());
        preparedStatement.setString(3, education.getField());
        preparedStatement.setDouble(4, education.getGrade());
        preparedStatement.setDate(5, (Date) education.getStartDate());
        preparedStatement.setDate(6, (Date) education.getEndDate());
        preparedStatement.setString(7, education.getCommunity());
        preparedStatement.setString(8, education.getDescription());
        preparedStatement.executeUpdate();
    }

    public void updateData(Education education) throws SQLException {
        String query = "UPDATE education "
                + "SET school_name = ?, field = ?, grade = ?, start_date = ?, end_date = ?, community = ?, description = ?"
                + "WHERE email = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, education.getSchoolName());
        preparedStatement.setString(2, education.getField());
        preparedStatement.setDouble(3, education.getGrade());
        preparedStatement.setDate(4, (Date) education.getStartDate());
        preparedStatement.setDate(5, (Date) education.getEndDate());
        preparedStatement.setString(6, education.getCommunity());
        preparedStatement.setString(7, education.getDescription());
        preparedStatement.setString(8, education.getEmail());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM education WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM education";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
