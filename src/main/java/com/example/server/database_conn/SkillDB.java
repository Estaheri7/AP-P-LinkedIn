package com.example.server.database_conn;

import com.example.server.models.Skill;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SkillDB extends BaseDB {

    public SkillDB() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS skills ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) UNIQUE NOT NULL,"
                + "skill_1 VARCHAR(40),"
                + "skill_2 VARCHAR(40),"
                + "skill_3 VARCHAR(40),"
                + "skill_4 VARCHAR(40),"
                + "skill_5 VARCHAR(40),"
                + "FOREIGN KEY (email) REFERENCES users(email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void insertData(Skill skill) throws SQLException {
        String query = "INSERT INTO skills (email, skill_1, skill_2, skill_3, skill_4, skill_5) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, skill.getEmail());
        preparedStatement.setString(2, skill.getSkill1());
        preparedStatement.setString(3, skill.getSkill2());
        preparedStatement.setString(4, skill.getSkill3());
        preparedStatement.setString(5, skill.getSkill4());
        preparedStatement.setString(6, skill.getSkill5());
        preparedStatement.executeUpdate();
    }

    public void updateData(Skill skill) throws SQLException {
        String query = "UPDATE skills SET skill_1 = ?, skill_2 = ?, skill_3 = ?, skill_4 = ?, skill_5 = ? WHERE email = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, skill.getSkill1());
        preparedStatement.setString(2, skill.getSkill2());
        preparedStatement.setString(3, skill.getSkill3());
        preparedStatement.setString(4, skill.getSkill4());
        preparedStatement.setString(5, skill.getSkill5());
        preparedStatement.setString(6, skill.getEmail());
        preparedStatement.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String query = "DELETE FROM skills WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAllData() throws SQLException {
        String query = "DELETE FROM skills";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
