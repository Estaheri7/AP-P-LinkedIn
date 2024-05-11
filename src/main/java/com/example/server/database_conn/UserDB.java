package com.example.server.database_conn;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDB {
    private Connection conn;

    public UserDB() throws SQLException {
        this.conn = SQLConnection.getConnection();
    }
}
