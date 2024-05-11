package com.example.server.database_conn;

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
}
