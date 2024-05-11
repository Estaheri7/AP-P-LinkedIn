package com.example.server.database_conn;

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
}
