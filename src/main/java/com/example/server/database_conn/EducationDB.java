package com.example.server.database_conn;


import com.example.server.models.Education;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EducationDB extends BaseDB {

    public EducationDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS education ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) NOT NULL,"
                + "school_name VARCHAR(40) NOT NULL,"
                + "field VARCHAR(40) NOT NULL,"
                + "grade FLOAT NOT NULL,"
                + "start_date DATE,"
                + "end_date DATE,"
                + "community VARCHAR(500),"
                + "description VARCHAR(1000),"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE"
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
        if (education.getStartDate() != null) {
            preparedStatement.setDate(5, new java.sql.Date(education.getStartDate().getTime()));
        } else {
            preparedStatement.setDate(5, null);
        }
        if (education.getEndDate() != null) {
            preparedStatement.setDate(6, new java.sql.Date(education.getEndDate().getTime()));
        } else {
            preparedStatement.setDate(6, null);
        }
        preparedStatement.setString(7, education.getCommunity());
        preparedStatement.setString(8, education.getDescription());
        preparedStatement.executeUpdate();
    }

    public void updateData(Education education) throws SQLException {
        String query = "UPDATE education "
                + "SET school_name = ?, field = ?, grade = ?, start_date = ?, end_date = ?, community = ?, description = ? "
                + "WHERE id = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, education.getSchoolName());
        preparedStatement.setString(2, education.getField());
        preparedStatement.setDouble(3, education.getGrade());
        if (education.getStartDate() != null) {
            preparedStatement.setDate(4, new java.sql.Date(education.getStartDate().getTime()));
        } else {
            preparedStatement.setDate(4, null);
        }
        if (education.getEndDate() != null) {
            preparedStatement.setDate(5, new java.sql.Date(education.getEndDate().getTime()));
        } else {
            preparedStatement.setDate(5, null);
        }
        preparedStatement.setString(6, education.getCommunity());
        preparedStatement.setString(7, education.getDescription());
        preparedStatement.setInt(8, education.getId());
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
    public Education getEducation(String email) throws SQLException {
        String query = "SELECT * FROM education WHERE email = ? ORDER BY id DESC LIMIT 1;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String schoolName = resultSet.getString("school_name");
            String field = resultSet.getString("field");
            double grade = resultSet.getDouble("grade");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            String community = resultSet.getString("community");
            String description = resultSet.getString("description");

            return new Education(id, email, schoolName, field, grade, startDate, endDate, community, description);
        }

        return null;
    }

    public ArrayList<Education> getAllEducations(String email) throws SQLException {
        String query = "SELECT * FROM education WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Education> educations = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String schoolName = resultSet.getString("school_name");
            String field = resultSet.getString("field");
            double grade = resultSet.getDouble("grade");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            String community = resultSet.getString("community");
            String description = resultSet.getString("description");

            Education education = new Education(id, email, schoolName, field, grade, startDate, endDate, community, description);
            educations.add(education);
        }

        return educations;
    }

    public ArrayList<Education> getEducationBySchoolName(String schoolName) throws SQLException {
        String query = "SELECT * FROM education WHERE school_name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, schoolName);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Education> educations = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String field = resultSet.getString("field");
            float grade = resultSet.getFloat("grade");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            String community = resultSet.getString("community");
            String description = resultSet.getString("description");
            Education education = new Education(id, email, schoolName, field, grade, startDate, endDate, community, description);
            educations.add(education);
        }

        return educations;
    }

    public ArrayList<Education> getEducationByField(String field) throws SQLException {
        String query = "SELECT * FROM education WHERE field = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, field);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Education> educations = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String schoolName = resultSet.getString("school_name");
            float grade = resultSet.getFloat("grade");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            String community = resultSet.getString("community");
            String description = resultSet.getString("description");
            Education education = new Education(id, email, schoolName, field, grade, startDate, endDate, community, description);
            educations.add(education);
        }

        return educations;
    }

    public ArrayList<Education> getEducationByCommunity(String community) throws SQLException {
        String query = "SELECT * FROM education WHERE community = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, community);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Education> educations = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String schoolName = resultSet.getString("school_name");
            String field = resultSet.getString("field");
            float grade = resultSet.getFloat("grade");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            String description = resultSet.getString("description");
            Education education = new Education(id, email, schoolName, field, grade, startDate, endDate, community, description);
            educations.add(education);
        }

        return educations;
    }

}
