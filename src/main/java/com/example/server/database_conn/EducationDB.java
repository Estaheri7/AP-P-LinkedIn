package com.example.server.database_conn;


import java.sql.SQLException;
import java.sql.Statement;

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
                + "start_date DATE NOT NULL,"
                + "end_date DATE,"
                + "community VARCHAR(500),"
                + "description VARCHAR(1000),"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE"
                + ");";

        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }
}
